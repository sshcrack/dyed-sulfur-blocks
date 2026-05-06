package me.sshcrack.dyeable_sulfur_blocks.client.particle;

import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredGeyserParticleOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Like vanilla {@code GeyserPlumeParticle} but tinted with the dye colour from
 * {@link ColoredGeyserParticleOptions}.  The full geyser-plume physics logic is
 * replicated here because the vanilla constructor is private.
 */
@Environment(EnvType.CLIENT)
public class ColoredGeyserPlumeParticle extends SingleQuadParticle {

    private static final float INITIAL_PROPULSION_FACTOR = 1.45F;
    private static final float GRADUAL_GRAVITY_FACTOR = 0.12F;
    private static final float GRAVITY_EXPONENT = 3.0F;

    private final SpriteSet sprites;
    private final double startY;
    private final double maxY;
    private final float initialPropulsion;
    private final float horizontalSprayX;
    private final float horizontalSprayZ;
    private final float minSize;
    private final float maxSize;
    private boolean done;

    private ColoredGeyserPlumeParticle(
            ClientLevel level,
            double x, double y, double z,
            double xa, double ya, double za,
            ColoredGeyserParticleOptions options,
            SpriteSet sprites) {

        super(level, x, y, z, xa, ya, za, sprites.first());

        int plumeHeight = 5 * Math.max(1, options.waterBlocks());
        this.hasPhysics = true;
        this.speedUpWhenYMotionIsBlocked = true;
        this.lifetime = plumeHeight * 5;
        this.yd = 0.0;
        this.startY = y;
        this.maxY = this.startY + plumeHeight - 1.0;
        this.horizontalSprayX = (level.getRandom().nextFloat() - 0.5F) * 0.2F;
        this.horizontalSprayZ = (level.getRandom().nextFloat() - 0.5F) * 0.2F;
        this.friction = 1.0F;
        this.initialPropulsion = (options.waterBlocks() == 1 ? 1.5F : 1.0F) * plumeHeight * INITIAL_PROPULSION_FACTOR;
        this.gravity = -this.initialPropulsion;

        float base = this.quadSize * 0.75F;
        this.minSize = base * (2.0F + plumeHeight / 8.0F);
        this.maxSize = base * (3.0F + plumeHeight / 8.0F);
        this.quadSize = this.minSize;
        this.sprites = sprites;
        this.setSpriteFromAge(sprites);

        // Apply dye colour (same technique as DustParticle)
        /*Vector3f rgb = ARGB.vector3fFromRGB24(options.color());
        this.rCol = rgb.x();
        this.gCol = rgb.y();
        this.bCol = rgb.z();*/
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.done && (this.yd < 0.0 || this.y > this.maxY || this.y == this.yo)) {
            this.lifetime = Math.min(this.lifetime, this.age + 5);
            this.friction = 0.0F;
            this.done = true;
        }

        double yProgressLinear = Math.clamp((this.y - this.startY) / (this.maxY - this.startY), 0.0, 1.0);
        double yProgressExponential = Math.pow(yProgressLinear, GRAVITY_EXPONENT);
        this.gravity = this.initialPropulsion * (float) yProgressExponential * GRADUAL_GRAVITY_FACTOR;
        this.xd = yProgressLinear * this.horizontalSprayX;
        this.zd = yProgressLinear * this.horizontalSprayZ;
        this.setSpriteFromAge(this.sprites);
        this.quadSize = this.minSize + (float) (yProgressLinear * (this.maxSize - this.minSize));
    }

    @Override
    protected SingleQuadParticle.@NonNull Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<ColoredGeyserParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(
                @NonNull ColoredGeyserParticleOptions options,
                @NonNull ClientLevel level,
                double x, double y, double z,
                double xAux, double yAux, double zAux,
                RandomSource random) {
            double rx = x + (random.nextFloat() - 0.5F) * 0.2F;
            double ry = y + random.nextFloat();
            double rz = z + (random.nextFloat() - 0.5F) * 0.2F;
            return new ColoredGeyserPlumeParticle(level, rx, ry, rz, xAux, yAux, zAux, options, this.sprites);
        }
    }
}
