package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import com.rianix.revenge.setting.settings.SettingMode;

import java.awt.*;

public class ModeComponent extends AbstractComponent {

    private final SettingMode setting;

    public ModeComponent(SettingMode setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, new Color(20, 20, 20, 120));
        IComponent.drawString(setting.getName() + ": " + setting.getValue(), new Point(rect.x + 1, rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE)) {
            if (btn == 0) setting.increment();
            else if (btn == 1) setting.decrement();
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return rect.height;
    }

    private static final class QuadPicker extends AbstractComponent {

        public QuadPicker(Rectangle rect) {
            super(rect);
        }

        @Override
        public void draw() {

        }
    }
}
