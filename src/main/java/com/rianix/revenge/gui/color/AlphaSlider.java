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

public class AlphaSlider extends AbstractComponent {

    private final SettingColor setting;
    private boolean sliding = false;

    public AlphaSlider(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        Color color = setting.getAWTValue(true);
        double multiplier = color.getAlpha() / 255D;
        IComponent.drawImage(rect, new ResourceLocation("minecraft:alpha.png"));
        IComponent.fillRect(rect, new Color(0, 0, 0, 0), new Color(color.getRed(), color.getGreen(), color.getBlue(), 255), new Color(color.getRed(), color.getGreen(), color.getBlue(), 255), new Color(0, 0, 0, 0));
        IComponent.drawLine(new Point((int) (rect.x + rect.width * multiplier), rect.y), new Point((int) (rect.x + rect.width * multiplier), rect.y + rect.height), 2, Color.BLACK);
        if (sliding) {
            double diff = MathHelper.clamp((Screen.MOUSE.getX() - rect.getX()) / (rect.getWidth() - 1D), 0D, 1D);
            ColorHolder ch = setting.getValue(false);
            setting.setValue(new ColorHolder(ch.getHue(), ch.getSaturation(), ch.getBrightness(), (int) (255 * diff)));
        }
    }

    @Override
    public void handleButton(int btn) {
        if (btn != -1 && rect.contains(Screen.MOUSE)) sliding = true;
        else if (sliding) sliding = false;
    }
}
