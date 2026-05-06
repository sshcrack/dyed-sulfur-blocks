package me.sshcrack.dyeable_sulfur_blocks.client.particle;

import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredGeyserBaseParticleOptions;
import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredGeyserParticleOptions;
import me.sshcrack.dyeable_sulfur_blocks.particle.ModParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ColoredGeyserEruptionParticle extends NoRenderParticle {
    private final int waterBlocks;
    private final double xa;
    private final double ya;
    private final double za;
    private final ColoredGeyserParticleOptions plumeParticle;
    private final ColoredGeyserBaseParticleOptions baseParticle;
    private final ColoredGeyserBaseParticleOptions poofParticle;

    protected ColoredGeyserEruptionParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double xAux,
            double yAux,
            double zAux,
            ColoredGeyserParticleOptions options
    ) {
        super(level, x, y, z);
        this.xa = xAux;
        this.ya = yAux;
        this.za = zAux;
        this.waterBlocks = options.waterBlocks();
        this.lifetime = 20;
        this.plumeParticle = new ColoredGeyserParticleOptions(ModParticleTypes.COLORED_GEYSER_PLUME, options.color(), this.waterBlocks);
        this.baseParticle = new ColoredGeyserBaseParticleOptions(ModParticleTypes.COLORED_GEYSER_BASE, options.color(), this.waterBlocks, 1.5F);
        this.poofParticle = new ColoredGeyserBaseParticleOptions(ModParticleTypes.COLORED_GEYSER_POOF, options.color(), this.waterBlocks, 2.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age % 2 == 0) {
            for (int i = 0; i < 2; i++) {
                this.level.addParticle(this.baseParticle, this.x, this.y, this.z, this.xa, this.ya, this.za);
            }
        }

        for (int i = 0; i < this.waterBlocks + 2; i++) {
            this.level.addParticle(this.plumeParticle, this.x, this.y, this.z, this.xa, this.ya, this.za);
        }

        if (this.age % 10 == 0) {
            for (int i = 0; i < 20; i++) {
                this.level.addParticle(this.poofParticle, this.x, this.y, this.z, this.xa, this.ya, this.za);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<ColoredGeyserParticleOptions> {
        public Provider(SpriteSet sprites) {
        }

        @Nullable
        public Particle createParticle(
                @NonNull ColoredGeyserParticleOptions options,
                @NonNull ClientLevel level,
                double x,
                double y,
                double z,
                double xAux,
                double yAux,
                double zAux,
                @NonNull RandomSource random
        ) {
            return new ColoredGeyserEruptionParticle(level, x, y, z, xAux, yAux, zAux, options);
        }
    }
}
