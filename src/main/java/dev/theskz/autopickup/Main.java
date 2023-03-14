package dev.theskz.autopickup;

import dev.theskz.autopickup.Commands.ConfigurePlayerData;
import dev.theskz.autopickup.Listeners.MainListener;
import dev.theskz.autopickup.Utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Config config;
    private DataManager dataManager;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();

        dataManager = new DataManager(config);
    }

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new MainListener(), this);

        //noinspection SpellCheckingInspection
        Objects.requireNonNull(getCommand("autopickup")).setExecutor(new ConfigurePlayerData());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "AutoPickup Plugin loaded");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Saving data...");

        ServerData server = this.dataManager.getServer();
        this.config.getConfig().set("server.auto_block_pickup", server.isAutoBlockPickup());
        this.config.getConfig().set("server.auto_loot_pickup", server.isAutoLootPickup());
        this.config.getConfig().set("server.auto_experience_pickup", server.isAutoExperiencePickup());
        this.config.getConfig().set("server.block_storage", server.isBlockStorage());
        this.config.getConfig().set("server.experience_storage", server.isExperienceStorage());

        List<String> playerUuids = new ArrayList<>();

        for (PlayerData player: this.dataManager.getAllPlayers()) {
            playerUuids.add(player.getUuid().toString());
            this.config.getConfig().set("players." + player.getUuid() + ".auto_block_pickup", player.isAutoBlockPickup());
            this.config.getConfig().set("players." + player.getUuid() + ".auto_loot_pickup", player.isAutoLootPickup());
            this.config.getConfig().set("players." + player.getUuid() + ".auto_experience_pickup", player.isAutoExperiencePickup());
            this.config.getConfig().set("players." + player.getUuid() + ".block_storage", player.isBlockStorage());
            this.config.getConfig().set("players." + player.getUuid() + ".experience_storage", player.isExperienceStorage());
            this.config.getConfig().set("players." + player.getUuid() + ".stored_blocks", Items.itemStackArrayToBase64(player.getStoredItems()));
        }
        this.config.getConfig().set("player-uuids", playerUuids);

        this.config.save();
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Done!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "AutoPickup Plugin unloaded");

    }

    public static Main getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

}
