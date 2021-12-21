package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.module.modules.main.ClickGuiModule;

import java.awt.*;

public class ModuleComponent extends AbstractComponent {

    private final Module module;
    private final ContainerComponent container = new ContainerComponent(2, 2, 4);
    private boolean opened = false;

    public ModuleComponent(Module module) {
        this.module = module;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, module.isToggled() ? clickGui.color.getAWTValue(true) : new Color(255, 255, 255, 52));
        IComponent.drawString(module.getName(), new Point(rect.x + 1, rect.y + 2), Color.WHITE);
        if (opened) {
            container.setRect(rect);
            container.draw();
        }
        if (clickGui.misc.getValue().equals("Dots")){
            IComponent.drawString("...", new Point(rect.x + 85, rect.y + 2), Color.WHITE);
        }
        if (clickGui.misc.getValue().equals("PlusMinus") && !opened){
            IComponent.drawString("+", new Point(rect.x + 85, rect.y + 2), Color.WHITE);
        }
        if (clickGui.misc.getValue().equals("PlusMinus") && opened){
            IComponent.drawString("-", new Point(rect.x + 85, rect.y + 2), Color.WHITE);
        }
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE)) {
            if (btn == 0) {
                module.toggle();
            } else if (btn == 1) {
                opened = !opened;
            }
        }
        if (opened) container.handleButton(btn);
    }

    @Override
    public void keyTyped(int key, char ch) {
        if (opened) container.keyTyped(key, ch);
    }

    @Override
    public int getAbsoluteHeight() {
        return rect.height + (opened ? container.getAbsoluteHeight() : 0);
    }

    @Override
    public void addChild(IComponent component) {
        container.addChild(component);
    }
}
