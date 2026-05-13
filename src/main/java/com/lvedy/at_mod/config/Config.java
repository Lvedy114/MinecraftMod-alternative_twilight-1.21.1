package com.lvedy.at_mod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // 娜迦基础属性
    public static final ModConfigSpec.DoubleValue MAX_HEALTH = BUILDER
            .translation("config.at_mod.naga.maxHealth")
            .comment("Naga max health")
            .defineInRange("naga.maxHealth", 210.0, 1.0, 10000.0);

    public static final ModConfigSpec.DoubleValue NON_DAZED_DAMAGE_MULTIPLIER = BUILDER
            .translation("config.at_mod.naga.nonDazedDamageMultiplier")
            .comment("Damage multiplier when Naga is NOT dazed (< 1 = damage reduction)")
            .defineInRange("naga.nonDazedDamageMultiplier", 0.5, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue DAZED_DAMAGE_MULTIPLIER = BUILDER
            .translation("config.at_mod.naga.dazedDamageMultiplier")
            .comment("Damage multiplier when Naga IS dazed (> 1 = damage increase)")
            .defineInRange("naga.dazedDamageMultiplier", 1.5, 0.0, 10.0);

    public static final ModConfigSpec.DoubleValue DAZE_SPEED_THRESHOLD = BUILDER
            .translation("config.at_mod.naga.dazeSpeedThreshold")
            .comment("Relative speed required between attacker and Naga to trigger daze")
            .defineInRange("naga.dazeSpeedThreshold", 2.0, 0.1, 20.0);

    // 阶段血量阈值
    public static final ModConfigSpec.DoubleValue PHASE2_HEALTH_RATIO = BUILDER
            .translation("config.at_mod.naga.phase2HealthRatio")
            .comment("Phase 2 health threshold ratio (enters phase 2 when below this ratio of max health)")
            .defineInRange("naga.phase2HealthRatio", 0.667, 0.0, 1.0);

    public static final ModConfigSpec.DoubleValue PHASE3_HEALTH_RATIO = BUILDER
            .translation("config.at_mod.naga.phase3HealthRatio")
            .comment("Phase 3 health threshold ratio (enters phase 3 when below this ratio of max health)")
            .defineInRange("naga.phase3HealthRatio", 0.333, 0.0, 1.0);

    // 毒液配置
    public static final ModConfigSpec.DoubleValue VENOM_DAMAGE = BUILDER
            .translation("config.at_mod.naga.venom.count")
            .comment("Venom's damage")
            .defineInRange("naga.venom.damage", 2D, 0, 200000);

    public static final ModConfigSpec.IntValue VENOM_COUNT = BUILDER
            .translation("config.at_mod.naga.venom.count")
            .comment("Number of venom projectiles fired per charge")
            .defineInRange("naga.venom.count", 3, 1, 20);

    public static final ModConfigSpec.DoubleValue VENOM_SPREAD = BUILDER
            .translation("config.at_mod.naga.venom.spread")
            .comment("Venom spread range around target (blocks)")
            .defineInRange("naga.venom.spread", 8.0, 0.0, 50.0);

    public static final ModConfigSpec.IntValue VENOM_LINGER_TICKS = BUILDER
            .translation("config.at_mod.naga.venom.lingerTicks")
            .comment("Venom lingering duration after landing (ticks, 20 ticks = 1 second)")
            .defineInRange("naga.venom.lingerTicks", 160, 20, 1200);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
