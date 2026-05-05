package me.sshcrack.dyeable_sulfur_blocks.client;

import me.sshcrack.dyeable_sulfur_blocks.ModBlocks;
import me.sshcrack.dyeable_sulfur_blocks.block.DyeablePotentSulfurBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;

public class DyeableSulfurBlocksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register a BlockTintSource for each dyed potent sulfur block.
        // The source returns the DyeColor's texture diffuse color so the GPU-tinted
        // cube model is multiplied with the right colour on the client.
        Block[] dyedBlocks = ModBlocks.DYED_POTENT_SULFUR.values().toArray(Block[]::new);
        BlockColorRegistry.register(
                List.of(state -> {
                    if (state.getBlock() instanceof DyeablePotentSulfurBlock dyeable) {
                        return dyeable.getDyeColor().getTextureDiffuseColor();
                    }
                    return -1;
                }),
                dyedBlocks
        );
    }
}
