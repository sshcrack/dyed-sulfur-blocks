package me.sshcrack.dyeable_sulfur_blocks.platform.fabric;

//? fabric {

import me.sshcrack.dyeable_sulfur_blocks.DyedSulfurBlocksMod;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		DyedSulfurBlocksMod.onInitializeClient();
	}

}
//?}
