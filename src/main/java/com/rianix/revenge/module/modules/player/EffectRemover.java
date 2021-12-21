package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.potion.Potion;

import java.util.Objects;

public class EffectRemover extends Module {
    SettingBoolean levitation = register("Levitation", true);
    SettingBoolean jumpBoost = register("JumpBoost", true);
    public EffectRemover() {
        super("EffectRemover", "Removes the effects from you.", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (levitation.getValue()) {
            if (mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation("levitation")))) {
                mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("levitation"));
            }
        }
        if (jumpBoost.getValue()) {
            if (mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation("jump_boost")))) {
                mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("jump_boost"));
            }
        }
    }
}
