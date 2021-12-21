package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketVehicleMove;

import java.util.Objects;

public class EntityVanish extends Module {
    Entity entity;

    public EntityVanish() {
        super("EntityVanish", "", 0, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        if (mc.player.getRidingEntity() != null) {
            entity = mc.player.getRidingEntity();
            mc.player.dismountRidingEntity();
            mc.world.removeEntity(entity);
        }
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) {
            toggle();
            return;
        }
        if (entity != null) {
            try {
                entity.posX = mc.player.posX;
                entity.posY = mc.player.posY;
                entity.posZ = mc.player.posZ;
                Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketVehicleMove(entity));
            } catch (Exception ignored) {
            }
        }
    }
}
