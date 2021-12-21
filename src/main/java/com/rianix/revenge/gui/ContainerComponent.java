package com.rianix.revenge.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContainerComponent implements IComponent {

    private final Rectangle rect = new Rectangle();
    private final int offsetX, offsetY, componentGap;
    protected final List<IComponent> children = new ArrayList<>();

    public ContainerComponent(int offsetX, int offsetY, int componentGap) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.componentGap = componentGap;
    }

    @Override
    public void draw() {
        IComponent.fillRect(rect, new Color(255, 255, 255, 52));
        children.forEach(IComponent::draw);
    }

    @Override
    public void handleButton(int btn) {
        children.forEach(c -> c.handleButton(btn));
    }

    @Override
    public void keyTyped(int key, char ch) {
        children.forEach(c -> c.keyTyped(key, ch));
    }

    @Override
    public int getAbsoluteHeight() {
        return children.size() > 0 ? children.stream().mapToInt(c -> c.getAbsoluteHeight() + componentGap).sum() - componentGap : 0;
    }

    @Override
    public void addChild(IComponent component) {
        children.add(component);
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public void setRect(Rectangle rect1) {
        int offset = offsetY;
        for (IComponent comp : children) {
            Rectangle rect2 = new Rectangle(rect1.x + offsetX, rect1.y + rect1.height + offset, rect1.width - offsetX * 2, rect1.height);
            comp.setRect(rect2);
            offset += comp.getAbsoluteHeight() + componentGap;
        }
        rect.setRect(rect1.x, rect1.y + rect1.height, rect1.width, offset - componentGap + offsetY);
    }
}
