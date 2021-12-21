package com.rianix.revenge.module.modules.player;

import com.mojang.authlib.GameProfile;
import com.rianix.revenge.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.UUID;

public class Bot extends Module {
    public Bot() {
        super("Bot", "Spawns a bot", 0, Category.PLAYER);
    }

    public void onEnable() {
        if (mc.player.isDead) {
            toggle();
            return;
        }
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("4b7ed64e-747e-4ee0-a9d8-0349ae33e7e7"), "FakePlayer"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.rotationYaw = mc.player.rotationYaw;
        fakePlayer.rotationPitch = mc.player.rotationPitch;
        fakePlayer.setHealth(mc.player.getHealth() + mc.player.getAbsorptionAmount());
        fakePlayer.inventory = mc.player.inventory;
        mc.world.addEntityToWorld(-101, fakePlayer);
        fakePlayer.onLivingUpdate();
    }

    @SubscribeEvent
    public void onPlayerLeaveEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.toggle();
    }

    public void onDisable() {
        if (mc.world != null) {
            mc.world.removeEntityFromWorld(-101);
        }
    }
}
