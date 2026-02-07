package com.neuromuser.artifactsmerging;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipe;
import com.neuromuser.artifactsmerging.registry.ModItems;
import com.neuromuser.artifactsmerging.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

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