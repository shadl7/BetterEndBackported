package mod.beethoven92.betterendforge.common.interfaces;

import net.minecraft.util.math.BlockPos;

public interface TeleportingEntity
{
	boolean beCanTeleport();

	void beSetExitPos(BlockPos pos);
	BlockPos beGetExitPos();
	void beResetExitPos();

	long beGetCooldown();
	void beSetCooldown(long time);

	default boolean hasCooldown()
	{
		return this.beGetCooldown() > 0;
	}
}
