package com.neuromuser.artifactsmerging.recipe;

import com.neuromuser.artifactsmerging.item.RandomArtifactItem;
import com.neuromuser.artifactsmerging.registry.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class ArtifactMergingRecipe extends SpecialCraftingRecipe {

    public ArtifactMergingRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    private boolean isArtifact(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return Registries.ITEM.getId(stack.getItem()).getNamespace().equals("artifacts");
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        int artifactCount = 0;

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (isArtifact(stack)) {
                    artifactCount++;
                } else {
                    return false;
                }
            }
        }

        return artifactCount == 2;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack firstArtifact = ItemStack.EMPTY;
        ItemStack secondArtifact = ItemStack.EMPTY;

        for (int i = 0; i < input.getSize(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (isArtifact(stack)) {
                if (firstArtifact.isEmpty()) {
                    firstArtifact = stack;
                } else if (secondArtifact.isEmpty()) {
                    secondArtifact = stack;
                }
            }
        }

        return RandomArtifactItem.createWithExcludedArtifacts(
                firstArtifact.getItem(),
                secondArtifact.getItem()
        );
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARTIFACT_MERGING_SERIALIZER.get();
    }
}