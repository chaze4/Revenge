package com.rianix.revenge.setting.settings;

import com.rianix.revenge.gui.color.ColorHolder;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.Setting;

import java.awt.*;

public class SettingColor extends Setting {

    private ColorHolder value;
    private boolean rainbow;

    public SettingColor(String name, Module mod, Color value, boolean rainbow) {
        this.name = name;
        this.mod = mod;
        this.value = new ColorHolder(value);
        this.type = Type.COLOR;
        this.rainbow = rainbow;
    }

    public Color getAWTValue(boolean rainbowCheck) {
        if (rainbowCheck && rainbow) {
            Color color = Color.getHSBColor((float) (((System.currentTimeMillis() + (double) value.getHue() * 100D * 200f) % (360f * 20f)) / (360f * 20f)), 0.5f, 1f);
            return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) value.getAlpha());
        }
//        if (rainbow)
//            return Color.getHSBColor((float) (((System.currentTimeMillis() + 32D * 200F) % (360F * 20F)) / (360F * 20F)), 0.5f, 1f);
        return value.toAWTColor();
    }

    public ColorHolder getValue(boolean rainbowCheck) {
        if (rainbowCheck && rainbow)
            return new ColorHolder(((System.currentTimeMillis() + value.getHue() * 200)%(360*20))/(360f * 20),0.5f,1f, value.getAlpha());
//        if (rainbow)
//            return new ColorHolder((float) (((System.currentTimeMillis() + 32D * 200F) % (360F * 20F)) / (360F * 20F)), 0.5f, 1f, 255);
        return value;
    }

    public void setAWTValue(Color value) {
        this.value.importFromAWTColor(value);
    }

    public void setValue(ColorHolder value) {
        this.value = value;
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }
}
