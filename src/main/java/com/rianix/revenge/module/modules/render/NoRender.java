package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", "No render things", 0, Category.RENDER);
    }

    public SettingBoolean armor = register("Armor",true);
    SettingBoolean fog = register("Fog",true);
    SettingBoolean weather = register("Weather", true);
    SettingBoolean viewBobbing = register("ViewBobbing", true);
    SettingBoolean items = register("Items", false);
    SettingBoolean overlay = register("Overlay", true);
    public SettingBoolean skylight = register("Skylight",true);
    public SettingBoolean hurtCam = register("HurtCamera",true);

    @Override
    public void onUpdate() {
        if ((weather.getValue()) && mc.world.isRaining()) {
            mc.world.setRainStrength(0);
        }
        if (items.getValue()) {
            mc.world.loadedEntityList.stream().filter(EntityItem.class::isInstance).map(EntityItem.class::cast).forEach(Entity::setDead);
        }
    }

    @Override
    public void onEnable() {
        if (viewBobbing.getValue()) {
            mc.gameSettings.viewBobbing = false;
        }
    }

    @Override
    public void onDisable() {
        if (viewBobbing.getValue()) {
            mc.gameSettings.viewBobbing = true;
        }
    }

    @SubscribeEvent
    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (fog.getValue()) {
            event.setDensity(0.0F);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
        if (overlay.getValue()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEvent(RenderGameOverlayEvent event) {
        if (overlay.getValue() && event.getType().equals(RenderGameOverlayEvent.ElementType.HELMET) || event.getType().equals(RenderGameOverlayEvent.ElementType.PORTAL)) {
            event.setCanceled(true);
        }
    }
}
