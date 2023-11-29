package net.undertaker.timeofsacrificemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraft.resources.ResourceLocation;
import net.undertaker.timeofsacrificemod.entity.custom.SmokeZoneEntity;

public class SmokeZoneRenderer extends EntityRenderer<SmokeZoneEntity> {
    public SmokeZoneRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
    @Override
    public ResourceLocation getTextureLocation(SmokeZoneEntity pEntity) {
        return null;
    }
    public void render(SmokeZoneEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMaxtrixStack,
                       MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks,pMaxtrixStack,pBuffer,pPackedLight);
    }
}
