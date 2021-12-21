package com.rianix.revenge.mixin.mixins;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.modules.render.CameraClip;
import com.rianix.revenge.module.modules.render.NoRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class})
public abstract class MixinEntityRenderer {
    @Shadow
    @Final
    public Minecraft mc;

    @Inject(method={"hurtCameraEffect"}, at={@At(value="HEAD")}, cancellable=true)
    public void hurtCameraEffectHook(float ticks, CallbackInfo info) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled() && Revenge.moduleManager.getModuleByClass(NoRender.class).hurtCam.getValue()) {
            info.cancel();
        }
    }

    @Inject(method={"updateLightmap"}, at={@At(value="HEAD")}, cancellable=true)
    private void updateLightmap(float partialTicks, CallbackInfo info) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled() && Revenge.moduleManager.getModuleByClass(NoRender.class).skylight.getValue()) {
            info.cancel();
        }
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=3, at=@At(value="STORE", ordinal=0), require=1)
    public double changeCameraDistanceHook(double range) {
        if (Revenge.moduleManager.getModuleByClass(CameraClip.class).isToggled()) {
            return Revenge.moduleManager.getModuleByClass(CameraClip.class).fov.getValue();
        } else {
            return range;
        }
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=7, at=@At(value="STORE", ordinal=0), require=1)
    public double orientCameraHook(double range) {
        if (Revenge.moduleManager.getModuleByClass(CameraClip.class).isToggled()) {
            return Revenge.moduleManager.getModuleByClass(CameraClip.class).fov.getValue();
        } else {
            return range;
        }
    }
}
