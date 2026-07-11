package com.macawmod;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Helper for storing a tamed Macaw on a player's persistent data
 * (used as a lightweight "shoulder" system without mixins).
 */
public final class MacawShoulderTracker {
    private static final String KEY = "MacawmodShoulderMacaw";

    private MacawShoulderTracker() {}

    /**
     * Stores the macaw's serialized data on the player, if the player's
     * shoulder slot is empty. Returns true if it was stored.
     */
    public static boolean tryMountOnShoulder(MacawEntity macaw, net.minecraft.entity.player.PlayerEntity player) {
        NbtCompound persistent = getPersistentData(player);
        if (persistent.contains(KEY)) {
            return false;
        }
        NbtCompound macawNbt = new NbtCompound();
        macaw.saveNbt(macawNbt);
        persistent.put(KEY, macawNbt);
        return true;
    }

    public static boolean hasMacawOnShoulder(ServerPlayerEntity player) {
        NbtCompound persistent = getPersistentData(player);
        return persistent.contains(KEY);
    }

    public static void dropMacawFromShoulder(ServerPlayerEntity player) {
        NbtCompound persistent = getPersistentData(player);
        if (!persistent.contains(KEY)) {
            return;
        }
        NbtCompound macawNbt = persistent.getCompound(KEY);
        persistent.remove(KEY);

        MacawEntity macaw = MacawModEntities.MACAW.create(player.getServerWorld());
        if (macaw != null) {
            macaw.readNbt(macawNbt);
            macaw.setOnShoulder(false);
            macaw.refreshPositionAndAngles(player.getX(), player.getY() + 0.5, player.getZ(), player.getYaw(), 0.0f);
            player.getServerWorld().spawnEntity(macaw);
        }
    }

    private static NbtCompound getPersistentData(net.minecraft.entity.player.PlayerEntity player) {
        // Use the player's own NBT-backed data via a stable custom compound stored in
        // the entity's command tags is not persistent enough, so we use a synthetic
        // field on the player's data tracker through NBT round-trip stored per instance.
        return PlayerDataAccessor.get(player);
    }
}