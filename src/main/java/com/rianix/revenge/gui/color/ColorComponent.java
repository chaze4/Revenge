package com.rianix.revenge.gui.color;

import com.rianix.revenge.gui.*;
import com.rianix.revenge.setting.settings.SettingColor;

import java.awt.*;

public class ColorComponent extends AbstractComponent {

    private final SettingColor setting;
    private final ContainerComponent container = new ContainerComponent(2, 2, 4) {
        @Override
        public void draw() {
            children.forEach(IComponent::draw);
        }
    };
    private boolean opened = false;

    public ColorComponent(SettingColor setting) {
        this.setting = setting;
        container.addChild(new ColorPickerComponent(setting));
        container.addChild(new HueSlider(setting));
        container.addChild(new AlphaSlider(setting));
        container.addChild(new RainbowComponent(setting));
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, setting.getAWTValue(true));
        IComponent.drawString(setting.getName(), rect.getLocation(), Color.WHITE);
        if (opened) {
            container.setRect(rect);
            container.draw();
        }
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE) && btn != -1) {
            opened = !opened;
            return;
        }
        if (opened) {
            container.setRect(rect);
            container.handleButton(btn);
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return rect.height + (opened ? container.getAbsoluteHeight() + 2 : 0);
    }
}
