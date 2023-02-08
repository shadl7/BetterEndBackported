package mod.beethoven92.betterendforge.client.event.mod;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.*;
import mod.beethoven92.betterendforge.common.block.material.ColoredMaterial;
import mod.beethoven92.betterendforge.common.block.material.StoneMaterial;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler 
{
	@SubscribeEvent
	public static void BlockColorHandler(ColorHandlerEvent.Block event)
	{
		event.getBlockColors().register((state, reader, pos, color) ->
				AuroraCrystalBlock.getBlockColor(pos), ModBlocks.AURORA_CRYSTAL.get());
		
		event.getBlockColors().register((state, reader, pos, color) ->
				TenaneaFlowersBlock.getBlockColor(pos), ModBlocks.TENANEA_FLOWERS.get());
		
		event.getBlockColors().register((state, reader, pos, color) ->
				RespawnObeliskBlock.getBlockColor(pos), ModBlocks.RESPAWN_OBELISK.get());

		registerColoredMaterialBlocks(event, ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
		registerColoredMaterialBlocks(event, ModBlocks.IRON_BULB_LANTERN_COLORED);
		registerColoredMaterialBlocks(event, ModBlocks.THALLASIUM.bulb_lantern_colored);
		registerColoredMaterialBlocks(event, ModBlocks.TERMINITE.bulb_lantern_colored);
		
		event.getBlockColors().register((state, reader, pos, color) ->
						HelixTreeLeavesBlock.getBlockColor(state),
		 				 ModBlocks.HELIX_TREE_LEAVES.get());
		
		event.getBlockColors().register((state, reader, pos, color) ->
						((JellyshroomCapBlock)(state.getBlock())).getBlockColor(state),
                         ModBlocks.JELLYSHROOM_CAP_PURPLE.get());
		
		event.getBlockColors().register(ModLanternBlock::getBlockColor,
						 getLanterns());
		
		event.getBlockColors().register((state, reader, pos, color) ->
						EndPortals.getColor(state.get(EndPortalBlock.PORTAL)),
                         ModBlocks.END_PORTAL_BLOCK.get());
	}
	
	@SubscribeEvent
	public static void ItemColorHandler(ColorHandlerEvent.Item event)
	{
		event.getItemColors().register((stack, tintIndex) ->
				AuroraCrystalBlock.getItemColor(), ModBlocks.AURORA_CRYSTAL.get());
		
		event.getItemColors().register((stack, tintIndex) ->
				TenaneaFlowersBlock.getItemColor(), ModBlocks.TENANEA_FLOWERS.get());
		
		event.getItemColors().register((stack, tintIndex) ->
				HelixTreeLeavesBlock.getItemColor(), ModBlocks.HELIX_TREE_LEAVES.get());
		
		event.getItemColors().register((stack, color) -> ((SpawnEggItem) stack.getItem()).getColor(color), ModItems.DRAGONFLY_SPAWN_EGG.get(), ModItems.END_FISH_SPAWN_EGG.get(),
				ModItems.SHADOW_WALKER_SPAWN_EGG.get(), ModItems.END_SLIME_SPAWN_EGG.get(),
				ModItems.CUBOZOA_SPAWN_EGG.get(), ModItems.SILK_MOTH_SPAWN_EGG.get());
		
		registerColoredMaterialItems(event, ModBlocks.HYDRALUX_PETAL_BLOCK_COLORED);
		registerColoredMaterialItems(event, ModBlocks.IRON_BULB_LANTERN_COLORED);
		registerColoredMaterialItems(event, ModBlocks.THALLASIUM.bulb_lantern_colored);
		registerColoredMaterialItems(event, ModBlocks.TERMINITE.bulb_lantern_colored);
		
		event.getItemColors().register((stack, tintIndex) ->
				JellyshroomCapBlock.getItemColor(stack), ModBlocks.JELLYSHROOM_CAP_PURPLE.get());
		
		event.getItemColors().register(ModLanternBlock::getItemColor,
						 getLanterns());
		
		event.getItemColors().register((stack, tintIndex) ->
						EndPortals.getColor(0),
		                 ModBlocks.END_PORTAL_BLOCK.get());
	}
	
	private static void registerColoredMaterialBlocks(ColorHandlerEvent.Block event, ColoredMaterial material)
	{
		event.getBlockColors().register((state, reader, pos, color) ->
						state.getBlock().getMaterialColor().colorValue,
                          material.getBlocks());
	}
	
	private static void registerColoredMaterialItems(ColorHandlerEvent.Item event, ColoredMaterial material)
	{
		event.getItemColors().register((stack, tintIndex) ->
						((BlockItem)stack.getItem()).getBlock().getMaterialColor().colorValue,
                         material.getBlocks());
	}
	
	private static Block[] getLanterns() {
		List<Block> result = new ArrayList<>();
		for (StoneMaterial m : StoneMaterial.getMaterials())
			result.add(m.lantern.get());
		result.add(ModBlocks.ANDESITE_LANTERN.get());
		result.add(ModBlocks.BLACKSTONE_LANTERN.get());
		result.add(ModBlocks.DIORITE_LANTERN.get());
		result.add(ModBlocks.END_STONE_LANTERN.get());
		result.add(ModBlocks.GRANITE_LANTERN.get());
		result.add(ModBlocks.PURPUR_LANTERN.get());
		result.add(ModBlocks.QUARTZ_LANTERN.get());
		return result.toArray(new Block[] {});
	}
}
