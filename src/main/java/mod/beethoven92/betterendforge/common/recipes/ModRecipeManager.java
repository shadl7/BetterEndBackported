package mod.beethoven92.betterendforge.common.recipes;

import com.google.common.collect.Maps;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.Map.Entry;

public class ModRecipeManager 
{
	public static final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> RECIPES = Maps.newHashMap();

	public static void addRecipe(IRecipeType<?> type, IRecipe<?> recipe) 
	{
        Map<ResourceLocation, IRecipe<?>> list = RECIPES.computeIfAbsent(type, k -> Maps.newHashMap());
        list.put(recipe.getId(), recipe);
	}
	
	public static Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> getMap(Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes) 
	{
		Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> result = Maps.newHashMap();

		for (IRecipeType<?> type : recipes.keySet()) 
		{
			Map<ResourceLocation, IRecipe<?>> typeList = Maps.newHashMap();
			typeList.putAll(recipes.get(type));
			result.put(type, typeList);
		}

		for (IRecipeType<?> type : RECIPES.keySet()) 
		{
			Map<ResourceLocation, IRecipe<?>> list = RECIPES.get(type);
			if (list != null)
			{
                Map<ResourceLocation, IRecipe<?>> typeList = result.computeIfAbsent(type, k -> Maps.newHashMap());
                for (Entry<ResourceLocation, IRecipe<?>> entry : list.entrySet()) {
					ResourceLocation id = entry.getKey();
					if (!typeList.containsKey(id))
						typeList.put(id, entry.getValue());
				}
			}
		}

		return result;
	}
}
