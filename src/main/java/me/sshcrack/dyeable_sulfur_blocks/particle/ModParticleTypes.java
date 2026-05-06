package me.sshcrack.dyeable_sulfur_blocks.particle;

import com.mojang.serialization.MapCodec;
import me.sshcrack.dyeable_sulfur_blocks.DyeableSulfurBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.function.Function;

public class ModParticleTypes {
    private ModParticleTypes() {
        /* This utility class should not be instantiated */
    }


    /**
     * NoRenderParticle orchestrator – spawns {@link #COLORED_NOXIOUS_GAS} particles around the source block.
     */
    public static final ParticleType<ColoredNoxiousGasCloudOptions> COLORED_NOXIOUS_GAS_CLOUD =
            register("colored_noxious_gas_cloud",
                    ColoredNoxiousGasCloudOptions.CODEC,
                    ColoredNoxiousGasCloudOptions.STREAM_CODEC);

    /**
     * Individual visible noxious-gas smoke particle, tinted with the dye colour.
     */
    public static final ParticleType<ColoredNoxiousGasOptions> COLORED_NOXIOUS_GAS =
            register("colored_noxious_gas",
                    ColoredNoxiousGasOptions.CODEC,
                    ColoredNoxiousGasOptions.STREAM_CODEC);

    /**
     * Geyser plume particle, tinted with the dye colour.
     */
    public static final ParticleType<ColoredGeyserParticleOptions> COLORED_GEYSER =
            register("colored_geyser",
                    ColoredGeyserParticleOptions::codec,
                    ColoredGeyserParticleOptions::streamCodec);

    private static <T extends ParticleOptions> ParticleType<T> register(
            String name,
            MapCodec<T> codec,
            StreamCodec<? super io.netty.buffer.ByteBuf, T> streamCodec) {

        return Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(DyeableSulfurBlocks.MOD_ID, name),
                new ParticleType<T>(false) {
                    @Override
                    public @NonNull MapCodec<T> codec() {
                        return codec;
                    }

                    @Override
                    public @NonNull StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                        // ByteBuf is a supertype of RegistryFriendlyByteBuf – safe widening cast
                        return streamCodec;
                    }
                });
    }


    private static <T extends ParticleOptions> ParticleType<T> register(
            String name,
            Function<? super ParticleType<T>, ? extends MapCodec<T>> codec,
            Function<? super ParticleType<T>, ? extends StreamCodec<? super io.netty.buffer.ByteBuf, T>> streamCodec) {

        return Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                Identifier.fromNamespaceAndPath(DyeableSulfurBlocks.MOD_ID, name),
                new ParticleType<T>(false) {
                    @Override
                    public @NonNull MapCodec<T> codec() {
                        return codec.apply(this);
                    }

                    @Override
                    public @NonNull StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                        // ByteBuf is a supertype of RegistryFriendlyByteBuf – safe widening cast
                        return streamCodec.apply(this);
                    }
                });
    }

    /**
     * Call once during mod initialisation to trigger class loading (and thereby registration).
     */
    public static void init() { /* Just initialize */ }
}
