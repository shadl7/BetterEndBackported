package mod.beethoven92.betterendforge.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class UmbrellaClusterJuiceItem extends Item
{
	public UmbrellaClusterJuiceItem(Properties properties) 
	{
		super(properties);
	}

	public int getUseDuration(@Nonnull ItemStack stack)
	{
		return 32;
	}

	@Nonnull
    public UseAction getUseAction(@Nonnull ItemStack stack)
	{
		return UseAction.DRINK;
	}

	@Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn)
	{
		return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
	}
}
