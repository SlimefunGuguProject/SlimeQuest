package io.github.mooy1.slimequest.implementation.questpages;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.mooy1.slimequest.implementation.QuestPage;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Map;

public class InfinityStart extends QuestPage {

    private final ItemStack ingot = getSFItem("INFINITE_INGOT");

    public InfinityStart() {
        super(new CustomItem(Material.GRASS_BLOCK, "&aINFINITY Start"));
    }

    @Override
    public void onOpen(@Nonnull Player p, @Nonnull ChestMenu menu) {
        menu.addItem(9, ingot, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void makeBackground(Map<Material, Integer[]> map) {
        map.put(Material.ORANGE_STAINED_GLASS_PANE, new Integer[] {15, 16, 17, 18, 19, 20});
    }
}
