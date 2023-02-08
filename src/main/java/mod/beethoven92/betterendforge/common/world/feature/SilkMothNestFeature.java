package mod.beethoven92.betterendforge.common.world.feature;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class SilkMothNestFeature extends Feature<NoFeatureConfig> {
	public SilkMothNestFeature() {
		super(NoFeatureConfig.field_236558_a_);
	}

	private static final Mutable POS = new Mutable();
	
	private boolean canGenerate(ISeedReader world) {
		BlockState state = world.getBlockState(SilkMothNestFeature.POS.up());
		if (state.isIn(BlockTags.LEAVES) || state.isIn(BlockTags.LOGS)) {
			state = world.getBlockState(SilkMothNestFeature.POS);
			if ((state.isAir() || state.isIn(ModBlocks.TENANEA_OUTER_LEAVES.get())) && world.isAirBlock(SilkMothNestFeature.POS.down())) {
				for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) {
                    return !world.getBlockState(SilkMothNestFeature.POS.down().offset(dir)).getMaterial().blocksMovement();
                }
			}
		}
		return false;
	}
	
	@Override
	public boolean generate(ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random rand, BlockPos pos,
                            @Nonnull NoFeatureConfig config)
	{		
		int maxY = world.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(pos.getX(), 0, pos.getZ()), maxY);
		POS.setPos(pos);
		for (int y = maxY; y > minY; y--) {
			POS.setY(y);
			if (canGenerate(world)) {
				Direction dir = BlockHelper.randomHorizontal(rand);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, dir).with(BlockProperties.ACTIVATED, false));
				POS.setY(y - 1);
				BlockHelper.setWithoutUpdate(world, POS, ModBlocks.SILK_MOTH_NEST.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, dir));
				return true;
			}
		}
		return false;
	}
}
