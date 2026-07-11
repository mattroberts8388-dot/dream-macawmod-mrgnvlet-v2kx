package com.macawmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class MacawModMod implements ModInitializer {
    public static final String MOD_ID = "macawmod";

    @Override
    public void onInitialize() {
        MacawModEntities.registerEntities();
        MacawModItems.registerItems();

        BiomeModifications.addSpawn(
            BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
            SpawnGroup.CREATURE,
            MacawModEntities.MACAW,
            12, 1, 3
        );

        // Grant night vision to players who have a tamed macaw sitting on their shoulder.
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (MacawShoulderTracker.hasMacawOnShoulder(player)) {
                    player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.NIGHT_VISION, 260, 0, true, false, false));
                }
            }
        });
    }
}