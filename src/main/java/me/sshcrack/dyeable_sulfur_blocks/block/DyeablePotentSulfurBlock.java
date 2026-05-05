package me.sshcrack.dyeable_sulfur_blocks.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.PotentSulfurBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.NonNull;

public class DyeablePotentSulfurBlock extends PotentSulfurBlock {

    public static final MapCodec<PotentSulfurBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    DyeColor.CODEC.fieldOf("color").forGetter(e -> ((DyeablePotentSulfurBlock) e).getDyeColor()),
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

    @Override
    public @NonNull MapCodec<PotentSulfurBlock> codec() {
        return CODEC;
    }
}
