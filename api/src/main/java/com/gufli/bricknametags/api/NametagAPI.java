package com.gufli.bricknametags.api;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus;

public class NametagAPI {

    private static NametagManager nametagManager;

    @ApiStatus.Internal
    public static void setNametagManager(NametagManager manager) {
        nametagManager = manager;
    }

    //

    public static void setNametag(Player player, Component prefix, Component suffix) {
        nametagManager.setNametag(player, prefix, suffix);
    }

    public static void setNametagPrefix(Player player, Component prefix) {
        nametagManager.setNametagPrefix(player, prefix);
    }

    public static void setSuffix(Player player, Component suffix) {
        nametagManager.setNametagSuffix(player, suffix);
    }

    public static void clear(Player player) {
        nametagManager.clear(player);
    }

}
