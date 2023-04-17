package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.world.end.DragonFightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonFightManager.class)
public class DragonFightManagerMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        if (GeneratorOptions.disableDragonFight) ci.cancel();
    }
}
