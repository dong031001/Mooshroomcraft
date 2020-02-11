package com.enigtech.mooshroomcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IResourceHandler {
    String name;
    ItemStack constructor;
    ItemStack output;
    int color;
    public IResourceHandler(String resourceName, ItemStack itemIn, ItemStack itemOut, int color){
        this.name = resourceName;
        this.constructor = itemIn;
        this.output = itemOut;
        this.color = color;
    }
}
