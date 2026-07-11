package com.macawmod;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MacawModEntities {
    public static EntityType<MacawEntity> MACAW;

    public static void registerEntities() {
        MACAW = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MacawModMod.MOD_ID, "macaw"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MacawEntity::new)
                .dimensions(EntityDimensions.fixed(0.5f, 0.9f))
                .build()
        );

        FabricDefaultAttributeRegistry.register(MACAW, MacawEntity.createMacawAttributes());
    }
}