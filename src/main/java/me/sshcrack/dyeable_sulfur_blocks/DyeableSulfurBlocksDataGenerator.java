package me.sshcrack.dyeable_sulfur_blocks;

import me.sshcrack.dyeable_sulfur_blocks.datagen.ModLanguageProvider;
import me.sshcrack.dyeable_sulfur_blocks.datagen.ModLootTableProvider;
import me.sshcrack.dyeable_sulfur_blocks.datagen.ModModelProvider;
import me.sshcrack.dyeable_sulfur_blocks.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DyeableSulfurBlocksDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModLanguageProvider::new);
	}
}
