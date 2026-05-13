package com.lvedy.at_mod.register;

import com.lvedy.at_mod.AlternativeTwilight;
import com.lvedy.at_mod.special.ModEntity.entity.NagaVenomEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, AlternativeTwilight.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<NagaVenomEntity>> NAGA_VENOM =
            ENTITY_TYPES.register("naga_venom", () -> EntityType.Builder.<NagaVenomEntity>of(NagaVenomEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(10)
                    .build(AlternativeTwilight.prefix("naga_venom").toString()));

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
