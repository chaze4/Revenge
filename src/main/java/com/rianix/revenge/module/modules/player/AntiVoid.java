package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class AntiVoid extends Module {
    public AntiVoid() {
        super("AntiVoid", "", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) {
            return;
        }
        if (!mc.player.noClip && mc.player.posY <= 0.0) {
            RayTraceResult trace = mc.world.rayTraceBlocks(mc.player.getPositionVector(), new Vec3d(mc.player.posX, 0.0, mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            mc.player.setVelocity(0.0, 0.0, 0.0);
            if (mc.player.getRidingEntity() != null) {
                mc.player.getRidingEntity().setVelocity(0.0, 0.0, 0.0);
            }
        }
    }
}
