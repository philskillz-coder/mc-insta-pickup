package dev.theskz.autopickup.Listeners;

import dev.theskz.autopickup.Main;
import dev.theskz.autopickup.Utils.DataManager;
import dev.theskz.autopickup.Utils.PlayerData;
import dev.theskz.autopickup.Utils.ServerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


public class MainListener implements Listener {

    private final DataManager dataManager;

    public MainListener() {
        dataManager = Main.getInstance().getDataManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!dataManager.PlayerExists(player.getUniqueId())) {
            dataManager.CreatePlayer(player.getUniqueId());
        }
    }

    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent event) {
        Player player = event.getPlayer();

        ServerData serverData = dataManager.getServer();
        PlayerData playerData = dataManager.getPlayer(player.getUniqueId());

        // block items
        if (serverData.isAutoBlockPickup() && playerData.isAutoBlockPickup()) {

            Inventory inventory = player.getInventory();
            for (Item item: event.getItems()) {
                Map<Integer, ItemStack> notFit = inventory.addItem(item.getItemStack());

                if (!notFit.isEmpty()) {
                    player.sendMessage(ChatColor.RED + "Some items didn't fit in your inventory!");
                    System.out.println(player.getName() + " didnt had enough inventory space -> dropping items");
                    Location loc = event.getBlock().getLocation();

                    for (Map.Entry<Integer, ItemStack> entry : notFit.entrySet()) {
                        player.getWorld().dropItemNaturally(loc, entry.getValue());
                    }
                }
            }

            event.getItems().clear();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        ServerData serverData = dataManager.getServer();
        PlayerData playerData = dataManager.getPlayer(player.getUniqueId());

        // block experience
        if (serverData.isAutoExperiencePickup() && playerData.isAutoExperiencePickup()) {
            int exp;
            if ((exp = event.getExpToDrop()) > 0) {
                player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(exp);
                event.setExpToDrop(0);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }

        ServerData serverData = dataManager.getServer();
        PlayerData playerData = dataManager.getPlayer(player.getUniqueId());

        // loot items
        if (serverData.isAutoLootPickup() && playerData.isAutoLootPickup()) {
            Inventory inventory = player.getInventory();
            for (ItemStack item: event.getDrops()) {
                Map<Integer, ItemStack> notFit = inventory.addItem(item);

                if (!notFit.isEmpty()) {
                    player.sendMessage(ChatColor.RED + "Some items didn't fit in your inventory!");
                    System.out.println(player.getName() + " didnt had enough inventory space -> dropping items");
                    Location loc = event.getEntity().getLocation();

                    for (Map.Entry<Integer, ItemStack> entry : notFit.entrySet()) {
                        player.getWorld().dropItemNaturally(loc, entry.getValue());
                    }
                }
            }

            event.getDrops().clear();
        }

        // loot experience
        if (serverData.isAutoExperiencePickup() && playerData.isAutoExperiencePickup()) {
            int exp;
            if ((exp = event.getDroppedExp()) > 0) {
                player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(exp);
                event.setDroppedExp(0);
            }
        }
    }
}
