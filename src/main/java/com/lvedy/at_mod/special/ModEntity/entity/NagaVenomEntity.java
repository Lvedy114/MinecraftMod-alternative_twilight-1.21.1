package com.lvedy.at_mod.special.ModEntity.entity;

import com.lvedy.at_mod.AlternativeTwilight;
import com.lvedy.at_mod.config.Config;
import com.lvedy.at_mod.register.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NagaVenomEntity extends ThrowableProjectile {

    public static final ResourceKey<DamageType> NAGA_VENOM_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, AlternativeTwilight.prefix("naga_venom"));

    private static final EntityDataAccessor<Boolean> DATA_LINGERING =
            SynchedEntityData.defineId(NagaVenomEntity.class, EntityDataSerializers.BOOLEAN);

    private int lingerTicks = 0;
    private static final float DAMAGE_RADIUS = 2.5F;
    private static final float DAMAGE_AMOUNT = (float) Config.VENOM_DAMAGE.getAsDouble();

    public NagaVenomEntity(EntityType<? extends NagaVenomEntity> type, Level level) {
        super(type, level);
    }

    public NagaVenomEntity(Level level, LivingEntity owner) {
        super(ModEntityTypes.NAGA_VENOM.get(), owner, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_LINGERING, false);
    }

    public boolean isLingering() {
        return this.entityData.get(DATA_LINGERING);
    }

    private void setLingering(boolean lingering) {
        this.entityData.set(DATA_LINGERING, lingering);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05;
    }

    @Override
    public void tick() {
        if (this.isLingering()) {
            this.tickLingering();
        } else {
            super.tick();
            this.tickFlying();
        }
    }

    private void tickFlying() {
        // 飞行阶段粒子拖尾
        if (this.level().isClientSide()) {
            for (int i = 0; i < 3; i++) {
                double offsetX = (this.random.nextDouble() - 0.5) * 0.3;
                double offsetY = (this.random.nextDouble() - 0.5) * 0.3;
                double offsetZ = (this.random.nextDouble() - 0.5) * 0.3;
                this.level().addParticle(ParticleTypes.DRAGON_BREATH,
                        this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ,
                        0, 0, 0);
            }
            // 紫黑色拖尾
            this.level().addParticle(ParticleTypes.WITCH,
                    this.getX(), this.getY(), this.getZ(),
                    0, -0.05, 0);
        }

        // 超时保护：飞行超过5秒自动消失
        if (this.tickCount > Config.VENOM_LINGER_TICKS.getAsInt()) {
            this.discard();
        }
    }

    private void tickLingering() {
        // 停止移动
        this.setDeltaMovement(Vec3.ZERO);

        lingerTicks++;

        if (this.level().isClientSide()) {
            // 滞留阶段粒子效果 - 紫黑色毒雾
            for (int i = 0; i < 5; i++) {
                double angle = this.random.nextDouble() * Math.PI * 2;
                double radius = this.random.nextDouble() * DAMAGE_RADIUS;
                double px = this.getX() + Math.cos(angle) * radius;
                double pz = this.getZ() + Math.sin(angle) * radius;
                double py = this.getY() + this.random.nextDouble() * 0.5;
                this.level().addParticle(ParticleTypes.DRAGON_BREATH,
                        px, py, pz,
                        0, 0.02, 0);
            }
            // 紫色魔法粒子
            for (int i = 0; i < 2; i++) {
                double angle = this.random.nextDouble() * Math.PI * 2;
                double r = this.random.nextDouble() * DAMAGE_RADIUS;
                this.level().addParticle(ParticleTypes.WITCH,
                        this.getX() + Math.cos(angle) * r,
                        this.getY() + this.random.nextDouble() * 0.8,
                        this.getZ() + Math.sin(angle) * r,
                        0, 0.03, 0);
            }
            // 偶尔添加深色烟雾粒子
            if (this.tickCount % 4 == 0) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                        this.getX() + (this.random.nextDouble() - 0.5) * DAMAGE_RADIUS * 2,
                        this.getY() + 0.1,
                        this.getZ() + (this.random.nextDouble() - 0.5) * DAMAGE_RADIUS * 2,
                        0, 0.03, 0);
            }
        }

        // 服务端：每秒造成伤害
        if (!this.level().isClientSide() && lingerTicks % 20 == 0) {
            dealLingeringDamage();
        }

        // 超时消失
        if (lingerTicks >= Config.VENOM_LINGER_TICKS.getAsInt()) {
            this.discard();
        }
    }

    private void dealLingeringDamage() {
        AABB area = new AABB(
                this.getX() - DAMAGE_RADIUS, this.getY() - 1, this.getZ() - DAMAGE_RADIUS,
                this.getX() + DAMAGE_RADIUS, this.getY() + 2, this.getZ() + DAMAGE_RADIUS
        );

        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, area,
                entity -> entity.isAlive() && entity != this.getOwner());

        if (!entities.isEmpty()) {
            var damageRegistry = this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
            var damageHolder = damageRegistry.getHolderOrThrow(NAGA_VENOM_DAMAGE);
            DamageSource venomSource = new DamageSource(damageHolder, this.getOwner());

            for (LivingEntity target : entities) {
                target.hurt(venomSource, DAMAGE_AMOUNT);
            }
        }
    }

    private void startLingering() {
        this.setLingering(true);
        this.setDeltaMovement(Vec3.ZERO);
        this.setNoGravity(true);
        this.lingerTicks = 0;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.isLingering()) {
            // 将位置设置到碰撞点上方一点
            this.setPos(result.getLocation().x, result.getLocation().y + 0.1, result.getLocation().z);
            this.startLingering();
        }
    }

    @Override
    public boolean isNoGravity() {
        return this.isLingering() || super.isNoGravity();
    }
}
