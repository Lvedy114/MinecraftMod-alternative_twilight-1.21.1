package com.lvedy.at_mod.client.sound;

import com.lvedy.at_mod.config.Config;
import com.lvedy.at_mod.register.ModSounds;
import twilightforest.entity.boss.Minoshroom;

public final class MinoshroomBossMusic {
    private MinoshroomBossMusic() {}

    public static void register() {
        BossMusicTracker<Minoshroom> tracker = new BossMusicTracker<>(
                "minoshroom",
                Minoshroom.class,
                () -> ModSounds.MINOSHROOM_MUSIC.get(),
                () -> Config.MINOSHROOM_MUSIC_ENABLED.get(),
                () -> Config.MINOSHROOM_MUSIC_DETECTION_RADIUS.getAsDouble(),
                () -> Config.MINOSHROOM_MUSIC_VOLUME.getAsDouble()
        );
        BossMusicManager.register(tracker);
    }
}
