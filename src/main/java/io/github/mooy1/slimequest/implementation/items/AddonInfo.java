package io.github.mooy1.slimequest.implementation.items;

import io.github.mooy1.slimequest.SlimeQuest;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public class AddonInfo extends SlimefunItem {

    private static final SlimefunItemStack QUEST_ADDON_INFO = new SlimefunItemStack(
            "QUEST_ADDON_INFO",
            Material.NETHER_STAR,
            "&bAddon Info",
            "&fVersion: &7" + SlimeQuest.getInstance().getPluginVersion(),
            "",
            "&fDiscord: &b@&7Riley&8#5911",
            "&7discord.gg/slimefun",
            "",
            "&fGithub: &b@&8&7Mooy1",
            "&7" + SlimeQuest.getInstance().getBugTrackerURL()
    );

    public AddonInfo() {
        super(QuestBook.SLIMEQUEST, QUEST_ADDON_INFO, RecipeType.NULL, null);
    }
}
