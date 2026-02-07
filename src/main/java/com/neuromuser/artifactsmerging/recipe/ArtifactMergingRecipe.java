package com.neuromuser.artifactsmerging.recipe;

import artifacts.item.ArtifactItem;
import com.neuromuser.artifactsmerging.item.RandomArtifactItem;
import com.neuromuser.artifactsmerging.registry.ModRecipes;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ArtifactMergingRecipe extends SpecialCraftingRecipe {

    public ArtifactMergingRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        ItemStack firstArtifact = ItemStack.EMPTY;
        ItemStack secondArtifact = ItemStack.EMPTY;
        int artifactCount = 0;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ArtifactItem) {
                    artifactCount++;
                    if (firstArtifact.isEmpty()) {
                        firstArtifact = stack;
                    } else if (secondArtifact.isEmpty()) {
                        secondArtifact = stack;
                    }
                } else {
                    return false;
                }
            }
        }

        return artifactCount == 2;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack firstArtifact = ItemStack.EMPTY;
        ItemStack secondArtifact = ItemStack.EMPTY;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ArtifactItem) {
                if (firstArtifact.isEmpty()) {
                    firstArtifact = stack;
                } else if (secondArtifact.isEmpty()) {
                    secondArtifact = stack;
                }
            }
        }

        ItemStack result = RandomArtifactItem.createWithExcludedArtifacts(
            firstArtifact.getItem(),
            secondArtifact.getItem()
        );
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 || height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARTIFACT_MERGING_SERIALIZER.get();
    }
}