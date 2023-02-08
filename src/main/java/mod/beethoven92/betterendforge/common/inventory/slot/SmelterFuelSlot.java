package mod.beethoven92.betterendforge.common.inventory.slot;

import mod.beethoven92.betterendforge.common.inventory.EndStoneSmelterContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SmelterFuelSlot extends Slot
{
	private final EndStoneSmelterContainer handler;
	
	public SmelterFuelSlot(EndStoneSmelterContainer handler, IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		
		this.handler = handler;
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return this.handler.isFuel(stack) || FurnaceFuelSlot.isBucket(stack);
	}
	
	@Override
	public int getItemStackLimit(@Nonnull ItemStack stack)
	{
		return FurnaceFuelSlot.isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	}
}
