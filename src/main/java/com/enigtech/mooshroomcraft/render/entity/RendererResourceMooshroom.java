package com.enigtech.mooshroomcraft.render.entity;

import com.enigtech.mooshroomcraft.block.BlockRegistry;
import com.enigtech.mooshroomcraft.block.BlockResourceMushroom;
import com.enigtech.mooshroomcraft.entity.EntityResourceMooshroom;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.MooshroomMushroomLayer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ILightReader;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RendererResourceMooshroom extends MobRenderer<EntityResourceMooshroom, CowModel<EntityResourceMooshroom>> {


    public RendererResourceMooshroom(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,new CowModel<>(), 0.7F);
        this.addLayer(new ResourceMooshroomLayer<>(this, 121212));
    }

    @Override
    public ResourceLocation getEntityTexture(EntityResourceMooshroom entity) {
        return new ResourceLocation("textures/entity/cow/brown_mooshroom.png");
    }

    class ResourceMooshroomLayer<T extends EntityResourceMooshroom> extends MooshroomMushroomLayer<T>{
        int color;
        public ResourceMooshroomLayer(IEntityRenderer rendererIn, int color) {
            super(rendererIn);
            this.color = color;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
                BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                BlockState blockstate = BlockRegistry.BLOCK_RESOURCE_MUSHROOM.getDefaultState();
                int i = LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F);
                matrixStackIn.push();
                matrixStackIn.translate(0.2F, -0.35F, 0.5D);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
                matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                renderMushroom(blockrendererdispatcher,blockstate, matrixStackIn, bufferIn, packedLightIn, i, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);

                matrixStackIn.pop();
                matrixStackIn.push();
                matrixStackIn.translate(0.2F, -0.35F, 0.5D);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(42.0F));
                matrixStackIn.translate(0.1F, 0.0D, -0.6F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-48.0F));
                matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                renderMushroom(blockrendererdispatcher,blockstate, matrixStackIn, bufferIn, packedLightIn, i, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
                matrixStackIn.pop();
                matrixStackIn.push();
                this.getEntityModel().getHead().translateRotate(matrixStackIn);
                matrixStackIn.translate(0.0D, -0.7F, -0.2F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-78.0F));
                matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
                matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
                renderMushroom(blockrendererdispatcher,blockstate, matrixStackIn, bufferIn, packedLightIn, i, net.minecraftforge.client.model.data.EmptyModelData.INSTANCE);
                matrixStackIn.pop();
            }
        }

        private void renderMushroom(BlockRendererDispatcher dispatcher, BlockState blockStateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferTypeIn, int combinedLightIn, int combinedOverlayIn, net.minecraftforge.client.model.data.IModelData modelData){
            IBakedModel ibakedmodel = dispatcher.getModelForState(blockStateIn);
            int i = color;
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            renderModel(matrixStackIn.getLast(), bufferTypeIn.getBuffer(RenderTypeLookup.getRenderType(blockStateIn)), blockStateIn, ibakedmodel, f, f1, f2, combinedLightIn, combinedOverlayIn, modelData);
        }

        private void renderModel(MatrixStack.Entry matrixEntry, IVertexBuilder buffer, @Nullable BlockState state, IBakedModel modelIn, float red, float green, float blue, int combinedLightIn, int combinedOverlayIn, net.minecraftforge.client.model.data.IModelData modelData){
            Random random = new Random();
            random.setSeed(42L);
            List<BakedQuad> quadList = modelIn.getQuads(state, null, random);
            for(BakedQuad bakedquad : quadList) {
                float f;
                float f1;
                float f2;
                if (bakedquad.getTintIndex()==1) {
                    f = MathHelper.clamp(red, 0.0F, 1.0F);
                    f1 = MathHelper.clamp(green, 0.0F, 1.0F);
                    f2 = MathHelper.clamp(blue, 0.0F, 1.0F);
                } else {
                    f = 1.0F;
                    f1 = 1.0F;
                    f2 = 1.0F;
                }
                buffer.addQuad(matrixEntry, bakedquad, f, f1, f2, combinedLightIn, combinedOverlayIn);
            }
        }
    }

}
