package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.implementation.data.QuestData;
import io.github.mooy1.slimequest.utils.MessageUtils;
import io.github.mooy1.slimequest.utils.StackUtils;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This object represents a single quest that can be completed and all of its requirements and rewards
 *
 * @author Mooy1
 *
 * ids and addons:
 * 0-100 vanilla
 * 100-200 slimefun
 * 200-250 sfcalc, extratools, moretools,
 * 250-300 FluffyMachines
 * 300-400 LiteXpansion
 * 400-500 InfinityExpansion
 *
 */
public class Quest {

    public static final List<String> names = new ArrayList<>();
    public static final List<Integer> ids = new ArrayList<>();
    public static final List<Quest> quests = new ArrayList<>();
    private static final int base = SlimeQuest.getInstance().getConfig().getInt("options.xp-reward-base");

    private ItemStack unlocked = null;
    private ItemStack locked = null;
    @Getter
    private final int id;
    private final String desc;
    private int levels = 0;
    private final String name;
    @Getter
    private final int[] reqIDs;
    private final String[] reqItems;
    private final int[] reqAmounts;
    @Getter
    private final int slot;
    private final int[] menuSlots;
    private ItemStack reward = null;
    private boolean consume = false;
    private String customReq = null;
    private String customDisplay = null;

    /**
     * This method creates a new {@link Quest}
     * @param name name of the quest
     * @param id id number of quest, later quests should be higher and must be unique
     * @param reqIDs previous quests that must be completed first
     * @param desc description/guide for quest
     * @param slot slot in the quest page
     * @param menuSlots slots of the quest page that will be affected when the quest is completed
     * @param reqItems required item ids
     * @param reqAmounts amount of each required item
     */
    @ParametersAreNonnullByDefault
    public Quest(String name, int id, int[] reqIDs, String desc, int slot,
                 int[] menuSlots, String[] reqItems, int[] reqAmounts) {

        quests.add(this);
        names.add(name);
        ids.add(id);

        this.id = id;
        this.menuSlots = menuSlots;
        this.name = name;
        this.desc = desc;
        this.reqIDs = reqIDs;
        this.slot = slot;
        this.reqItems = reqItems;
        this.reqAmounts = reqAmounts;

        makeDisplayItems();
    }

    public void makeDisplayItems() {
        String id;
        if (this.customDisplay != null) {
            id = customDisplay;
        } else if (this.reqItems.length > 0) {
            id = reqItems[0];
        } else {
            return;
        }
        ItemStack item = StackUtils.getItemFromIDorElse(id.toUpperCase(), 1, Material.BARRIER);

        this.unlocked = item.clone();
        this.locked = item.clone();
        makeUnlockedItem(this.unlocked);
        makeLockedItem(this.locked);
    }

    @Nonnull
    public Quest setReward(@Nonnull String id, int amount) {
        this.reward = StackUtils.getItemFromID(id, amount);
        makeDisplayItems();
        return this;
    }

    @Nonnull
    public Quest setCustomReq(@Nonnull String req, @Nonnull String customDisplayID) {
        this.customReq = req;
        this.customDisplay = customDisplayID;
        makeDisplayItems();
        return this;
    }

    @Nonnull
    public Quest setConsume() {
        this.consume = true;
        makeDisplayItems();
        return this;
    }

    @Nonnull
    public Quest setXP(int xp) {
        this.levels = base * xp;
        makeDisplayItems();
        return this;
    }

    protected void makeUnlockedItem(@Nonnull ItemStack item) {

        List<String> lore = new ArrayList<>();
        String name = ChatColor.GREEN + "Completed: " + this.name;

        lore.add("");
        lore.add(ChatColor.AQUA + this.desc);

        StackUtils.enchant(item);

        StackUtils.insertLoreAndRename(item, lore, name);
    }

    protected void makeLockedItem(@Nonnull ItemStack item) {

        List<String> lore = new ArrayList<>();
        String name = ChatColor.GOLD + "Quest: " + this.name;

        lore.add("");
        lore.add(ChatColor.AQUA + this.desc);
        lore.add("");
        lore.add(ChatColor.GREEN + "Requirements: ");
        for (int i = 0 ; i < this.reqAmounts.length ; i++) {
            lore.add(ChatColor.GREEN + ItemUtils.getItemName(StackUtils.getItemFromID(this.reqItems[i], 1))+ " x " + this.reqAmounts[i]);
        }
        if (this.customReq != null) {
            lore.add(ChatColor.GREEN + this.customReq);
        }
        if (this.consume) {
            lore.add("");
            lore.add(ChatColor.GREEN + "Consumes required items!");
        }
        lore.add("");
        if (this.reward != null) {
            lore.add(ChatColor.GREEN + "Reward: " + ItemUtils.getItemName(this.reward));
        }
        if (this.levels > 0) {
            lore.add(ChatColor.GREEN + "Levels: " + this.levels);
        }

        StackUtils.insertLoreAndRename(item, lore, name);
    }

    public static final ItemStack RED = new CustomItem(Material.RED_STAINED_GLASS_PANE, " ");
    public static final ItemStack GREEN = new CustomItem(Material.LIME_STAINED_GLASS_PANE, " ");

    @ParametersAreNonnullByDefault
    public void addQuestStacks(ChestMenu menu, Player p, int stageID, int pageID) {
        ItemStack item;
        ItemStack extra;
        if (QuestData.check(p, this.id)) {
            item = this.unlocked;
            extra = GREEN;
        } else {
            item = this.locked;
            extra = RED;
        }
        menu.addItem(this.slot, item, this.makeQuestHandler(stageID, pageID));

        for (int slot : this.menuSlots) {
            menu.addItem(slot, extra);
        }
    }

    @Nonnull
    protected ChestMenu.MenuClickHandler makeQuestHandler(int stageID, int pageID) {
        return (player, slot, itemStack, clickAction) -> {
            if (QuestData.check(player, this.id)) {
                return false;
            }

            if (this.reqIDs.length > 0 && !QuestData.checkAll(player, this.reqIDs)) {
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);
                MessageUtils.messageWithCD(player, ChatColor.RED + "You must complete previous quests first!", 1000);
                return false;
            }

            if (this.customReq != null) {
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);
                MessageUtils.messageWithCD(player, ChatColor.RED + "You have not met the requirements!", 1000);
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
                String id = this.reqItems[i];
                if (contentsID.containsKey(id) && contentsID.get(id) >= this.reqAmounts[i]) {
                    match++;
                }
            }

            if (match != this.reqItems.length) {
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1, 1);
                MessageUtils.messageWithCD(player, ChatColor.RED + "You do not have all the required items!", 1000);
                return false;
            }

            if (giveRewards(player, inv, contents)) {
                giveUnlock(player, false);
                new QuestMenu(player, stageID, pageID, true);
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
                String id = this.reqItems[i];
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
        QuestData.add(player, this.id);
    }
}
