package com.neuromuser.artifactsmerging.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class RelicMergingRecipeSerializer implements RecipeSerializer<RelicMergingRecipe> {

    @Override
    public @NotNull RelicMergingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        return new RelicMergingRecipe(id, CraftingBookCategory.MISC);
    }

    @Override
    public RelicMergingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        return new RelicMergingRecipe(id, CraftingBookCategory.MISC);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull RelicMergingRecipe recipe) {
    }
}