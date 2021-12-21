package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiHunger extends Module {
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer player = (CPacketPlayer) event.getPacket();
            double differenceY = mc.player.posY - mc.player.lastTickPosY;
            boolean groundCheck = differenceY == 0D;
            if ((groundCheck) && (!mc.playerController.isHittingBlock)) {
                mc.player.onGround = true;
            }
        }
    });

    public AntiHunger() {
        super("AntiHunger", "Causes you to not lose hunger", 0, Category.PLAYER);
    }
}
