package mod.beethoven92.betterendforge.common.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.ParticleType;

import javax.annotation.Nonnull;

public class InfusionParticleType extends ParticleType<InfusionParticleData>
{
	public InfusionParticleType() 
	{
		super(true, InfusionParticleData.DESERIALIZER);
	}

	@Nonnull
    @Override
	public Codec<InfusionParticleData> func_230522_e_() 
	{
		return InfusionParticleData.CODEC;
	}

}
