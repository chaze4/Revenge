package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingInteger;
import com.rianix.revenge.util.Timer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class Replenish extends Module {
    public Replenish() {
        super("Replenish","",0,Category.MISC);
    }

    private final SettingInteger threshold  = register("Threshold", 64, 1, 64);
    private final SettingInteger delay = register("Delay", 0, 0, 10);
    private final Timer timer = new Timer();
    private final ArrayList<Item> Hotbar = new ArrayList();

    @Override
    public void onEnable() {
        if (nullCheck()) {
            return;
        }
        Hotbar.clear();
        for (int l_I = 0; l_I < 9; ++l_I) {
            ItemStack l_Stack = Replenish.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Stack.isEmpty() && !Hotbar.contains(l_Stack.getItem())) {
                Hotbar.add(l_Stack.getItem());
                continue;
            }
            Hotbar.add(Items.AIR);
        }
    }

    @Override
    public void onUpdate() {
        if (Replenish.mc.currentScreen != null) {
            return;
        }
        if (!timer.passedMs(delay.getValue() * 1000L)) {
            return;
        }
        for (int l_I = 0; l_I < 9; ++l_I) {
            if (!RefillSlotIfNeed(l_I)) continue;
            timer.reset();
            return;
        }
    }

    private boolean RefillSlotIfNeed(int p_Slot) {
        ItemStack l_Stack = Replenish.mc.player.inventory.getStackInSlot(p_Slot);
        if (l_Stack.isEmpty() || l_Stack.getItem() == Items.AIR) {
            return false;
        }
        if (!l_Stack.isStackable()) {
            return false;
        }
        if (l_Stack.getCount() >= threshold.getValue()) {
            return false;
        }
        for (int l_I = 9; l_I < 36; ++l_I) {
            ItemStack l_Item = Replenish.mc.player.inventory.getStackInSlot(l_I);
            if (l_Item.isEmpty() || !CanItemBeMergedWith(l_Stack, l_Item)) continue;
            Replenish.mc.playerController.windowClick(Replenish.mc.player.inventoryContainer.windowId, l_I, 0, ClickType.QUICK_MOVE, Replenish.mc.player);
            Replenish.mc.playerController.updateController();
            return true;
        }
        return false;
    }

    private boolean CanItemBeMergedWith(ItemStack p_Source, ItemStack p_Target) {
        return p_Source.getItem() == p_Target.getItem() && p_Source.getDisplayName().equals(p_Target.getDisplayName());
    }
}
