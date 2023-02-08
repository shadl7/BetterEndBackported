package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public class MossyObsidianBlock extends Block {
	public MossyObsidianBlock() {
		super(AbstractBlock.Properties.from(Blocks.OBSIDIAN).hardnessAndResistance(3).tickRandomly());
	}

	@Override
	public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, Random random) {
		if (random.nextInt(16) == 0 && !canSurvive(state, world, pos)) {
			world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
		}
	}

	public static boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isIn(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else if (blockState.getFluidState().getLevel() == 8) {
			return false;
		} else {
			int i = LightEngine.func_215613_a(world, state, pos, blockState, blockPos, Direction.UP,
					state.getOpacity(world, blockPos));
			return i < 5;
		}
	}
}
