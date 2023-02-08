package mod.beethoven92.betterendforge.common.inventory.slot;

import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SmelterOutputSlot extends Slot
{
	private final PlayerEntity player;
	private int amount;

	public SmelterOutputSlot(PlayerEntity player, IInventory inventoryIn, int index, int xPosition, int yPosition) 
	{
		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
	}	
	
	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return false;
	}
	
	@Nonnull
    @Override
	public ItemStack decrStackSize(int amount)
	{
		if (this.getHasStack()) 
		{
			this.amount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}
	
	@Nonnull
    @Override
	public ItemStack onTake(@Nonnull PlayerEntity thePlayer, @Nonnull ItemStack stack)
	{
		this.onCrafting(stack);
		super.onTake(player, stack);
		return stack;
	}
	
	@Override
	protected void onCrafting(@Nonnull ItemStack stack, int amount)
	{
		this.amount += amount;
		this.onCrafting(stack);
	}
	
	@Override
	protected void onCrafting(ItemStack stack) 
	{
		stack.onCrafting(this.player.world, this.player, this.amount);
		if (!this.player.world.isRemote && this.inventory instanceof EndStoneSmelterTileEntity) 
		{
			((EndStoneSmelterTileEntity) this.inventory).dropExperience(player);
		}
		this.amount = 0;
	}
}
