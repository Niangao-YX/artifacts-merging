package com.neuromuser.artifactsmerging.registry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.neuromuser.artifactsmerging.recipe.ArtifactMergingRecipe;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryKeys;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create("artifacts-merging", RegistryKeys.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeSerializer<ArtifactMergingRecipe>> ARTIFACT_MERGING_SERIALIZER =
            RECIPE_SERIALIZERS.register("artifact_merging",
                    () -> new RecipeSerializer<ArtifactMergingRecipe>() {
                        private final MapCodec<ArtifactMergingRecipe> codec = RecordCodecBuilder.mapCodec(instance ->
                                instance.group(
                                        CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> CraftingRecipeCategory.MISC)
                                ).apply(instance, ArtifactMergingRecipe::new)
                        );

                        private final PacketCodec<RegistryByteBuf, ArtifactMergingRecipe> packetCodec =
                                PacketCodec.tuple(
                                        CraftingRecipeCategory.PACKET_CODEC, recipe -> CraftingRecipeCategory.MISC,
                                        ArtifactMergingRecipe::new
                                );

                        @Override
                        public MapCodec<ArtifactMergingRecipe> codec() {
                            return codec;
                        }

                        @Override
                        public PacketCodec<RegistryByteBuf, ArtifactMergingRecipe> packetCodec() {
                            return packetCodec;
                        }
                    });

    public static void register() {
        RECIPE_SERIALIZERS.register();
    }
}