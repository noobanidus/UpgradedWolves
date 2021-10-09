package com.example.upgradedwolves.loot_table.modifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
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
    public ChestLootModifier(LootItemCondition[] conditionsIn, NonNullList<ChestItem> chestItems) {
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
        public ChestLootModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] ailootcondition) {
            JsonArray stacksJson = GsonHelper.getAsJsonArray(json, "chestItems");
            NonNullList<ChestItem> chestItems = NonNullList.create();

            for (int i = 0; i < stacksJson.size(); i++) {
                JsonObject itemStack = stacksJson.get(i).getAsJsonObject();
                int min = GsonHelper.getAsInt(itemStack, "minItem");
                int max =  GsonHelper.getAsInt(itemStack, "maxItem");
                chestItems.add(new ChestItem(
                        ForgeRegistries.ITEMS.getValue(
                                new ResourceLocation(
                                        GsonHelper.getAsString(itemStack, "item"))
                        ),
                        min,max,
                        GsonHelper.getAsFloat(itemStack, "chance")
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
                obj.addProperty("minItem", stack.min);
                obj.addProperty("maxItem", stack.max);
                obj.addProperty("chance", stack.chance);
                chestItems.add(obj);
            }

            json.add("chestItems", chestItems);

            return json;
        }
    }


    public static class ChestItem {
        public IntRange range;
        public Item item;
        public float chance;
        public int min;
        public int max;

        public ChestItem(Item itemIn, int min, int max, float chanceIn) {
            this.item = itemIn;
            this.min = min;
            this.max = max;
            this.range = IntRange.range(min,max);
            this.chance = chanceIn;
        }
    }
}