package me.sshcrack.dyeable_sulfur_blocks.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jspecify.annotations.NonNull;

/**
 * Options for the colored geyser plume particle.
 * Like vanilla {@link net.minecraft.core.particles.GeyserParticleOptions} but also carries a dye colour.
 */
public record ColoredGeyserParticleOptions(ParticleType<ColoredGeyserParticleOptions> type, int color,
                                           int waterBlocks) implements ParticleOptions {

    public static MapCodec<ColoredGeyserParticleOptions> codec(ParticleType<ColoredGeyserParticleOptions> type) {
        return RecordCodecBuilder.mapCodec(i ->
                i.group(
                        ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(ColoredGeyserParticleOptions::color),
                        ExtraCodecs.POSITIVE_INT.fieldOf("water_blocks").forGetter(ColoredGeyserParticleOptions::waterBlocks)
                ).apply(i, (a, b) -> new ColoredGeyserParticleOptions(type, a, b)));
    }

    public static StreamCodec<ByteBuf, ColoredGeyserParticleOptions> streamCodec(ParticleType<ColoredGeyserParticleOptions> type) {
        return StreamCodec.composite(
                ByteBufCodecs.INT, ColoredGeyserParticleOptions::color,
                ByteBufCodecs.INT, ColoredGeyserParticleOptions::waterBlocks,
                (a, b) -> new ColoredGeyserParticleOptions(type, a, b));
    }

    @Override
    public @NonNull ParticleType<ColoredGeyserParticleOptions> getType() {
        return type;
    }
}
