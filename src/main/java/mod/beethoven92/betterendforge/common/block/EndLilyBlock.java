package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class EndLilyBlock extends UnderwaterPlantBlock
{
	public static final EnumProperty<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final VoxelShape SHAPE_BOTTOM = Block.makeCuboidShape(4, 0, 4, 12, 16, 12);
	private static final VoxelShape SHAPE_TOP = Block.makeCuboidShape(2, 0, 2, 14, 6, 14);
	
	public EndLilyBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) 
	{
		return state.get(SHAPE) == TripleShape.TOP ? 13 : 0; 
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		VoxelShape shape = state.get(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
		return shape.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		if (state.get(SHAPE) == TripleShape.TOP) 
		{
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		}
		else if (state.get(SHAPE) == TripleShape.BOTTOM) 
		{
			return isTerrain(worldIn.getBlockState(pos.down()));
		}
		else 
		{
			BlockState up = worldIn.getBlockState(pos.up());
			BlockState down = worldIn.getBlockState(pos.down());
			return up.getBlock() == this && down.getBlock() == this;
		}
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return stateIn.get(SHAPE) == TripleShape.TOP ? Blocks.AIR.getDefaultState() : Blocks.WATER.getDefaultState();
		}
		else 
		{
			return stateIn;
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(SHAPE);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.get(SHAPE) == TripleShape.TOP ? Fluids.EMPTY.getDefaultState() : Fluids.WATER.getStillFluidState(false);
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) 
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return false;
	}

}