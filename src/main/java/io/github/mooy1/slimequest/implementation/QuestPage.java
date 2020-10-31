package io.github.mooy1.slimequest.implementation;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

public abstract class QuestPage {
    private final ItemStack item;
    private final Map<Material, Integer[]> background = new HashMap<>();

    @ParametersAreNonnullByDefault
    protected QuestPage(ItemStack item) {
        this.item = item;
        makeBackground(background);
    }

    public void makeMenu(ChestMenu menu, Player p) {
        for (Material material : background.keySet()) {
            for (Integer i : background.get(material)) {
                menu.addItem(i, new ItemStack(material), ChestMenuUtils.getEmptyClickHandler());
            }
        }
        onOpen(p, menu);
    }

    public ItemStack getSFItem(String id) {
        SlimefunItem sfItem = SlimefunItem.getByID(id);
        if (sfItem != null) return sfItem.getItem();
        return new ItemStack(Material.BARRIER);
    }

    public abstract void onOpen(@Nonnull Player p, @Nonnull ChestMenu menu);

    public abstract void makeBackground(Map<Material, Integer[]> map);

    @Nonnull
    protected ItemStack getItem() {
        return item;
    }
}
