package net.undertaker.timeofsacrificemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.undertaker.timeofsacrificemod.entity.custom.AmethystSparkEntity;

public class AmethystSparkEntityRenderer extends EntityRenderer<AmethystSparkEntity> {
    public AmethystSparkEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(AmethystSparkEntity pEntity) {
        return new ResourceLocation("timeofsacrifice:entity/amethyst_spark_entity");
    }
    public void render(AmethystSparkEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMaxtrixStack,
                       MultiBufferSource pBuffer, int pPackedLight){
        super.render(pEntity, pEntityYaw, pPartialTicks,pMaxtrixStack,pBuffer,pPackedLight);
    }
}
