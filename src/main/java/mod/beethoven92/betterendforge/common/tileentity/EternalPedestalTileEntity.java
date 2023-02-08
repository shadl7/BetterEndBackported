package mod.beethoven92.betterendforge.common.tileentity;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EternalPedestalTileEntity extends PedestalTileEntity
{
	private EternalRitual linkedRitual;
	
	public EternalPedestalTileEntity() 
	{
		super(ModTileEntityTypes.ETERNAL_PEDESTAL.get());
	}
	
	public boolean hasRitual() 
	{
		return this.linkedRitual != null;
	}
	
	public void linkRitual(EternalRitual ritual) 
	{
		this.linkedRitual = ritual;
	}
	
	public EternalRitual getRitual() 
	{
		return this.linkedRitual;
	}
	
	@Override
	public void setWorldAndPos(@Nonnull World world, @Nonnull BlockPos pos)
	{
		super.setWorldAndPos(world, pos);
		if (hasRitual()) 
		{
			this.linkedRitual.setWorld(world);
		}
	}
	
	@Override
	public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbt)
	{
		super.read(state, nbt);
		if (nbt.contains("ritual")) 
		{
			this.linkedRitual = new EternalRitual(world);
			this.linkedRitual.read(nbt.getCompound("ritual"));
		}
	}
	
	@Nonnull
    @Override
	public CompoundNBT write(@Nonnull CompoundNBT compound)
	{
		super.write(compound);
		if (this.hasRitual()) 
		{
			compound.put("ritual", linkedRitual.write(new CompoundNBT()));
		}
		return compound;
	}
}
