package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class FogColor extends Module {
    public FogColor() {
        super("FogColor","",0,Category.RENDER);
    }

    SettingColor overworldColor = register("Overworld", Color.GREEN,false);
    SettingColor netherColor = register("Nether", Color.GREEN,false);
    SettingColor endColor = register("End", Color.GREEN,false);

    @SubscribeEvent
    public void fogColors(final EntityViewRenderEvent.FogColors event) {
        switch (mc.player.dimension) {
            case -1:
                event.setRed(netherColor.getAWTValue(true).getRed() / 255f);
                event.setGreen(netherColor.getAWTValue(true).getGreen() / 255f);
                event.setBlue(netherColor.getAWTValue(true).getBlue() / 255f);
            case 1:
                event.setRed(endColor.getAWTValue(true).getRed() / 255f);
                event.setGreen(endColor.getAWTValue(true).getGreen() / 255f);
                event.setBlue(endColor.getAWTValue(true).getBlue() / 255f);
            case 0:
                event.setRed(overworldColor.getAWTValue(true).getRed() / 255f);
                event.setGreen(overworldColor.getAWTValue(true).getGreen() / 255f);
                event.setBlue(overworldColor.getAWTValue(true).getBlue() / 255f);
        }
    }
}
