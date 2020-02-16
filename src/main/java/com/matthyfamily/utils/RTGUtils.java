package com.matthyfamily.utils;

import com.matthyfamily.tileentity.DataTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RTGUtils {
    public DataTileEntity getTE(World world, BlockPos pos) {
        return (DataTileEntity) world.getTileEntity(pos);
    }
    public DataTileEntity getTENoArgs() {
        // Get the player
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        // Raytrace to get what block user is looking at
        RayTraceResult raytraced = player.rayTrace(200, 1.0F);
        // Just in case the user is looking at nothing
        BlockPos pos = new BlockPos(new Vec3d(0, 0, 0));
        // Set the BlockPos pos
        pos = new BlockPos(raytraced.hitVec);
        // Get the world for finally getting the TileEntity
        World world = player.getEntityWorld();
        return this.getTE(world, pos);
    }
}