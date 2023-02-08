package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class EndCropBlock extends PlantBlock
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 14, 14);
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	
	private final Block[] terrain;
	
	public EndCropBlock(Properties properties, Block... terrain) 
	{
		super(properties);
		this.terrain = terrain;
		this.setDefaultState(getDefaultState().with(AGE, 0));
	}

	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Nonnull
    @Override
	public OffsetType getOffsetType()
	{
		return OffsetType.NONE;
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		for (Block block: terrain) 
		{
			if (state.isIn(block)) 
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canGrow(@Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient)
	{
		return state.get(AGE) < 3;
	}
	
	@Override
	public boolean canUseBonemeal(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state)
	{
		return state.get(AGE) < 3;
	}
	
	@Override
	public void grow(@Nonnull ServerWorld worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state)
	{
		if (rand.nextInt(8) == 0)
		{
			int age = state.get(AGE);
			if (age < 3) 
		    {
				BlockHelper.setWithUpdate(worldIn, pos, state.with(AGE, age + 1));
			}
		}
	}
	
	@Override
	public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random)
	{
		//super.randomTick(state, worldIn, pos, random);
		grow(worldIn, random, pos, state);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
}
