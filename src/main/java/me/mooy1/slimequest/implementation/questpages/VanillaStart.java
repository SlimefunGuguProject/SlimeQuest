package me.mooy1.slimequest.implementation.questpages;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.slimequest.implementation.QuestPage;
import me.mooy1.slimequest.utils.MessageUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class VanillaStart extends QuestPage {
    public VanillaStart() {
        super(new CustomItem(Material.GRASS_BLOCK, "&aVanilla Start"));
    }

    @Override
    public void onOpen(@Nonnull Player p, @Nonnull ChestMenu menu) {
        menu.addItem(20, new ItemStack(Material.GLASS));
        menu.addMenuClickHandler(20, (player, i, itemStack, clickAction) -> false);
        menu.addItem(9, new ItemStack(Material.GLASS), ChestMenuUtils.getEmptyClickHandler());
    }
}
