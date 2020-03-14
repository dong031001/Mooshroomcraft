package com.enigtech.mooshroomcraft.render.entity;

import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.MooshroomMushroomLayer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.Map;

public class RendererResourceMooshroom extends MobRenderer<EntityResourceMooshroom, CowModel<EntityResourceMooshroom>> {

    Block mushroom;

    public RendererResourceMooshroom(EntityRendererManager renderManagerIn, Block mushroom) {
        super(renderManagerIn,new CowModel<>(), 0.7F);
        this.mushroom = mushroom;
        this.addLayer(new ResourceMooshroomLayer<>(this, mushroom));
    }

    @Override
    public ResourceLocation getEntityTexture(EntityResourceMooshroom entity) {
        return new ResourceLocation("textures/entity/cow/brown_mooshroom.png");
    }

    class ResourceMooshroomLayer<T extends EntityResourceMooshroom> extends MooshroomMushroomLayer<T>{
        Block mushroom;
        public ResourceMooshroomLayer(IEntityRenderer rendererIn, Block mushroom) {
            super(rendererIn);
            this.mushroom = mushroom;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
                BlockState blockstate = mushroom.getDefaultState();
                int i = LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F);
                matrixStackIn.push();
                matrixStackIn.translate(0.2F, -0.35F, 0.5D);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
                matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
                matrixStackIn.pop();
                matrixStackIn.push();
                matrixStackIn.translate(0.2F, -0.35F, 0.5D);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(42.0F));
                matrixStackIn.translate(0.1F, 0.0D, -0.6F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
                matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
                matrixStackIn.pop();
                matrixStackIn.push();
                this.getEntityModel().getHead().setAnglesAndRotation(matrixStackIn);
                matrixStackIn.translate(0.0D, -0.7F, -0.2F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-78.0F));
                matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                blockrendererdispatcher.renderBlock(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
                matrixStackIn.pop();
            }
        }
    }

}
