package net.kognition.inscribed.impl.util.codecs;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.BaseMapCodec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static net.kognition.inscribed.impl.Inscribed.LOGGER;
import static net.kognition.inscribed.impl.Inscribed.isDev;

/**
 * Key and value decoded independently, unknown set of keys. Duplicated keys do not cause failure, every duplicate key after the first is dropped.
 * @author TheEnderCore
 */
public record DeduplicationMapCodec<K, V>(
        Codec<K> keyCodec,
        Codec<V> elementCodec
) implements BaseMapCodec<K, V>, Codec<Map<K, V>> {
    @Override
    public <T> DataResult<Pair<Map<K, V>, T>> decode(final DynamicOps<T> ops, final T input) {
        return ops.getMap(input).setLifecycle(Lifecycle.stable()).flatMap(map -> decode(ops, map)).map(r -> Pair.of(r, input));
    }

    @Override
    public <T> DataResult<T> encode(final Map<K, V> input, final DynamicOps<T> ops, final T prefix) {
        return encode(input, ops, ops.mapBuilder()).build(prefix);
    }

    @Override
    public <T> DataResult<Map<K, V>> decode(final DynamicOps<T> ops, final MapLike<T> input) {
        final Object2ObjectMap<K, V> read = new Object2ObjectArrayMap<>();
        final Stream.Builder<Pair<T, T>> failed = Stream.builder();

        final DataResult<Unit> result = input.entries().reduce(
                DataResult.success(Unit.INSTANCE, Lifecycle.stable()),
                (r, pair) -> {
                    T data = pair.getFirst();
                    final DataResult<K> key = keyCodec().parse(ops, data);
                    final DataResult<V> value = elementCodec().parse(ops, pair.getSecond());

                    final DataResult<Pair<K, V>> entryResult = key.apply2stable(Pair::of, value);
                    final Optional<Pair<K, V>> entry = entryResult.resultOrPartial();
                    if (entry.isPresent()) {
                        final V existingValue = read.putIfAbsent(entry.get().getFirst(), entry.get().getSecond());
                        if (existingValue != null) {
                            failed.add(pair);
                            if (isDev()) LOGGER.warn("Duplicate entry for key: {}, {}", entry.get().getFirst(), data);
                        }
                    }
                    if (entryResult.isError()) {
                        failed.add(pair);
                    }

                    return r.apply2stable((u, p) -> u, entryResult);
                },
                (r1, r2) -> r1.apply2stable((u1, u2) -> u1, r2)
        );

        final Map<K, V> elements = ImmutableMap.copyOf(read);
        final T errors = ops.createMap(failed.build());

        return result.map(_ -> elements).setPartial(elements).mapError(e -> e + " missed input: " + errors);
    }

    @Override
    public String toString() {
        return "DeduplicationMapCodec[" + keyCodec + " -> " + elementCodec + ']';
    }
}
