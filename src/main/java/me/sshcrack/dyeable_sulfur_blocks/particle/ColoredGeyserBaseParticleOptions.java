package me.sshcrack.dyeable_sulfur_blocks.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record ColoredGeyserBaseParticleOptions(ParticleType<ColoredGeyserBaseParticleOptions> type, int color,
                                               int waterBlocks, float burstImpulseBase) implements ParticleOptions {
    public static MapCodec<ColoredGeyserBaseParticleOptions> codec(ParticleType<ColoredGeyserBaseParticleOptions> type) {
        return RecordCodecBuilder.mapCodec(
                i -> i.group(
                                ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter(e -> e.color),
                                ExtraCodecs.POSITIVE_INT.fieldOf("water_blocks").forGetter(o -> o.waterBlocks),
                                Codec.FLOAT.fieldOf("burst_impulse_base").forGetter(o -> o.burstImpulseBase)
                        )
                        .apply(i, (color, waterBlocks, burstImpulseBase) -> new ColoredGeyserBaseParticleOptions(type, color, waterBlocks, burstImpulseBase))
        );
    }

    public static StreamCodec<? super ByteBuf, ColoredGeyserBaseParticleOptions> streamCodec(final ParticleType<ColoredGeyserBaseParticleOptions> type) {
        return StreamCodec.composite(
                ByteBufCodecs.INT, ColoredGeyserBaseParticleOptions::color,
                ByteBufCodecs.INT,
                o -> o.waterBlocks,
                ByteBufCodecs.FLOAT,
                o -> o.burstImpulseBase,
                (color, waterBlocks, burstImpulseBase) -> new ColoredGeyserBaseParticleOptions(type, color, waterBlocks, burstImpulseBase)
        );
    }

    @Override
    public ParticleType<ColoredGeyserBaseParticleOptions> getType() {
        return this.type;
    }
}
