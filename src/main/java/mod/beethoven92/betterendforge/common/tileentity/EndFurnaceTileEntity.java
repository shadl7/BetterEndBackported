package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class EndFurnaceTileEntity extends AbstractFurnaceTileEntity
{
	public EndFurnaceTileEntity()
	{
		super(ModTileEntityTypes.FURNACE.get(), IRecipeType.SMELTING);
	}

	@Nonnull
    @Override
	protected ITextComponent getDefaultName() 
	{
		return new TranslationTextComponent("container.furnace");
	}

	@Nonnull
    @Override
	protected Container createMenu(int id, @Nonnull PlayerInventory player)
	{
		return new FurnaceContainer(id, player, this, this.furnaceData);
	}

}
