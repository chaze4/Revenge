package com.rianix.revenge.gui.color;

import java.awt.*;

public class ColorHolder {

    private float h, s, b, a;

    public ColorHolder(Color color) {
        importFromAWTColor(color);
    }

    public ColorHolder(float h, float s, float b, float a) {
        this.h = h;
        this.s = s;
        this.b = b;
        this.a = a;
    }

    public float getHue() {
        return h;
    }

    public void setHue(float h) {
        this.h = h;
    }

    public float getSaturation() {
        return s;
    }

    public void setSaturation(float s) {
        this.s = s;
    }

    public float getBrightness() {
        return b;
    }

    public void setBrightness(float b) {
        this.b = b;
    }

    public float getAlpha() {
        return a;
    }

    public void setAlpha(float a) {
        this.a = a;
    }

    public Color toAWTColor() {
        Color c = Color.getHSBColor(h, s, b);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) a);
    }

    public void importFromAWTColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        this.h = hsb[0];
        this.s = hsb[1];
        this.b = hsb[2];
        this.a = color.getAlpha();
    }
}
