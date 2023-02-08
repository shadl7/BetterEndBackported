package mod.beethoven92.betterendforge.common.world.structure.piece;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public abstract class BasePiece extends StructurePiece {
	protected BasePiece(int i) {
		super(mod.beethoven92.betterendforge.common.init.ModStructurePieces.CAVE_PIECE, i);
	}

	protected BasePiece(CompoundNBT tag) {
		super(mod.beethoven92.betterendforge.common.init.ModStructurePieces.CAVE_PIECE, tag);
	}

	
	protected abstract void fromNbt(CompoundNBT tag);
}