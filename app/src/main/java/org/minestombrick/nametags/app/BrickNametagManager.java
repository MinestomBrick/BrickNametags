package org.minestombrick.nametags.app;

import org.minestombrick.nametags.api.NametagManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.minestombrick.placeholders.api.PlaceholderAPI;
import org.minestombrick.scheduler.api.SchedulerAPI;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class BrickNametagManager implements NametagManager {

    private final static PlainTextComponentSerializer PLAIN_TEXT = PlainTextComponentSerializer.plainText();

    private final static String UNIQUEID = "BRNTGS";
    private long COUNTER = 0;

    private final Map<Player, BrickNametag> nametags = new ConcurrentHashMap<>();

    BrickNametagManager() {
        SchedulerAPI.get().asyncRepeating(() -> nametags.keySet().forEach(this::refresh),
                100, TimeUnit.MILLISECONDS);
    }

    // API

    @Override
    public void setNametag(@NotNull Player player, @NotNull Component prefix, @NotNull Component suffix) {
        BrickNametag nametag = new BrickNametag(prefix, suffix);
        nametags.put(player, nametag);
        refresh(player);
    }

    @Override
    public void setNametagPrefix(@NotNull Player player, @NotNull Component prefix) {
        BrickNametag previous = nametags.get(player);
        if (previous != null) {
            setNametag(player, prefix, previous.suffix());
            return;
        }
        setNametag(player, prefix, Component.text(""));
    }

    @Override
    public void setNametagSuffix(@NotNull Player player, @NotNull Component suffix) {
        BrickNametag previous = nametags.get(player);
        if (previous != null) {
            setNametag(player, previous.prefix(), suffix);
            return;
        }
        setNametag(player, Component.text(""), suffix);
    }

    @Override
    public void clear(Player player) {
        nametags.remove(player);
        leaveTeam(player);
    }

    // INTERNAL

    private void refresh(Player player) {
        BrickNametag nametag = nametags.get(player);
        if (nametag == null) {
            return;
        }

        Component prefix = nametag.prefix();
        if (MinecraftServer.getExtensionManager().hasExtension("brickplaceholders")) {
            prefix = PlaceholderAPI.get().replace(player, prefix);
        }

        Component suffix = nametag.suffix();
        if (MinecraftServer.getExtensionManager().hasExtension("brickplaceholders")) {
            suffix = PlaceholderAPI.get().replace(player, suffix);
        }

        String strPrefix = PLAIN_TEXT.serialize(prefix);
        String strSuffix = PLAIN_TEXT.serialize(prefix);

        // If player is already in the team -> ignore
        Team previous = findTeam(player);
        if (previous != null && checkSimilar(previous, strPrefix, strSuffix)) {
            return;
        }

        // Remove from old team
        leaveTeam(player);

        // Do not update if the prefix or suffix are empty
        if (strPrefix.equals("") && strSuffix.equals("")) {
            return;
        }

        Team team = findTeam(strPrefix, strSuffix);
        if (team != null) {
            // Team already exists
            team.addMember(player.getUsername());
            team.sendUpdatePacket();
            return;
        }

        // Team doesn't exist
        NamedTextColor color = NamedTextColor.WHITE;
        TextColor lastColor = prefix.style().color();
        if (lastColor != null) {
            color = NamedTextColor.nearestTo(lastColor);
        }

        String name = UNIQUEID + (COUNTER++);
        team = MinecraftServer.getTeamManager().createTeam(name, Component.text(name), prefix, color, suffix);
        team.addMember(player.getUsername());
    }

    private void leaveTeam(Player player) {
        Team team = findTeam(player);
        if (team == null) {
            return;
        }
        team.removeMember(player.getUsername());
        if (team.getMembers().isEmpty()) {
            MinecraftServer.getTeamManager().deleteTeam(team);
        }
    }

    private boolean checkSimilar(Team team, String prefix, String suffix) {
        return PLAIN_TEXT.serialize(team.getPrefix()).equals(prefix)
                && PLAIN_TEXT.serialize(team.getSuffix()).equals(suffix);
    }

    private Team findTeam(String prefix, String suffix) {
        return MinecraftServer.getTeamManager().getTeams().stream()
                .filter(team -> checkSimilar(team, prefix, suffix))
                .findFirst().orElse(null);
    }

    private Team findTeam(Player player) {
        return MinecraftServer.getTeamManager().getTeams().stream()
                .filter(t -> t.getMembers().contains(player.getUsername()))
                .findFirst().orElse(null);
    }
}
