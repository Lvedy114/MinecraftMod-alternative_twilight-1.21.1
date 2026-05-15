package com.lvedy.at_mod.client.sound;

import com.lvedy.at_mod.AlternativeTwilight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用 Boss 背景音乐管理器。
 * <p>
 * 模块只需实例化一个 {@link BossMusicTracker} 并调用 {@link #register(BossMusicTracker)}，
 * 本类会按统一节奏驱动所有 tracker 的检测和启停。
 */
@EventBusSubscriber(modid = AlternativeTwilight.MODID, value = Dist.CLIENT)
public class BossMusicManager {
    private static final List<BossMusicTracker<?>> TRACKERS = new ArrayList<>();
    private static final int CHECK_INTERVAL_TICKS = 10;
    private static int checkCooldown = 0;

    public static synchronized void register(BossMusicTracker<?> tracker) {
        for (BossMusicTracker<?> existing : TRACKERS) {
            if (existing.id().equals(tracker.id())) return;
        }
        TRACKERS.add(tracker);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof LocalPlayer player)) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != player) return;

        if (checkCooldown > 0) {
            checkCooldown--;
            return;
        }
        checkCooldown = CHECK_INTERVAL_TICKS;

        for (BossMusicTracker<?> tracker : TRACKERS) {
            tracker.update(mc, player);
        }
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        Minecraft mc = Minecraft.getInstance();
        for (BossMusicTracker<?> tracker : TRACKERS) {
            tracker.stop(mc);
        }
    }
}
