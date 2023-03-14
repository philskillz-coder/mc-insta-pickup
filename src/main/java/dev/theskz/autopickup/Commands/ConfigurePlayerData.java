package dev.theskz.autopickup.Commands;

import dev.theskz.autopickup.Main;
import dev.theskz.autopickup.Utils.DataManager;
import dev.theskz.autopickup.Utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ConfigurePlayerData implements Listener, CommandExecutor {
    private final String guiName = "AutoPickup Settings";
    private final DataManager dataManager;

    private final ItemStack autoBlockPickupEnabled = createItem("Blocks", Material.GRASS_BLOCK, "§7§l[§r §2§lENABLED§r §l§7]§r", "§r§oAutomatic block drop pickup", "Click to disable");
    private final ItemStack autoBlockPickupDisabled = createItem("Blocks", Material.GRASS_BLOCK, "§7§l[§r §4§lDISABLED§r §l§7]§r", "§r§oAutomatic block pickup", "Click to enable");
    private final ItemStack autoLootPickupEnabled = createItem("Loot", Material.ROTTEN_FLESH, "§7§l[§r §2§lENABLED§r §l§7]§r", "§r§oAutomatic mob drop pickup", "Click to disable");
    private final ItemStack autoLootPickupDisabled = createItem("Loot", Material.ROTTEN_FLESH, "§7§l[§r §4§lDISABLED§r §l§7]§r", "§r§oAutomatic mob drop pickup", "Click to enable");
    private final ItemStack autoExperiencePickupEnabled = createItem("Experience", Material.EXPERIENCE_BOTTLE, "§7§l[§r §2§lENABLED§r §l§7]§r", "§r§oAutomatic experience pickup", "Click to disable");
    private final ItemStack autoExperiencePickupDisabled = createItem("Experience", Material.EXPERIENCE_BOTTLE, "§7§l[§r §4§lDISABLED§r §l§7]§r", "§r§oAutomatic experience pickup", "Click to enable");



    private final List<UUID> openedGuis;


    public ConfigurePlayerData() {
        dataManager = Main.getInstance().getDataManager();
        openedGuis = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!openedGuis.contains(player.getUniqueId())) {
            return;
        }

        PlayerData data = dataManager.getPlayer(player.getUniqueId());
        Inventory gui = player.getOpenInventory().getTopInventory();

        int clickedSlot = event.getSlot();


        if (clickedSlot == 0) { // auto pickup block toggle
            data.setAutoBlockPickup(!data.isAutoBlockPickup());
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            if (data.isAutoBlockPickup()) {
                gui.setItem(0, autoBlockPickupEnabled);
            } else {
                gui.setItem(0, autoBlockPickupDisabled);
            }
        } else if (clickedSlot == 1) { // auto experience pickup toggle
            data.setAutoLootPickup(!data.isAutoLootPickup());
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            if (data.isAutoLootPickup()) {
                gui.setItem(1, autoLootPickupEnabled);
            } else {
                gui.setItem(1, autoLootPickupDisabled);
            }
        } else if (clickedSlot == 2) {
            data.setAutoExperiencePickup(!data.isAutoExperiencePickup());
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);

            if (data.isAutoExperiencePickup()) {
                gui.setItem(2, autoExperiencePickupEnabled);
            } else {
                gui.setItem(2, autoExperiencePickupDisabled);
            }
        }

        player.updateInventory();
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        openedGuis.remove(player.getUniqueId());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players");
            return true;
        }

        Player player = (Player) sender;

        PlayerData playerData = dataManager.getPlayer(player.getUniqueId());

        Inventory configInventory = Bukkit.createInventory(player, 9, guiName);
        if (playerData.isAutoBlockPickup()) {
            configInventory.setItem(0, autoBlockPickupEnabled);
        } else {
            configInventory.setItem(0, autoBlockPickupDisabled);
        }

        if (playerData.isAutoLootPickup()) {
            configInventory.setItem(1, autoLootPickupEnabled);
        } else {
            configInventory.setItem(1, autoLootPickupDisabled);
        }

        if (playerData.isAutoExperiencePickup()) {
            configInventory.setItem(2, autoExperiencePickupEnabled);
        } else {
            configInventory.setItem(2, autoExperiencePickupDisabled);
        }

        player.openInventory(configInventory);
        openedGuis.add(player.getUniqueId());

        return false;
    }

    private ItemStack createItem(String name, Material mat, String ... lore) {
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
