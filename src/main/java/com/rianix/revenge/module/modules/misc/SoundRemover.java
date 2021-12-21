package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundRemover extends Module {
    SettingBoolean fireworks = register("Fireworks", false);
    SettingBoolean bats = register("Bats", false);
    SettingBoolean snowballs = register("Snowballs", false);
    public SoundRemover() {
        super("SoundRemover", "Removes sounds", 0, Category.MISC);
    }

    @SubscribeEvent
    public void onPlaySound(final PlaySoundAtEntityEvent event) {
        if ((bats.getValue() && event.getSound().equals(SoundEvents.ENTITY_BAT_AMBIENT)) || event.getSound().equals(SoundEvents.ENTITY_BAT_DEATH) || event.getSound().equals(SoundEvents.ENTITY_BAT_HURT) || event.getSound().equals(SoundEvents.ENTITY_BAT_LOOP) || event.getSound().equals(SoundEvents.ENTITY_BAT_TAKEOFF)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
        if ((fireworks.getValue() && event.getSound().equals(SoundEvents.ENTITY_FIREWORK_BLAST)) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_BLAST_FAR) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_LARGE_BLAST) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_SHOOT) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_LAUNCH) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_TWINKLE) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_TWINKLE_FAR) || event.getSound().equals(SoundEvents.ENTITY_FIREWORK_LARGE_BLAST_FAR)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
        if ((snowballs.getValue() && event.getSound().equals(SoundEvents.ENTITY_SNOWBALL_THROW))) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
    }
}
