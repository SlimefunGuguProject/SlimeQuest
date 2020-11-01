package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.implementation.data.PlayerData;
import io.github.mooy1.slimequest.utils.MessageUtils;
import io.github.mooy1.slimequest.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Quest {

    public static List<String> names = new ArrayList<>();
    public static List<Integer> ids = new ArrayList<>();
    public static List<Quest> quests = new ArrayList<>();

    private final ItemStack unlocked;
    private final ItemStack locked;
    private final int id;
    private final int levels;
    private final String name;
    private final int reqID;
    private final String[] reqIDs;
    private final int[] reqAmounts;
    private final int slot;
    private final ItemStack reward;
    private final boolean consume;
    private final String customReq;

    @ParametersAreNonnullByDefault
    public Quest(String name, int id, String displayMaterialID, int slot, @Nullable String reward, int amount, int levels,
                 String[] reqIDs, int[] reqAmounts, int reqID, boolean consume, @Nullable String customReq) {

        quests.add(this);
        names.add(name);
        ids.add(id);

        this.id = id;
        this.name = name;
        this.levels = levels;
        this.reqID = reqID;
        this.slot = slot;
        this.consume = consume;
        this.customReq = customReq;
        this.reqIDs = reqIDs;
        this.reqAmounts = reqAmounts;

        if (reward == null) {
            this.reward = null;
        } else {
            this.reward = StackUtils.getItemFromID(reward, amount);
        }

        ItemStack item = StackUtils.getItemFromIDorElse(displayMaterialID, 1, Material.BARRIER);
        this.unlocked = item.clone();
        this.locked = item.clone();
        makeUnlockedItem(this.unlocked);
        makeLockedItem(this.locked);
    }

    protected void makeUnlockedItem(@Nonnull ItemStack item) {

        List<String> lore = new ArrayList<>();
        String name = ChatColor.GREEN + "Completed: " + this.name;

        StackUtils.enchant(item);

        MessageUtils.broadcast(lore.toString());
        StackUtils.insertLoreAndRename(item, lore, name);
    }

    protected void makeLockedItem(@Nonnull ItemStack item) {

        List<String> lore = new ArrayList<>();
        String name = ChatColor.GOLD + "Quest: " + this.name;

        lore.add("");
        lore.add(ChatColor.GREEN + "Requirements: ");
        for (int i = 0 ; i < this.reqAmounts.length ; i++) {
            lore.add(ChatColor.GREEN + ItemUtils.getItemName(StackUtils.getItemFromID(this.reqIDs[i], 1))+ " x " + this.reqAmounts[i]);
        }
        if (this.customReq != null) {
            lore.add(ChatColor.GREEN + this.customReq);
        }
        lore.add("");
        lore.add(ChatColor.GREEN + "Consumes Items: " + this.consume);
        lore.add("");
        if (this.reward != null) {
            lore.add(ChatColor.GREEN + "Reward: " + ItemUtils.getItemName(this.reward));
        }
        if (this.reward != null) {
            lore.add(ChatColor.GREEN + "Levels: " + this.levels);
        }

        StackUtils.insertLoreAndRename(item, lore, name);
    }

    public void addQuestStack(@Nonnull ChestMenu menu, @Nonnull Player p) {
        ItemStack item;
        if (PlayerData.get().check(p, this.id)) {
            item = this.unlocked;
        } else {
            item = this.locked;
        }
        menu.addItem(this.slot, item, this.makeQuestHandler(menu));
    }

    @Nonnull
    protected ChestMenu.MenuClickHandler makeQuestHandler(ChestMenu menu) {
        return (player, slot, itemStack, clickAction) -> {
            if (PlayerData.get().check(player, this.id)) {
                return false;
            }

            if (this.reqID > -1 && !PlayerData.get().check(player, this.reqID)) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 1);
                MessageUtils.message(player, ChatColor.RED + "You must complete the quest: " + Quest.names.get(Quest.ids.indexOf(this.reqID)) + " first!");
                return false;
            }

            Inventory inv = player.getInventory();

            Map<String, Integer> contentsID = new HashMap<>();
            ItemStack[] contents = inv.getContents();

            for (ItemStack item : contents) {
                String id = StackUtils.getIDFromItem(item);
                if (id == null) continue;
                contentsID.merge(id, item.getAmount(), Integer::sum);
            }

            int match = 0;
            for (int i = 0 ; i < this.reqAmounts.length ; i++) {
                String id = this.reqIDs[i];
                if (contentsID.containsKey(id) && contentsID.get(id) >= this.reqAmounts[i]) {
                    match++;
                }
            }

            if (match != this.reqIDs.length) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 1);
                MessageUtils.message(player, ChatColor.RED + "You do not have all the required items!");
                return false;
            }

            if (giveRewards(player, inv, contents)) {
                giveUnlock(player, false);
                unlockQuestStack(menu, this.slot);
            }
            return false;
        };
    }

    public boolean giveRewards(Player player, Inventory inv, ItemStack [] contents) {
        if (this.reward != null) {

            if (Arrays.asList(contents).contains(null)) {
                inv.addItem(reward);
            } else {
                MessageUtils.message(player, ChatColor.GOLD + "Not enough room to claim!");
                return false;
            }
        }
        return true;
    }

    public void giveUnlock(Player player, boolean free) {
        if (this.levels > 0) {
            player.setLevel(player.getLevel() + this.levels);
        }

        if (this.consume && !free) {

            for (int i = 0 ; i < this.reqAmounts.length ; i++) {
                String id = this.reqIDs[i];
                int remaining = this.reqAmounts[i];
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && Objects.equals(StackUtils.getIDFromItem(item), id)) {

                        if (item.getAmount() >= remaining) {
                            ItemUtils.consumeItem(item, remaining, false);
                            remaining = 0;
                        } else {
                            remaining = remaining - item.getAmount();
                            ItemUtils.consumeItem(item, item.getAmount(), false);
                        }
                    }

                    if (remaining == 0) break;
                }
            }
        }

        MessageUtils.message(player, ChatColor.GREEN + "Completed quest: " + this.name);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        PlayerData.get().add(player, this.id);
    }

    protected void unlockQuestStack(@Nonnull ChestMenu menu, int slot) {
        menu.replaceExistingItem(slot, this.unlocked);
        menu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
    }
}
