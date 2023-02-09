package mod.beethoven92.betterendforge.data;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModLootModifiers;
import mod.beethoven92.betterendforge.common.lootmodifier.BetterEndChorusFruitLootModifier;
import mod.beethoven92.betterendforge.common.lootmodifier.BetterEndMusicDiscLootModifier;
import mod.beethoven92.betterendforge.common.lootmodifier.lootcondition.FromLootTable;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

	public ModLootModifierProvider(DataGenerator gen) {
		super(gen, BetterEnd.MOD_ID);
	}

	@Override
	protected void start() {
		add("better_end_music_disc", ModLootModifiers.BETTER_END_MUSIC_DISC,
				new BetterEndMusicDiscLootModifier(new ILootCondition[] { RandomChance.builder(0.1f).build(),
						new FromLootTable(LootTables.CHESTS_END_CITY_TREASURE) }, 0, 5));
		add("better_end_chorus_fruit", ModLootModifiers.BETTER_END_CHORUS_FRUIT,
				new BetterEndChorusFruitLootModifier(new ILootCondition[] { RandomChance.builder(0.7f).build(),
				        new LootTableIdCondition.Builder(new ResourceLocation("minecraft", "entities/enderman")).build()}, 0, 2));
	}

}
