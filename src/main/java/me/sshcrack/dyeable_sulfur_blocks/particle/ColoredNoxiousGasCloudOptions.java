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
 * Options for the colored noxious gas cloud particle (the NoRenderParticle orchestrator).
 * Carries the dye colour so the cloud can spawn correctly coloured gas particles.
 */
public record ColoredNoxiousGasCloudOptions(int color) implements ParticleOptions {

    public static final MapCodec<ColoredNoxiousGasCloudOptions> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(ColoredNoxiousGasCloudOptions::color))
                    .apply(i, ColoredNoxiousGasCloudOptions::new));

    public static final StreamCodec<ByteBuf, ColoredNoxiousGasCloudOptions> STREAM_CODEC =
            ByteBufCodecs.INT.map(ColoredNoxiousGasCloudOptions::new, ColoredNoxiousGasCloudOptions::color);

    @Override
    public @NonNull ParticleType<ColoredNoxiousGasCloudOptions> getType() {
        return ModParticleTypes.COLORED_NOXIOUS_GAS_CLOUD;
    }
}
