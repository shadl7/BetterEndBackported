package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class SilkMothNestBlock extends Block {
	public static final BooleanProperty ACTIVE = BlockProperties.ACTIVATED;
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty FULLNESS = BlockProperties.FULLNESS;
	private static final VoxelShape TOP = makeCuboidShape(6, 0, 6, 10, 16, 10);
	private static final VoxelShape BOTTOM = makeCuboidShape(0, 0, 0, 16, 16, 16);

	public SilkMothNestBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(ACTIVE, FACING, FULLNESS);
	}

	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return state.get(ACTIVE) ? BOTTOM : TOP;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		Direction dir = ctx.getPlacementHorizontalFacing().getOpposite();
		return this.getDefaultState().with(FACING, dir);
	}

	@Nonnull
    @Override
	public BlockState updatePostPlacement(BlockState state, @Nonnull Direction facing, @Nonnull BlockState neighborState, @Nonnull IWorld world,
                                          @Nonnull BlockPos pos, @Nonnull BlockPos neighborPos) {
		if (!state.get(ACTIVE)) {
			if (hasEnoughSolidSide(world, pos.up(), Direction.DOWN)
					|| world.getBlockState(pos.up()).isIn(BlockTags.LEAVES)) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		}
		return state;
	}

	@Nonnull
    @Override
	public BlockState rotate(@Nonnull BlockState state, @Nonnull Rotation rotation) {
		return BlockHelper.rotateHorizontal(state, rotation, FACING);
	}

	@Nonnull
    @Override
	public BlockState mirror(@Nonnull BlockState state, @Nonnull Mirror mirror) {
		return BlockHelper.mirrorHorizontal(state, mirror, FACING);
	}

	@Override
	public void onBlockHarvested(@Nonnull World world, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
		if (!state.get(ACTIVE) && player.isCreative()) {
			BlockHelper.setWithUpdate(world, pos.down(), Blocks.AIR);
		}
		BlockState up = world.getBlockState(pos.up());
		if (up.isIn(this) && !up.get(ACTIVE)) {
			BlockHelper.setWithUpdate(world, pos.up(), Blocks.AIR);
		}
		super.onBlockHarvested(world, pos, state, player);
	}

	@Override
	public void randomTick(BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
		if (!state.get(ACTIVE)) {
			return;
		}
		if (random.nextBoolean()) {
			return;
		}
		Direction dir = state.get(FACING);
		BlockPos spawn = pos.offset(dir);
		if (!world.getBlockState(spawn).isAir()) {
			return;
		}
		int count = world
				.getEntitiesWithinAABB(ModEntityTypes.SILK_MOTH.get(), new AxisAlignedBB(pos).grow(16), (entity) -> true).size();
		if (count > 8) {
			return;
		}
		SilkMothEntity moth = new SilkMothEntity(ModEntityTypes.SILK_MOTH.get(), world);
		moth.setLocationAndAngles(spawn.getX() + 0.5, spawn.getY() + 0.5, spawn.getZ() + 0.5, dir.getHorizontalAngle(),
				0);
		moth.setMotion(new Vector3d(dir.getXOffset() * 0.4, 0, dir.getZOffset() * 0.4));
		moth.setHive(world, pos);
		world.addEntity(moth);
		world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1, 1);
	}
}
