package mod.beethoven92.betterendforge.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class EnchantedPetalItem extends Item
{
	public EnchantedPetalItem(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean hasEffect(@Nonnull ItemStack stack)
	{
		return true;
	}
}
