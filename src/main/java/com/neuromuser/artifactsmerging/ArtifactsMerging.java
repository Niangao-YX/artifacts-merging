package com.neuromuser.artifactsmerging;

import com.neuromuser.artifactsmerging.registry.ModItems;
import com.neuromuser.artifactsmerging.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtifactsMerging implements ModInitializer {
        public static final String MOD_ID = "artifacts-merging";
        public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

        @Override
        public void onInitialize() {
                ModItems.register();
                ModRecipes.register();
                LOGGER.info("Artifacts Merging initialized!");
        }
}