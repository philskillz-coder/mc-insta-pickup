package dev.theskz.autopickup.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DataManager {

    private final ServerData server;
    private final Map<UUID, PlayerData> players;

    public DataManager(Config config) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Loading Data...");
        YamlConfiguration yaml = config.getConfig();
        this.server = new ServerData(
                yaml.getBoolean("server.auto_block_pickup", false),
                yaml.getBoolean("server.auto_loot_pickup", false),
                yaml.getBoolean("server.auto_experience_pickup", false),
                yaml.getBoolean("server.block_storage", false),
                yaml.getBoolean("server.experience_storage")
        );

        this.players = new HashMap<>();
        List<String> players = yaml.getStringList("player-uuids");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Loading " + players.size() + " players...");

        for (String uuidString : players) {
            this.players.put(
                    UUID.fromString(uuidString), new PlayerData(
                            UUID.fromString(uuidString),
                            yaml.getBoolean("players." + uuidString + ".auto_block_pickup", false),
                            yaml.getBoolean("players." + uuidString + ".auto_loot_pickup", false),
                            yaml.getBoolean("players." + uuidString + ".auto_experience_pickup", false),
                            yaml.getBoolean("players." + uuidString + ".block_storage", false),
                            yaml.getBoolean("players." + uuidString + ".experience_storage", false),
                            Items.itemStackArrayFromBase64(
                                    yaml.getString("players." + uuidString + ".stored_blocks")
                            ),
                            yaml.getInt("players." + uuidString + ".stored_experience", 0)
                    )
            );
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Done!");

    }

    public ServerData getServer() {
        return server;
    }


    public boolean PlayerExists(UUID playerUuid) {
        return this.players.containsKey(playerUuid);
    }

    public void CreatePlayer(UUID playerUuid) {
        PlayerData data = new PlayerData(
                playerUuid,
                false,
                false,
                false,
                false,
                false,
                new ItemStack[]{},
                0
        );

        this.players.put(
                data.getUuid(),
                data
        );

    }

    public PlayerData getPlayer(UUID playerUuid) {
        return this.players.get(playerUuid);
    }

    public List<PlayerData> getAllPlayers() {
        return new ArrayList<>(this.players.values());
    }

}
