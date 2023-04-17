package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class BiomeModification 
{
	@SubscribeEvent(priority = EventPriority.HIGH)
    public static void addFeaturesToEndBiomes(final BiomeLoadingEvent event) 
	{
		if (event.getCategory() == Category.THEEND)
		{
			if (event.getName() == null) return;
			
			// Add surface structures to biomes
			if (!event.getName().getPath().contains("mountain") && 
					!event.getName().getPath().contains("lake"))
			{
			    event.getGeneration().getStructures().add(() -> ModConfiguredStructures.ETERNAL_PORTAL);
			}
			event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).add(() -> ModConfiguredFeatures.CRASHED_SHIP);
			event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS).add(() -> ModConfiguredFeatures.TUNEL_CAVE);
			// Add ores to all biomes
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.THALLASIUM_ORE);
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.ENDER_ORE);
			event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> ModConfiguredFeatures.FLAVOLITE_LAYER);
			
			
			// Add end caves to biomes that have caves enabled
			if (ModBiomes.getBiome(event.getName()).hasCaves()) 
			{
	  			event.getGeneration().getFeatures(Decoration.RAW_GENERATION).add(() -> ModConfiguredFeatures.ROUND_CAVE);
			}
			
			// Add scattered nbt structures to biomes
			if (!ModBiomes.getBiome(event.getName()).getNBTStructures().isEmpty())
			{
				event.getGeneration().getFeatures(Decoration.SURFACE_STRUCTURES).add(() -> ModConfiguredFeatures.NBT_STRUCTURES);
			}
			
			// If the Deadly End Phantoms mod is installed, their specter will spawn in shadow forest
			// instead of vanilla phantoms
			if (event.getName().equals(ModBiomes.SHADOW_FOREST.getID()))
			{
				MobSpawnInfo.Spawners phantom = new MobSpawnInfo.Spawners(EntityType.byKey("deadlyendphantoms:specter").orElse(EntityType.PHANTOM), 10, 1, 2);
				event.getSpawns().getSpawner(EntityClassification.MONSTER).add(phantom);
			}
		}
    }
}
