package com.rianix.revenge.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.main.Globals;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Messages {

    public static void sendPlayerMessage(String... message) {
        for (String m : message) Minecraft.getMinecraft().player.sendChatMessage(m);
    }

    public static void sendClientMessage(String... message) {
        for (String m : message) {
            if (Revenge.moduleManager.getModuleByClass(Globals.class).notify.getValue().equals("Client")) {
                    String prefix = ChatFormatting.GRAY + "[" + ChatFormatting.WHITE + Revenge.NAME + ChatFormatting.GRAY + "] " + ChatFormatting.RESET;
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefix + m));
            }
            else {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(m));
            }
        }
    }

    public static void sendSilentMessage(String... message) {
        for (String m : message) {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(m));
        }
    }
}
