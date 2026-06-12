package com.lvedy.at_mod;

import com.lvedy.at_mod.special.ModEntity.client.NagaVenomRenderer;
import com.lvedy.at_mod.register.ModEntityTypes;
import com.lvedy.at_mod.special.ModItems.SpearItem;
import com.lvedy.at_mod.client.sound.NagaBossMusic;
import com.lvedy.at_mod.client.sound.LichBossMusic;
import com.lvedy.at_mod.client.sound.HydraBossMusic;
import com.lvedy.at_mod.client.sound.UrGhastBossMusic;
import com.lvedy.at_mod.client.sound.KnightPhantomBossMusic;
import com.lvedy.at_mod.client.sound.SnowQueenBossMusic;
import com.lvedy.at_mod.client.sound.MinoshroomBossMusic;
import com.lvedy.at_mod.client.sound.AlphaYetiBossMusic;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = AlternativeTwilight.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class AlternativeTwilightClient {
    public AlternativeTwilightClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        SpearItem.ClientExtensions.spearAnim(event);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.NAGA_VENOM.get(), NagaVenomRenderer::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        AlternativeTwilight.LOGGER.info("HELLO FROM CLIENT SETUP");
        AlternativeTwilight.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

        // 注册 Boss 背景音乐
        event.enqueueWork(NagaBossMusic::register);
        event.enqueueWork(LichBossMusic::register);
        event.enqueueWork(HydraBossMusic::register);
        event.enqueueWork(UrGhastBossMusic::register);
        event.enqueueWork(KnightPhantomBossMusic::register);
        event.enqueueWork(SnowQueenBossMusic::register);
        event.enqueueWork(MinoshroomBossMusic::register);
        event.enqueueWork(AlphaYetiBossMusic::register);
    }
}
