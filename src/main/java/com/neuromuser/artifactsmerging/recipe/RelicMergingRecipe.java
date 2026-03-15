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
import org.jetbrains.annotations.NotNull;

public class RelicMergingRecipe extends CustomRecipe {

    private static final TagKey<Item> RELICS_TAG = TagKey.create(Registries.ITEM, new ResourceLocation("artifactsmerging", "relics"));

    public RelicMergingRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inventory, @NotNull Level level) {
        int count = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(RELICS_TAG)) count++;
                else return false;
            }
        }
        return count == 2;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer inventory, @NotNull RegistryAccess registryAccess) {
        Item ex1 = null, ex2 = null;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && stack.is(RELICS_TAG)) {
                if (ex1 == null) ex1 = stack.getItem();
                else ex2 = stack.getItem();
            }
        }
        if (ex1 == null || ex2 == null) return ItemStack.EMPTY;
        return RandomArtifactItem.create(ex1, ex2, "artifactsmerging:relics");
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 || height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.RELIC_MERGING_SERIALIZER.get();
    }
}