package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ChestItemTileEntityRenderer extends ItemStackTileEntityRenderer {
	private EChestTileEntity chest;

	@Override
	public void func_239207_a_(@Nonnull ItemStack stack, @Nonnull TransformType p_239207_2_, @Nonnull MatrixStack matrixStack,
                               @Nonnull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		if (chest == null)
			chest = new EChestTileEntity();
		chest.setChest(Block.getBlockFromItem(stack.getItem()));
		TileEntityRendererDispatcher.instance.renderItem(chest, matrixStack, buffer, combinedLight, combinedOverlay);
	}

}
