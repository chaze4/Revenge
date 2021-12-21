package com.rianix.revenge.gui.color;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.setting.settings.SettingColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class HueSlider extends AbstractComponent {

    private final SettingColor setting;
    private boolean sliding = false;

    public HueSlider(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        ColorHolder ch = setting.getValue(false);
        IComponent.drawImage(rect, new ResourceLocation("minecraft:hue.png"));
        IComponent.drawLine(new Point((int) (rect.x + rect.width * ch.getHue()), rect.y), new Point((int) (rect.x + rect.width * ch.getHue()), rect.y + rect.height), 2, Color.BLACK);
        if (sliding) {
            double diff = MathHelper.clamp((Screen.MOUSE.x - rect.x) / (rect.width - 1D), 0D, 1D);
            setting.setValue(new ColorHolder((float) diff, ch.getSaturation(), ch.getBrightness(), ch.getAlpha()));
        }
    }

    @Override
    public void handleButton(int btn) {
        if (btn != -1 && rect.contains(Screen.MOUSE)) sliding = true;
        else if (sliding) sliding = false;
    }
}
