package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class SilentEXP extends Module {
    SettingInteger tickDelay = this.register("TickDelay", 1, 0, 10);
    int prvSlot;
    private int delayStep = 0;
    public SilentEXP() {
        super("SilentEXP", "", 0, Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (mc.currentScreen == null && InventoryUtil.findItemInHotbar(Items.EXPERIENCE_BOTTLE) != -1) {
            if (delayStep < tickDelay.getValue()) {
                delayStep++;
                return;
            }
            delayStep = 0;
            prvSlot = mc.player.inventory.currentItem;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(findExpInHotbar()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.inventory.currentItem = prvSlot;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(prvSlot));
        }
    }

    private int findExpInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}
