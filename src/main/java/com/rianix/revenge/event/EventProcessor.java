package com.rianix.revenge.event;

import com.rianix.revenge.Revenge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventProcessor {

    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {

    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {

    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Revenge.EVENT_BUS.post(event);
    }
}
