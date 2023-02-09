package mod.beethoven92.betterendforge.common.lootmodifier;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;
import java.util.Random;

public class BetterEndChorusFruitLootModifier extends LootModifier {
    private int min, max;
    private static final List<Item> CHORUS = ImmutableList.of(Items.CHORUS_FRUIT);

    public BetterEndChorusFruitLootModifier(ILootCondition[] conditionsIn, int min, int max) {
        super(conditionsIn);
        this.min = min;
        this.max = max;
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Random rand = context.getRandom();
        int count = rand.nextInt(max - min) + min;
        for (int i = 0; i < count; i++) generatedLoot.add(CHORUS.get(rand.nextInt(CHORUS.size())).getDefaultInstance());
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BetterEndChorusFruitLootModifier> {

        @Override
        public BetterEndChorusFruitLootModifier read(ResourceLocation name, JsonObject json,
                                                   ILootCondition[] conditionsIn) {
            int min = JSONUtils.getInt(json, "min");
            int max = JSONUtils.getInt(json, "max");
            if (min >= max)
                throw new JsonSyntaxException("min has to be smaller than max");
            return new BetterEndChorusFruitLootModifier(conditionsIn, min, max);
        }

        @Override
        public JsonObject write(BetterEndChorusFruitLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("min", instance.min);
            json.addProperty("max", instance.max);
            return json;
        }
    }
}
