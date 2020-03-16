package com.enigtech.mooshroomcraft.util;

import com.enigtech.mooshroomcraft.IConfigHandler;
import com.enigtech.mooshroomcraft.IConfigHandler.IEffectHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

public class IOHandler {

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static ArrayList<IConfigHandler> readFromFile(File file) throws IOException {

        JsonReader reader = gson.newJsonReader(new FileReader(file));
        return readResources(reader);
    }

    public static ArrayList<IConfigHandler> readResources(JsonReader reader) throws IOException{
        ArrayList<IConfigHandler> resources = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            resources.add(readResource(reader));
        }
        reader.endArray();
        return resources;
    }

    public static IConfigHandler readResource(JsonReader reader) throws IOException{
        reader.beginObject();
        IConfigHandler resource = new IConfigHandler();
        while(reader.hasNext()){
            String s = reader.nextName();
            if ("name".equals(s)) {
                resource.name = reader.nextString();
            } else if ("color".equals(s)) {
                resource.color = reader.nextInt();
            } else if("displayName".equals(s)){
                resource.displayName = reader.nextString();
            } else if ("effects".equals(s)) {
                ArrayList<IEffectHandler> effectHandlers = readEffects(reader);
                resource.effects = effectHandlers.toArray(new IEffectHandler[0]);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return resource;
    }

    public static ArrayList<IEffectHandler> readEffects(JsonReader reader) throws IOException{
        ArrayList<IEffectHandler> list = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()) list.add(readEffect(reader));
        reader.endArray();
        return list;
    }

    public static IEffectHandler readEffect(JsonReader reader) throws IOException {
        reader.beginObject();
        IEffectHandler effectHandler = new IEffectHandler();
        while(reader.hasNext()){
            String s = reader.nextName();
            if ("effect".equals(s)) {
                effectHandler.effect = reader.nextString();
            } else if ("time".equals(s)) {
                effectHandler.time = reader.nextInt();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return effectHandler;
    }
}
