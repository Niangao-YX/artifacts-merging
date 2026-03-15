package com.neuromuser.artifactsmerging.item;

import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;

public class RandomArtifactItem extends Item {

    public RandomArtifactItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(Item excluded1, Item excluded2, String poolTag) {
        ItemStack stack = new ItemStack(ModItems.RANDOM_ARTIFACT.get());
        CompoundTag nbt = new CompoundTag();
        nbt.putString("excluded1", BuiltInRegistries.ITEM.getKey(excluded1).toString());
        nbt.putString("excluded2", BuiltInRegistries.ITEM.getKey(excluded2).toString());
        nbt.putString("pool", poolTag);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
        return stack;
    }
}