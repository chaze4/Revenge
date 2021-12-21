package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingDouble;

public class CameraClip extends Module {
    public CameraClip() {
        super("CameraClip","",0,Category.RENDER);
    }

    public SettingDouble fov = register("Fov",10.0,-10.0,50.0);
}
