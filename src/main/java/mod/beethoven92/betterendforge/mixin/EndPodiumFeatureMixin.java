package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EndPodiumFeature.class)
public abstract class EndPodiumFeatureMixin
{
	@Shadow @Final private boolean activePortal;

	@Inject(method = "generate(Lnet/minecraft/world/ISeedReader;Lnet/minecraft/world/gen/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/NoFeatureConfig;)Z", at = @At("HEAD"), cancellable = true)
	private void beGeneratePortal(ISeedReader level, ChunkGenerator chunkGenerator, Random random, BlockPos origin,
								  NoFeatureConfig config, CallbackInfoReturnable<Boolean> info)
	{
		if (!GeneratorOptions.hasPortal()) {
			info.setReturnValue(false);
			info.cancel();
		}
	}
}