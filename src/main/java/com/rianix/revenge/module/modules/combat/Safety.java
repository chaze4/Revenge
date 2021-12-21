package com.rianix.revenge.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.rianix.revenge.module.Module;
import com.rianix.revenge.util.BlockUtil;
import com.rianix.revenge.util.PlayerUtil;

public class Safety extends Module {
    public Safety() {
        super("Safety","",0,Category.COMBAT);
    }

    boolean safety = false;

    @Override
    public void onUpdate() {
        safety = mc.world.getBlockState(mc.player.getPosition()).getMaterial().isSolid() || PlayerUtil.isInHole();
    }

    @Override
    public String getDisplayInfo() {
        if (safety) {
            return ChatFormatting.GREEN + "SAFE";
        }
        else {
            return ChatFormatting.RED + "UNSAFE";
        }
    }
}
