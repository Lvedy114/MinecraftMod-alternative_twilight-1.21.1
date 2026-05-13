package com.lvedy.at_mod.register;

import com.lvedy.at_mod.special.ModItems.SpearItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import twilightforest.util.TFToolMaterials;

import static com.lvedy.at_mod.AlternativeTwilight.MODID;

public class ModItem {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<Item> WOODEN_SPEAR = ITEMS.registerItem(
            "spear/wooden_spear", props -> new SpearItem(Tiers.WOOD, new Item.Properties().attributes(SpearItem.createAttributes(2, -2.4, 1)),2F));
            
    public static final DeferredItem<Item> STONE_SPEAR = ITEMS.registerItem(
            "spear/stone_spear", props -> new SpearItem(Tiers.STONE, new Item.Properties().attributes(SpearItem.createAttributes(3, -2.8, 1.1)), 1.8F));

    public static final DeferredItem<Item> COPPER_SPEAR = ITEMS.registerItem(
            "spear/copper_spear", props -> new SpearItem(ModTiers.COPPER, new Item.Properties().attributes(SpearItem.createAttributes(3, -2.8, 1.1)), 1.7F));

    public static final DeferredItem<Item> IRON_SPEAR = ITEMS.registerItem(
            "spear/iron_spear", props -> new SpearItem(Tiers.IRON, new Item.Properties().attributes(SpearItem.createAttributes(4, -3, 1.2)),1.6F));

    public static final DeferredItem<Item> GOLDEN_SPEAR = ITEMS.registerItem(
            "spear/golden_spear", props -> new SpearItem(ModTiers.GOLDEN_SPEAR, new Item.Properties().attributes(SpearItem.createAttributes(2, -2.2, 1.0)), 2.1F));
            
    public static final DeferredItem<Item> DIAMOND_SPEAR = ITEMS.registerItem(
            "spear/diamond_spear", props -> new SpearItem(Tiers.DIAMOND, new Item.Properties().attributes(SpearItem.createAttributes(5, -2.8, 1.2)), 1.75F));
            
    public static final DeferredItem<Item> NETHERITE_SPEAR = ITEMS.registerItem(
            "spear/netherite_spear", props -> new SpearItem(Tiers.NETHERITE, new Item.Properties().attributes(SpearItem.createAttributes(6, -2.8, 1.3)), 1.8F));

    public static final DeferredItem<Item> IRONWOOD_SPEAR = ITEMS.registerItem(
            "spear/ironwood_spear", props -> new SpearItem(TFToolMaterials.IRONWOOD, new Item.Properties().attributes(SpearItem.createAttributes(4, -2.4, 1.1)), 2F));

    public static final DeferredItem<Item> KNIGHT_SPEAR = ITEMS.registerItem(
            "spear/knight_spear", props -> new SpearItem(TFToolMaterials.KNIGHTMETAL, new Item.Properties().attributes(SpearItem.createAttributes(6, -2.4, 1.3)), 2.1F));

    //block_item
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", ModBlock.EXAMPLE_BLOCK);

    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }

}