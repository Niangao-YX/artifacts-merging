package com.neuromuser.artifactsmerging.event;

import com.neuromuser.artifactsmerging.ArtifactsMerging;
import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = ArtifactsMerging.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();

        ItemStack carried = player.containerMenu.getCarried();
        if (!carried.isEmpty() && carried.is(ModItems.RANDOM_ARTIFACT.get())) {
            player.containerMenu.setCarried(resolve(carried));
            playSound(player);
            return;
        }

        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);
            if (!stack.isEmpty() && stack.is(ModItems.RANDOM_ARTIFACT.get())) {
                player.getInventory().setItem(i, resolve(stack));
                playSound(player);
                return;
            }
        }
    }

    private static ItemStack resolve(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null || !nbt.contains("excluded1") || !nbt.contains("pool")) return stack;

        Item ex1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("excluded1")));
        Item ex2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("excluded2")));

        TagKey<Item> poolTag = TagKey.create(Registries.ITEM, new ResourceLocation(nbt.getString("pool")));

        List<Item> pool = new ArrayList<>();
        for (Item item : Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(poolTag)) {
            if (item != ex1 && item != ex2) pool.add(item);
        }

        if (pool.isEmpty()) return stack;
        return new ItemStack(pool.get((int) (Math.random() * pool.size())));
    }

    private static void playSound(Player player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.2F);
    }
}