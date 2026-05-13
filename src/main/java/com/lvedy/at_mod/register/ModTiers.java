package com.lvedy.at_mod.register;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final SimpleTier COPPER = new SimpleTier(
            BlockTags.INCORRECT_FOR_STONE_TOOL, // Copper uses stone mining level
            200, // Durability
            5.0f, // Mining speed
            1.5f, // Attack damage bonus
            14, // Enchantability
            () -> Ingredient.of(Items.COPPER_INGOT) // Repair ingredient
    );

    // 金长矛的专属等级（耐久度+100）
    public static final SimpleTier GOLDEN_SPEAR = new SimpleTier(
            Tiers.GOLD.getIncorrectBlocksForDrops(),
            Tiers.GOLD.getUses() + 100, // 32 + 100 = 132
            Tiers.GOLD.getSpeed(),
            Tiers.GOLD.getAttackDamageBonus(),
            Tiers.GOLD.getEnchantmentValue(),
            Tiers.GOLD::getRepairIngredient
    );
}
