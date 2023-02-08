package mod.beethoven92.betterendforge.mixin;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerList.class)
public class PlayerListMixin 
{
	@Final
	@Shadow
	private static Logger LOGGER;
	
	@Final
	@Shadow
	private MinecraftServer server;
	
	@Final
	@Shadow
	private List<ServerPlayerEntity> players;
	
	@Final
	@Shadow
	private Map<UUID, ServerPlayerEntity> uuidToPlayerMap;
	
	@Final
	@Shadow
	private DynamicRegistries.Impl field_232639_s_;

    @Shadow
	private int viewDistance;
	
	@Shadow
	public CompoundNBT readPlayerDataFromFile(ServerPlayerEntity player)
	{
		return null;
	}
	
	@Shadow
	public int getMaxPlayers() 
	{
		return 0;
	}
	
	@Shadow
	private void setPlayerGameTypeBasedOnOther(ServerPlayerEntity player, @Nullable ServerPlayerEntity oldPlayer, ServerWorld world) {}
	
	@Shadow
	public MinecraftServer getServer() 
	{
		return null;
	}
	@Shadow
	public void updatePermissionLevel(ServerPlayerEntity player) {}
	
	@Shadow
	protected void sendScoreboard(ServerScoreboard scoreboardIn, ServerPlayerEntity playerIn) {}
	
	@Shadow
	public void func_232641_a_(ITextComponent p_232641_1_, ChatType p_232641_2_, UUID p_232641_3_) {}
	
	@Shadow
	public void sendPacketToAllPlayers(IPacket<?> packetIn) {}
	 
	@Shadow
	public void sendWorldInfo(ServerPlayerEntity playerIn, ServerWorld worldIn) {}
	
	@Inject(method = "initializeConnectionToPlayer", at = @At(value = "HEAD"))
	public void be_initializeConnectionToPlayer(NetworkManager netManager, ServerPlayerEntity playerIn, CallbackInfo info) 
	{
	}
}
