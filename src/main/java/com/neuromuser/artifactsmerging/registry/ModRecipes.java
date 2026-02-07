package com.neuromuser.artifactsmerging.registry;

import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipe;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipeSerializer;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKeys;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
        DeferredRegister.create("artifacts-merging", RegistryKeys.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeSerializer<ArtifactMergingRecipe>> ARTIFACT_MERGING_SERIALIZER = 
        RECIPE_SERIALIZERS.register("artifact_merging", ArtifactMergingRecipeSerializer::new);

    public static void register() {
        RECIPE_SERIALIZERS.register();
    }
}