package me.sshcrack.dyeable_sulfur_blocks;

import me.sshcrack.dyeable_sulfur_blocks.block.DyeablePotentSulfurBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.EnumMap;
import java.util.Map;

public class ModBlocks {

    public static final Map<DyeColor, Block> DYED_POTENT_SULFUR = new EnumMap<>(DyeColor.class);

    public static void init() {
        for (DyeColor color : DyeColor.values()) {
            Block block = Registry.register(
                    BuiltInRegistries.BLOCK,
                    Identifier.fromNamespaceAndPath(DyeableSulfurBlocks.MOD_ID, color.getName() + "_potent_sulfur"),
                    new DyeablePotentSulfurBlock(color,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.POTENT_SULFUR))
            );
            DYED_POTENT_SULFUR.put(color, block);
        }

        // Allow our dyed blocks to use the POTENT_SULFUR block entity type so all the
        // geyser/gas tickers are inherited without any duplication.
        for (Block block : DYED_POTENT_SULFUR.values()) {
            ((FabricBlockEntityType) BlockEntityTypes.POTENT_SULFUR).addValidBlock(block);
        }
    }
}
