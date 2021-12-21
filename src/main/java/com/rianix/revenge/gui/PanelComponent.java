package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.ClickGuiModule;

import java.awt.*;

public class PanelComponent extends AbstractComponent {

    private final String name;
    private final ContainerComponent cont = new ContainerComponent(2, 2, 4);
    private final Point attachPoint = new Point();
    private boolean dragging = false;

    public PanelComponent(Rectangle rect, String name) {
        super(rect);
        this.name = name;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, clickGui.color.getAWTValue(true));
        IComponent.drawString(name, new Point(rect.x + 1, rect.y + 2), Color.WHITE);
        cont.setRect(rect);
        cont.draw();
        if (dragging) {
            rect.translate(Screen.MOUSE.x - attachPoint.x, Screen.MOUSE.y - attachPoint.y);
            attachPoint.setLocation(Screen.MOUSE);
        }
    }

    @Override
    public void handleButton(int btn) {
        if (rect.contains(Screen.MOUSE) && btn != -1) {
            dragging = true;
            attachPoint.setLocation(Screen.MOUSE);
        } else if (dragging) dragging = false;
        cont.handleButton(btn);
    }

    @Override
    public void keyTyped(int key, char ch) {
        cont.keyTyped(key, ch);
    }

    @Override
    public int getAbsoluteHeight() {
        return rect.height + cont.getAbsoluteHeight();
    }

    @Override
    public void addChild(IComponent component) {
        cont.addChild(component);
    }
}
