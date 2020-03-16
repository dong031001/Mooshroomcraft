package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import com.enigtech.mooshroomcraft.util.IOHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.enigtech.mooshroomcraft.Mooshroomcraft.CONFIG_FILE;
import static com.enigtech.mooshroomcraft.Mooshroomcraft.CONFIG_FOLDER;

public class IConfigHandler {

    private static HashMap<String, IResource> resourceMap = new HashMap<>();

    public String name;
    public String displayName;
    public int color;
    public IEffectHandler[] effects = new IEffectHandler[]{};

    public IConfigHandler(){

    }

    public IConfigHandler(String name, int color){
        this.name=name;
        this.color=color;
    }

    public IConfigHandler addEffect(String ID, int time){
        return addEffect(new IEffectHandler(ID, time));
    }

    public IConfigHandler addEffect(IEffectHandler effect){
        IEffectHandler[] oldEffects = effects.clone();
        effects = new IEffectHandler[oldEffects.length+1];
        if(oldEffects.length!=0) System.arraycopy(oldEffects, 0, effects, 0, oldEffects.length);
        effects[effects.length-1] = effect;
        return this;
    }

    public static class IEffectHandler{
        public IEffectHandler(){ }
        public IEffectHandler(String ID, int time){
            this.effect = ID;
            this.time = time;
        }
        public String effect;
        public int time;
    }

    private static IConfigHandler[] defaultResources = new IConfigHandler[]{
            new IConfigHandler("iron", 0xbfbfbf).addEffect("minecraft:slowness", 200).addEffect("minecraft:resistance", 1200),
            new IConfigHandler("gold", 0xfff945).addEffect("minecraft:haste",400).addEffect("minecraft:absorption", 800),
            new IConfigHandler("diamond", 55295).addEffect("minecraft:glowing",200).addEffect("minecraft:resistance", 2400),
            new IConfigHandler("lapis", 3151789).addEffect("minecraft:speed", 800),
            new IConfigHandler("coal", 3947580).addEffect("minecraft:fire_resistance", 2400).addEffect("minecraft:mining_fatigue", 800),
            new IConfigHandler("quartz", 16777215).addEffect("minecraft:night_vision", 2400),
            new IConfigHandler("redstone", 0xff0000).addEffect("minecraft:haste", 400).addEffect("minecraft:jump_boost", 1200)
    };


    public static ArrayList<IConfigHandler> loadResources() throws IOException {
        File file = new File(CONFIG_FILE);
        File folder = new File(CONFIG_FOLDER);
        if(!file.isFile()){
            System.out.println(folder.mkdirs());
            System.out.println(file.createNewFile());
            FileWriter writer = new FileWriter(file);
            writer.write(IOHandler.gson.toJson(defaultResources));
            writer.flush();
            writer.close();
        }
        return IOHandler.readFromFile(file);
    }


    static void init() throws IOException {
        for(IConfigHandler resource:loadResources()){
            ArrayList<EffectInstance> effects = new ArrayList<>();

            for(IEffectHandler effectHandler:resource.effects){
                Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectHandler.effect));
                effects.add(new EffectInstance(effect, effectHandler.time));
            }

            registerResource(resource.name, resource.color, resource.displayName, effects);
        }
        //registerResource("iron",0xbfbfbf);
        //registerResource("gold",0xfff945);
    }

    public static int getColor(String name){
        if(name==null|| name.equals("")) return 0;
        return resourceMap.get(name).color;
    }

    public static String getDisplayName(String name){
        if(name==null|| name.equals("")) return null;
        return resourceMap.get(name).displayName;
    }

    public static EffectInstance[] getEffectInstances(String name){
        return resourceMap.get(name).stewEffect;
    }

    public static ItemMoosher getMoosher(String name){
        return resourceMap.get(name).moosher;
    }

    public static Item getMushroom(String name){
        return resourceMap.get(name).mushroom;
    }

    public static Item getMushroomStew(String name){
        return resourceMap.get(name).mushroomStew;
    }

    public static EntityType<EntityResourceMooshroom> getMooshroom(String name){
        return resourceMap.get(name).mooshroomEntityType;
    }

    public static Block getMushroomBlock(String name){ return resourceMap.get(name).mushroomBlock;}

    public static Set<String> getResourceNames(){
        return resourceMap.keySet();
    }

    private static void registerResource(String name, int color, String displayName, ArrayList<EffectInstance> effectInstances){
        resourceMap.put(name, new IResource(name, color, displayName, effectInstances));
    }

    public String toString(){
        return this.name+", color:"+color;
    }

    public static String getNameFromTag(CompoundNBT tag){
        if(tag.contains("resource")) return tag.getString("resource");
        if(tag.contains("BlockEntityTag")&&tag.getCompound("BlockEntityTag").contains("resource")) return tag.getCompound("BlockEntityTag").getString("resource");
        return null;
    }

}
