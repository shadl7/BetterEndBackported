package mod.beethoven92.betterendforge.mixin;


import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Random;

@Mixin(value = ChorusFlowerBlock.class, priority = 100)
public abstract class ChorusFlowerBlockMixin extends Block
{
	private static final VoxelShape SHAPE_FULL = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_HALF = Block.makeCuboidShape(0, 0, 0, 16, 4, 16);


	public ChorusFlowerBlockMixin(Properties properties)
	{
		super(properties);
	}

	@Shadow
	@Final
	private ChorusPlantBlock plantBlock;

	@Inject(method = "isValidPosition", at = @At("HEAD"), cancellable = true)
	private void be_isValidPosition(BlockState state, IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (world.getBlockState(pos.down()).isIn(ModBlocks.CHORUS_NYLIUM.get())) return;
		info.setReturnValue(true);
		info.cancel();
	}

	@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
	private void be_randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
		if (!Objects.requireNonNull(world.getBlockState(pos.down()).getBlock().getRegistryName()).toString().equals("minecraft:endstone")) return;
		BlockPos up = pos.up();
		if(world.isAirBlock(up) && up.getY() < 256 && state.get(ChorusFlowerBlock.AGE) < 5) {
			this.placeGrownFlower(world, up, state.get(ChorusFlowerBlock.AGE) + 1);
			world.setBlockState(pos, plantBlock.getDefaultState().with(ChorusPlantBlock.UP, true).with(ChorusPlantBlock.DOWN, true), Constants.BlockFlags.NO_NEIGHBOR_DROPS);
			info.cancel();
		}
	}
	@Shadow
	private void placeGrownFlower(World worldIn, BlockPos pos, int age) {}

	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext reader) {
		if (GeneratorOptions.changeChorusPlant()) {
			return state.get(ChorusFlowerBlock.AGE) == 5 ? SHAPE_HALF : SHAPE_FULL;
		} else {
			return super.getShape(state, world, pos, reader);
		}
	}

	@Inject(method = "placeDeadFlower", at = @At("HEAD"), cancellable = true)
	private void be_placeDeadFlower(World world, BlockPos pos, CallbackInfo info) {
		BlockState down = world.getBlockState(pos.down());
		if (down.isIn(Blocks.CHORUS_PLANT) || down.isIn(ModTags.GEN_TERRAIN)) {
			world.setBlockState(pos, this.getDefaultState().with(BlockStateProperties.AGE_0_5, 5), 2);
			world.playEvent(1034, pos, 0);
		}
		info.cancel();
	}
}