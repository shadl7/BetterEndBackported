package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class UpDownPlantBlock extends Block
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
	
	public UpDownPlantBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Nonnull
    @Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Override
	public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState down = worldIn.getBlockState(pos.down());
		BlockState up = worldIn.getBlockState(pos.up());
		return (isTerrain(down) || down.getBlock() == this) && (isSupport(up, worldIn, pos) || up.getBlock() == this);
	}
	
	protected boolean isTerrain(BlockState state) 
	{
		return state.isIn(ModTags.END_GROUND);
	}
	
	protected boolean isSupport(BlockState state, IWorldReader world, BlockPos pos) 
	{
		return hasEnoughSolidSide(world, pos.up(), Direction.UP);
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
	public void onBlockHarvested(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull PlayerEntity player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);
		worldIn.neighborChanged(pos, Blocks.AIR, pos.down());
	}
}
