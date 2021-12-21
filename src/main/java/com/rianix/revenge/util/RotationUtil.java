package com.rianix.revenge.util;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtil implements Global{

    public static int getDirection4D() {
        return MathHelper.floor((double)((double)(RotationUtil.mc.player.rotationYaw * 4.0f / 360.0f) + 0.5)) & 3;
    }

    public static String getDirection4DString() {
        int directionNumber = RotationUtil.getDirection4D();
        if (directionNumber == 0) {
            return "South [+Z]";
        }
        if (directionNumber == 1) {
            return "West [-X]";
        }
        if (directionNumber == 2) {
            return "North [-Z]";
        }
        if (directionNumber == 3) {
            return "East [+X]";
        }
        else return null;
    }

    public static BlockPos getDirection4DCityPos() {
        int directionNumber = RotationUtil.getDirection4D();
        if (directionNumber == 0) {
            return new BlockPos(0, 0, 2);
        }
        if (directionNumber == 1) {
            return new BlockPos(-2, 0, 0);
        }
        if (directionNumber == 2) {
            return new BlockPos(0, 0, -2);
        }
        if (directionNumber == 3) {
            return new BlockPos(2, 0, 0);
        }
        else return null;
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ);
    }

    public static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - mc.player.rotationYaw)), mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - mc.player.rotationPitch))};
    }

    public static void faceVector(Vec3d vec, boolean normalizeAngle) {
        float[] rotations = getLegitRotations(vec);
        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? (float)MathHelper.normalizeAngle((int)((int)rotations[1]), (int)360) : rotations[1], mc.player.onGround));
    }
}
