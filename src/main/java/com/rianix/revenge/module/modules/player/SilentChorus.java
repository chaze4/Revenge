package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class SilentChorus extends Module {
    public SilentChorus() {
        super("SilentChorus","",0,Category.PLAYER);
    }

    int oldSlot;

    @Override
    public void onEnable() {
        oldSlot = mc.player.inventory.currentItem;
    }

    @Override
    public void onUpdate() {
        int slot = InventoryUtil.findItemInHotbar(Items.CHORUS_FRUIT);
        if (InventoryUtil.findItemInHotbar(Items.CHORUS_FRUIT) != -1) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            toggle();
        }
    }
}
