package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BubbleCoralBlock extends UnderwaterPlantBlock
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 14, 16);
	
	public BubbleCoralBlock(Properties properties) 
	{
		super(properties);
	}
	@Nonnull
    @Override
	public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context)
	{
		return SHAPE;
	}
	
	@Nonnull
    @Override
	public OffsetType getOffsetType() 
	{
		return OffsetType.NONE;
	}
	
	@Override
	public void animateTick(@Nonnull BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble() * 0.5F + 0.5F;
		double z = pos.getZ() + rand.nextDouble();
		worldIn.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0D, 0.0D, 0.0D);
	}
}
