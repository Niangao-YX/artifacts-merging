package com.neuromuser.artifactsmerging.event;

import com.neuromuser.artifactsmerging.ArtifactsMerging;
import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = ArtifactsMerging.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();

        ItemStack carried = player.containerMenu.getCarried();
        if (!carried.isEmpty() && carried.is(ModItems.RANDOM_ARTIFACT.get())) {
            player.containerMenu.setCarried(resolve(player, carried));
            playSound(player);
            return;
        }

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);
            if (!stack.isEmpty() && stack.is(ModItems.RANDOM_ARTIFACT.get())) {
                player.getInventory().setItem(i, resolve(player, stack));
                playSound(player);
                return;
            }
        }
    }

    private static ItemStack resolve(Player player, ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData == null) return stack;
        CompoundTag nbt = customData.copyTag();
        if (!nbt.contains("excluded1") || !nbt.contains("pool")) return stack;

        Item ex1 = BuiltInRegistries.ITEM.get(ResourceLocation.parse(nbt.getString("excluded1")));
        Item ex2 = BuiltInRegistries.ITEM.get(ResourceLocation.parse(nbt.getString("excluded2")));

        TagKey<Item> poolTag = TagKey.create(Registries.ITEM, ResourceLocation.parse(nbt.getString("pool")));
        Optional<HolderSet.Named<Item>> tagHolder = player.level().registryAccess()
                .registryOrThrow(Registries.ITEM).getTag(poolTag);

        List<Item> pool = new ArrayList<>();
        tagHolder.ifPresent(holders -> {
            for (Holder<Item> holder : holders) {
                Item item = holder.value();
                if (item != ex1 && item != ex2) pool.add(item);
            }
        });

        if (pool.isEmpty()) return stack;
        return new ItemStack(pool.get((int) (Math.random() * pool.size())));
    }

    private static void playSound(Player player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.2F);
    }
}
