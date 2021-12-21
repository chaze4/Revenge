package com.rianix.revenge.gui.color;

import com.rianix.revenge.gui.AbstractComponent;
import com.rianix.revenge.gui.IComponent;
import com.rianix.revenge.gui.Screen;
import com.rianix.revenge.setting.settings.SettingColor;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ColorPickerComponent extends AbstractComponent {

    private final SettingColor setting;
    private final Point cursor = new Point();
    private boolean dragging = false;

    public ColorPickerComponent(SettingColor setting) {
        this.setting = setting;
    }

    @Override
    public void draw() {
        rect.setSize(rect.width, rect.width);
        ColorHolder ch = setting.getValue(false);
        if (dragging) {
            float s = MathHelper.clamp((Screen.MOUSE.x - rect.x) / (rect.width - 1F), 0F, 1F),
                    b = MathHelper.clamp(1F + (rect.y - Screen.MOUSE.y) / (rect.height - 1F), 0F, 1F);
            setting.setValue(new ColorHolder(ch.getHue(), s, b, ch.getAlpha()));
        }
        cursor.setLocation(rect.width * ch.getSaturation(), rect.height - rect.height * ch.getBrightness());
        Color c1 = Color.getHSBColor(ch.getHue(), 0, 1),
                c2 = Color.getHSBColor(ch.getHue(), 1, 1),
                c3 = new Color(0, 0, 0, 0),
                c4 = new Color(0, 0, 0),
                marker = Color.getHSBColor(ch.getHue() - 0.3F, ch.getSaturation() - 0.1F, ch.getBrightness() - 0.1F);
        IComponent.fillRect(rect, c1, c2, c2, c1);
        IComponent.fillRect(rect, c3, c3, c4, c4);
        IComponent.drawLine(new Point(rect.x + cursor.x, rect.y + cursor.y - 3), new Point(rect.x + cursor.x, rect.y + cursor.y + 3), 1, marker);
        IComponent.drawLine(new Point(rect.x + cursor.x - 3, rect.y + cursor.y), new Point(rect.x + cursor.x + 3, rect.y + cursor.y), 1, marker);
    }

    @Override
    public void handleButton(int btn) {
        rect.setSize(rect.width, rect.width);
        if (rect.contains(Screen.MOUSE) && btn == 0) {
            dragging = true;
        } else if (btn == -1 && dragging) {
            dragging = false;
        }
    }

    @Override
    public int getAbsoluteHeight() {
        return rect.width;
    }
}
