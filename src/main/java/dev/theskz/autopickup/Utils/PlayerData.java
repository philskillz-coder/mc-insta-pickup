package dev.theskz.autopickup.Utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private boolean autoBlockPickup;
    private boolean autoLootPickup;
    private boolean autoExperiencePickup;
    private boolean blockStorage;
    private boolean experienceStorage;
    private final List<ItemStack> storedItems;
    private int storedExperience;

    public PlayerData(UUID uuuid, boolean autoBlockPickup, boolean autoLootPickup, boolean autoExperiencePickup, boolean blockStorage, boolean experienceStorage, ItemStack[] storedItems, int storedExperience) {
        this.uuid = uuuid;
        this.autoBlockPickup = autoBlockPickup;
        this.autoLootPickup = autoLootPickup;
        this.autoExperiencePickup = autoExperiencePickup;
        this.blockStorage = blockStorage;
        this.experienceStorage = experienceStorage;
        this.storedItems = Arrays.asList(storedItems);
        this.storedExperience = storedExperience;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isAutoBlockPickup() {
        return autoBlockPickup;
    }

    public void setAutoBlockPickup(boolean autoBlockPickup) {
        this.autoBlockPickup = autoBlockPickup;
    }

    public boolean isAutoLootPickup() {
        return autoLootPickup;
    }

    public void setAutoLootPickup(boolean autoLootPickup) {
        this.autoLootPickup = autoLootPickup;
    }

    public boolean isAutoExperiencePickup() {
        return autoExperiencePickup;
    }

    public void setAutoExperiencePickup(boolean autoExperiencePickup) {
        this.autoExperiencePickup = autoExperiencePickup;
    }

    public boolean isBlockStorage() {
        return blockStorage;
    }

    public void setBlockStorage(boolean blockStorage) {
        this.blockStorage = blockStorage;
    }

    public boolean isExperienceStorage() {
        return experienceStorage;
    }

    public void setExperienceStorage(boolean experienceStorage) {
        this.experienceStorage = experienceStorage;
    }

    public void storeItem(ItemStack item) {
        this.storedItems.add(item);
    }

    public ItemStack[] getStoredItems() {
        return storedItems.toArray(new ItemStack[0]);
    }

    public void storeExperience(int experience) {
        this.storedExperience += experience;
    }

    public int getStoredExperience() {
        return storedExperience;
    }
}
