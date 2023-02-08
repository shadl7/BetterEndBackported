package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockState;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class UnderwaterWallPlantBlock extends WallPlantBlock implements ILiquidContainer
{
	public UnderwaterWallPlantBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return worldIn.getFluidState(pos).getFluid() == Fluids.WATER && super.isValidPosition(state, worldIn, pos);
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
}
