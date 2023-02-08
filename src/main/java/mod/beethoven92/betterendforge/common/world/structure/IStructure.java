package mod.beethoven92.betterendforge.common.world.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;

import java.util.Random;

public interface IStructure {
	void generate(IServerWorld world, BlockPos pos, Random random);
}