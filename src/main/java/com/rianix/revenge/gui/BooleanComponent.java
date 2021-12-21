package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.setting.settings.SettingBoolean;

import java.awt.*;

public class BooleanComponent extends AbstractComponent {

    private final SettingBoolean setting;

    public BooleanComponent(SettingBoolean setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, setting.getValue() ? clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString(setting.getName(), new Point(rect.x + 1, rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE) && btn != -1) {
            setting.setValue(!setting.getValue());
        }
    }
}
