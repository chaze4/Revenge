package com.rianix.revenge.module.modules.player;

import com.rianix.revenge.module.Module;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;

public class MCP extends Module {
    private boolean clicked = false;

    public MCP() {
        super("MCP", "Middle click pearl.", 0, Category.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!clicked) {
                throwPearl();
            }
            clicked = true;
        } else {
            clicked = false;
        }
    }

    private void throwPearl() {
        int oldslot = mc.player.inventory.currentItem;
        for (int i = 0; i < 9; ++i) {
            ItemStack Stack = mc.player.inventory.getStackInSlot(i);
            if (mc.player.inventory.getStackInSlot(i).isEmpty())
                continue;
            if (Stack.getItem() instanceof ItemEnderPearl) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(i));
                mc.playerController.updateController();
                break;
            }
        }
        mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
        mc.player.connection.sendPacket(new CPacketHeldItemChange(oldslot));
        mc.playerController.updateController();
    }
}
