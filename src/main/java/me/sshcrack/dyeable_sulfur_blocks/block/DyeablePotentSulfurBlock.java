package me.sshcrack.dyeable_sulfur_blocks.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.PotentSulfurBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

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

    @Override
    public MapCodec<DyeablePotentSulfurBlock> codec() {
        return CODEC;
    }
}
