package io.github.mooy1.slimequest.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class QuestTabCompleter implements TabCompleter {

    private static final int MAX_SUGGESTIONS = 80;

    private final QuestCommand command;

    public QuestTabCompleter(@Nonnull QuestCommand command) {
        this.command = command;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, String[] args) {
        if (args.length == 1) {

            List<String> subCommands = new ArrayList<>();
            for (SubCommand command : command.commands) {
                if (!command.isOp() || sender.hasPermission("slimequest.admin")) {
                    subCommands.add(command.getName());
                }
            }
            return createReturnList(subCommands);

        } else if (args.length > 0) {
            for (SubCommand command : command.commands) {
                if (args[0].equalsIgnoreCase(command.getName())) {
                    return createReturnList(command.onTab(sender, args));
                }
            }
        }

        return null;
    }

    @Nonnull
    private List<String> createReturnList(@Nonnull List<String> list) {
        if (list.size() >= MAX_SUGGESTIONS) {
            list = list.subList(0, MAX_SUGGESTIONS);
        }
        return list;
    }
}
