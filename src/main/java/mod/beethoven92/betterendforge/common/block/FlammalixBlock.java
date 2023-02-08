package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class FlammalixBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 14, 14);

	public FlammalixBlock() {
		super(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE));
	}
	
	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.PALLIDIUM_FULL.get()) ||
			state.isIn(ModBlocks.PALLIDIUM_HEAVY.get()) ||
			state.isIn(ModBlocks.PALLIDIUM_THIN.get()) ||
			state.isIn(ModBlocks.PALLIDIUM_TINY.get());
	}
	
	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader view, @Nonnull BlockPos pos, @Nonnull ISelectionContext ePos) {
		return SHAPE;
	}
	
	@Nonnull
    @Override
	public OffsetType getOffsetType() {
		return OffsetType.NONE;
	}

	

}
