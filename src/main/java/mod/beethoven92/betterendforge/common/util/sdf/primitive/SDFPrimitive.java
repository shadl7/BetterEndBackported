package mod.beethoven92.betterendforge.common.util.sdf.primitive;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

public abstract class SDFPrimitive extends SDF
{
	protected Function<BlockPos, BlockState> placerFunction;
	
	public SDFPrimitive setBlock(Function<BlockPos, BlockState> placerFunction) 
	{
		this.placerFunction = placerFunction;
		return this;
	}
	
	public SDFPrimitive setBlock(BlockState state) 
	{
		this.placerFunction = (pos) -> state;
		return this;
	}
	
	public SDFPrimitive setBlock(Block block) 
	{
		this.placerFunction = (pos) -> block.getDefaultState();
		return this;
	}
	
	public BlockState getBlockState(BlockPos pos) 
	{
		return placerFunction.apply(pos);
	}
}
