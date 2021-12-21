package com.rianix.revenge.module.modules.render;

import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;

public class PenisEsp extends Module {
    boolean enabled;
    private float spin;
    private float cumsize;
    private float amount;
    public PenisEsp() {
        super("PenisEsp", "Fan module", 0, Category.RENDER);
    }

    public void onEnable() {
        this.spin = 0.0F;
        this.cumsize = 0.0F;
        this.amount = 0.0F;
        enabled = true;
    }

    @Override
    public void onDisable() {
        enabled = false;
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        if (enabled) {
            Iterator iterator = mc.world.loadedEntityList.iterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                if (o instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) o;
                    double x2 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) mc.getRenderPartialTicks();
                    double x = x2 - mc.getRenderManager().viewerPosX;
                    double y2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) mc.getRenderPartialTicks();
                    double y = y2 - mc.getRenderManager().viewerPosY;
                    double z2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) mc.getRenderPartialTicks();
                    double z = z2 - mc.getRenderManager().viewerPosZ;

                    GL11.glPushMatrix();
                    RenderHelper.disableStandardItemLighting();
                    RenderUtil.drawPenis(player, x, y, z, this.spin, this.cumsize, this.amount);
                    RenderHelper.enableStandardItemLighting();
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
