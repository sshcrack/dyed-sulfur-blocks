package me.sshcrack.dyeable_sulfur_blocks;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.EnumMap;
import java.util.Map;

public class ModItems {

    public static final Map<DyeColor, Item> DYED_POTENT_SULFUR_ITEMS = new EnumMap<>(DyeColor.class);

    public static void init() {
        for (Map.Entry<DyeColor, Block> entry : ModBlocks.DYED_POTENT_SULFUR.entrySet()) {
            DyeColor color = entry.getKey();
            Block block = entry.getValue();
            Item item = Registry.register(
                    BuiltInRegistries.ITEM,
                    Identifier.fromNamespaceAndPath(DyeableSulfurBlocks.MOD_ID, color.getName() + "_potent_sulfur"),
                    new BlockItem(block, new Item.Properties())
            );
            DYED_POTENT_SULFUR_ITEMS.put(color, item);
        }

        ResourceKey<CreativeModeTab> coloredBlocksTab = ResourceKey.create(
                Registries.CREATIVE_MODE_TAB,
                Identifier.withDefaultNamespace("colored_blocks")
        );
        CreativeModeTabEvents.modifyOutputEvent(coloredBlocksTab).register(output -> {
            for (Item item : DYED_POTENT_SULFUR_ITEMS.values()) {
                output.accept(item);
            }
        });
    }
}
