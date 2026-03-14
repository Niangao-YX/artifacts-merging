package com.neuromuser.artifactsmerging.recipe;

import com.neuromuser.artifactsmerging.item.RandomArtifactItem;
import com.neuromuser.artifactsmerging.registry.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ArtifactMergingRecipe extends CustomRecipe {

    private static final TagKey<Item> ARTIFACTS_TAG = TagKey.create(Registries.ITEM, new ResourceLocation("artifacts", "artifacts"));

    public ArtifactMergingRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inventory, Level level) {
        int artifactCount = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(ARTIFACTS_TAG)) {
                    artifactCount++;
                } else {
                    return false;
                }
            }
        }
        return artifactCount == 2;
    }

    @Override
    public ItemStack assemble(CraftingContainer inventory, RegistryAccess registryAccess) {
        Item ex1 = null, ex2 = null;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.is(ARTIFACTS_TAG)) {
                if (ex1 == null) ex1 = stack.getItem();
                else ex2 = stack.getItem();
            }
        }
        if (ex1 == null || ex2 == null) return ItemStack.EMPTY;
        return RandomArtifactItem.createWithExcludedArtifacts(ex1, ex2);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 || height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ARTIFACT_MERGING_SERIALIZER.get();
    }
}