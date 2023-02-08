package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class ShadowGrassBlock extends TerrainBlock
{
	public ShadowGrassBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public void animateTick(@Nonnull BlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, Random rand)
	{
		if (rand.nextInt(32) == 0) 
		{
			worldIn.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + rand.nextDouble(), (double) pos.getY() + 1.1D, (double) pos.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}
