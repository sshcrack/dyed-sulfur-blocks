package me.sshcrack.dyeable_sulfur_blocks.client.particle;

import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredNoxiousGasOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

/**
 * Like vanilla {@code NoxiousGasParticle} but tinted with the dye colour stored in
 * {@link ColoredNoxiousGasOptions}.  Extends {@code BaseAshSmokeParticle} just as the
 * vanilla class does, then overrides the default white colour with the dye's RGB values.
 */
@Environment(EnvType.CLIENT)
public class ColoredNoxiousGasParticle extends BaseAshSmokeParticle {

    private final float fadeOutStartingPoint;

    protected ColoredNoxiousGasParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            float scale,
            SpriteSet sprites,
            int color) {

        super(level, x, y, z, 0.1F, 0.1F, 0.1F, xa, ya, za, scale, sprites, 0.3F, 5, -0.02F, true);

        // Tint with the dye colour (ARGB.vector3fFromRGB24 normalises to 0-1 floats)
        Vector3f rgb = ARGB.vector3fFromRGB24(color);
        this.rCol = rgb.x();
        this.gCol = rgb.y();
        this.bCol = rgb.z();

        this.lifetime = (int) (6.0 / (this.random.nextFloat() * 0.5 + 0.5) * scale);
        this.fadeOutStartingPoint = this.lifetime / 2.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > this.fadeOutStartingPoint) {
            float framesSinceFade = this.age - this.fadeOutStartingPoint;
            this.setAlpha((this.lifetime - framesSinceFade) / this.lifetime);
        }
    }

    @Override
    public SingleQuadParticle.@NonNull Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<ColoredNoxiousGasOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                ColoredNoxiousGasOptions options,
                @NonNull ClientLevel level,
                double x, double y, double z,
                double xAux, double yAux, double zAux,
                @NonNull RandomSource random) {
            return new ColoredNoxiousGasParticle(level, x, y, z, xAux, yAux, zAux, 3.0F, this.sprites, options.color());
        }
    }
}
