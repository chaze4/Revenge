package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;

public class RotationLock extends Module {
    public RotationLock() {
        super("RotationLock", "", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        mc.player.rotationYaw = Math.round(mc.player.rotationYaw / 45f) * 45f;
    }
}
