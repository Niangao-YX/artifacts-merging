package com.neuromuser.artifactsmerging.event;

import com.neuromuser.artifactsmerging.ArtifactsMerging;
import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ArtifactsMerging.MOD_ID)
public class ModEvents {
    private static final String[] ARTIFACTS = {
            "plastic_drinking_hat", "novelty_drinking_hat", "snorkel", "night_vision_goggles",
            "villager_hat", "superstitious_hat", "cowboy_hat", "anglers_hat", "lucky_scarf",
            "scarf_of_invisibility", "cross_necklace", "panic_necklace", "shock_pendant",
            "flame_pendant", "thorn_pendant", "charm_of_sinking", "charm_of_shrinking",
            "cloud_in_a_bottle", "obsidian_skull", "antidote_vessel", "universal_attractor",
            "crystal_heart", "helium_flamingo", "chorus_totem", "warp_drive", "digging_claws",
            "feral_claws", "power_glove", "fire_gauntlet", "pocket_piston", "vampiric_glove",
            "golden_hook", "onion_ring", "pickaxe_heater", "withered_bracelet", "aqua_dashers",
            "bunny_hoppers", "kitty_slippers", "running_shoes", "snowshoes", "steadfast_spikes",
            "flippers", "rooted_boots", "strider_shoes", "whoopee_cushion"
    };

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
        if (nbt == null || !nbt.contains("excluded1")) return stack;

        Item ex1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("excluded1")));
        Item ex2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("excluded2")));

        List<Item> pool = new ArrayList<>();
        for (String id : ARTIFACTS) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("artifacts", id));
            if (item != null && item != Items.AIR && item != ex1 && item != ex2) pool.add(item);
        }

        if (pool.isEmpty()) return stack;
        return new ItemStack(pool.get((int) (Math.random() * pool.size())));
    }

    private static void playSound(Player player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.2F);
    }
}