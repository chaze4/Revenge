package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;

public class FullBright extends Module {
    public FullBright() {
        super("FullBright", "Turns up brightness to see in the dark.", 0, Category.RENDER);
    }

    public void onUpdate() {
        mc.gameSettings.gammaSetting = 100;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
