package com.lvedy.at_mod.register;

import com.lvedy.at_mod.AlternativeTwilight;
import com.lvedy.at_mod.config.Config;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, AlternativeTwilight.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> BLOOD_LUST =
            EFFECTS.register("blood_lust", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xB30000) {}
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            AlternativeTwilight.prefix("effect.blood_lust.attack"),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            level -> readConfig(Config.BLOOD_LUST_ATTACK_PER_LEVEL::getAsDouble) * (level + 1))
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            AlternativeTwilight.prefix("effect.blood_lust.speed"),
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL,
                            level -> readConfig(Config.BLOOD_LUST_SPEED_PER_LEVEL::getAsDouble) * (level + 1)));

    public static final DeferredHolder<MobEffect, MobEffect> BLOOD_LUST_CURSE =
            EFFECTS.register("blood_lust_curse", () -> new MobEffect(MobEffectCategory.HARMFUL, 0x4A0000) {});

    /**
     * 注册阶段 NeoForge 会调一次 curve.apply(0) 来填 AttributeTemplate.amount，
     * 此时 ModConfig 尚未加载，直接读 Config 会抛 IllegalStateException。
     * 这个回填值对 curve 模式无实际影响（create(level) 时会重新调 curve），所以未加载时返回 0 即可。
     */
    private static double readConfig(java.util.function.DoubleSupplier supplier) {
        try {
            return supplier.getAsDouble();
        } catch (IllegalStateException notLoadedYet) {
            return 0.0D;
        }
    }

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}


