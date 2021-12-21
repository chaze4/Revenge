package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingMode;
import com.rianix.revenge.util.*;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CityBlocker extends Module {
    public CityBlocker() {
        super("CityBlocker","",0,Category.COMBAT);
    }

    Map<BlockPos, Integer> retries = new HashMap<BlockPos, Integer>();
    Timer retryTimer = new Timer();

    SettingBoolean rotate = register("Rotate",false);

    @Override
    public void onEnable() {
        retries.clear();
        retryTimer.reset();
    }

    @Override
    public void onUpdate() {
        if (retryTimer.passedMs(2500L)) {
            retries.clear();
            retryTimer.reset();
        }
        final BlockPos cp = addPos(Objects.requireNonNull(RotationUtil.getDirection4DCityPos()));
        int originalSlot = mc.player.inventory.currentItem;
        int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        int eChestSot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1 && eChestSot == -1) {
            toggle();
            return;
        }
        mc.player.inventory.currentItem = obbySlot == -1 ? eChestSot : obbySlot;
        mc.playerController.updateController();
        if (BlockUtil.isPositionPlaceable(cp, false) == 1) {
            if (retries.get(cp) == null || retries.get(cp) < 4) {
                BlockUtil.placeBlock(cp, EnumHand.MAIN_HAND, rotate.getValue(), true, false);
                retries.put(cp, retries.get(cp) == null ? 1 : retries.get(cp) + 1);
                retryTimer.reset();
            }
        } else {
            BlockUtil.placeBlock(cp, EnumHand.MAIN_HAND, rotate.getValue(), true, false);
            toggle();
        }
        mc.player.inventory.currentItem = originalSlot;
        mc.playerController.updateController();
    }

    private BlockPos addPos(final BlockPos pos){
        final BlockPos pPos = PlayerUtil.getPlayerPos(0.2);
        return new BlockPos(pPos.getX() + pos.getX(), pPos.getY() + pos.getY(), pPos.getZ() + pos.getZ());
    }
}
