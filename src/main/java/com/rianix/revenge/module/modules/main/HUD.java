package com.rianix.revenge.module.modules.main;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingMode;
import com.rianix.revenge.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HUD extends Module {
    public HUD() {
        super("HUD","",0,Category.MAIN);
        greeterMode.add("None");
        greeterMode.add("Client");
        greeterMode.add("Time");
    }


    ArrayList<String> greeterMode = new ArrayList<String>();

    SettingColor color = register("Color", Color.GREEN,false);
    SettingBoolean watermark = register("Watermark",true);
    SettingMode greeter = register("Greeter", greeterMode, "None");
    SettingBoolean arrayList = register("ArrayList",true);
    SettingBoolean coords = register("Coords",true);
    SettingBoolean direction = register("Direction",true);
    SettingBoolean ping = register("Ping",true);

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            final ScaledResolution sr = new ScaledResolution(mc);
            if (arrayList.getValue()) {
                int y = 2;
                final ArrayList<String> list = new ArrayList<String>();
                for (final Module m : Revenge.moduleManager.getModules()) {
                    if (m.isToggled()) {
                        if (m.getCategory() != Category.MAIN) {
                            list.add(m.getFullDisplayInfo());
                        }
                    }
                    list.sort(Comparator.comparingInt(s -> Revenge.fontManager.getStringWidth(s)));
                    Collections.reverse(list);
                }
                for (final String name : list) {
                    Revenge.fontManager.drawStringWithShadow(name, (float) ((float) (sr.getScaledWidth() - Revenge.fontManager.getStringWidth(name)) - 0.5), y, color.getAWTValue(true).getRGB());
                    y += 10;
                }
            }
            boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
            float nether = !inHell ? 0.125f : 8.0f;
            String posX = new DecimalFormat("#0.0").format(mc.player.posX);
            String posY = new DecimalFormat("#0.0").format(mc.player.posY);
            String posZ = new DecimalFormat("#0.0").format(mc.player.posZ);
            String netherPosX = new DecimalFormat("#0.0").format(mc.player.posX * (double)nether);
            String netherPosZ = new DecimalFormat("#0.0").format(mc.player.posZ * (double)nether);
            String coordinates = "XYZ " + posX + ", " + posY + ", " + posZ + " " + "[" + netherPosX + ", " + netherPosZ + "]";
            String dir = Revenge.rotationManager.getDirection4DString();
            int y = 530;
            if (coords.getValue()) {
                Revenge.fontManager.drawStringWithShadow(coordinates, 1, y, color.getAWTValue(true).getRGB());
                y -= 10;
            }
            if (direction.getValue()) {
                Revenge.fontManager.drawStringWithShadow(dir, 1, y, color.getAWTValue(true).getRGB());
            }
            int y2 = 1;
            if (watermark.getValue()) {
                Revenge.fontManager.drawStringWithShadow(Revenge.NAME_VERSION, 1, y2, color.getAWTValue(true).getRGB());
                y2 += 10;
            }
            if (greeter.getValue().equals("Client")) {
                Revenge.fontManager.drawStringWithShadow("Welcome to Revenge " + ChatFormatting.GRAY + mc.player.getName() + ChatFormatting.RESET + "!",1,y2, color.getAWTValue(true).getRGB());
                y2 += 10;
            }
            if (greeter.getValue().equals("Time")) {
                Revenge.fontManager.drawStringWithShadow(MathUtil.getTimeOfDay() + ChatFormatting.GRAY + mc.player.getName() + ChatFormatting.RESET + "!",1,y2, color.getAWTValue(true).getRGB());
                y2 += 10;
            }
            if (ping.getValue()) {
                Revenge.fontManager.drawStringWithShadow("Ping: " + ChatFormatting.GRAY + Revenge.serverManager.getPing() + "ms", 1, y2, color.getAWTValue(true).getRGB());
                y2 += 10;
            }
        }
    }
}
