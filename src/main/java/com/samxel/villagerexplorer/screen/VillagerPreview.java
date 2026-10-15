package com.samxel.villagerexplorer.screen;

import com.samxel.villagerexplorer.villager.Profession;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.villager.Villager;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public final class VillagerPreview {
    private static final float PITCH = -3.0F;

    private VillagerPreview() {
    }

    public static @Nullable Villager create(Profession profession) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }

        Villager villager = EntityTypes.VILLAGER.create(level, EntitySpawnReason.TRIGGERED);
        if (villager == null) {
            return null;
        }

        // preview entities are never spawned, but rendering requires an ID; negative IDs can't clash with server ones
        villager.setId(-1 - profession.ordinal());
        villager.setVillagerData(villager.getVillagerData().withProfession(level.registryAccess(), profession.key()));
        return villager;
    }

    public static void extractFacingFront(GuiGraphicsExtractor graphics, Villager villager, int x0, int y0, int x1, int y1, float scale) {
        EntityRenderer<? super Villager, ?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(villager);
        EntityRenderState state = renderer.createRenderState(villager, 1.0F);
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
