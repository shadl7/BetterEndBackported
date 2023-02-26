package mod.beethoven92.betterendforge.mixin;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Random;

@Mixin(targets = "net.minecraft.world.gen.feature.structure.EndCityPieces$2")
public class EndCityPiecesMixin {
    /**
     * @author Doctor Who
     * @reason Saving The World
     */
    @Overwrite
    public boolean generate(TemplateManager p_71170_, int p_71171_, EndCityPieces.CityTemplate p_71172_, BlockPos p_71173_, List<StructurePiece> p_71174_, Random p_71175_) {
        Rotation rotation = p_71172_.placeSettings.getRotation();
        EndCityPieces.CityTemplate $$7 = EndCityPieces.addHelper(p_71174_, EndCityPieces.addPiece(p_71170_, p_71172_, new BlockPos(3 + p_71175_.nextInt(2), -3, 3 + p_71175_.nextInt(2)), "tower_base", rotation, true));
        $$7 = EndCityPieces.addHelper(p_71174_, EndCityPieces.addPiece(p_71170_, $$7, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
        EndCityPieces.CityTemplate endcitypieces$endcitypiece1 = p_71175_.nextInt(6) == 0 ? $$7 : null;
        int i = 1 + p_71175_.nextInt(3);

        for(int j = 0; j < i; ++j) {
            $$7 = EndCityPieces.addHelper(p_71174_, EndCityPieces.addPiece(p_71170_, $$7, new BlockPos(0, 4, 0), "tower_piece", rotation, true));
            if (j < i - 1 && p_71175_.nextInt(3) == 0) {
                endcitypieces$endcitypiece1 = $$7;
            }
        }

        if (endcitypieces$endcitypiece1 != null) {
            for(Tuple<Rotation, BlockPos> tuple : EndCityPieces.TOWER_BRIDGES) {
                if (p_71175_.nextBoolean()) {
                    EndCityPieces.CityTemplate endcitypieces$endcitypiece2 = EndCityPieces.addHelper(p_71174_, EndCityPieces.addPiece(p_71170_, endcitypieces$endcitypiece1, tuple.getB(), "bridge_end", rotation.getRotated(tuple.getA()), true));
                    EndCityPieces.recursiveChildren(p_71170_, EndCityPieces.TOWER_BRIDGE_GENERATOR, p_71171_ + 1, endcitypieces$endcitypiece2, (BlockPos)null, p_71174_, p_71175_);
                }
            }

            EndCityPieces.addHelper(p_71174_, EndCityPieces.addPiece(p_71170_, $$7, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
        } else {
            if (p_71171_ < 15) {
                return EndCityPieces.recursiveChildren(p_71170_, EndCityPieces.FAT_TOWER_GENERATOR, p_71171_ + 1, $$7, (BlockPos)null, p_71174_, p_71175_);
            }

            EndCityPieces.addHelper(p_71174_, EndCityPieces.addPiece(p_71170_, $$7, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
        }

        return true;
    }
}
