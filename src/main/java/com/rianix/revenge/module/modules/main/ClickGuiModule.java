package com.rianix.revenge.module.modules.main;

import com.rianix.revenge.Revenge;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.setting.settings.SettingBoolean;
import com.rianix.revenge.setting.settings.SettingColor;
import com.rianix.revenge.setting.settings.SettingMode;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGui", "Displays Gui screen.", Keyboard.KEY_RSHIFT, Category.MAIN);
        miscModeList.add("Dots");
        miscModeList.add("PlusMinus");
        miscModeList.add("None");
    }

    ArrayList<String> miscModeList = new ArrayList<String>();
    public final SettingColor color = this.register("Color", Color.GREEN, false);
    public SettingMode misc = this.register("Render", miscModeList ,"PlusMinus");

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Revenge.instance.clickGui);
        this.toggle();
    }
}
