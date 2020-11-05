package io.github.mooy1.slimequest.utils;

import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection of utils for modifying ItemStacks and getting their ids
 *
 * @author Mooy1
 */
public final class StackUtils {

    private StackUtils() {}

    @Nullable
    public static String getIDFromItem(@Nullable ItemStack item) {
        if (item == null) {

            return null;

        } else {

            SlimefunItem sfItem = SlimefunItem.getByItem(item);

            if (sfItem != null) {

                return sfItem.getId();

            }
        }

        String id = item.getType().toString();

        if (id.equals("COPPER_INGOT")) {
            id = "MC_COPPER_INGOT";
        }

        return id;
    }

    @Nonnull
    public static ItemStack getItemFromIDorElse(@Nonnull String id, int amount, @Nonnull Material or) {

        SlimefunItem sfItem = SlimefunItem.getByID(id);

        if (sfItem != null) {

            return new CustomItem(sfItem.getItem(), amount);

        } else {

            String changedID = id;

            if (id.equals("MC_COPPER_INGOT")) {
                changedID = "COPPER_INGOT";
            }

            Material material = Material.getMaterial(changedID);

            if (material != null){

                return new ItemStack(material, amount);

            }
        }

        return new ItemStack(or);
    }

    @Nullable
    public static ItemStack getItemFromID(@Nonnull String id, int amount) {

        SlimefunItem sfItem = SlimefunItem.getByID(id);

        if (sfItem != null) {

            return new CustomItem(sfItem.getItem(), amount);

        } else {

            String changedID = id;

            if (id.equals("MC_COPPER_INGOT")) {
                changedID = "COPPER_INGOT";
            }

            Material material = Material.getMaterial(changedID);

            if (material != null){

                return new ItemStack(material, amount);

            }
        }

        return null;
    }

    @ParametersAreNonnullByDefault
    public static void insertLoreAndRename(ItemStack item, List<String> lores, @Nullable String name) {
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;

        List<String> lore = new ArrayList<>();

        if (meta.getLore() != null) {
            lore = meta.getLore();
        }

        for (int i = 0 ; i < lore.size() ; i++) {
            if (lore.get(i).equals("")) {
                lore = lore.subList(0, i);
            }
        }

        lore.addAll(lores);

        meta.setLore(lore);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        if (name != null) {
            meta.setDisplayName(name);
        }

        item.setItemMeta(meta);
    }

    public static void enchant(@Nonnull ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
    }
}
