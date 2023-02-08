package mod.beethoven92.betterendforge.client.renderer;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.DragonflyEntityModel;
import mod.beethoven92.betterendforge.client.renderer.layer.EyesLayer;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class DragonflyEntityRenderer extends MobRenderer<DragonflyEntity, DragonflyEntityModel>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, 
			"textures/entity/dragonfly.png");
	private static final RenderType GLOW = RenderType.getEyes(new ResourceLocation(BetterEnd.MOD_ID, 
			"textures/entity/dragonfly_glow.png"));
	
	public DragonflyEntityRenderer(EntityRendererManager renderManagerIn) 
	{
		super(renderManagerIn, new DragonflyEntityModel(), 0.5F);
		
		this.addLayer(new EyesLayer<DragonflyEntity, DragonflyEntityModel>(this) 
	    {
			@Nonnull
            @Override
			public RenderType getRenderType() 
			{
				return GLOW;
			}
	    });
	}

	@Nonnull
    @Override
	public ResourceLocation getEntityTexture(@Nonnull DragonflyEntity entity)
	{
		return TEXTURE;
	}
}
