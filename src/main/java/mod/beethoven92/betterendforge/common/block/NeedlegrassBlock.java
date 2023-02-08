package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class NeedlegrassBlock extends PlantBlock
{
	public NeedlegrassBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public void onEntityCollision(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn)
	{
		if (entityIn instanceof LivingEntity)
		{
			entityIn.attackEntityFrom(DamageSource.CACTUS, 0.1F);
		}
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		return state.isIn(ModBlocks.SHADOW_GRASS.get());
	}
	
	@Override
	public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull PathType type)
	{
		return false;
	}
}
