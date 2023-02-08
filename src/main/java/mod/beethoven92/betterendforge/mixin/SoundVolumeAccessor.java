package mod.beethoven92.betterendforge.mixin;

import net.minecraft.client.audio.LocatableSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LocatableSound.class)
public interface SoundVolumeAccessor 
{
	@Accessor("volume")
	void setVolume(float volume);
}
