package com.macawmod;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MacawModItems {
    public static SpawnEggItem MACAW_SPAWN_EGG;

    public static final RegistryKey<ItemGroup> MACAW_GROUP_KEY =
        RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MacawModMod.MOD_ID, "macaw"));

    public static void registerItems() {
        MACAW_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier(MacawModMod.MOD_ID, "macaw_spawn_egg"),
            new SpawnEggItem(MacawModEntities.MACAW, 0x2266cc, 0xffdd33,
                new net.minecraft.item.Item.Settings())
        );

        ItemGroup group = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MACAW_SPAWN_EGG))
            .displayName(Text.translatable("itemgroup.macawmod.macaw"))
            .entries((displayContext, entries) -> entries.add(MACAW_SPAWN_EGG))
            .build();

        Registry.register(Registries.ITEM_GROUP, MACAW_GROUP_KEY, group);
    }
}