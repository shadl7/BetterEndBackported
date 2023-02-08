package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class UnderwaterPlantBlockWithAge extends UnderwaterPlantBlock
{
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	
	public UnderwaterPlantBlockWithAge(Properties properties) 
	{
		super(properties);
	}
	
	public abstract void doGrow(ISeedReader world, Random random, BlockPos pos);
	
	@Override
	public void grow(@Nonnull ServerWorld worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state)
	{
		if (rand.nextInt(4) == 0) 
		{
			int age = state.get(AGE);
			if (age < 3) 
			{
				worldIn.setBlockState(pos, state.with(AGE, age + 1));
			}
			else 
			{
				doGrow(worldIn, rand, pos);
			}
		}
	}
	
	@Override
	public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random)
	{
		if (canGrow(worldIn, pos, state, false)) 
		{
			grow(worldIn, random, pos, state);
		}
	}
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
}
