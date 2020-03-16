package com.enigtech.mooshroomcraft.util;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.Mooshroomcraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemUtil {
    public static ITextComponent getDisplayName(ItemStack stack, String suffix) {
        if(stack.hasTag()){
            String resourceName;
            String displayName;
            assert stack.getTag() != null;
            resourceName = IConfigHandler.getNameFromTag(stack.getTag());
            if(IConfigHandler.getDisplayName(resourceName)!=null){
                displayName = I18n.format("format."+ Mooshroomcraft.MOD_ID+"."+suffix, IConfigHandler.getDisplayName(resourceName));
            }else{
                displayName = I18n.format("format."+Mooshroomcraft.MOD_ID+"."+suffix, I18n.format("resource."+Mooshroomcraft.MOD_ID+"."+resourceName));
            }
            return new StringTextComponent(displayName);
        }else{
            return new StringTextComponent(I18n.format("item."+ Mooshroomcraft.MOD_ID+"."+suffix));
        }
    }
}
