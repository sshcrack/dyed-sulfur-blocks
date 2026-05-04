package me.sshcrack.dyeable_sulfur_blocks.dyeable_sulfur_blocks.platform.fabric;

//? fabric {

import me.sshcrack.dyeable_sulfur_blocks.dyeable_sulfur_blocks.DyedSulfurBlocksMod;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		DyedSulfurBlocksMod.onInitialize();
		FabricEventSubscriber.registerEvents();
	}
}
//?}
