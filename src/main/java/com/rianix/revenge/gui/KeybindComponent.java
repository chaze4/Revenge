package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.module.modules.main.ClickGuiModule;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeybindComponent extends AbstractComponent {

    private final Module module;
    private boolean listening = false;

    public KeybindComponent(Module module) {
        this.module = module;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, listening ? clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString("Key: " + (listening ? "..." : Keyboard.getKeyName(module.getKey())), new Point(rect.x + 1, rect.y + 2), Color.WHITE);
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE) && btn != -1) {
            listening = !listening;
        }
    }

    @Override
    public void keyTyped(int key, char ch) {
        if (listening) {
            if (key == Keyboard.KEY_BACK || key == Keyboard.KEY_DELETE) {
                module.setKey(0);
                return;
            }
            module.setKey(key);
            listening = false;
        }
    }
}
