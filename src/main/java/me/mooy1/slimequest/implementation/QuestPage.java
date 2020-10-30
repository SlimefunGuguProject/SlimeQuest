package me.mooy1.slimequest.implementation;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class QuestPage {
    private final ItemStack item;

    @ParametersAreNonnullByDefault
    protected QuestPage(ItemStack item) {
        this.item = item;
    }

    public abstract void onOpen(@Nonnull Player p, @Nonnull ChestMenu menu);

    @Nonnull
    protected ItemStack getItem() {
        return item;
    }

    @Nonnull
    private ItemStack makeQuestItem(@Nonnull ItemStack item) {
        return item;
    }

    @Nonnull
    private ChestMenu.MenuClickHandler makeQuestHandler() {
        return (ChestMenu.MenuClickHandler) (player, i, itemStack, clickAction) -> false;
    }
}
