package com.neuromuser.artifactsmerging.registry;

import com.neuromuser.artifactsmerging.ArtifactsMerging;
import com.neuromuser.artifactsmerging.item.RandomArtifactItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, ArtifactsMerging.MOD_ID);

    public static final DeferredHolder<Item, RandomArtifactItem> RANDOM_ARTIFACT =
            ITEMS.register("random_artifact",
                    () -> new RandomArtifactItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
