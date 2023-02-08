package mod.beethoven92.betterendforge.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.UUID;

public class HammerItem extends ToolItem
{
	public final static UUID ATTACK_KNOCKBACK_MODIFIER = MathHelper.getRandomUUID(ThreadLocalRandom.current());
	
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	
	public HammerItem(IItemTier tier, float attackDamage, float attackSpeed, double knockback, Properties builderIn) 
	{
		super(attackDamage, attackSpeed, tier, Sets.newHashSet(), builderIn.addToolType(ToolType.get("hammer"), tier.getHarvestLevel()));
		
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage + tier.getAttackDamage(), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", knockback, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public boolean canPlayerBreakBlockWhileHolding(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player)
	{
		return state.getMaterial().equals(Material.ROCK) ||
				   state.getMaterial().equals(Material.GLASS) ||
				   state.isIn(Blocks.DIAMOND_BLOCK) ||
				   state.isIn(Blocks.EMERALD_BLOCK) ||
				   state.isIn(Blocks.LAPIS_BLOCK) ||
				   state.isIn(Blocks.REDSTONE_BLOCK);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker)
	{
		stack.damageItem(1, attacker, ((entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND)));
		
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(@Nonnull ItemStack stack, World worldIn, @Nonnull BlockState state, @Nonnull BlockPos pos,
                                    @Nonnull LivingEntity entityLiving)
	{
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) 
		{
			stack.damageItem(1, entityLiving, ((entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND)));
		}

		return true;
	}
	
	@Override
	public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state)
	{
		if (state.getMaterial().equals(Material.GLASS)) 
		{
			return this.getTier().getEfficiency() * 2.0F;
		}
		if (this.canHarvestBlock(state))
		{
			float mult;
			if (state.isIn(Blocks.DIAMOND_BLOCK) || state.isIn(Blocks.EMERALD_BLOCK) || state.isIn(Blocks.LAPIS_BLOCK) 
					|| state.isIn(Blocks.REDSTONE_BLOCK)) 
			{
				mult = this.getTier().getEfficiency();
			} 
			else 
			{
				mult = this.getTier().getEfficiency() / 2.0F;
			}
			return Math.max(mult, 1.0F);
		}
		return 1.0F;
	}
	
	@Override
	public boolean canHarvestBlock(BlockState state) 
	{
		if (state.getMaterial().equals(Material.GLASS)) 
		{
			return true;
		}
		if (!state.isIn(Blocks.REDSTONE_BLOCK) && !state.isIn(Blocks.DIAMOND_BLOCK) && !state.isIn(Blocks.EMERALD_BLOCK) && !state.isIn(Blocks.LAPIS_BLOCK) && !state.getMaterial().equals(Material.ROCK)) 
		{
			return false;
		}
		int level = this.getTier().getHarvestLevel();
		if (state.isIn(Blocks.IRON_ORE) || state.isIn(Blocks.LAPIS_BLOCK) || state.isIn(Blocks.LAPIS_ORE)) 
		{
			return level >= 1;
		}
		if (state.isIn(Blocks.DIAMOND_BLOCK) && !state.isIn(Blocks.DIAMOND_ORE) || state.isIn(Blocks.EMERALD_ORE) || state.isIn(Blocks.EMERALD_BLOCK) || state.isIn(Blocks.GOLD_ORE) || state.isIn(Blocks.REDSTONE_ORE))
		{
			return level >= 2;
		}
		if (state.isIn(Blocks.OBSIDIAN) || state.isIn(Blocks.CRYING_OBSIDIAN) || state.isIn(Blocks.RESPAWN_ANCHOR) || state.isIn(Blocks.ANCIENT_DEBRIS)) 
		{
			return level >= 3;
		}
		return true;
	}
	
	@Nonnull
    @Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot)
	{
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
	}
}
