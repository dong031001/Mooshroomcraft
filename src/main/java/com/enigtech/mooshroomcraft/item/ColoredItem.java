package com.enigtech.mooshroomcraft.item;

import net.minecraft.item.Item;

public abstract class ColoredItem extends Item implements ItemColored {

    int color;
    public ColoredItem(int color) {
        super(new Item.Properties().group(ItemRegistry.ITEM_GROUP));
        this.color=color;
    }

    @Override
    public int getColor(){
        return color;
    }


}
