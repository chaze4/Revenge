package com.rianix.revenge.mixin.mixins;

import com.google.common.base.Stopwatch;
import com.rianix.revenge.Revenge;
import com.rianix.revenge.manager.ConfigManager;
import com.rianix.revenge.mixin.mixins.accessor.AccessorEntityPlayerSP;
import com.rianix.revenge.module.modules.render.NoRender;
import com.rianix.revenge.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.crash.CrashReport;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    public EntityPlayerSP player;
    @Shadow
    public PlayerControllerMP playerController;

    private boolean handActive = false;
    private boolean isHittingBlock = false;

    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    public void rightClickMousePre(CallbackInfo ci) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled()) {
            isHittingBlock = playerController.getIsHittingBlock();
            playerController.isHittingBlock = false;
        }
    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    public void rightClickMousePost(CallbackInfo ci) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled() && !playerController.getIsHittingBlock()) {
            playerController.isHittingBlock = isHittingBlock;
        }
    }

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    public void sendClickBlockToControllerPre(boolean leftClick, CallbackInfo ci) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled()) {
            handActive = player.isHandActive();
            ((AccessorEntityPlayerSP) player).setHandActive(false);
        }
    }

    @Inject(method = "sendClickBlockToController", at = @At("RETURN"))
    public void sendClickBlockToControllerPost(boolean leftClick, CallbackInfo ci) {
        if (Revenge.moduleManager.getModuleByClass(NoRender.class).isToggled() && !player.isHandActive()) {
            ((AccessorEntityPlayerSP) player).setHandActive(handActive);
        }
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void onShutdown(CallbackInfo ci) {
        Stopwatch watch = Stopwatch.createStarted();
        ConfigManager.save();
        System.out.printf("revenge save config took %sms", watch.stop());
    }

    @Inject(method = "crashed", at = @At("HEAD"))
    public void onCrash(CrashReport crash, CallbackInfo ci) {
        Stopwatch watch = Stopwatch.createStarted();
        ConfigManager.save();
        System.out.printf("revenge save config took %sms", watch.stop());
    }

    @Redirect(method={"createDisplay"}, at=@At(value="INVOKE", target="Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private void createDisplay(String title) {
        Display.setTitle((String)"Revenge 1.6");
    }
}
