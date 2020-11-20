package io.github.mooy1.slimequest.implementation.stages.sf;

import io.github.mooy1.slimequest.implementation.Quest;
import io.github.mooy1.slimequest.implementation.QuestPage;
import io.github.mooy1.slimequest.implementation.QuestStage;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public class SFElectricity extends QuestStage {
    public SFElectricity() {
        super("Slimefun - Electricity", "Slimefun", 1, Material.REDSTONE);
    }

    @Override
    protected void addPages() {
        add(new QuestPage("Novice Electrician", new int[0])
            .add(new Quest("Regulations", 130, new int[0], "May I see your license?",
                28, new int[0], new SlimefunItemStack[]{SlimefunItems.ENERGY_REGULATOR}, new int[]{1})
                .setReward(SlimefunItems.ELECTRIC_MOTOR, 1)));
    }
}
