package com.neuromuser.artifactsmerging;

import com.neuromuser.artifactsmerging.registry.ModItems;
import com.neuromuser.artifactsmerging.registry.ModRecipes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArtifactsMerging.MOD_ID)
public class ArtifactsMerging {
    public static final String MOD_ID = "artifactsmerging";
    public static final Logger LOGGER = LogManager.getLogger();

    public ArtifactsMerging(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModRecipes.register(modEventBus);
        LOGGER.info("Artifacts Merging initialized!");
    }
}
