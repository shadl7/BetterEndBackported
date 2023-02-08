package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class PathBlock extends Block
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 15, 16);
	
	public PathBlock(Block block)
	{
		super(AbstractBlock.Properties.from(block).setAllowsSpawn((state, world, pos, type) -> false));
		if (block instanceof TerrainBlock)
		{
			TerrainBlock terrain = (TerrainBlock)block;
			terrain.setPathBlock(this);
		}
	}
	
	@Nonnull
    @Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Nonnull
    @Override
	public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos,
                                        @Nonnull ISelectionContext context)
	{
		return SHAPE;
	}
}
