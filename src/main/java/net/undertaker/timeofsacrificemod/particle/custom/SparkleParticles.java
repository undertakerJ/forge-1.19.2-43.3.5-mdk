package net.undertaker.timeofsacrificemod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SparkleParticles extends TextureSheetParticle {

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    protected SparkleParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.8f;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.quadSize *= 1.5F;
        this.lifetime = 40;
        this.setSpriteFromAge(spriteSet);

        this.setColor(0.6f, 0.0f, 1.0f);
    }



    protected SparkleParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed, boolean isSpecialParticle) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        // Другие настройки частицы
    }

    @Override
    public void tick() {
        super.tick();
        shrink();
        if (this.quadSize <= 0.0F) {
            this.remove(); // Уничтожить частицу, если её размер меньше или равен 0
        }
    }

    private void shrink() {
        float scale = (float) age / (float) lifetime; // Вычисление масштаба
        this.quadSize -= 0.015F * scale; // Уменьшение размера частицы
        // Поправка координаты yd для движения вверх
        this.yd = 0.05D * (double) scale;
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new SparkleParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }

    }
}
