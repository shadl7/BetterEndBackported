package mod.beethoven92.betterendforge.client.event.mod;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.particles.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleFactoryRegistration 
{
	@SubscribeEvent
	public static void RegisterParticleFactory(ParticleFactoryRegisterEvent event)
	{
		Minecraft mc = Minecraft.getInstance();
		
		mc.particles.registerFactory(ModParticleTypes.INFUSION.get(), InfusionParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.PORTAL_SPHERE.get(), PortalSphereParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.GLOWING_SPHERE.get(), GlowingSphereParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.AMBER_SPHERE.get(), GlowingSphereParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.TENANEA_PETAL.get(), TenaneaPetalParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.GEYSER_PARTICLE.get(), GeyserParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.SULPHUR_PARTICLE.get(), SulphurParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.SNOWFLAKE_PARTICLE.get(), SnowflakeParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.JUNGLE_SPORE.get(), JungleSporeParticle.Factory::new);
		mc.particles.registerFactory(ModParticleTypes.FIREFLY.get(), JungleSporeParticle.Factory::new);
		//mc.particles.registerFactory(ModParticleTypes.SMARAGDANT.get(), (sprite) -> new GlowingSphereParticle.Factory(sprite));
	}
}
