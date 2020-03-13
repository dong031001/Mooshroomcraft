package com.enigtech.mooshroomcraft.item;

import net.minecraft.item.Item;

public class ColoredItem extends Item {
    private int color;
    public ColoredItem(int color) {
        super(new Item.Properties());
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
