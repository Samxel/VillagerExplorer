package com.samxel.villagerexplorer.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Util;

final class Motion {
    private static final long MAX_FRAME_MILLIS = 100L;

    private long lastFrame = -1L;
    private float delta;

    void frame() {
        long now = Util.getMillis();
        this.delta = this.lastFrame < 0 ? 0.0F : Math.min(now - this.lastFrame, MAX_FRAME_MILLIS);
        this.lastFrame = now;
    }

    // frame-rate independent exponential approach, millis is roughly the time to cover two thirds of the distance
    float approach(float current, float target, float millis) {
        if (Math.abs(target - current) < 0.01F) {
            return target;
        }
        float factor = 1.0F - (float) Math.exp(-this.delta / millis);
        return current + (target - current) * factor;
    }

    static float ease(float progress) {
        return progress * progress * (3.0F - 2.0F * progress);
    }

    static void click() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
