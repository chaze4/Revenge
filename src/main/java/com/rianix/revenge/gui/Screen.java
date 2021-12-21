package com.rianix.revenge.gui;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.gui.color.ColorComponent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Screen extends GuiScreen {

    public static final Point MOUSE = new Point();
    private final List<IComponent> PANELS = new ArrayList<>();

    public Screen() {
        Point offset = new Point(110, 50);
        for (Module.Category cat : Module.Category.values()) {
            PanelComponent panel = new PanelComponent(new Rectangle(new Point(offset), new Dimension(100, 10)), cat.name());
            Revenge.moduleManager.getModsInCategory(cat).forEach(m -> {
                ModuleComponent module = new ModuleComponent(m);
                Revenge.settingsManager.getSettingsInMod(m).forEach(s -> {
                    switch (s.getType()) {
                        case INTEGER:
                            module.addChild(new SliderInteger((SettingInteger) s));
                            break;
                        case BOOLEAN:
                            module.addChild(new BooleanComponent((SettingBoolean) s));
                            break;
                        case DOUBLE:
                            module.addChild(new SliderDouble((SettingDouble) s));
                            break;
                        case MODE:
                            module.addChild(new ModeComponent((SettingMode) s));
                            break;
                        case COLOR:
                            module.addChild(new ColorComponent((SettingColor) s));
                            break;
                    }
                });
                module.addChild(new KeybindComponent(m));
                panel.addChild(module);
            });
            PANELS.add(panel);
            offset.translate(105, 0);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0, width, height, 0, -3000, 3000);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        MOUSE.setLocation(mouseX, mouseY);
        PANELS.forEach(IComponent::draw);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        MOUSE.setLocation(mouseX, mouseY);
        PANELS.forEach(p -> p.handleButton(mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        MOUSE.setLocation(mouseX, mouseY);
        PANELS.forEach(p -> p.handleButton(-1));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) mc.displayGuiScreen(null);
        PANELS.forEach(p -> p.keyTyped(keyCode, typedChar));
    }
}
