package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.setting.settings.SettingInteger;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class SliderInteger extends AbstractComponent {

    private final SettingInteger setting;
    private boolean sliding = false;

    public SliderInteger(SettingInteger setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        double Multiplier = (double) (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        IComponent.fillRect(rect, new Color(70, 70, 70, 140));
        IComponent.fillRect(new Rectangle(rect.x, rect.y, (int) (rect.width * Multiplier), rect.height), clickGui.color.getAWTValue(true));
        IComponent.drawString(setting.getName() + ": " + setting.getValue(), new Point(rect.x + 1, rect.y + 2), Color.WHITE);
        if (sliding) {
            double diff = MathHelper.clamp((Screen.MOUSE.getX() - rect.getX()) / (rect.getWidth() - 1D), 0D, 1D);
            setting.setValue((int) ((setting.getMax() - setting.getMin()) * diff + setting.getMin()));
        }
    }

    @Override
    public void handleButton(int btn) {
        if (btn != -1 && rect.contains(Screen.MOUSE)) sliding = true;
        else if (sliding) sliding = false;
    }
}
