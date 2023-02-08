package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
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
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class DoublePlantBlock extends Block implements IGrowable
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(4, 2, 4, 12, 16, 12);
	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);
	public static final BooleanProperty TOP = BooleanProperty.create("top");
	
	public DoublePlantBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(TOP, false));
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
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		BlockState down = worldIn.getBlockState(pos.down());
		BlockState up = worldIn.getBlockState(pos.up());
		return state.get(TOP) ? down.getBlock() == this : isTerrain(down) && (up.getMaterial().isReplaceable());
	}
	
	protected boolean isTerrain(BlockState state)
	{
		return state.isIn(ModTags.END_GROUND);
	}
	
	public boolean canStayAt(BlockState state, IWorldReader world, BlockPos pos) 
	{
		BlockState down = world.getBlockState(pos.down());
		BlockState up = world.getBlockState(pos.up());
		return state.get(TOP) ? down.getBlock() == this : isTerrain(down) && (up.getBlock() == this);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack)
	{
		int rot = worldIn.rand.nextInt(4);
		BlockState bs = this.getDefaultState().with(ROTATION, rot);
		BlockHelper.setWithoutUpdate(worldIn, pos, bs);
		BlockHelper.setWithoutUpdate(worldIn, pos.up(), bs.with(TOP, true));
	}
	
	@Nonnull
    @Override
	public BlockState updatePostPlacement(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn,
                                          @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
	{
		if (!canStayAt(stateIn, worldIn, currentPos)) 
		{
			return Blocks.AIR.getDefaultState();
		}
		else {
			return stateIn;
		}
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

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(ROTATION, TOP);
	}
}
