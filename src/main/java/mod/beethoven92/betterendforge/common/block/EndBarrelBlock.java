package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.tileentity.EndBarrelTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class EndBarrelBlock extends BarrelBlock {
	public EndBarrelBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
		return new EndBarrelTileEntity();
	}

	@Nonnull
    @Override
	public ActionResultType onBlockActivated(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player,
                                             @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
		if (world.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity blockEntity = world.getTileEntity(pos);
			if (blockEntity instanceof EndBarrelTileEntity) {
				player.openContainer((EndBarrelTileEntity) blockEntity);
				player.addStat(Stats.OPEN_BARREL);
				PiglinTasks.func_234478_a_(player, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void tick(@Nonnull BlockState state, ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random rand) {
		TileEntity blockEntity = world.getTileEntity(pos);
		if (blockEntity instanceof EndBarrelTileEntity) {
			((EndBarrelTileEntity) blockEntity).tick();
		}
	}

	@Nonnull
    @Override
	public BlockRenderType getRenderType(@Nonnull BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer,
                                ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity blockEntity = world.getTileEntity(pos);
			if (blockEntity instanceof EndBarrelTileEntity) {
				((EndBarrelTileEntity) blockEntity).setCustomName(stack.getDisplayName());
			}
		}
	}
}
