package com.neuromuser.artifactsmerging.registry;

import com.neuromuser.artifactsmerging.ArtifactsMerging;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipe;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipeSerializer;
import com.neuromuser.artifactsmerging.recipe.RelicMergingRecipe;
import com.neuromuser.artifactsmerging.recipe.RelicMergingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ArtifactsMerging.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ArtifactMergingRecipe>> ARTIFACT_MERGING_SERIALIZER =
            RECIPE_SERIALIZERS.register("artifact_merging", ArtifactMergingRecipeSerializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RelicMergingRecipe>> RELIC_MERGING_SERIALIZER =
            RECIPE_SERIALIZERS.register("relic_merging", RelicMergingRecipeSerializer::new);

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
