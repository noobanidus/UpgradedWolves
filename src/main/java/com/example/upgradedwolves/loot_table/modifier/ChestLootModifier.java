package com.example.upgradedwolves.loot_table.modifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;

public class ChestLootModifier extends LootModifier
{
    private final NonNullList<ChestItem> chestItems;
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    public ChestLootModifier(ILootCondition[] conditionsIn, NonNullList<ChestItem> chestItems) {
        super(conditionsIn);
        this.chestItems = chestItems;
    }


    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        for (ChestItem chestItem : chestItems) {
            generatedLoot.removeIf(itemStack -> itemStack.getItem() == chestItem.item);
            float rand = context.getRandom().nextFloat();
            float chance = chestItem.chance + Float.parseFloat("0." + context.getLootingModifier());
            if (chance > 1f) {
                chance = 1f;
            }
            if (rand <= chance) {
                generatedLoot.add(new ItemStack(chestItem.item));
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<ChestLootModifier>
    {
        @Override
        public ChestLootModifier read(ResourceLocation location, JsonObject json, ILootCondition[] ailootcondition) {
            JsonArray stacksJson = JSONUtils.getJsonArray(json, "chestItems");
            NonNullList<ChestItem> chestItems = NonNullList.create();

            for (int i = 0; i < stacksJson.size(); i++) {
                JsonObject itemStack = stacksJson.get(i).getAsJsonObject();
                RandomValueRange range = new RandomValueRange(JSONUtils.getInt(itemStack, "minItem"), JSONUtils.getInt(itemStack, "maxItem"));
                chestItems.add(new ChestItem(
                        ForgeRegistries.ITEMS.getValue(
                                new ResourceLocation(
                                        JSONUtils.getString(itemStack, "item"))
                        ),
                        range,
                        JSONUtils.getFloat(itemStack, "chance")
                ));
            }

            return new ChestLootModifier(ailootcondition, chestItems);
        }

        @Override
        public JsonObject write(ChestLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);

            JsonArray chestItems = new JsonArray();

            for(ChestItem stack : instance.chestItems) {
                JsonObject obj = new JsonObject();
                obj.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.item).toString());
                obj.addProperty("minItem", stack.range.getMin());
                obj.addProperty("maxItem", stack.range.getMax());
                obj.addProperty("chance", stack.chance);
                chestItems.add(obj);
            }

            json.add("chestItems", chestItems);

            return json;
        }
    }


    public static class ChestItem {
        public RandomValueRange range;
        public Item item;
        public float chance;

        public ChestItem(Item itemIn, RandomValueRange rangeIn, float chanceIn) {
            this.item = itemIn;
            this.range = rangeIn;
            this.chance = chanceIn;
        }
    }
}