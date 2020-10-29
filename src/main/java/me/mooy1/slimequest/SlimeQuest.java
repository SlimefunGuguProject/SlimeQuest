package me.mooy1.slimequest;

import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;

import javax.annotation.Nonnull;

public class SlimeQuest extends JavaPlugin implements SlimefunAddon {

    private static SlimeQuest instance;
    private final Config config = new Config(this);

    @Override
    public void onEnable() {
        instance = this;

        //stats
        //final Metrics metrics = new Metrics(this, 8991);

        PaperLib.suggestPaper(this);

        //auto update
        /*if (getDescription().getVersion().startsWith("DEV - ")) {
            getLogger().log(Level.INFO, "Starting auto update");
            Updater updater = new GitHubBuildsUpdater(this, this.getFile(), "Mooy1/InfinityExpansion/master");
            updater.start();
        } else {
            getLogger().log(Level.WARNING, "You must be on a DEV build to auto update!");
        }*/

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

    @NonNull
    public Config getMainConfig() {
        return config;
    }
}
