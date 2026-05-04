package me.sshcrack.dyeable_sulfur_blocks.platform.fabric.datagen;

//? fabric {
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
//? != 1.19.2 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//?}

public class FabricDataGeneratorEntrypoint implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		final FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider((FabricPackOutput output) -> new ModRecipeProvider(output, generator.getRegistries()));
	}

}
//?}
