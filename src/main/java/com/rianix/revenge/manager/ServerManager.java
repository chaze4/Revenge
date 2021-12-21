package com.rianix.revenge.manager;

import com.rianix.revenge.util.Global;
import java.util.Objects;

public class ServerManager implements Global {

    public int getPing() {
        if (mc.player == null || mc.world == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.getConnection().getGameProfile().getId()).getResponseTime();
        } catch (Exception e) {
            return 0;
        }
    }
}
