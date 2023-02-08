package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class BlueVineLanternBlock extends Block
{
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
	
	public BlueVineLanternBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(NATURAL, false));
	}

	@Override
	public boolean isValidPosition(BlockState state, @Nonnull IWorldReader worldIn, @Nonnull BlockPos pos)
	{
		return !state.get(NATURAL) || worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.BLUE_VINE.get();
	}
	
	@Nonnull
    @Override
	public BlockState updatePostPlacement(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn,
                                          @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else {
			return stateIn;
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(NATURAL);
	}
}
