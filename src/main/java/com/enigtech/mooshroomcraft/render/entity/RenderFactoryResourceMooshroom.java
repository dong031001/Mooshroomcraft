package com.enigtech.mooshroomcraft.render.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryResourceMooshroom implements IRenderFactory {
    Block mushroom;
    public RenderFactoryResourceMooshroom(Block mushroom){
        this.mushroom=mushroom;
    }
    @Override
    public EntityRenderer createRenderFor(EntityRendererManager manager) {
        return new RendererResourceMooshroom(manager, mushroom);
    }
}
