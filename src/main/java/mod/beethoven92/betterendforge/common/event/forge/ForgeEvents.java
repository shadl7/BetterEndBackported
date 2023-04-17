package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ForgeEvents {
	@SubscribeEvent
	public static void removeBlindness(PotionApplicableEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getPotionEffect().getPotion() == Effects.BLINDNESS && entity.getAttributeManager().hasAttributeInstance(ModAttributes.BLINDNESS_RESISTANCE.get()) && entity.getAttributeValue(ModAttributes.BLINDNESS_RESISTANCE.get()) > 0) event.setResult(Result.DENY);
	}
}
