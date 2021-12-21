package com.rianix.revenge.event.events;

import com.rianix.revenge.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class DeathEvent extends Event {
    public EntityPlayer player;

    public DeathEvent(EntityPlayer player) {
        this.player = player;
    }
}
