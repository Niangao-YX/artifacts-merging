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

                ServerLifecycleEvents.SERVER_STARTED.register(server -> {
                        try {
                                RecipeManager recipeManager = server.getRecipeManager();
                                ArtifactMergingRecipe recipe = new ArtifactMergingRecipe(CraftingRecipeCategory.MISC);
                                RecipeEntry<ArtifactMergingRecipe> entry = new RecipeEntry<>(
                                        Identifier.of(MOD_ID, "artifact_merging"),
                                        recipe
                                );

                                Field recipesByTypeField = RecipeManager.class.getDeclaredField("recipesByType");
                                recipesByTypeField.setAccessible(true);
                                Multimap<RecipeType<?>, RecipeEntry<?>> recipesByType =
                                        (Multimap<RecipeType<?>, RecipeEntry<?>>) recipesByTypeField.get(recipeManager);

                                ImmutableMultimap.Builder<RecipeType<?>, RecipeEntry<?>> builder = ImmutableMultimap.builder();
                                builder.putAll(recipesByType);
                                builder.put(RecipeType.CRAFTING, entry);
                                recipesByTypeField.set(recipeManager, builder.build());

                                Field recipesByIdField = RecipeManager.class.getDeclaredField("recipesById");
                                recipesByIdField.setAccessible(true);
                                Map<Identifier, RecipeEntry<?>> recipesById =
                                        (Map<Identifier, RecipeEntry<?>>) recipesByIdField.get(recipeManager);

                                ImmutableMap.Builder<Identifier, RecipeEntry<?>> builder2 = ImmutableMap.builder();
                                builder2.putAll(recipesById);
                                builder2.put(entry.id(), entry);
                                recipesByIdField.set(recipeManager, builder2.build());

                        } catch (Exception e) {
                                LOGGER.error("Failed to inject recipe via reflection", e);
                        }
                });

                LOGGER.info("Artifacts Merging initialized!");
        }
}