package com.neuromuser.artifactsmerging.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class ArtifactMergingRecipeSerializer implements RecipeSerializer<ArtifactMergingRecipe> {

    public static final MapCodec<ArtifactMergingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC)
                    .forGetter(ArtifactMergingRecipe::category))
                    .apply(instance, ArtifactMergingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArtifactMergingRecipe> STREAM_CODEC =
            StreamCodec.of(
                    (buf, recipe) -> CraftingBookCategory.STREAM_CODEC.encode(buf, recipe.category()),
                    buf -> new ArtifactMergingRecipe(CraftingBookCategory.STREAM_CODEC.decode(buf))
            );

    @Override
    public @NotNull MapCodec<ArtifactMergingRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, ArtifactMergingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
