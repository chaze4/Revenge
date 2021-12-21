package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;

public class SCAR20 extends Module {
    public SCAR20() {
        super("SCAR-20","",0,Category.COMBAT);
    }

    private long lastShootTime;

    SettingInteger delay = register("Delay", 5000, 100, 10000);
    SettingInteger packets = register("Packets", 10, 1, 300);
    SettingBoolean bypass = register("Bypass", false);

    @Override
    public void onEnable() {
        lastShootTime = System.currentTimeMillis();
    }

    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<>(event -> {

        if (event.getPacket() instanceof CPacketPlayerDigging) {
            if (((CPacketPlayerDigging) event.getPacket()).getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM) {

                    if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow || mc.player.getHeldItemOffhand().getItem() instanceof ItemBow) {
                        if (System.currentTimeMillis() - lastShootTime >= delay.getValue()) {
                            lastShootTime = System.currentTimeMillis();

                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));

                            for (int i = 0; i < packets.getValue(); ++i) {
                                if (bypass.getValue()) {
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1e-10, mc.player.posZ, false));
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1e-10, mc.player.posZ, true));
                                } else {
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 1e-10, mc.player.posZ, true));
                                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1e-10, mc.player.posZ, false));
                                }
                            }
                        }
                    }
                }
            }
    });
}
