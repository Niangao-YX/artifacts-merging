package com.neuromuser.artifactsmerging.item;

import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class RandomArtifactItem extends Item {

    public RandomArtifactItem(Properties properties) {
        super(properties);
    }

    public static ItemStack createWithExcludedArtifacts(Item excluded1, Item excluded2) {
        ItemStack stack = new ItemStack(ModItems.RANDOM_ARTIFACT.get());
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("excluded1", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(excluded1)).toString());
        nbt.putString("excluded2", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(excluded2)).toString());
        return stack;
    }
}
