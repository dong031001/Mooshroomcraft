package com.enigtech.mooshroomcraft.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

public class ItemResourceMushroomStew extends Item implements ItemColored {
    int color;
    public ItemResourceMushroomStew(int color) {
        super(new Properties().food((new Food.Builder()
                .hunger(6)
                .saturation(6)
                .effect(new EffectInstance(Effects.SLOWNESS,100),100)
                .effect(new EffectInstance(Effects.ABSORPTION,200),100)
                .build()
        )));
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
