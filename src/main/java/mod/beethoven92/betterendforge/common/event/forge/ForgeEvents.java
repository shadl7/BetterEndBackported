package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ForgeEvents {

	@SubscribeEvent
	public static void removeBlindness(PotionApplicableEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getPotionEffect().getPotion() == Effects.BLINDNESS
				&& entity.getAttributeManager().hasAttributeInstance(ModAttributes.BLINDNESS_RESISTANCE.get())
				&& entity.getAttributeValue(ModAttributes.BLINDNESS_RESISTANCE.get()) > 0)
			event.setResult(Result.DENY);
	}

	@SubscribeEvent
	public static void spawnInEnd(PlayerEvent.PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		if (player instanceof ServerPlayerEntity && !player.getTags().contains("betterendforge.end_spawned")) {
			player.addTag("betterendforge.end_spawned");
			spawnHelper((ServerPlayerEntity) player);
		}
	}

	@SubscribeEvent
	public static void respawnInEnd(PlayerEvent.PlayerRespawnEvent event) {
		if (event.isEndConquered()) return;
		if (event.getPlayer() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			if (player.func_241140_K_() == null) spawnHelper(player);
		}
	}

	public static void spawnHelper(ServerPlayerEntity player) {
		if (player.world.isRemote || !GeneratorOptions.changeSpawn()) return;
		player.teleport(
				getEnd(player),
				GeneratorOptions.getSpawn().getX(),
				GeneratorOptions.getSpawn().getY(),
				GeneratorOptions.getSpawn().getZ(),
				player.getYaw(0.0F),
				player.getPitch(0.0F));
		int y = getAirPlace(player);
		if (y == 114514) {
			player.teleport(getEnd(player), player.getPosX(), 256, player.getPosZ(), player.getYaw(0.0F), player.getPitch(0.0F));
			player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 600));
			player.sendStatusMessage(new TranslationTextComponent("event.betterendforge.spawnInEnd"), true);
			return;
		}
		if (!player.world.getBlockState(new BlockPos(GeneratorOptions.getSpawn().getX(), GeneratorOptions.getSpawn().getY() + 1, GeneratorOptions.getSpawn().getZ())).isAir()) {
			player.teleport(getEnd(player), player.getPosX(), y, player.getPosZ(), player.getYaw(0.0F), player.getPitch(0.0F));
		}
	}

	public static ServerWorld getEnd(ServerPlayerEntity player) {
		return Objects.requireNonNull(player.world.getServer()).getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("minecraft:the_end")));
	}

	public static int getAirPlace(ServerPlayerEntity player) {
		for (int i = (int) player.getPosY(); i < 256; i ++) {
			if (player.world.getBlockState(new BlockPos(player.getPosX(), i, player.getPosZ())).isAir()) return i;
		}
		return 114514;
	}
}
