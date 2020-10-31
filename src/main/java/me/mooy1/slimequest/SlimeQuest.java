package me.mooy1.slimequest;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import me.mooy1.slimequest.command.QuestCommand;
import me.mooy1.slimequest.implementation.QuestBook;
import me.mooy1.slimequest.implementation.QuestRegistry;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

import javax.annotation.Nonnull;

public class SlimeQuest extends JavaPlugin implements SlimefunAddon, Listener {

    private static SlimeQuest instance;
    public final Config config = new Config(this);

    @Override
    public void onEnable() {
        instance = this;

        //stats
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 9265);

        PaperLib.suggestPaper(this);

        //auto update
        /*if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }*/

        //quests
        new QuestRegistry(this);

        //item
        new QuestBook(this).register(this);

        //commands
        new QuestCommand(this).register();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Mooy1/SlimeQuest/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nonnull
    public static SlimeQuest getInstance() {
        return instance;
    }
}
