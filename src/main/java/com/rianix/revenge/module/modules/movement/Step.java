package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;

public class Step extends Module {
    SettingDouble height = this.register("Height", 2.5, 0.5, 2.0);
    SettingBoolean entity = this.register("EntityStep", false);
    public Step() {
        super("Step", "Step up blocks.", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (entity.getValue() && mc.player.getRidingEntity() != null) {
            mc.player.getRidingEntity().stepHeight = (float) height.getValue();
        }
        mc.player.stepHeight = (float) height.getValue();
    }

    public void onDisable() {
        mc.player.stepHeight = 0.5f;
    }
}
