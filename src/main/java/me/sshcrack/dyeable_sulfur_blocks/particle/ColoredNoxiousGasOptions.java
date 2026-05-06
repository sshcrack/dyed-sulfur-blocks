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
 * Options for each individual visible noxious-gas smoke particle.
 * Spawned inside {@link me.sshcrack.dyeable_sulfur_blocks.client.particle.ColoredNoxiousGasCloudParticle}.
 */
public record ColoredNoxiousGasOptions(int color) implements ParticleOptions {

    public static final MapCodec<ColoredNoxiousGasOptions> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(ColoredNoxiousGasOptions::color))
                    .apply(i, ColoredNoxiousGasOptions::new));

    public static final StreamCodec<ByteBuf, ColoredNoxiousGasOptions> STREAM_CODEC =
            ByteBufCodecs.INT.map(ColoredNoxiousGasOptions::new, ColoredNoxiousGasOptions::color);

    @Override
    public @NonNull ParticleType<ColoredNoxiousGasOptions> getType() {
        return ModParticleTypes.COLORED_NOXIOUS_GAS;
    }
}
