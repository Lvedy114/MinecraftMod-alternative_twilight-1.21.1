package com.lvedy.at_mod.mixin.Naga;

import com.lvedy.at_mod.config.Config;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.entity.ai.goal.NagaMovementPattern;
import twilightforest.entity.boss.Naga;

import java.util.Objects;

@Mixin(Naga.class)
public abstract class NagaMixin {

    @Shadow public abstract boolean isStunlessCharging();
    @Shadow public abstract NagaMovementPattern getMovementPattern();

    @Inject(method = "hurt", at = @At("HEAD"))
    public void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Naga naga = (Naga) (Object) this;
        if (this.isStunlessCharging()) {
            Entity attacker = source.getDirectEntity();
            if (attacker == null) {
                attacker = source.getEntity();
            }

            if (attacker != null) {
                Vec3 attacker_speed = attacker.getDeltaMovement();
                Vec3 naga_speed = naga.getDeltaMovement();
                double speed = attacker_speed.subtract(naga_speed).length();
                if (speed > Config.DAZE_SPEED_THRESHOLD.getAsDouble()) {
                    Vec3 nagaLook = naga.getLookAngle();
                    Vec3 toAttacker = attacker.position().subtract(naga.position()).normalize();
                    double dot = nagaLook.dot(toAttacker);
                    if (dot > 0) { // Frontal attack
                        NagaMovementPattern pattern = this.getMovementPattern();
                        if (pattern != null) {
                            pattern.doDaze();
                        }
                    }
                }
            }
        }
        if(!naga.isDazed())
            amount = (float) (amount * Config.NON_DAZED_DAMAGE_MULTIPLIER.getAsDouble());
        else
            amount = (float) (amount * Config.DAZED_DAMAGE_MULTIPLIER.getAsDouble());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onNaga(EntityType type, Level level, CallbackInfo ci) {
        Naga naga = (Naga) (Object) this;
        double maxHealth = Config.MAX_HEALTH.getAsDouble();
        Objects.requireNonNull(naga.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(maxHealth);
        naga.setHealth((float) maxHealth);
    }
}