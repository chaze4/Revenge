package com.rianix.revenge.module.modules.movement;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.MathUtil;
import com.rianix.revenge.util.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class PhaseWalk extends Module {
    public PhaseWalk() {
        super("PhaseWalk","",0,Category.MOVEMENT);
    }

    SettingBoolean fallPacket = register("FallPacket", true);
    SettingBoolean instantWalk = register("InstantWalk", true);
    SettingDouble instantWalkSpeed = register("InstantWalkSpeed", 18.0, 10.0, 19.0);
    SettingDouble phaseSpeed = register("PhaseSpeed", 4.24, 1, 7);
    SettingBoolean phaseCheck = register("PhaseCheck", true);
    SettingBoolean downOnShift = register("DownOnShift", true);
    SettingBoolean stopMotion = register("StopMotion", true);
    SettingInteger stopMotionDelay = register("StopMotionDelay",5, 0, 20);
    int delay = 0;

    public void onUpdate() {
        delay++;
        if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindSneak.isKeyDown()) {
            if (phaseCheck.getValue() && !eChestCheck() && !mc.world.getBlockState(PlayerUtil.getPlayerPos()).getBlock().equals(Blocks.AIR) || !mc.world.getBlockState(PlayerUtil.getPlayerPos().up()).getBlock().equals(Blocks.AIR)) {
                if (mc.player.collidedVertically && mc.gameSettings.keyBindSneak.isPressed() && mc.player.isSneaking()) {
                    double[] dirSpeed = this.getMotion(phaseSpeed.getValue() / 100);
                    if (downOnShift.getValue() && mc.player.collidedVertically && mc.gameSettings.keyBindSneak.isKeyDown())mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + dirSpeed[0], mc.player.posY - .0424, mc.player.posZ + dirSpeed[1], mc.player.rotationYaw, mc.player.rotationPitch, false));
                    else mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + dirSpeed[0], mc.player.posY, mc.player.posZ + dirSpeed[1], mc.player.rotationYaw, mc.player.rotationPitch, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, -1337.0, mc.player.posZ, mc.player.rotationYaw * -5, mc.player.rotationPitch * -5, true));
                    if (fallPacket.getValue()) mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_RIDING_JUMP));
                    if (downOnShift.getValue() && mc.player.collidedVertically && mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.setPosition(mc.player.posX + dirSpeed[0], mc.player.posY - .0424, mc.player.posZ + dirSpeed[1]);
                    else mc.player.setPosition(mc.player.posX + dirSpeed[0], mc.player.posY, mc.player.posZ + dirSpeed[1]);
                    mc.player.motionZ = 0.0;
                    mc.player.motionY = 0.0;
                    mc.player.motionX = 0.0;
                    mc.player.noClip = true;
                }
                if (mc.player.collidedHorizontally && stopMotion.getValue() ?  delay >= stopMotionDelay.getValue() : mc.player.collidedHorizontally) {
                    double[] dirSpeed = this.getMotion(phaseSpeed.getValue() / 100);
                    if (downOnShift.getValue() && mc.player.collidedVertically && mc.gameSettings.keyBindSneak.isKeyDown())mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + dirSpeed[0], mc.player.posY - .1, mc.player.posZ + dirSpeed[1], mc.player.rotationYaw, mc.player.rotationPitch, false));
                    else mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + dirSpeed[0], mc.player.posY, mc.player.posZ + dirSpeed[1], mc.player.rotationYaw, mc.player.rotationPitch, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, -1337.0, mc.player.posZ, mc.player.rotationYaw * -5, mc.player.rotationPitch * -5, true));
                    if (fallPacket.getValue()) mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_RIDING_JUMP));
                    if (downOnShift.getValue() && mc.player.collidedVertically && mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.setPosition(mc.player.posX + dirSpeed[0], mc.player.posY - .1, mc.player.posZ + dirSpeed[1]); //
                    else mc.player.setPosition(mc.player.posX + dirSpeed[0], mc.player.posY, mc.player.posZ + dirSpeed[1]);

                    mc.player.motionZ = 0.0;
                    mc.player.motionY = 0.0;
                    mc.player.motionX = 0.0;
                    mc.player.noClip = true;
                    delay = 0;
                    //
                } else {
                    if (instantWalk.getValue()) {
                        final double[] dir = MathUtil.directionSpeed(instantWalkSpeed.getValue() / 100);
                        mc.player.motionX = dir[0];
                        mc.player.motionZ = dir[1];
                    }

                }
            }
        }
    }

    public void onEnable() {

    }

    private double[] getMotion(double speed) {
        float moveForward = mc.player.movementInput.moveForward;
        float moveStrafe = mc.player.movementInput.moveStrafe;
        float rotationYaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? -45 : 45);
            } else if (moveStrafe < 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            } else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        double posX = (double)moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + (double)moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        double posZ = (double)moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - (double)moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[]{posX, posZ};
    }

    public void onDisable() {
        mc.player.noClip = false;


    }

    private boolean eChestCheck() {
        String loc = String.valueOf(mc.player.posY);
        String deciaml = loc.split("\\.")[1];
        return deciaml.equals("875");
    }
}
