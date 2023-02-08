package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class AttachedBlock extends Block
{
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public AttachedBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.UP));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockState blockState = this.getDefaultState();
		IWorldReader worldView = context.getWorld();
		BlockPos blockPos = context.getPos();
		Direction[] directions = context.getNearestLookingDirections();
		for (Direction direction : directions) {
			Direction direction2 = direction.getOpposite();
			blockState = blockState.with(FACING, direction2);
			if (blockState.isValidPosition(worldView, blockPos)) {
				return blockState;
			}
		}
		return null;
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
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	public boolean isValidPosition(BlockState state, @Nonnull IWorldReader worldIn, BlockPos pos)
	{
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return hasEnoughSolidSide(worldIn, blockPos, direction) || worldIn.getBlockState(blockPos).isIn(BlockTags.LEAVES);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING);
	}
	
	@Nonnull
    public BlockState rotate(BlockState state, Rotation rot)
	{
		return state.with(FACING, rot.rotate(state.get(FACING)));
    }

	@Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn)
	{
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}
}
