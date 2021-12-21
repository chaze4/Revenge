package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemBoat;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class BoatBypass extends Module {
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && mc.player.getHeldItemMainhand().getItem() instanceof ItemBoat || mc.player.getHeldItemOffhand().getItem() instanceof ItemBoat) {
            event.cancel();
        }
    });

    public BoatBypass() {
        super("BoatBypass", "Allows you to place the boat on servers where it is patched.", 0, Category.MISC);
    }
}
