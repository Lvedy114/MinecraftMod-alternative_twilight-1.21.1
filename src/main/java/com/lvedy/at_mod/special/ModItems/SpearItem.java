package com.lvedy.at_mod.special.ModItems;

import com.lvedy.at_mod.AlternativeTwilight;
import com.lvedy.at_mod.register.ModItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.phys.AABB;
import java.util.List;

public class SpearItem extends TieredItem {

    public static final ResourceKey<DamageType> SPEAR_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, AlternativeTwilight.prefix("spear"));
    
    // 保存右键时的突进速度
    private final float dashSpeed;

    public SpearItem(Tier tier, Properties properties, float dashSpeed) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()));
        this.dashSpeed = dashSpeed;
    }

    public static ItemAttributeModifiers createAttributes(double damage, double speed, double range) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(AlternativeTwilight.prefix("range_modifier"), range, AttributeModifier.Operation.ADD_VALUE),EquipmentSlotGroup.MAINHAND).build();
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity entity) {
        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        return super.onLeftClickEntity(stack, player, entity);
    }

    // 设置玩家手持长矛时的动画为“矛”专属动画（手臂微微抬起并固定）
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        // 确保在服务端运行，玩家正手持长矛且速度大于1
        if (!level.isClientSide() && isSelected && entity instanceof Player player && player.getDeltaMovement().length() > 1.0) {
            AABB boundingBox = player.getBoundingBox().inflate(1.3);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, boundingBox, e -> e != player && e.isAlive());

            if (!targets.isEmpty()) {
                float totalDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                var damageRegistry = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
                var damageHolder = damageRegistry.getHolderOrThrow(SPEAR_DAMAGE);
                DamageSource spearSource = new DamageSource(damageHolder, player);

                for (LivingEntity target : targets) {
                    if (target.invulnerableTime == 0) {
                        /*System.out.println("基础伤害: " + totalDamage);
                        System.out.println("速度: " + player.getDeltaMovement().length());
                        System.out.println("实际伤害: " + totalDamage*Math.pow(player.getDeltaMovement().length(),2));*/
                        float speed = (float) player.getDeltaMovement().length();
                        target.hurt(spearSource, (float) Math.max(1, totalDamage*Math.pow(speed-1,2)));
                        target.invulnerableTime = 10; // 给目标 10 tick 无敌时间
                        stack.hurtAndBreak(2, player, EquipmentSlot.MAINHAND);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        // 使用保存的 dashSpeed 作为初速度
        Vec3 vec1 = player.getForward().scale(this.dashSpeed);
        player.setDeltaMovement(vec1);
        player.getCooldowns().addCooldown(this, 100);
        return super.use(level, player, usedHand);
    }

    
    public static class ClientExtensions {
        public static final EnumProxy<HumanoidModel.ArmPose> SPEAR_POSE = new EnumProxy<>(
                HumanoidModel.ArmPose.class, false, (IArmPoseTransformer) (model, entity, arm) -> {
            if (arm == HumanoidArm.RIGHT) {
                model.rightArm.xRot = -0.5F; // 向前略微抬起
                model.rightArm.yRot = -0.1F;
                model.rightArm.zRot = 0.0F;
            } else {
                model.leftArm.xRot = -0.5F;
                model.leftArm.yRot = 0.1F;
                model.leftArm.zRot = 0.0F;
            }
        }
        );

        public static void spearAnim(RegisterClientExtensionsEvent event){
            IClientItemExtensions spearExtension = new IClientItemExtensions() {
                @Override
                public HumanoidModel.ArmPose getArmPose(@NotNull LivingEntity entityLiving, @NotNull InteractionHand hand, @NotNull ItemStack itemStack) {
                    return SPEAR_POSE.getValue();
                }
            };

            // 为所有的长矛注册这个客户端扩展
            event.registerItem(spearExtension,
                    ModItem.WOODEN_SPEAR.get(),
                    ModItem.STONE_SPEAR.get(),
                    ModItem.COPPER_SPEAR.get(),
                    ModItem.IRON_SPEAR.get(),
                    ModItem.GOLDEN_SPEAR.get(),
                    ModItem.DIAMOND_SPEAR.get(),
                    ModItem.NETHERITE_SPEAR.get(),
                    ModItem.IRONWOOD_SPEAR.get(),
                    ModItem.KNIGHT_SPEAR.get()
            );
        }
    }
}
