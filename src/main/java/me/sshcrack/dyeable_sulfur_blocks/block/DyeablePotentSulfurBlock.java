package me.sshcrack.dyeable_sulfur_blocks.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredGeyserParticleOptions;
import me.sshcrack.dyeable_sulfur_blocks.particle.ColoredNoxiousGasCloudOptions;
import me.sshcrack.dyeable_sulfur_blocks.particle.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PotentSulfurBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PotentSulfurState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class DyeablePotentSulfurBlock extends PotentSulfurBlock {

    public static final MapCodec<DyeablePotentSulfurBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    DyeColor.CODEC.fieldOf("color").forGetter(DyeablePotentSulfurBlock::getDyeColor),
                    propertiesCodec()
            ).apply(instance, DyeablePotentSulfurBlock::new)
    );

    private final DyeColor dyeColor;

    public DyeablePotentSulfurBlock(DyeColor dyeColor, BlockBehaviour.Properties properties) {
        super(properties);
        this.dyeColor = dyeColor;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public @NonNull MapCodec<PotentSulfurBlock> codec() {
        return (MapCodec<PotentSulfurBlock>) (MapCodec) CODEC;
    }

    // -----------------------------------------------------------------------
    // Colored client tickers
    // -----------------------------------------------------------------------

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {

        int color = dyeColor.getTextureDiffuseColor();
        var client = level.isClientSide();
        return createTickerHelper(
                type,
                BlockEntityTypes.POTENT_SULFUR,
                switch (blockState.getValue(STATE)) {
                    case DRY -> null;
                    case WET -> client ? createColoredNoxiousGasTicker(color) : null;
                    case DORMANT -> client
                            ? createColoredNoxiousGasTicker(color)
                            : PotentSulfurBlockEntity.SERVER_WAITING_COUNTDOWN_TICKER;
                    case ERUPTING -> client
                            ? createColoredGeyserTicker(color)
                            : PotentSulfurBlockEntity.SERVER_LAUNCH_ENTITY_TICKER.andThen(PotentSulfurBlockEntity.SERVER_WAITING_COUNTDOWN_TICKER);
                }
        );
    }

    // -----------------------------------------------------------------------
    // Replicates private static methods from PotentSulfurBlockEntity
    // -----------------------------------------------------------------------

    private static BlockEntityTicker<PotentSulfurBlockEntity> createColoredNoxiousGasTicker(int color) {
        return (level, pos, state, entity) -> {
            if (level.getGameTime() % 20L == 0L) {
                BlockPos sourceBlock = findNoxiousGasSourceBlock(level, pos);
                if (sourceBlock != null) {
                    Vec3 center = sourceBlock.getCenter();
                    level.addParticle(
                            new ColoredNoxiousGasCloudOptions(color),
                            center.x, center.y, center.z,
                            0.0, 0.0, 0.0);
                }
            }
        };
    }

    private static BlockEntityTicker<PotentSulfurBlockEntity> createColoredGeyserTicker(int color) {
        return (level, pos, state, entity) -> {
            BlockPos sourceBlock = findNoxiousGasSourceBlock(level, pos);
            if (sourceBlock != null) {
                if ((level.getGameTime() - entity.eruptionTick) % 20L == 0L) {
                    Vec3 sulfurCenter = pos.getCenter();
                    Vec3 sourceBottom = sourceBlock.getBottomCenter();
                    int waterBlocks = (int) Math.floor(sourceBottom.y - sulfurCenter.y);
                    level.addParticle(
                            new ColoredGeyserParticleOptions(ModParticleTypes.COLORED_GEYSER, color, waterBlocks),
                            //new GeyserParticleOptions(ParticleTypes.GEYSER, waterBlocks),
                            sourceBottom.x, sourceBottom.y, sourceBottom.z,
                            0.0, 0.0, 0.0);
                }
            }
        };
    }

    /**
     * Replicates {@code PotentSulfurBlockEntity.findNoxiousGasSourceBlock} which is {@code private static}.
     * Walks upward from two blocks above {@code origin} looking for the first air block
     * not covered by water (up to 4 water blocks = 5 steps).
     */
    @Nullable
    private static BlockPos findNoxiousGasSourceBlock(Level level, BlockPos origin) {
        int maxY = origin.getY() + 4 + 1;
        BlockPos.MutableBlockPos pos = origin.above(2).mutable();
        while (pos.getY() <= maxY) {
            if (!level.getFluidState(pos).isSourceOfType(Fluids.WATER)) {
                if (level.getBlockState(pos).isAir()) {
                    return pos.immutable();
                }
                break;
            }
            pos.move(Direction.UP);
        }
        return null;
    }
}
