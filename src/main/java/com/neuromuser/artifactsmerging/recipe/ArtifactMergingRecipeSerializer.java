package com.neuromuser.artifactsmerging.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class ArtifactMergingRecipeSerializer implements RecipeSerializer<ArtifactMergingRecipe> {

    @Override
    public @NotNull ArtifactMergingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        return new ArtifactMergingRecipe(id, CraftingBookCategory.MISC);
    }

    @Override
    public ArtifactMergingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        return new ArtifactMergingRecipe(id, CraftingBookCategory.MISC);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ArtifactMergingRecipe recipe) {
    }
}
