package me.sshcrack.dyeable_sulfur_blocks.client;

import me.sshcrack.dyeable_sulfur_blocks.ModBlocks;
import me.sshcrack.dyeable_sulfur_blocks.block.DyeablePotentSulfurBlock;
import me.sshcrack.dyeable_sulfur_blocks.client.particle.ColoredGeyserPlumeParticle;
import me.sshcrack.dyeable_sulfur_blocks.client.particle.ColoredNoxiousGasCloudParticle;
import me.sshcrack.dyeable_sulfur_blocks.client.particle.ColoredNoxiousGasParticle;
import me.sshcrack.dyeable_sulfur_blocks.particle.ModParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DyeableSulfurBlocksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // ── Block tinting ────────────────────────────────────────────────────
        // Multiplies the tintindex-0 faces of the tinted cube model with the
        // dye colour, giving each block its correct hue.
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

        // ── Colored particle factories ────────────────────────────────────────
        // The cloud orchestrator (NoRenderParticle – no sprite needed)
        ParticleProviderRegistry.getInstance().register(
                ModParticleTypes.COLORED_NOXIOUS_GAS_CLOUD,
                ColoredNoxiousGasCloudParticle.Provider::new);

        // Individual visible gas-smoke particles (need the noxious_gas sprite sheet)
        ParticleProviderRegistry.getInstance().register(
                ModParticleTypes.COLORED_NOXIOUS_GAS,
                ColoredNoxiousGasParticle.Provider::new);

        // Geyser plume particles (need the geyser sprite sheet)
        ParticleProviderRegistry.getInstance().register(
                ModParticleTypes.COLORED_GEYSER,
                ColoredGeyserPlumeParticle.Provider::new);
    }
}
