package io.github.mooy1.slimequest;

import io.github.mooy1.slimequest.command.QuestCommand;
import io.github.mooy1.slimequest.implementation.QuestRegistry;
import io.github.mooy1.slimequest.implementation.items.AddonInfo;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.mooy1.slimequest.implementation.items.QuestBook;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class SlimeQuest extends JavaPlugin implements SlimefunAddon, Listener {

    private static SlimeQuest instance;

    @Override
    public void onEnable() {
        instance = this;

        //config
        updateConfig();

        //stats
        @SuppressWarnings("unused")
        final Metrics metrics = new Metrics(this, 9265);

        PaperLib.suggestPaper(this);

        //auto update
        /*if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/SlimeQuest/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }*/

        //quests
        new QuestRegistry(this);

        //items
        new AddonInfo().register(this);
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

    public static void registerEvents(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    private void updateConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
    }

    public static void log(Level level , String... logs) {
        for (String log : logs) {
            instance.getLogger().log(level, log);
        }
    }
}
