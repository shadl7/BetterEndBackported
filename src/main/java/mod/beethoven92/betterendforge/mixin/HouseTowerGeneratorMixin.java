package mod.beethoven92.betterendforge.mixin;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.EndCityPieces;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Random;

@Pseudo
@Mixin(targets = {"net.minecraft.world.gen.feature.structure.EndCityPieces$1"})
public class HouseTowerGeneratorMixin {

    /**
     * @author Doctor Who
     * @reason Saving The World
     */
    @Overwrite
    public boolean generate(TemplateManager p_71161_, int p_71162_, EndCityPieces.CityTemplate p_71163_, BlockPos p_71164_, List<StructurePiece> p_71165_, Random p_71166_) {
        if (p_71162_ > 16) {
            return false;
        } else {
            Rotation rotation = p_71163_.getRotation();

            EndCityPieces.CityTemplate endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, p_71163_, p_71164_, "base_floor", rotation, true));
            int i = p_71166_.nextInt(4);
            if (i == 0) {
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor", rotation, false));

                EndCityPieces.recursiveChildren(p_71161_, EndCityPieces.TOWER_GENERATOR, p_71162_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71165_, p_71166_);
            } else if (i == 1) {
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "third_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 8, -1), "third_roof", rotation, false));

                EndCityPieces.recursiveChildren(p_71161_, EndCityPieces.TOWER_GENERATOR, p_71162_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71165_, p_71166_);
            } else if (i == 2) {
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "third_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(0, 4, 0), "third_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 8, -1), "third_roof", rotation, true));

                EndCityPieces.recursiveChildren(p_71161_, EndCityPieces.TOWER_GENERATOR, p_71162_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71165_, p_71166_);
            } else {
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "third_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(1, 4, 1), "second_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 4, -1), "third_floor_2", rotation, false));
                endcitypieces$endcitypiece = EndCityPieces.addHelper(p_71165_, EndCityPieces.addPiece(p_71161_, endcitypieces$endcitypiece, new BlockPos(-1, 8, -1), "third_roof", rotation, true));

                EndCityPieces.recursiveChildren(p_71161_, EndCityPieces.TOWER_GENERATOR, p_71162_ + 1, endcitypieces$endcitypiece, (BlockPos)null, p_71165_, p_71166_);
            }
            return true;
        }
    }
}
