package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class VentBubbleColumnBlock extends Block implements IBucketPickupHandler
{
	public VentBubbleColumnBlock(Properties properties) 
	{
		super(properties);
	}

	@Nonnull
    @Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		return VoxelShapes.empty();
	}
	
	@Override
	public boolean isValidPosition(@Nonnull BlockState state, IWorldReader worldIn, BlockPos pos)
	{
		BlockState blockState = worldIn.getBlockState(pos.down());
		return blockState.isIn(this) || blockState.isIn(ModBlocks.HYDROTHERMAL_VENT.get());
	}
	
	@Nonnull
    @Override
	public BlockState updatePostPlacement(BlockState stateIn, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld worldIn,
                                          @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos)
	{
		if (!stateIn.isValidPosition(worldIn, currentPos)) 
		{
			return Blocks.WATER.getDefaultState();
		}
		else 
		{
			BlockPos up = currentPos.up();
			if (worldIn.getBlockState(up).isIn(Blocks.WATER)) 
			{
				BlockHelper.setWithoutUpdate(worldIn, up, this);
				worldIn.getPendingBlockTicks().scheduleTick(up, this, 5);
			}
		}
		return stateIn;
	}
	
	@Override
	public void onEntityCollision(@Nonnull BlockState state, World worldIn, BlockPos pos, @Nonnull Entity entityIn)
	{
		BlockState blockState = worldIn.getBlockState(pos.up());
		if (blockState.isAir())
		{
			entityIn.onEnterBubbleColumnWithAirAbove(false);
			if (!worldIn.isRemote()) 
			{
				ServerWorld serverWorld = (ServerWorld) worldIn;

				for (int i = 0; i < 2; ++i) 
				{
					serverWorld.spawnParticle(ParticleTypes.SPLASH, (double) pos.getX() + worldIn.rand.nextDouble(), pos.getY() + 1, (double) pos.getZ() + worldIn.rand.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
					serverWorld.spawnParticle(ParticleTypes.BUBBLE, (double) pos.getX() + worldIn.rand.nextDouble(), pos.getY() + 1, (double) pos.getZ() + worldIn.rand.nextDouble(), 1, 0.0D, 0.01D, 0.0D, 0.2D);
				}
			}
		}
		else 
		{
			entityIn.onEnterBubbleColumn(false);
		}
	}
	
	@Override
	public void animateTick(@Nonnull BlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, Random rand)
	{
		if (rand.nextInt(4) == 0) 
		{
			double px = pos.getX() + rand.nextDouble();
			double py = pos.getY() + rand.nextDouble();
			double pz = pos.getZ() + rand.nextDouble();
			worldIn.addOptionalParticle(ParticleTypes.BUBBLE_COLUMN_UP, px, py, pz, 0, 0.04, 0);
		}
		if (rand.nextInt(200) == 0) 
		{
			worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, 
					SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
		}
	}
	
	@Nonnull
    @Override
	public BlockRenderType getRenderType(@Nonnull BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}
	
	@Nonnull
    @Override
	public FluidState getFluidState(@Nonnull BlockState state)
	{
		return Fluids.WATER.getStillFluidState(false);
	}

	@Nonnull
    @Override
	public Fluid pickupFluid(IWorld worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state)
	{
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
		return Fluids.WATER;
	}
}
