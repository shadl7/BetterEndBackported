package mod.beethoven92.betterendforge.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerList.class)
public class PlayerListMixin 
{
	@Shadow
	public MinecraftServer getServer() 
	{
		return null;
	}
}
