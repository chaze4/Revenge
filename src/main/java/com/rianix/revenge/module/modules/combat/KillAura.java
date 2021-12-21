package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.manager.FriendsManager;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author blaze
 */

public class KillAura extends Module {
    private final SettingBoolean onlySword = register("OnlySword", true);
    private final SettingDouble range = register("Range", 5.5d, 0.0d, 7.0d);
    private final SettingBoolean rotate = register("Rotate", false);
    private final SettingBoolean crits = register("Criticals", true);
    private final SettingBoolean delay = register("Delay", true);
    private boolean isAttacking = false;
    // criticals
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if (crits.getValue() && packet.getAction().equals(CPacketUseEntity.Action.ATTACK) && mc.player != null && mc.player.onGround && isAttacking) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });

    public KillAura() {
        super("KillAura", "", 0, Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (mc.world.playerEntities.isEmpty())
            return; // if the client world or client player don't exist or there are no players in render distance return
        if (onlySword.getValue()) {
            if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return; // not holding sword
        }

        List<EntityPlayer> list = new ArrayList<>();
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == mc.player) continue;
            if (mc.player.getDistance(player) > range.getValue()) continue;
            if (player.getHealth() <= 0 || player.isDead) continue; //  ignore player dead
            if (!FriendsManager.isFriend(player.getName())) continue; //ignore friends
            list.add(player);
        }
        if (list.isEmpty()) return;
        attack(list.get(0));
    }

    private void attack(EntityPlayer target) {
        if (mc.player.getCooledAttackStrength(0f) >= 1f || !delay.getValue()) { // check hit delay
            isAttacking = true; // set variable for criticals
            if (rotate.getValue())
                Revenge.rotationManager.rotate(target.posX, target.posY, target.posZ); // rotate to the target
            mc.playerController.attackEntity(mc.player, target); // attack
            mc.player.swingArm(EnumHand.MAIN_HAND); // swing hand
            if (rotate.getValue()) Revenge.rotationManager.reset(); // reset rotation
            isAttacking = false; // set variable for criticals
        }
    }
}
