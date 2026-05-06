package me.sshcrack.dyeable_sulfur_blocks.client.particle;

import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredNoxiousGasCloudOptions;
import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredNoxiousGasOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Like vanilla {@code NoxiousGasCloudParticle} but spawns {@link ColoredNoxiousGasParticle}
 * instances instead of white ones.  The colour is carried in the spawning
 * {@link ColoredNoxiousGasCloudOptions}.
 */
@Environment(EnvType.CLIENT)
public class ColoredNoxiousGasCloudParticle extends NoRenderParticle {

    private final int color;

    protected ColoredNoxiousGasCloudParticle(
            ClientLevel level,
            double x, double y, double z,
            int color) {
        super(level, x, y, z);
        this.lifetime = 20;
        this.color = color;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age % 2 == 0) {
            BlockPos sourceBlock = BlockPos.containing(this.x, this.y, this.z);
            Vec3 particlePos = pickRandomParticleSpawnPoint(this.level, sourceBlock);
            if (PotentSulfurBlockEntity.canBeReachedByNoxiousGas(this.level, sourceBlock, particlePos)) {
                spawnColoredGasParticle(this.level, particlePos, this.color);
            }
        }
    }

    private static Vec3 pickRandomParticleSpawnPoint(Level level, BlockPos centerBlock) {
        RandomSource random = level.getRandom();
        Vec3 dir = new Vec3(random.nextFloat() - 0.5F, 0.0, random.nextFloat() - 0.5F).normalize();
        float distance = random.nextFloat() * 3.0F;
        return centerBlock.getCenter().add(dir.scale(distance)).subtract(0.0, 0.25, 0.0);
    }

    private static void spawnColoredGasParticle(Level level, Vec3 pos, int color) {
        level.addAlwaysVisibleParticle(
                new ColoredNoxiousGasOptions(color),
                pos.x, pos.y, pos.z,
                0.0, 0.0, 0.0);
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<ColoredNoxiousGasCloudOptions> {
        /**
         * SpriteSet is accepted but ignored – this is a {@link NoRenderParticle}.
         */
        @SuppressWarnings("unused")
        public Provider(SpriteSet ignored) {
        }

        @Nullable
        @Override
        public Particle createParticle(
                ColoredNoxiousGasCloudOptions options,
                @NonNull ClientLevel level,
                double x, double y, double z,
                double xAux, double yAux, double zAux,
                @NonNull RandomSource random) {
            return new ColoredNoxiousGasCloudParticle(level, x, y, z, options.color());
        }
    }
}
