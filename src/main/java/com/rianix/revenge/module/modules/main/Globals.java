package com.rianix.revenge.module.modules.main;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.setting.settings.SettingMode;

import java.util.ArrayList;

public class Globals extends Module {
    public Globals() {
        super("Globals","",0,Category.MAIN);
        mode.add("Client");
        mode.add("Silent");
    }

    ArrayList<String> mode = new ArrayList<>();

    public float hue;
    SettingBoolean cfont = register("CustomFont",true);
    public SettingMode notify = register("Notify",mode,"Client");

    @Override
    public void onUpdate() {
        Revenge.fontManager.setCustomFont(cfont.getValue());
    }
}
