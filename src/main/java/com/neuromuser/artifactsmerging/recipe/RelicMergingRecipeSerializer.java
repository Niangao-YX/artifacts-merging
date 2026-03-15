package com.neuromuser.artifactsmerging.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class RelicMergingRecipeSerializer implements RecipeSerializer<RelicMergingRecipe> {

    public static final MapCodec<RelicMergingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC)
                    .forGetter(RelicMergingRecipe::category))
                    .apply(instance, RelicMergingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RelicMergingRecipe> STREAM_CODEC =
            StreamCodec.of(
                    (buf, recipe) -> CraftingBookCategory.STREAM_CODEC.encode(buf, recipe.category()),
                    buf -> new RelicMergingRecipe(CraftingBookCategory.STREAM_CODEC.decode(buf))
            );

    @Override
    public @NotNull MapCodec<RelicMergingRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, RelicMergingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
