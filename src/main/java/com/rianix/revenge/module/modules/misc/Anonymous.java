package com.rianix.revenge.module.modules.misc;

import com.rianix.revenge.command.Messages;
import com.rianix.revenge.event.events.PacketEvent;
import com.rianix.revenge.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ChatType;

public class Anonymous extends Module {
    public Anonymous() {
        super("Anonymous", "", 0, Category.MISC);
    }

    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat) event.getPacket();
            if (packet.getType() != ChatType.GAME_INFO && this.getChatNames(packet.getChatComponent().getFormattedText(), packet.getChatComponent().getUnformattedText())) {
                event.cancel();
            }
        }
    });

    private boolean getChatNames(String message, final String unformatted) {
        String out = message;
            if (mc.player == null) {
                return false;
            }
            out = out.replace(mc.player.getName(), "Player");
        Messages.sendSilentMessage(out);
        return true;
    }

    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team) networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        dname = dname.replace(mc.player.getName(), "Player");
        return dname;
    }
}
