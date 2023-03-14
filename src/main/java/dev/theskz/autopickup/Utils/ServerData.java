package dev.theskz.autopickup.Utils;

public class ServerData {
    private boolean autoBlockPickup;
    private boolean autoLootPickup;
    private boolean autoExperiencePickup;

    private boolean blockStorage;
    private boolean experienceStorage;

    public ServerData(boolean autoBlockPickup, boolean autoLootPickup, boolean autoExperiencePickup, boolean blockStorage, boolean experienceStorage) {
        this.autoBlockPickup = autoBlockPickup;
        this.autoLootPickup = autoLootPickup;
        this.autoExperiencePickup = autoExperiencePickup;
        this.blockStorage = blockStorage;
        this.experienceStorage = experienceStorage;
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
}
