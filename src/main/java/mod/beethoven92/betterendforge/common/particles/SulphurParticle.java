package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class SulphurParticle extends SpriteTexturedParticle
{
	private int ticks;
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;

	protected SulphurParticle(ClientWorld world, double x, double y, double z,
							  IAnimatedSprite spriteWithAge)
	{
		super(world, x, y, z, 1, 1, 1);
		
		this.selectSpriteWithAge(spriteWithAge);
		
		this.maxAge = ModMathHelper.randRange(150, 300, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.setColor(1, 1, 1);
		this.particleAlpha = 0;
		
		preVX = rand.nextGaussian() * 0.015;
		preVY = rand.nextGaussian() * 0.015;
		preVZ = rand.nextGaussian() * 0.015;
		
		nextVX = rand.nextGaussian() * 0.015;
		nextVY = rand.nextGaussian() * 0.015;
		nextVZ = rand.nextGaussian() * 0.015;
	}

	@Nonnull
    @Override
	public IParticleRenderType getRenderType() 
	{
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public void tick() 
	{
		ticks ++;
		if (ticks > 200) 
		{
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = rand.nextGaussian() * 0.015;
			nextVY = rand.nextGaussian() * 0.015;
			nextVZ = rand.nextGaussian() * 0.015;
			if (rand.nextInt(4) == 0) 
			{
				nextVY = Math.abs(nextVY);
			}
			ticks = 0;
		}
		double delta = (double) ticks / 200.0;
		
		if (this.age <= 40) 
		{
			this.setAlphaF(this.age / 40F);
		}
		else if (this.age >= this.maxAge - 40) 
		{
			this.setAlphaF((this.maxAge - this.age) / 40F);
		}
		
		if (this.age >= this.maxAge) 
		{
			this.setExpired();
		}
		
		this.motionX = MathHelper.lerp(delta, preVX, nextVX);
		this.motionY = MathHelper.lerp(delta, preVY, nextVY);
		this.motionZ = MathHelper.lerp(delta, preVZ, nextVZ);
		
		super.tick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> 
	{
		private final IAnimatedSprite sprite;

	    public Factory(IAnimatedSprite sprite) 
	    {
	         this.sprite = sprite;
	    }
	    
	    @Override
	    public Particle makeParticle(@Nonnull BasicParticleType type, @Nonnull ClientWorld worldIn, double x, double y, double z,
                                     double xSpeed, double ySpeed, double zSpeed)
	    {
	    	return new SulphurParticle(worldIn, x, y, z, sprite);
	    }
	}

}
