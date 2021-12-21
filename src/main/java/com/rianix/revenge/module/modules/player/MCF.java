package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.manager.FriendsManager;
import com.rianix.revenge.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
    private boolean isButtonDown = false;

    public MCF() {
        super("MCF", "Middle click friend.", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.isButtonDown && mc.currentScreen == null) {
                this.onClick();
            }
            this.isButtonDown = true;
        } else {
            this.isButtonDown = false;
        }
    }

    private void onClick() {
        Entity entity;
        RayTraceResult result = MCF.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            if (FriendsManager.isFriend(entity.getName())) {
                FriendsManager.removeFriend(entity.getName());
                Messages.sendClientMessage(entity.getName() + " was removed from the friends list");
            }
            else {
                FriendsManager.addFriend(entity.getName());
                Messages.sendClientMessage("Added " + entity.getName() + " to your friends list");
            }
        }
        this.isButtonDown = true;
    }
}