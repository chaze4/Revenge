package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.mixin.mixins.accessor.IMinecraftMixin;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastPlace extends Module {
    SettingBoolean everything = register("Everything", false);
    SettingBoolean blocks = register("Blocks", false);
    SettingBoolean crystals = register("Crystals", true);
    SettingBoolean exp = register("Exp", true);
    public FastPlace() {
        super("FastPlace", "", 0, Category.MISC);
    }

    @Override
    public void onUpdate() {
        if (everything.getValue()) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
        if (exp.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
        if (blocks.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock || mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
        if (crystals.getValue() && mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal || mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal) {
            ((IMinecraftMixin)mc).setRightClickDelayTimerAccessor(0);
        }
    }
}
