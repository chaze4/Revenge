package com.rianix.revenge.module;

import com.rianix.revenge.module.modules.combat.*;
import com.rianix.revenge.module.modules.main.*;
import com.rianix.revenge.module.modules.misc.*;
import com.rianix.revenge.module.modules.movement.*;
import com.rianix.revenge.module.modules.player.*;
import com.rianix.revenge.module.modules.render.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ModuleManager {

    public ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<Module>();
        MinecraftForge.EVENT_BUS.register(this);
        init();
    }

    public void init() {
        //COMBAT
        modules.add(new Burrow());
        modules.add(new CityBlocker());
        modules.add(new FastBow());
        modules.add(new KillAura());
        modules.add(new Offhand());
        modules.add(new Safety());
        modules.add(new SCAR20());
        modules.add(new SilentEXP());
        modules.add(new Surround());
        //MAIN
        modules.add(new ClickGuiModule());
        modules.add(new Globals());
        modules.add(new HUD());
        //MISC
        modules.add(new Anonymous());
        modules.add(new AutoFish());
        modules.add(new BoatBypass());
        modules.add(new FastPlace());
        modules.add(new MultiTask());
        modules.add(new PacketUse());
        modules.add(new Replenish());
        modules.add(new SoundRemover());
        //MOVEMENT
        modules.add(new EntitySpeed());
        modules.add(new NoSlow());
        modules.add(new PhaseWalk());
        modules.add(new ReverseStep());
        modules.add(new RotationLock());
        modules.add(new Sprint());
        modules.add(new Step());
        //PLAYER
        modules.add(new AntiHunger());
        modules.add(new AntiVoid());
        modules.add(new AutoRespawn());
        modules.add(new DeathExplorer());
        modules.add(new EffectRemover());
        modules.add(new Bot());
        modules.add(new MCF());
        modules.add(new MCP());
        modules.add(new EntityVanish());
        modules.add(new SilentChorus());
        modules.add(new Velocity());
        //RENDER
        modules.add(new CameraClip());
        modules.add(new CustomTime());
        modules.add(new FogColor());
        modules.add(new FullBright());
        modules.add(new Lightning());
        modules.add(new NoRender());
        modules.add(new PenisEsp());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public <R extends Module> R getModuleByClass(Class<R> clazz) {
        return (R) modules.stream().filter(m -> m.getClass().equals(clazz)).findFirst().orElse(null);
    }

    public ArrayList<Module> getModsInCategory(Module.Category cat) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getCategory() == cat) {
                mods.add(m);
            }
        }
        return mods;
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (Module m : modules) {
                if (m.getKey() == Keyboard.getEventKey()) {
                    m.toggle();
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null)
            return;
        for (Module m : modules) {
            if (m.isToggled()) {
                m.onUpdate();
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        for (Module m : modules) {
            if (m.isToggled()) {
                m.render();
            }
        }
    }
}
