package com.rianix.revenge.manager;

/*
 * By Blaze On 29-08-2021 so fucking tired and sad ;(!
 */

import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.util.Global;
import com.rianix.revenge.util.RotationUtil;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;

public class RotationManager implements Global {

    private float yaw = 0;
    private float pitch = 0;
    private boolean shouldRotate = false;
    @EventHandler
    private final Listener<PacketEvent.Send> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if (shouldRotate) {
                packet.yaw = yaw;
                packet.pitch = pitch;
            }
        }
    });

    public RotationManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // this resets yaw and pitch
    public void reset() {
        shouldRotate = false;
        if (mc.player == null) return;
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;

    }

    // sets yaw and pitch of packets you send, don't forget to call reset() otherwise you'll get desynced
    public void rotate(double x, double y, double z) {
        if (mc.player == null) return;
        Double[] v = calculateLookAt(x, y, z, mc.player);
        shouldRotate = true;
        yaw = v[0].floatValue();
        pitch = v[1].floatValue();
    }

    private Double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        //to degree
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;
        yaw += 90.0;
        return new Double[]{yaw, pitch};
    }

    public int getDirection4D() {
        return RotationUtil.getDirection4D();
    }

    public String getDirection4DString() {
        return RotationUtil.getDirection4DString();
    }
}
