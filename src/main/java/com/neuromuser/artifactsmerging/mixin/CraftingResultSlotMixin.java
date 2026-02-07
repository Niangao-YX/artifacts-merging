package com.neuromuser.artifactsmerging.mixin;

import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    private void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (stack.getItem() == ModItems.RANDOM_ARTIFACT.get()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains("excluded1") && nbt.contains("excluded2")) {
                String excluded1Id = nbt.getString("excluded1");
                String excluded2Id = nbt.getString("excluded2");

                Item excluded1 = net.minecraft.registry.Registries.ITEM.get(new Identifier(excluded1Id));
                Item excluded2 = net.minecraft.registry.Registries.ITEM.get(new Identifier(excluded2Id));

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

    private static ItemStack getRandomArtifact(Item excludedArtifact1, Item excludedArtifact2) {
        List<Item> allArtifacts = new ArrayList<>();

        allArtifacts.add(artifacts.registry.ModItems.PLASTIC_DRINKING_HAT.get());
        allArtifacts.add(artifacts.registry.ModItems.NOVELTY_DRINKING_HAT.get());
        allArtifacts.add(artifacts.registry.ModItems.SNORKEL.get());
        allArtifacts.add(artifacts.registry.ModItems.NIGHT_VISION_GOGGLES.get());
        allArtifacts.add(artifacts.registry.ModItems.VILLAGER_HAT.get());
        allArtifacts.add(artifacts.registry.ModItems.SUPERSTITIOUS_HAT.get());
        allArtifacts.add(artifacts.registry.ModItems.COWBOY_HAT.get());
        allArtifacts.add(artifacts.registry.ModItems.ANGLERS_HAT.get());
        allArtifacts.add(artifacts.registry.ModItems.LUCKY_SCARF.get());
        allArtifacts.add(artifacts.registry.ModItems.SCARF_OF_INVISIBILITY.get());
        allArtifacts.add(artifacts.registry.ModItems.CROSS_NECKLACE.get());
        allArtifacts.add(artifacts.registry.ModItems.PANIC_NECKLACE.get());
        allArtifacts.add(artifacts.registry.ModItems.SHOCK_PENDANT.get());
        allArtifacts.add(artifacts.registry.ModItems.FLAME_PENDANT.get());
        allArtifacts.add(artifacts.registry.ModItems.THORN_PENDANT.get());
        allArtifacts.add(artifacts.registry.ModItems.CHARM_OF_SINKING.get());
        allArtifacts.add(artifacts.registry.ModItems.CLOUD_IN_A_BOTTLE.get());
        allArtifacts.add(artifacts.registry.ModItems.OBSIDIAN_SKULL.get());
        allArtifacts.add(artifacts.registry.ModItems.ANTIDOTE_VESSEL.get());
        allArtifacts.add(artifacts.registry.ModItems.UNIVERSAL_ATTRACTOR.get());
        allArtifacts.add(artifacts.registry.ModItems.CRYSTAL_HEART.get());
        allArtifacts.add(artifacts.registry.ModItems.HELIUM_FLAMINGO.get());
        allArtifacts.add(artifacts.registry.ModItems.CHORUS_TOTEM.get());
        allArtifacts.add(artifacts.registry.ModItems.DIGGING_CLAWS.get());
        allArtifacts.add(artifacts.registry.ModItems.FERAL_CLAWS.get());
        allArtifacts.add(artifacts.registry.ModItems.POWER_GLOVE.get());
        allArtifacts.add(artifacts.registry.ModItems.FIRE_GAUNTLET.get());
        allArtifacts.add(artifacts.registry.ModItems.POCKET_PISTON.get());
        allArtifacts.add(artifacts.registry.ModItems.VAMPIRIC_GLOVE.get());
        allArtifacts.add(artifacts.registry.ModItems.GOLDEN_HOOK.get());
        allArtifacts.add(artifacts.registry.ModItems.ONION_RING.get());
        allArtifacts.add(artifacts.registry.ModItems.PICKAXE_HEATER.get());
        allArtifacts.add(artifacts.registry.ModItems.AQUA_DASHERS.get());
        allArtifacts.add(artifacts.registry.ModItems.BUNNY_HOPPERS.get());
        allArtifacts.add(artifacts.registry.ModItems.KITTY_SLIPPERS.get());
        allArtifacts.add(artifacts.registry.ModItems.RUNNING_SHOES.get());
        allArtifacts.add(artifacts.registry.ModItems.SNOWSHOES.get());
        allArtifacts.add(artifacts.registry.ModItems.STEADFAST_SPIKES.get());
        allArtifacts.add(artifacts.registry.ModItems.FLIPPERS.get());
        allArtifacts.add(artifacts.registry.ModItems.ROOTED_BOOTS.get());
        allArtifacts.add(artifacts.registry.ModItems.WHOOPEE_CUSHION.get());

        allArtifacts.removeIf(item ->
                item == excludedArtifact1 || item == excludedArtifact2
        );

        if (allArtifacts.isEmpty()) {
            return ItemStack.EMPTY;
        }

        Item randomArtifact = allArtifacts.get((int)(Math.random() * allArtifacts.size()));
        return new ItemStack(randomArtifact);
    }
}