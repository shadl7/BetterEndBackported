package mod.beethoven92.betterendforge.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = {"net.minecraft.world.gen.feature.structure.EndCityPieces$4"})
public class EndCityTowerMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"), method = {"generate"})
    public int getCloudHeight(Random instance, int bound) {
        return instance.nextInt(8);
    }

    @ModifyConstant(constant = {@Constant(intValue = 2)}, method = {"generate"})
    public int modifyConstant(int constant) {
        return 8;
    }
}
