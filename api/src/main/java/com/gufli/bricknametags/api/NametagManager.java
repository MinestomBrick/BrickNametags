package com.gufli.bricknametags.api;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface NametagManager {

    void setNametag(@NotNull Player player, @NotNull Component prefix, @NotNull Component suffix);

    void setNametagPrefix(@NotNull Player player, @NotNull Component prefix);

    void setNametagSuffix(@NotNull Player player, @NotNull Component suffix);

    void clear(Player player);

}