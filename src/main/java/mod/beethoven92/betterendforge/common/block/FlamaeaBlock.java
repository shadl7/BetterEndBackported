package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class FlamaeaBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);

	public FlamaeaBlock() {
		super(AbstractBlock.Properties.create(Material.PLANTS).sound(SoundType.WET_GRASS).zeroHardnessAndResistance());
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(Blocks.WATER);
	}

	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return SHAPE;
	}

	@Nonnull
    @Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}
}
