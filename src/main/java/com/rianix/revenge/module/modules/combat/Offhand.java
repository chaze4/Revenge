package com.rianix.revenge.module.modules.combat;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingDouble;
import com.rianix.revenge.setting.settings.SettingMode;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

import java.util.ArrayList;

public class Offhand extends Module {
    public Offhand() {
        super("Offhand","",0,Category.COMBAT);
        modeList.add("Totem");
        modeList.add("Crystal");
        modeList.add("Gapple");
    }

    ArrayList<String> modeList = new ArrayList<>();

    SettingMode mode = register("Mode",modeList,"Totem");
    SettingBoolean gapSword = register("GapSword",true);
    SettingDouble totemHp = register("HP",14.5,0,36);

    @Override
    public void onUpdate() {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {
            float hp = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            if (hp > totemHp.getValue()) {
                if (mode.getValue().equals("Crystal") && !(((gapSword.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && mc.gameSettings.keyBindUseItem.isKeyDown()))) {
                    swapItems(getItemSlot(Items.END_CRYSTAL));
                    return;
                } else if (((gapSword.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && mc.gameSettings.keyBindUseItem.isKeyDown())) {
                    swapItems(getItemSlot(Items.GOLDEN_APPLE));
                    return;
                }
                if (mode.getValue().equals("Totem")) {
                    swapItems(getItemSlot(Items.TOTEM_OF_UNDYING));
                    return;
                }
                if (mode.getValue().equals("Gapple")) {
                    swapItems(getItemSlot(Items.GOLDEN_APPLE));
                    return;
                }
            } else {
                swapItems(getItemSlot(Items.TOTEM_OF_UNDYING));
                return;
            }
            if (mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                swapItems(getItemSlot(Items.TOTEM_OF_UNDYING));
            }
        }
    }

    public void swapItems(int slot) {
        if (slot == -1 || mc.player.inventory.getStackInSlot(slot).getItem() != Items.TOTEM_OF_UNDYING) return;
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.updateController();
    }

    private int getItemSlot(Item input) {
        if (input == mc.player.getHeldItemOffhand().getItem()) return -1;
        for (int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    if (input == Items.GOLDEN_APPLE) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }

    @Override
    public String getDisplayInfo() {
        return this.mode.getValue();
    }
}
