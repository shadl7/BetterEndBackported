package mod.beethoven92.betterendforge.common.world.feature;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class SingleBlockFeature extends Feature<NoFeatureConfig>
{
	private final Block block;
	
	public SingleBlockFeature(Block block) 
	{
		super(NoFeatureConfig.field_236558_a_);
		this.block = block;
	}

	@Override
	public boolean generate(ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random rand, BlockPos pos,
                            @Nonnull NoFeatureConfig config)
	{
		if (!world.getBlockState(pos.down()).isIn(ModTags.GEN_TERRAIN)) 
		{
			return false;
		}
		
		BlockState state = block.getDefaultState();
		if (block.getStateContainer().getProperty("waterlogged") != null) 
		{
			boolean waterlogged = !world.getFluidState(pos).isEmpty();
			state = state.with(BlockStateProperties.WATERLOGGED, waterlogged);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
		
		return true;
	}
}
