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
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BoluxMushroomBlock extends PlantBlock {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 9, 15);

	public BoluxMushroomBlock() {
		super(AbstractBlock.Properties.create(Material.TALL_PLANTS).zeroHardnessAndResistance().doesNotBlockMovement()
				.sound(SoundType.PLANT).setLightLevel(s -> 10));
	}

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.isIn(ModBlocks.RUTISCUS.get());
	}

	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return SHAPE;
	}

	@Nonnull
    @Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}

	@Override
	public boolean canGrow(@Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		return false;
	}
}
