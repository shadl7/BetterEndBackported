package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredStructures;
import mod.beethoven92.betterendforge.common.init.ModConfiguredSurfaceBuilders;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.structure.StructureFeatures;

public class FoggyMushroomlandBiome extends BetterEndBiome 
{
	public FoggyMushroomlandBiome()
	{
		super(new BiomeTemplate("foggy_mushroomland").
				setFoliageColor(73, 210, 209).
				setGrassColor(73, 210, 209).
				setFogColor(41, 122, 173).
				setFogDensity(3).
				setWaterColor(119, 227, 250).
				setWaterFogColor(119, 227, 250).
				setSurface(ModConfiguredSurfaceBuilders.get(ModConfiguredSurfaceBuilders.MUSHROOMLAND_SURFACE)).
				setParticles(ModParticleTypes.GLOWING_SPHERE.get(), 0.001F).
				setAmbientSound(ModSoundEvents.AMBIENT_FOGGY_MUSHROOMLAND.get()).
				setMusic(ModSoundEvents.MUSIC_FOGGY_MUSHROOMLAND.get()).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.MOSSY_GLOWSHROOM).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BLUE_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.UMBRELLA_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CREEPING_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.DENSE_VINE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CYAN_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CYAN_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.END_LILY).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BUBBLE_CORAL).
				addStructure(ModConfiguredStructures.GIANT_MOSSY_GLOWSHROOM).
				addStructure(StructureFeatures.END_CITY). // END CITY
				setMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.DRAGONFLY.get(), 80, 2, 5).
				setMobSpawn(EntityClassification.AMBIENT, ModEntityTypes.END_FISH.get(), 20, 2, 5).
				//.addMobSpawn(ModEntityTypes.END_SLIME, 10, 1, 2)
				setMobSpawn(EntityClassification.MONSTER, EntityType.ENDERMAN, 10, 1, 2));
	}
}