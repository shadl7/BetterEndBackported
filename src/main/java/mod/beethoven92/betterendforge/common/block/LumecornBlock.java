package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class LumecornBlock extends Block {
	public static final EnumProperty<LumecornShape> SHAPE = EnumProperty.create("shape", LumecornShape.class);
	private static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(6, 0, 6, 10, 16, 10);
	private static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(6, 0, 6, 10, 8, 10);
	
	public LumecornBlock(AbstractBlock.Properties properties) {
		super(properties);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> stateManager) {
		stateManager.add(SHAPE);
	}

	
	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return state.get(SHAPE) == LumecornShape.LIGHT_TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}
	
	@Override
	public boolean isValidPosition(BlockState state, @Nonnull IWorldReader world, @Nonnull BlockPos pos) {
		LumecornShape shape = state.get(SHAPE);
		if (shape == LumecornShape.BOTTOM_BIG || shape == LumecornShape.BOTTOM_SMALL) {
			return world.getBlockState(pos.down()).isIn(ModTags.END_GROUND);
		}
		else if (shape == LumecornShape.LIGHT_TOP) {
			return world.getBlockState(pos.down()).isIn(this);
		}
		else {
			return world.getBlockState(pos.down()).isIn(this) && world.getBlockState(pos.up()).isIn(this);
		}
	}
	
	@Nonnull
    @Override
	public BlockState updatePostPlacement(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn,
                                          @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) {
			return Blocks.AIR.getDefaultState();
		}
		else {
			return stateIn;
		}
	}
}
