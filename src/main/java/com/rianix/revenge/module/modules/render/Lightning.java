package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.event.events.DeathEvent;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Lightning extends Module {
    public Lightning() {
        super("Lightning","",0,Category.RENDER);
    }

    SettingBoolean sound = register("Sound",true);

    @EventHandler
    private final Listener<DeathEvent> onEntityDeath = new Listener<>(event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }
        final EntityLightningBolt bolt = new EntityLightningBolt((World)mc.world, Double.longBitsToDouble(Double.doubleToLongBits(2.700619365101586E307) ^ 0x7FC33AA2E6830ED7L), Double.longBitsToDouble(Double.doubleToLongBits(4.288545480809007E306) ^ 0x7F986DA963B0A5BFL), Double.longBitsToDouble(Double.doubleToLongBits(3.3865723560928404E307) ^ 0x7FC81CFA62BC4207L), false);
        if (this.sound.getValue()) {
            mc.world.playSound(event.player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, Float.intBitsToFloat(Float.floatToIntBits(13.150525f) ^ 0x7ED2688D), Float.intBitsToFloat(Float.floatToIntBits(10.325938f) ^ 0x7EA5370B), false);
        }
        bolt.setLocationAndAngles(event.player.posX, event.player.posY, event.player.posZ, Float.intBitsToFloat(Float.floatToIntBits(3.2116163E38f) ^ 0x7F719D7B), Float.intBitsToFloat(Float.floatToIntBits(2.2278233E38f) ^ 0x7F279A51));
        mc.world.spawnEntity((Entity)bolt);
    });
}

