package me.sshcrack.dyeable_sulfur_blocks.client.particle;

import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredGeyserBaseParticleOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ColoredGeyserBaseParticle extends BaseAshSmokeParticle {
    private ColoredGeyserBaseParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double xAux,
            double yAux,
            double zAux,
            int color,
            int waterBlocks,
            float burstImpulseBase,
            SpriteSet sprites
    ) {
        float burstImpulse = burstImpulseBase + 0.25F * waterBlocks;
        float size = 3.0F + 0.125F * waterBlocks;
        super(level, x, y, z, burstImpulse, burstImpulse, burstImpulse, xAux, yAux, zAux, size, sprites, 0.0F, 0, 0.0F, true);
        this.friction = 0.725F;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.yd = Math.abs(this.yd);
        float lifetimeFactor = 0.8F + 0.2F * level.getRandom().nextFloat();
        this.lifetime = (int) (25.0F * lifetimeFactor);

        // Apply dye colour (same technique as DustParticle)
        Vector3f rgb = ARGB.vector3fFromRGB24(color);
        this.rCol = rgb.x();
        this.gCol = rgb.y();
        this.bCol = rgb.z();
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<ColoredGeyserBaseParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(
                ColoredGeyserBaseParticleOptions options,
                @NonNull ClientLevel level,
                double x,
                double y,
                double z,
                double xAux,
                double yAux,
                double zAux,
                RandomSource random
        ) {
            double randomX = x + (random.nextFloat() - 0.5F) * 0.5F;
            double randomY = y + (random.nextFloat() - 0.5F) * 0.5F + 0.2F;
            double randomZ = z + (random.nextFloat() - 0.5F) * 0.5F;
            return new ColoredGeyserBaseParticle(
                    level,
                    randomX, randomY, randomZ,
                    xAux, yAux, zAux,
                    options.color(),
                    options.waterBlocks(),
                    options.burstImpulseBase(),
                    this.sprites
            );
        }
    }
}
