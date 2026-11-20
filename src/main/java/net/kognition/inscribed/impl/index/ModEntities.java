package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.entity.SpikeProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

/**
 * @author TheEnderCore
 */
public interface ModEntities {
    EntityTypeRegistrant ENTITIES = new EntityTypeRegistrant(Inscribed.MOD_ID);

    EntityType<SpikeProjectile> SPIKE = ENTITIES.register("spike",
            EntityType.Builder.<SpikeProjectile>of(SpikeProjectile::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.45F, 0.45F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
    );

    static void init() {}
}
