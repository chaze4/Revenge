package com.rianix.revenge.gui.color;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.setting.settings.SettingColor;

import java.awt.*;

public class RainbowComponent extends AbstractComponent {

    private final SettingColor setting;

    public RainbowComponent(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, setting.isRainbow() ? clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString("Rainbow", new Point(rect.x + 1, rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE) && btn != -1) {
            setting.setRainbow(!setting.isRainbow());
        }
    }
}
