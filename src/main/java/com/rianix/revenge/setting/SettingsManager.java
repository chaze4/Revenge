package com.rianix.revenge.setting;

import com.rianix.revenge.module.Module;

import java.util.ArrayList;

public class SettingsManager {

    public ArrayList<Setting> settings;

    public SettingsManager() {
        settings = new ArrayList<>();
    }

    public ArrayList<Setting> getSettingsInMod(Module mod) {
        ArrayList<Setting> sets = new ArrayList<>();
        for (Setting s : settings) {
            if (s.getMod() == mod) {
                sets.add(s);
            }
        }
        return sets;
    }

    public void setSettings(ArrayList<Setting> settings) {
        this.settings = settings;
    }
}
