package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;

public class DeathExplorer extends Module {
    boolean explorer;
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<>(event -> {
        if (explorer && event.getPacket() instanceof CPacketPlayer) {
            event.cancel();
        }
    });

    public DeathExplorer() {
        super("DeathExplorer", "", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (mc.player.getHealth() == 0.0f) {
            mc.player.setHealth(20.0f);
            mc.player.isDead = false;
            explorer = true;
            mc.displayGuiScreen(null);
            mc.player.setPositionAndUpdate(mc.player.posX, mc.player.posY, mc.player.posZ);
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.respawnPlayer();
        }
        explorer = false;
    }
}
