package net.kognition.inscribed.impl.util.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

/**
 * @author AcoYT
 */
public enum PearlPlacement {
    TOP(0, 119, 5, 16, 16),
    LEFT(1, 90, 34, 16, 16),
    BOTTOM(2, 119, 63, 16, 16),
    RIGHT(3, 148, 34, 16, 16),
    BIG(4, 111, 26, 32, 32);

    private final int id;
    private final int[] location;
    private final int[] size;

    private static final IntFunction<PearlPlacement> BY_ID = ByIdMap.continuous(e -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, PearlPlacement> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, e -> e.id);

    PearlPlacement(int id, int x, int y, int width, int height) {
        this.id = id;
        this.location = new int[]{x, y};
        this.size = new int[]{width, height};
    }

    public int[] getLocation() {
        return location;
    }

    public int[] getSize() {
        return size;
    }

    public boolean isHovered(int leftPos, int topPos, double mouseX, double mouseY) {
        int left = leftPos + location[0];
        int right = leftPos + location[0] + size[0];
        int top = topPos + location[1];
        int bottom = topPos + location[1] + size[1];

        return mouseX >= left && mouseX <= right && mouseY <= bottom && mouseY >= top;
    }
}
