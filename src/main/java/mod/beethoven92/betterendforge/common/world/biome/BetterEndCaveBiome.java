package mod.beethoven92.betterendforge.common.world.biome;

import net.minecraft.block.BlockState;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class BetterEndCaveBiome extends BetterEndBiome
{
	private final WeightedList<Feature<?>> floorFeatures = new WeightedList<>();
	private final WeightedList<Feature<?>> ceilFeatures = new WeightedList<>();

	public BetterEndCaveBiome(BiomeTemplate definition)
	{
		super(definition.setCaveBiome());
	}

	public void addFloorFeature(Feature<?> feature, int weight) 
	{
		floorFeatures.func_226313_a_(feature, weight);
	}

	public void addCeilFeature(Feature<?> feature, int weight) 
	{
		ceilFeatures.func_226313_a_(feature, weight);
	}

	public Feature<?> getFloorFeature(Random random) 
	{
		return floorFeatures.func_234005_b_() ? null : floorFeatures.func_226318_b_(random);
	}

	public Feature<?> getCeilFeature(Random random) 
	{
		return ceilFeatures.func_234005_b_() ? null : ceilFeatures.func_226318_b_(random);
	}
	
	public float getFloorDensity() 
	{
		return 0;
	}
	
	public float getCeilDensity() 
	{
		return 0;
	}
	
	public BlockState getCeil(BlockPos pos) 
	{
		return null;
	}

	public BlockState getWall(BlockPos pos) {
		return null;
	}
}
