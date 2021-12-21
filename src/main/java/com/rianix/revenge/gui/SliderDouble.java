package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.setting.settings.SettingDouble;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class SliderDouble extends AbstractComponent {

    private final SettingDouble setting;
    private boolean sliding = false;

    public SliderDouble(SettingDouble setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        double multiplier = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        IComponent.fillRect(rect, new Color(70, 70, 70, 140));
        IComponent.fillRect(new Rectangle(rect.x, rect.y, (int) (rect.width * multiplier), rect.height), clickGui.color.getAWTValue(true));
        IComponent.drawString(String.format("%s: %.2f", setting.getName(), setting.getValue()), new Point(rect.x + 1, rect.y + 2), Color.WHITE);
        if (sliding) {
            double diff = MathHelper.clamp((Screen.MOUSE.getX() - rect.getX()) / (rect.getWidth() - 1D), 0D, 1D);
            setting.setValue((setting.getMax() - setting.getMin()) * diff + setting.getMin());
        }
    }

    @Override
    public void handleButton(int btn) {
        if (btn != -1 && rect.contains(Screen.MOUSE)) sliding = true;
        else if (sliding) sliding = false;
    }
}
