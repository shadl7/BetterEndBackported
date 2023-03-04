package mod.beethoven92.betterendforge.mixin;


import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ChorusPlantFeature.class)
public class ChorusPlantFeatureMixin {
	@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
	private void be_generate(ISeedReader structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, NoFeatureConfig defaultFeatureConfig, CallbackInfoReturnable<Boolean> info) {
		if (structureWorldAccess.isAirBlock(blockPos) && structureWorldAccess.getBlockState(blockPos.down()).isIn(ModBlocks.CHORUS_NYLIUM.get())) {
			ChorusFlowerBlock.generatePlant(structureWorldAccess, blockPos, random, (8 + random.nextInt(9)));
			BlockState bottom = structureWorldAccess.getBlockState(blockPos);
			if (bottom.isIn(Blocks.CHORUS_PLANT)) {
				if (GeneratorOptions.changeChorusPlant()) {
					BlockHelper.setWithoutUpdate(structureWorldAccess, blockPos, bottom.with(BlockProperties.ROOTS, true).with(SixWayBlock.DOWN, true));
				} else {
					structureWorldAccess.getWorld().setBlockState(blockPos, bottom.with(SixWayBlock.DOWN, true));
				}
			}
			info.setReturnValue(true);
		}
	}
}
