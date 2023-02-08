package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.tileentity.HydrothermalVentTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class HydrothermalVentBlock extends Block implements IWaterLoggable
{
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 1, 1, 15, 16, 15);
	
	public HydrothermalVentBlock(Properties properties) 
	{
		super(properties);
		this.setDefaultState(getDefaultState().with(WATERLOGGED, true).with(ACTIVATED, false));
	}
	
	@Nonnull
    @Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return new HydrothermalVentTileEntity();
	}
	
	@Override
	public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack)
	{
		if (worldIn instanceof ServerWorld && state.get(WATERLOGGED) && worldIn.getBlockState(pos.up()).isIn(Blocks.WATER))
		{
			randomTick(state,(ServerWorld) worldIn, pos, worldIn.rand);
		}
	}
	
	@Override
	public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		state = worldIn.getBlockState(pos.down());
		return state.isIn(ModBlocks.SULPHURIC_ROCK.stone.get());
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getPos();
		return this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER);
	}
	
	@Nonnull
    @Override
	public BlockState updatePostPlacement(@Nonnull BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn,
                                          @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
	{
		if (!isValidPosition(stateIn, worldIn, currentPos)) 
		{
			return Blocks.WATER.getDefaultState();
		}
		else if (stateIn.get(WATERLOGGED) && facing == Direction.UP && facingState.isIn(Blocks.WATER)) 
		{
			worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 20);
		}
		return stateIn;
	}
	
	@Override
	public void animateTick(BlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
	{
		if (!stateIn.get(ACTIVATED) && rand.nextBoolean()) 
		{
			double x = pos.getX() + rand.nextDouble();
			double y = pos.getY() + 0.9 + rand.nextDouble() * 0.3;
			double z = pos.getZ() + rand.nextDouble();
			if (stateIn.get(WATERLOGGED)) 
			{
				worldIn.addParticle(ModParticleTypes.GEYSER_PARTICLE.get(), x, y, z, 0, 0, 0);
			}
			else 
			{
				worldIn.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
			}
		}
	}
	
	@Override
	public void randomTick(@Nonnull BlockState state, ServerWorld worldIn, BlockPos pos, @Nonnull Random random)
	{
		BlockPos up = pos.up();
		if (worldIn.getBlockState(up).isIn(Blocks.WATER)) 
		{
			BlockHelper.setWithoutUpdate(worldIn, up, ModBlocks.VENT_BUBBLE_COLUMN.get());
			worldIn.getPendingBlockTicks().scheduleTick(up, ModBlocks.VENT_BUBBLE_COLUMN.get(), 5);
		}
	}
	
	@Nonnull
    @Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(WATERLOGGED, ACTIVATED);
	}
}
