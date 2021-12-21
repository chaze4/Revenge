package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.util.BlockUtil;
import com.rianix.revenge.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Burrow extends Module {
    public Burrow() {
        super("Burrow","",0,Category.COMBAT);
    }

    SettingBoolean any = register("Any",false);
    SettingBoolean anvil = register("Anvil",false);
    SettingBoolean echest = register("EChest",true);
    SettingBoolean rotate = register("Rotate",false);

    private BlockPos originalPos;
    private int prvSlot;

    @Override
    public void onEnable() {
        originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (mc.world.getBlockState(new BlockPos(originalPos)).getBlock().equals(Blocks.OBSIDIAN) || intersectsWithEntity(this.originalPos)) {
            toggle();
            return;
        }
        prvSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        if (mc.player.onGround) {
            if (any.getValue() && InventoryUtil.findHotbarBlock(Block.class) != -1) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarBlock(Block.class)));
            }
            else if (anvil.getValue() && InventoryUtil.findHotbarBlock(BlockAnvil.class) != -1) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarBlock(BlockAnvil.class)));
            }
            else if (echest.getValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarBlock(BlockEnderChest.class)));
            }
            else if (InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarBlock(BlockObsidian.class)));
            }
            else { toggle(); return; }

            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));
            BlockUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, rotate.getValue(), true, false);
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            mc.player.setSneaking(false);
            mc.player.inventory.currentItem = prvSlot;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(prvSlot));
            toggle();
        }
    }

    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity.equals(mc.player)) continue;
            if (entity instanceof EntityItem) continue;
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
        }
        return false;
    }
}
