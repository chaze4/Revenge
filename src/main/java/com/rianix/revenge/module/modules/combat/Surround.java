package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.*;
import com.rianix.revenge.util.Timer;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class Surround extends Module {
    public Surround() {
        super("Surround","",0,Category.COMBAT);
    }

    SettingInteger delay = register("Delay", 0, 0, 250);
    SettingBoolean center = register("Center", true);
    SettingBoolean rotate = register("Rotate", false);
    Set<Vec3d> extendingBlocks = new HashSet<Vec3d>();
    Map<BlockPos, Integer> retries = new HashMap<BlockPos, Integer>();
    Timer timer = new Timer();
    Timer retryTimer = new Timer();
    BlockPos startPos;
    boolean didPlace = false;
    boolean offHand = false;
    boolean isSneaking;
    int lastHotbarSlot;
    int placements = 0;
    int extenders = 1;

    @Override
    public void onEnable() {
        lastHotbarSlot = mc.player.inventory.currentItem;
        startPos = EntityUtil.getRoundedBlockPos(mc.player);
        if (center.getValue()) {
            if (mc.world.getBlockState(new BlockPos(mc.player.getPositionVector())).getBlock() == Blocks.WEB) {
                Revenge.positionManager.setPositionPacket(mc.player.posX, this.startPos.getY(), mc.player.posZ, true, true, true);
            } else {
                Revenge.positionManager.setPositionPacket((double)this.startPos.getX() + 0.5, this.startPos.getY(), (double)this.startPos.getZ() + 0.5, true, true, true);
            }
        }
        retries.clear();
        retryTimer.reset();
    }

    @Override
    public void onUpdate() {
        if (check()) {
            return;
        }
        boolean onEChest = mc.world.getBlockState(new BlockPos(mc.player.getPositionVector())).getBlock() == Blocks.ENDER_CHEST;
        if (mc.player.posY - (int)mc.player.posY < 0.7) {
            onEChest = false;
        }
        if (!BlockUtil.isSafe(mc.player, onEChest ? 1:0, true)) {
            placeBlocks(mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray(mc.player, onEChest ? 1 : 0, true), true, false);
        } else if (!BlockUtil.isSafe(mc.player, onEChest ? 0 : -1, false)) {
            placeBlocks(mc.player.getPositionVector(), BlockUtil.getUnsafeBlockArray(mc.player, onEChest ? 0 : -1, false), false, false);
        }
        processExtendingBlocks();
        if (didPlace) {
            timer.reset();
        }
    }

    @Override
    public void onDisable() {
        isSneaking = PlayerUtil.stopSneaking(isSneaking);
    }

    private void processExtendingBlocks() {
        if (extendingBlocks.size() == 2 && extenders < 1) {
            Vec3d[] array = new Vec3d[2];
            int i = 0;
            for (Vec3d extendingBlock : extendingBlocks) {
                array[i] = extendingBlock;
                ++i;
            }
            int placementsBefore = placements;
            if (areClose(array) != null) {
                placeBlocks(areClose(array), EntityUtil.getUnsafeBlockArrayFromVec3d(areClose(array), 0, true), true, false);
            }
            if (placementsBefore < placements) {
                extendingBlocks.clear();
            }
        } else if (extendingBlocks.size() > 2 || extenders >= 1) {
            extendingBlocks.clear();
        }
    }

    private Vec3d areClose(Vec3d[] vec3ds) {
        int matches = 0;
        for (Vec3d vec3d : vec3ds) {
            for (Vec3d pos : EntityUtil.getUnsafeBlockArray(mc.player, 0, true)) {
                if (!vec3d.equals(pos)) continue;
                ++matches;
            }
        }
        if (matches == 2) {
            return mc.player.getPositionVector().add(vec3ds[0].add(vec3ds[1]));
        }
        return null;
    }

    private boolean placeBlocks(Vec3d pos, Vec3d[] vec3ds, boolean hasHelpingBlocks, boolean isHelping) {
        boolean gotHelp;
        for (Vec3d vec3d : vec3ds) {
            gotHelp = true;
            BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            switch (BlockUtil.isPositionPlaceable(position, false)) {
                case 1: {
                    if (retries.get(position) == null || retries.get(position) < 4) {
                        placeBlock(position);
                        retries.put(position, retries.get(position) == null ? 1 : retries.get(position) + 1);
                        retryTimer.reset();
                        continue;
                    }
                }
                case 2: {
                    if (!hasHelpingBlocks) continue;
                    gotHelp = placeBlocks(pos, BlockUtil.getHelpingBlocks(vec3d), false, true);
                }
                case 3: {
                    if (gotHelp) {
                        placeBlock(position);
                    }
                    if (!isHelping) continue;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check() {
        if (!startPos.equals(EntityUtil.getRoundedBlockPos(mc.player))) {
            toggle();
            return true;
        }
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int echestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        offHand = InventoryUtil.isBlock(mc.player.getHeldItemOffhand().getItem(), BlockObsidian.class);
        didPlace = false;
        extenders = 1;
        placements = 0;
        if (obbySlot == -1 && !offHand && echestSlot == -1) {
            toggle();
            return true;
        }
        isSneaking = PlayerUtil.stopSneaking(isSneaking);
        if (mc.player.inventory.currentItem != lastHotbarSlot && mc.player.inventory.currentItem != obbySlot && mc.player.inventory.currentItem != echestSlot) {
            lastHotbarSlot = mc.player.inventory.currentItem;
        }
        if (retryTimer.passedMs(2500L)) {
            retries.clear();
            retryTimer.reset();
        }
        return !timer.passedMs(delay.getValue());
    }

    private void placeBlock(BlockPos pos) {
        int originalSlot = mc.player.inventory.currentItem;
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int echestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        mc.player.inventory.currentItem = obbySlot == -1 ? echestSlot : obbySlot;
        mc.playerController.updateController();
        isSneaking = BlockUtil.placeBlock(pos, offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, rotate.getValue(), true, isSneaking);
        mc.player.inventory.currentItem = originalSlot;
        mc.playerController.updateController();
        didPlace = true;
        ++placements;
    }
}
