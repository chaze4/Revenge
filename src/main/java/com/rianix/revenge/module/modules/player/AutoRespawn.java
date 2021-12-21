package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.client.gui.GuiGameOver;

public class AutoRespawn extends Module {
    SettingBoolean coords = this.register("DeathCoords", true);

    public AutoRespawn() {
        super("AutoRespawn", "Removes the death screen.", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiGameOver) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
        if (coords.getValue() && mc.currentScreen instanceof GuiGameOver) {
            Messages.sendClientMessage("You have died at x" + (int) mc.player.posX + " y" + (int) mc.player.posY + " z" + (int) mc.player.posZ);
        }
    }
}
