package com.rianix.revenge.event.events;

import com.rianix.revenge.event.Event;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;

public class RenderEntityLayerEvent extends Event {
    public EntityLivingBase entity;
    public LayerRenderer<?> layer;

    public RenderEntityLayerEvent(EntityLivingBase entity, LayerRenderer<?> layer) {
        this.entity = entity;
        this.layer = layer;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public void setEntity(final EntityLivingBase entityLivingBase) {
        this.entity = entityLivingBase;
    }

    public LayerRenderer<?> getLayer() {
        return this.layer;
    }

    public void setLayer(final LayerRenderer<?> layerRenderer) {
        this.layer = layerRenderer;
    }

    public EntityLivingBase component1() {
        return this.entity;
    }

    public LayerRenderer<?> component2() {
        return this.layer;
    }

    public RenderEntityLayerEvent copy(final EntityLivingBase entity, final LayerRenderer<?> layer) {
        return new RenderEntityLayerEvent(entity, layer);
    }

    public static RenderEntityLayerEvent copy$default(final RenderEntityLayerEvent renderEntityLayerEvent, EntityLivingBase entityLivingBase, LayerRenderer layerRenderer, final int n, final Object object) {
        if ((n & 0x1) != 0x0) {
            entityLivingBase = renderEntityLayerEvent.entity;
        }
        if ((n & 0x2) != 0x0) {
            layerRenderer = renderEntityLayerEvent.layer;
        }
        return renderEntityLayerEvent.copy(entityLivingBase, (LayerRenderer<?>)layerRenderer);
    }


    public String toString() {
        return "RenderEntityLayerEvent(entity=" + this.entity + ", layer=" + this.layer + ')';
    }

    public int hashCode() {
        int result2 = this.entity.hashCode();
        result2 = result2 * 31 + this.layer.hashCode();
        return result2;
    }

    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RenderEntityLayerEvent)) {
            return false;
        }
        final RenderEntityLayerEvent renderEntityLayerEvent = (RenderEntityLayerEvent)other;
        return this.entity == renderEntityLayerEvent.entity && this.layer == renderEntityLayerEvent.layer;
    }
}
