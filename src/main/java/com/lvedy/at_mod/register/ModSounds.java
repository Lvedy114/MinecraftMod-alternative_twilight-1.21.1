package com.lvedy.at_mod.register;

import com.lvedy.at_mod.AlternativeTwilight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, AlternativeTwilight.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> NAGA_MUSIC =
            SOUND_EVENTS.register("music.naga",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.naga")));

    public static final DeferredHolder<SoundEvent, SoundEvent> LICH_MUSIC =
            SOUND_EVENTS.register("music.lich",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.lich")));

    public static final DeferredHolder<SoundEvent, SoundEvent> HYDRA_MUSIC =
            SOUND_EVENTS.register("music.hydra",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.hydra")));

    public static final DeferredHolder<SoundEvent, SoundEvent> UR_GHAST_MUSIC =
            SOUND_EVENTS.register("music.ur_ghast",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.ur_ghast")));

    public static final DeferredHolder<SoundEvent, SoundEvent> KNIGHT_PHANTOM_MUSIC =
            SOUND_EVENTS.register("music.knight_phantom",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.knight_phantom")));

    public static final DeferredHolder<SoundEvent, SoundEvent> SNOW_QUEEN_MUSIC =
            SOUND_EVENTS.register("music.snow_queen",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.snow_queen")));

    public static final DeferredHolder<SoundEvent, SoundEvent> MINOSHROOM_MUSIC =
            SOUND_EVENTS.register("music.minoshroom",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.minoshroom")));

    public static final DeferredHolder<SoundEvent, SoundEvent> ALPHA_YETI_MUSIC =
            SOUND_EVENTS.register("music.alpha_yeti",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(AlternativeTwilight.MODID, "music.alpha_yeti")));

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
