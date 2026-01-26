package org.inatel.cdg.main;

import org.inatel.cdg.main.FoodEntity;

import java.util.Random;

public class FoodRoulete
{
    private static float[] probabilities = {50, 20, 15, 10, 5};

    private static String[] commonNames =
            {
                    "Apple",
                    "Banana"
            };

    private static String[] uncommonNames =
            {
                    "Cheese",
                    "Bread"
            };

    private static String[] rareNames =
            {
                    "Tuna",
                    "Sandwich"
            };

    private static String[] epicNames =
            {
                    "Sushi",
                    "Pizza"
            };

    private static String[] legendaryNames =
            {
                    "Ambrosia",
                    "Golden Apple of the Hesperides",
                    "Bread of Osiris",
                    "Divine Maize of Quetzalcoatl",
                    "Mead of Poetry",
                    "Ultimeatum"
            };

    private static Random random = new Random();

    public static FoodEntity RollForFood()
    {
        float roll = random.nextFloat() * 100f;
        float accumulated = 0f;

        accumulated += probabilities[0];
        if (roll < accumulated)
            return new FoodEntity(randomFrom(commonNames), Rarity.COMMON);

        accumulated += probabilities[1];
        if (roll < accumulated)
            return new FoodEntity(randomFrom(uncommonNames), Rarity.UNCOMMON);

        accumulated += probabilities[2];
        if (roll < accumulated)
            return new FoodEntity(randomFrom(rareNames), Rarity.RARE);

        accumulated += probabilities[3];
        if (roll < accumulated)
            return new FoodEntity(randomFrom(epicNames), Rarity.EPIC);

        return new FoodEntity(randomFrom(legendaryNames), Rarity.LEGENDARY);
    }

    private static String randomFrom(String[] array)
    {
        return array[random.nextInt(array.length)];
    }
}
