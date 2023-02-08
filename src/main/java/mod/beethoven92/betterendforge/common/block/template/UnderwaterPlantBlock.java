package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import java.util.Random;

public class UnderwaterPlantBlock extends Block implements IGrowable, ILiquidContainer, IForgeShearable
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 14, 12);
	
	public UnderwaterPlantBlock(Properties properties)
	{
		super(properties);
	}

	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
	}
	
	@Nonnull
    @Override
	public OffsetType getOffsetType()
	{
		return OffsetType.XZ;
	}
	
	@Override
	public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState down = worldIn.getBlockState(pos.down());
		state = worldIn.getBlockState(pos);
		return isTerrain(down) && state.getFluidState().getFluid().equals(Fluids.WATER.getStillFluid());
	}
	
	protected boolean isTerrain(BlockState state) 
	{		
		return state.isIn(ModTags.END_GROUND) || state.getBlock() == ModBlocks.ENDSTONE_DUST.get();
	}
	
	@Nonnull
    @Override
	public BlockState updatePostPlacement(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn,
                                          @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			worldIn.destroyBlock(currentPos, true);
			return Blocks.WATER.getDefaultState();
		}
		else 
		{
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			return stateIn;
		}
	}
	
	@Override
	public boolean canContainFluid(@Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Fluid fluidIn)
	{
		return false;
	}

	@Override
	public boolean receiveFluid(@Nonnull IWorld worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull FluidState fluidStateIn)
	{
		return false;
	}
	
	@Nonnull
    @Override
	public FluidState getFluidState(@Nonnull BlockState state)
	{
		return Fluids.WATER.getStillFluidState(false);
	}
	
	@Override
	public boolean canGrow(@Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient)
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state)
	{
		return false;
	}

	@Override
	public void grow(@Nonnull ServerWorld worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state)
	{	
	}

}
