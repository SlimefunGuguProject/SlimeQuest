package io.github.mooy1.slimequest.implementation.questpages;

import io.github.mooy1.slimequest.implementation.QuestPage;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Map;

public class VanillaAdvanced extends QuestPage {
    public VanillaAdvanced() {
        super(new CustomItem(Material.DIRT, "&aVanilla Advanced"));
    }

    @Override
    public void onOpen(@Nonnull Player p, @Nonnull ChestMenu menu) {
        menu.addItem(20, new ItemStack(Material.GLASS));
        menu.addMenuClickHandler(20, (player, i, itemStack, clickAction) -> false);
        menu.addItem(9, new ItemStack(Material.GLASS), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void makeBackground(Map<Material, Integer[]> map) {

    }
}
