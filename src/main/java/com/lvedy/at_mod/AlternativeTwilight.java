package com.lvedy.at_mod;

import com.lvedy.at_mod.config.Config;
import com.lvedy.at_mod.register.ModBlock;
import com.lvedy.at_mod.register.ModEffects;
import com.lvedy.at_mod.register.ModEntityTypes;
import com.lvedy.at_mod.register.ModItem;
import com.lvedy.at_mod.register.ModSounds;
import com.lvedy.at_mod.register.ModTabs;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.Locale;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AlternativeTwilight.MODID)
public class AlternativeTwilight {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "at_mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AlternativeTwilight(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        ModBlock.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ModItem.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ModTabs.register(modEventBus);
        // Register the Deferred Register to the mod event bus so entity types get registered
        ModEntityTypes.register(modEventBus);
        // Register the Deferred Register to the mod event bus so sound events get registered
        ModSounds.register(modEventBus);
        // Register the Deferred Register to the mod event bus so mob effects get registered
        ModEffects.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (AlternativeTwilight) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        //modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC, "at_mod_config.toml");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
