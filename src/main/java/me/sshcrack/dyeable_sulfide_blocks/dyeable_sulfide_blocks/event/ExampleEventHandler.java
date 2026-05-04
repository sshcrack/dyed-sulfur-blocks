package me.sshcrack.dyeable_sulfur_blocks.dyeable_sulfur_blocks.event;

import me.sshcrack.dyeable_sulfur_blocks.dyeable_sulfur_blocks.DyedSulfurBlocksMod;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class ExampleEventHandler {

	public static void onPlayerHurt(ServerPlayer player) {
		boolean pvp = Objects.requireNonNull(player.level()).isPvpAllowed();
		if (pvp) {
			DyedSulfurBlocksMod.LOGGER.info("{} took damage. PVP is allowed.", player.getDisplayName());
		} else {
			DyedSulfurBlocksMod.LOGGER.info("{} took damage. PVP is disallowed.", player.getDisplayName());
		}
	}
}
