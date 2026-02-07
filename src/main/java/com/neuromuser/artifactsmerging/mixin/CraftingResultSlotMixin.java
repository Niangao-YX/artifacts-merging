package com.neuromuser.artifactsmerging.mixin;

import com.neuromuser.artifactsmerging.registry.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
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
public abstract class CraftingResultSlotMixin extends Slot {
    @Unique
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

    public CraftingResultSlotMixin(net.minecraft.inventory.Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    private void artifactsmerging$onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (artifactsmerging$transform(player, stack)) return;

        for (int i = 0; i < player.getInventory().size(); i++) {
            if (artifactsmerging$transform(player, player.getInventory().getStack(i))) break;
        }
    }

    @Unique
    private boolean artifactsmerging$transform(PlayerEntity player, ItemStack stack) {
        if (stack.isEmpty() || !stack.isOf(ModItems.RANDOM_ARTIFACT.get())) return false;

        // 1.21.1 Replacement for getNbt()
        NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component == null) return false;

        NbtCompound nbt = component.copyNbt();
        if (!nbt.contains("excluded1")) return false;

        // 1.21.1 Replacement for new Identifier()
        Item ex1 = Registries.ITEM.get(Identifier.of(nbt.getString("excluded1")));
        Item ex2 = Registries.ITEM.get(Identifier.of(nbt.getString("excluded2")));

        List<Item> pool = new ArrayList<>();
        for (String id : ARTIFACTS) {
            Item item = Registries.ITEM.get(Identifier.of("artifacts", id));
            if (item != net.minecraft.item.Items.AIR && item != ex1 && item != ex2) pool.add(item);
        }

        if (pool.isEmpty()) return false;

        ItemStack result = new ItemStack(pool.get((int) (Math.random() * pool.size())));

        if (player.currentScreenHandler.getCursorStack() == stack) {
            player.currentScreenHandler.setCursorStack(result);
            stack.setCount(0);
        } else {
            int slot = player.getInventory().indexOf(stack);
            if (slot != -1) player.getInventory().setStack(slot, result);
        }

        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1.0F, 1.2F);

        return true;
    }
}