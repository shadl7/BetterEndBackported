package mod.beethoven92.betterendforge.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;
import java.util.OptionalLong;

@Mixin(DimensionType.class)
public abstract class EndHeightMixin {
    @Shadow
    public abstract int getLogicalHeight();

    @Shadow
    @Final
    private int logicalHeight;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void modifyCodec(CallbackInfo ci) {
        DimensionType.CODEC = RecordCodecBuilder.create((builder) -> {
            return builder.group(Codec.LONG.optionalFieldOf("fixed_time").xmap((fixedTime) -> {
                return fixedTime.map(OptionalLong::of).orElseGet(OptionalLong::empty);
            }, (fixedTime) -> {
                return fixedTime.isPresent() ? Optional.of(fixedTime.getAsLong()) : Optional.empty();
            }).forGetter((type) -> {
                return type.fixedTime;
            }), Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::hasSkyLight),
                    Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::getHasCeiling),
                    Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::isUltrawarm),
                    Codec.BOOL.fieldOf("natural").forGetter(DimensionType::isNatural),
                    Codec.doubleRange(1.0E-5F, 3.0E7D).fieldOf("coordinate_scale").forGetter(DimensionType::getCoordinateScale),
                    Codec.BOOL.fieldOf("piglin_safe").forGetter(DimensionType::isPiglinSafe),
                    Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::doesBedWork),
                    Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::doesRespawnAnchorWorks),
                    Codec.BOOL.fieldOf("has_raids").forGetter(DimensionType::isHasRaids),
                    Codec.intRange(0, 384).fieldOf("logical_height").forGetter(DimensionType::getLogicalHeight),
                    ResourceLocation.CODEC.fieldOf("infiniburn").forGetter((type) -> {
                return type.infiniburn;
            }), ResourceLocation.CODEC.fieldOf("effects").orElse(DimensionType.OVERWORLD_ID).forGetter((type) -> {
                return type.effects;
            }), Codec.FLOAT.fieldOf("ambient_light").forGetter((type) -> {
                return type.ambientLight;
            })).apply(builder, DimensionType::new);
        });
    }
}

