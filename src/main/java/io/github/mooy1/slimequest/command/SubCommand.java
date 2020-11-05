package io.github.mooy1.slimequest.command;

import lombok.Getter;
import lombok.NonNull;
import io.github.mooy1.slimequest.SlimeQuest;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {

    protected final SlimeQuest plugin;
    protected final QuestCommand cmd;
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final boolean op;

    @ParametersAreNonnullByDefault
    protected SubCommand(SlimeQuest plugin, QuestCommand cmd, String name, String description, boolean op) {
        this.plugin = plugin;
        this.cmd = cmd;
        this.name = name;
        this.description = description;
        this.op = op;
    }

    public abstract void onExecute(@Nonnull CommandSender sender, @Nonnull String[] args);

    @NonNull
    public List<String> onTab(@Nonnull CommandSender sender, @Nonnull String[] args) {
        return new ArrayList<>();
    }
}