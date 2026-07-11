package com.macawmod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight in-memory persistent-per-session store keyed by player UUID.
 * Data persists for the duration of the server session (and while a player
 * is online), which is sufficient for the shoulder mechanic. Avoids mixins.
 */
public final class PlayerDataAccessor {
    private static final Map<UUID, NbtCompound> DATA = new ConcurrentHashMap<>();
    private static final Map<PlayerEntity, Boolean> SEEN = new WeakHashMap<>();

    private PlayerDataAccessor() {}

    public static NbtCompound get(PlayerEntity player) {
        SEEN.put(player, Boolean.TRUE);
        return DATA.computeIfAbsent(player.getUuid(), id -> new NbtCompound());
    }
}