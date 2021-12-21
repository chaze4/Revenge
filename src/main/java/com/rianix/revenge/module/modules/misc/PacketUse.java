package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;

public class PacketUse extends Module {
    public PacketUse() {
        super("PacketUse","",0,Category.MISC);
    }

    public SettingBoolean food = register("Food", true);
    public SettingBoolean potion = register("Potion", true);
    public SettingBoolean all = register("All", false);
}
