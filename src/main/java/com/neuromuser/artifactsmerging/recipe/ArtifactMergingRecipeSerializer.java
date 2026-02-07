package com.neuromuser.artifactsmerging.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;

public class ArtifactMergingRecipeSerializer implements RecipeSerializer<ArtifactMergingRecipe> {

    @Override
    public ArtifactMergingRecipe read(Identifier id, JsonObject json) {
        return new ArtifactMergingRecipe(id, CraftingRecipeCategory.MISC);
    }

    @Override
    public ArtifactMergingRecipe read(Identifier id, PacketByteBuf buf) {
        return new ArtifactMergingRecipe(id, CraftingRecipeCategory.MISC);
    }

    @Override
    public void write(PacketByteBuf buf, ArtifactMergingRecipe recipe) {
    }
}