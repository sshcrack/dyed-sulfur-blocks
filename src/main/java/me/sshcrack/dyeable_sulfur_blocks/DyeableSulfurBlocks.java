package me.sshcrack.dyeable_sulfur_blocks;

import me.sshcrack.dyeable_sulfur_blocks.particle.ModParticleTypes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DyeableSulfurBlocks implements ModInitializer {
    public static final String MOD_ID = "dyeable_sulfur_blocks";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModParticleTypes.init(); // register COLORED_NOXIOUS_GAS_CLOUD, COLORED_NOXIOUS_GAS, COLORED_GEYSER
        ModBlocks.init();
        ModItems.init();
        LOGGER.info("Dyeable Sulfur Blocks initialized!");
    }
}
