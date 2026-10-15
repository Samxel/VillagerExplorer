package com.samxel.villagerexplorer.screen;

import com.samxel.villagerexplorer.villager.Merchant;
import com.samxel.villagerexplorer.villager.VillagerBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerType;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public final class VillagerPreview {
    private static final float PITCH = -3.0F;

    private VillagerPreview() {
    }

    public static @Nullable LivingEntity create(Merchant merchant) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }

        ResourceKey<VillagerProfession> profession = merchant.profession();
        LivingEntity entity = profession == null
                ? EntityTypes.WANDERING_TRADER.create(level, EntitySpawnReason.TRIGGERED)
                : EntityTypes.VILLAGER.create(level, EntitySpawnReason.TRIGGERED);
        if (entity == null) {
            return null;
        }

        // preview entities are never spawned, but rendering requires an ID; negative IDs can't clash with server ones
        entity.setId(-1 - merchant.ordinal());
        if (entity instanceof Villager villager && profession != null) {
            villager.setVillagerData(villager.getVillagerData().withProfession(level.registryAccess(), profession));
        }
        return entity;
    }

    public static void setBiome(LivingEntity entity, @Nullable VillagerBiome biome) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && entity instanceof Villager villager) {
            ResourceKey<VillagerType> type = biome == null ? VillagerType.PLAINS : biome.type();
            villager.setVillagerData(villager.getVillagerData().withType(level.registryAccess(), type));
        }
    }

    public static void extractFacingFront(GuiGraphicsExtractor graphics, LivingEntity entity, int x0, int y0, int x1, int y1, float scale) {
        EntityRenderer<? super LivingEntity, ?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
        EntityRenderState state = renderer.createRenderState(entity, 1.0F);
        if (state instanceof LivingEntityRenderState living) {
            living.bodyRot = 0.0F;
            living.yRot = 0.0F;
            living.xRot = PITCH;
        }
        state.shadowPieces.clear();
        state.outlineColor = EntityRenderState.NO_OUTLINE;

        Quaternionf rotation = new Quaternionf()
                .rotateZ((float) Math.PI)
                .rotateY((float) Math.PI)
                .rotateX((float) Math.toRadians(PITCH));
        graphics.entity(state, scale, new Vector3f(0.0F, 1.0F, 0.0F), rotation, null, x0, y0, x1, y1);
    }
}
