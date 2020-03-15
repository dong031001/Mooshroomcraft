package com.enigtech.mooshroomcraft;

import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.enigtech.mooshroomcraft.item.ItemMoosher;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import com.enigtech.mooshroomcraft.util.IOHandler;

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
        if(oldEffects.length!=0) for(int i = 0; i<oldEffects.length;i++) effects[i]=oldEffects[i];
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

    private static final IConfigHandler[] defaultResources = new IConfigHandler[]{
            new IConfigHandler("iron", 0xbfbfbf).addEffect("minecraft:slowness", 200).addEffect("minecraft:absorption", 400),
            new IConfigHandler("gold", 0xfff945)
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
            registerResource(resource.name, resource.color);
        }
        //registerResource("iron",0xbfbfbf);
        //registerResource("gold",0xfff945);
    }

    public static int getColor(String name){
        if(name==null||name=="") return 0;
        return resourceMap.get(name).color;
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

    private static void registerResource(String name, int color){
        resourceMap.put(name, new IResource(name, color));
    }

    public String toString(){
        return this.name+", color:"+color;
    }

}
