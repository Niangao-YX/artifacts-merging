package com.neuromuser.artifactsmerging.mixin;

import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    private void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (stack.isOf(ModItems.RANDOM_ARTIFACT.get())) {
            net.minecraft.component.type.NbtComponent component = stack.get(net.minecraft.component.DataComponentTypes.CUSTOM_DATA);

            if (component != null) {
                NbtCompound nbt = component.copyNbt();

                if (nbt.contains("excluded1") && nbt.contains("excluded2")) {
                    String excluded1Id = nbt.getString("excluded1");
                    String excluded2Id = nbt.getString("excluded2");

                    Item excluded1 = Registries.ITEM.get(Identifier.of(excluded1Id));
                    Item excluded2 = Registries.ITEM.get(Identifier.of(excluded2Id));

                    ItemStack randomArtifact = getRandomArtifact(excluded1, excluded2);

                    stack.setCount(0);

                    player.getWorld().playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F
                    );

                    if (!player.getInventory().insertStack(randomArtifact)) {
                        player.dropItem(randomArtifact, false);
                    }
                }
            }
        }
    }

    @Unique
    private static ItemStack getRandomArtifact(Item excludedArtifact1, Item excludedArtifact2) {
        List<Item> allArtifacts = new ArrayList<>();

        String[] artifactIds = {
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

        for (String id : artifactIds) {
            Item item = Registries.ITEM.get(Identifier.of("artifacts", id));
            if (item != excludedArtifact1 && item != excludedArtifact2) {
                allArtifacts.add(item);
            }
        }

        if (allArtifacts.isEmpty()) {
            return ItemStack.EMPTY;
        }

        Item randomArtifact = allArtifacts.get((int)(Math.random() * allArtifacts.size()));
        return new ItemStack(randomArtifact);
    }
}