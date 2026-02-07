package com.neuromuser.artifactsmerging.registry;

import com.neuromuser.artifactsmerging.item.RandomArtifactItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create("artifacts-merging", RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> RANDOM_ARTIFACT =
            ITEMS.register("random_artifact",
                    () -> new RandomArtifactItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)));

    public static void register() {
        ITEMS.register();
    }
}