package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;

public class CharniaBlock extends UnderwaterPlantBlock
{
	public CharniaBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP) 
				&& worldIn.getFluidState(pos).getFluid() == Fluids.WATER;
	}
}
