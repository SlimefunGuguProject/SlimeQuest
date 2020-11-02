package io.github.mooy1.slimequest.implementation;

import io.github.mooy1.slimequest.SlimeQuest;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class QuestRegistry implements Listener {

    public static final HashMap<Player, Pair<Integer, Integer>> history = new HashMap<>();

    public QuestRegistry(SlimeQuest instance) {
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(this, instance);
        List<String> plugins = new ArrayList<>();

        //register stages
        int id = 0;
        for (QuestStage.Type type : QuestStage.Type.values()) {
            QuestStage stage = new QuestStage(type, id);

            //register pages of each stage
            for (QuestPage page : stage.getPages()) {
                String req = page.getReq();

                //check for required addons
                if (plugins.contains(req) || req.equals("Vanilla") || manager.isPluginEnabled(req)) {
                    if (!plugins.contains(req)) {
                        plugins.add(req);
                        instance.getLogger().log(Level.INFO, "Registering " + req + " Quests...");
                    }
                    //cant register the quests of the page until we verify that it's addon is enabled
                    page.registerQuests();
                    stage.registerPage(page);
                }
            }

            if (stage.getRegisteredPages().size() > 0) {
                QuestMenu.stages.add(stage);
            }
            id++;
        }

        instance.getLogger().log(Level.INFO, "Quests registered!");
    }

    public static void openBook(Player p) {
        if (!history.containsKey(p)) {
            new QuestMenu(p, 0, 0, false);
        } else {
            Pair<Integer, Integer> playerHistory = history.get(p);
            new QuestMenu(p, playerHistory.getFirstValue(), playerHistory.getSecondValue(), false);
        }
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        history.remove(e.getPlayer());
    }
}
