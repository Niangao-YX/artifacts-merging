package com.neuromuser.artifactsmerging.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

public class RandomArtifactItem extends Item {

    public RandomArtifactItem(Settings settings) {
        super(settings);
    }

    public static ItemStack createWithExcludedArtifacts(Item excluded1, Item excluded2) {
        ItemStack stack = new ItemStack(com.neuromuser.artifactsmerging.registry.ModItems.RANDOM_ARTIFACT.get());
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, (currentNbt) ->
                currentNbt.apply(nbt -> {
                    nbt.putString("excluded1", Registries.ITEM.getId(excluded1).toString());
                    nbt.putString("excluded2", Registries.ITEM.getId(excluded2).toString());
                })
        );
        return stack;
    }
}