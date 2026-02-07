package com.neuromuser.artifactsmerging.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class RandomArtifactItem extends Item {

    public RandomArtifactItem(Settings settings) {
        super(settings);
    }

    public static ItemStack createWithExcludedArtifacts(Item excluded1, Item excluded2) {
        ItemStack stack = new ItemStack(com.neuromuser.artifactsmerging.registry.ModItems.RANDOM_ARTIFACT.get());
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("excluded1", net.minecraft.registry.Registries.ITEM.getId(excluded1).toString());
        nbt.putString("excluded2", net.minecraft.registry.Registries.ITEM.getId(excluded2).toString());
        return stack;
    }
}