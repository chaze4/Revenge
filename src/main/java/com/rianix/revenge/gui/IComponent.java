package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.nio.FloatBuffer;

public interface IComponent {

    static void drawLine(Point start, Point end, int thickness, Color color) {
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        GlStateManager.glLineWidth(thickness);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(start.x, start.y, 0D)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .endVertex();
        bb.pos(end.x, end.y, 0D)
                .color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F)
                .endVertex();
        Tessellator.getInstance().draw();
    }

    static void fillRect(Rectangle rect, Color c1, Color c2, Color c3, Color c4) {
        BufferBuilder bb = Tessellator.getInstance().getBuffer();
        bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bb.pos(rect.x, rect.y + rect.height, 0D)
                .color(c4.getRed() / 255F, c4.getGreen() / 255F, c4.getBlue() / 255F, c4.getAlpha() / 255F)
                .endVertex();
        bb.pos(rect.x + rect.width, rect.y + rect.height, 0D)
                .color(c3.getRed() / 255F, c3.getGreen() / 255F, c3.getBlue() / 255F, c3.getAlpha() / 255F)
                .endVertex();
        bb.pos(rect.x + rect.width, rect.y, 0D)
                .color(c2.getRed() / 255F, c2.getGreen() / 255F, c2.getBlue() / 255F, c2.getAlpha() / 255F)
                .endVertex();
        bb.pos(rect.x, rect.y, 0D)
                .color(c1.getRed() / 255F, c1.getGreen() / 255F, c1.getBlue() / 255F, c1.getAlpha() / 255F)
                .endVertex();
        Tessellator.getInstance().draw();
    }

    static void fillRect(Rectangle rect, Color color) {
        Gui.drawRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, color.getRGB());
    }

    static void drawImage (Rectangle r, ResourceLocation resource) {
        GlStateManager.enableTexture2D();
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        Gui.drawModalRectWithCustomSizedTexture(r.x, r.y, 0, 0, r.width, r.height, r.width, r.height);
        GlStateManager.disableTexture2D();
    }
    static void drawString(String text, Point pos, Color color) {
        GlStateManager.enableTexture2D();
        Revenge.fontManager.drawStringWithShadow(text, pos.x, pos.y, color.getRGB());
        GlStateManager.disableTexture2D();
    }

    void draw();

    void handleButton(int btn);

    void keyTyped(int key, char ch);

    int getAbsoluteHeight();

    void addChild(IComponent component);

    Rectangle getRect();

    void setRect(Rectangle rect);

}
