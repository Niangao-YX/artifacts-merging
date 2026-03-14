package com.neuromuser.artifactsmerging.registry;

import com.neuromuser.artifactsmerging.ArtifactsMerging;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipe;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArtifactsMerging.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ArtifactMergingRecipe>> ARTIFACT_MERGING_SERIALIZER =
            RECIPE_SERIALIZERS.register("artifact_merging", ArtifactMergingRecipeSerializer::new);

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
