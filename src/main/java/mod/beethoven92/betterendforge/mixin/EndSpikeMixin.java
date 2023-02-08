package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.nbt.CompoundNBT;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.gen.feature.EndSpikeFeature;

@Mixin(EndSpikeFeature.EndSpike.class)
public abstract class EndSpikeMixin
{
	@Final
@Shadow
private int height;

	@Inject(method = "getHeight", at = @At("HEAD"), cancellable = true)
	private void be_getSpikeHeight(CallbackInfoReturnable<Integer> info) {
		if (!GeneratorOptions.isDirectSpikeHeight()) {
			int x = getCenterX();
			int z = getCenterZ();
			String pillarID = String.format("%d_%d", x, z);
			CompoundNBT pillar = WorldDataAPI.getCompoundTag(BetterEnd.MOD_ID, "pillars");
			int minY = pillar.contains(pillarID) ? pillar.getInt(pillarID) : 65;
			int maxY = minY + height - 54;
			info.setReturnValue(maxY);
		}
	}

	@Shadow
	public int getCenterX() {
		return 0;
	}

	@Shadow
	public int getCenterZ() {
		return 0;
	}
}
