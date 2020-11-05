package io.github.mooy1.slimequest.implementation.stages.vanilla;

import io.github.mooy1.slimequest.SlimeQuest;
import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import io.github.mooy1.slimequest.implementation.QuestStage;
import io.github.mooy1.slimequest.implementation.data.QuestData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Vanilla extends QuestStage implements Listener {
    public Vanilla() {
        super("Vanilla", 0, "Vanilla", -1, Material.GRASS_BLOCK);
    }

    @Override
    protected void addPages() {
        add(new QuestPage("Start", new int[0])
                .add(new Quest("Wood", 0, new int[] {}, "Punch trees...",
                10, new int[] {11, 12}, new String[]{"OAK_LOG"}, new int[]{8}))

                .add(new Quest("Mining", 1, new int[]{0}, "Temporary",
                13, new int[] {14, 15, 22, 31}, new String[]{"STONE_PICKAXE"}, new int[]{1}))

                .add(new Quest("Coal", 2, new int[]{1}, "You're gonna need a lot of this",
                16, new int[] {25, 34}, new String[]{"COAL"}, new int[]{16}))

                .add(new Quest("Furnace", 3, new int[]{1}, "Tier 0 furnace",
                40, new int[] {41, 42}, new String[]{"FURNACE"}, new int[]{1}))

                .add(new Quest("Iron", 4, new int[]{2, 3}, "First of many",
                43, new int[] {44}, new String[]{"IRON_INGOT"}, new int[]{1}))
        );

        add(new QuestPage("Mining", new int[] {36, 37, 38, 39, 40, 41, 42, 43, 44})
                .add(new Quest("Gold", 5, new int[] {4}, "2 karat?",
                        28, new int[] {}, new String[]{"GOLD_INGOT"}, new int[]{2}))

                .add(new Quest("Diamond", 6, new int[] {4}, "Shiny",
                        30, new int[] {21}, new String[]{"DIAMOND"}, new int[]{5}))

                .add(new Quest("Lapis", 7, new int[] {4}, "What even is this",
                        32, new int[] {23}, new String[]{"LAPIS_LAZULI"}, new int[]{3}))

                .add(new Quest("Redstone", 8, new int[] {4}, "Not as bad as the alloy",
                        34, new int[] {}, new String[]{"REDSTONE"}, new int[]{8}))

                .add(new Quest("Obsidian", 9, new int[] {6}, "Have fun",
                        12, new int[] {11, 13}, new String[]{"OBSIDIAN"}, new int[]{14}))

                .add(new Quest("Enchanting", 10, new int[] {9, 7}, "Shiny Shiny",
                        14, new int[] {}, new String[]{"ENCHANTING_TABLE"}, new int[]{1}))

                .add(new Quest("Nether", 11, new int[] {9}, "",
                        10, new int[] {}, new String[]{}, new int[]{}).setCustomReq("Enter the Nether", "PURPLE_STAINED_GLASS_PANE"))
        );

        SlimeQuest.registerEvents(this);
    }

    /**
     * This handler unlocks the nether quest and is an example of how to have custom req quests
     */
    @EventHandler
    public void onEnterNether(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            Player p = e.getPlayer();
            if (!QuestData.check(p, 11) && QuestData.check(p, 9)) {
                Quest quest = Quest.quests.get(Quest.ids.indexOf(11));
                quest.giveUnlock(e.getPlayer(), true);
                quest.giveRewards(p, p.getInventory(), p.getInventory().getStorageContents());
            }
        }
    }
}
