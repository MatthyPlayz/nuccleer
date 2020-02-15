package com.matthyfamily.tileentity;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DataTileEntity extends TileEntity implements IEnergyStorage, ITickable {

    public int energy = 0;
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energy = compound.getInteger("Energy");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("Energy", energy);
        return compound;
    }

    public void addRF(int amt, NBTTagCompound compound) {
        compound.setInteger("Energy", compound.getInteger("Energy") + amt);
        energy += amt;
    }

    public void subRF(int amt, NBTTagCompound compound) {
        compound.setInteger("Energy", compound.getInteger("Energy") - amt);
        energy -= amt;
    }

    public int getEnergyAmount() {
        return energy;
    }

    public int addRFRaw(int amt, NBTTagCompound compound) {
        compound.setInteger("Energy", compound.getInteger("Energy") + amt);
        energy += amt;
        writeToNBT(compound);
        return amt;
    }
    private DataTileEntity getTE(World world, BlockPos pos) {
        return (DataTileEntity) world.getTileEntity(pos);
    }

    public EnergyStorage energyStorage;

    public boolean canConnectEnergy(EnumFacing from) {
        return false;
    }
    public DataTileEntity easyGetTE() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        RayTraceResult raytraced = player.rayTrace(200, 1.0F);
        BlockPos pos = new BlockPos(new Vec3d(0, 0, 0));
        if ((raytraced != null)) {
            int blockHitX = (int) raytraced.hitVec.x;
            int blockHitY = (int) raytraced.hitVec.y;
            int blockHitZ = (int) raytraced.hitVec.z;
            System.out.println(blockHitX + "	" + blockHitY + "\t" + blockHitZ);
            pos = new BlockPos(raytraced.hitVec);
        }
        World world = player.getEntityWorld();
        //System.out.println("x: " + pos.getX() +" y: " + pos.getY() +" z: " + pos.getZ());
        //int energyReceived = Math.min(getMaxEnergyStored(from) - getEnergyStored(from), Math.min(getMaxReceive(), maxReceive));
        int energyReceived = 1;
        DataTileEntity te = this.getTE(world, pos);
        return te;
    }
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        TileEntity te = easyGetTE();
        //System.out.println("x: " + pos.getX() +" y: " + pos.getY() +" z: " + pos.getZ());
        //int energyReceived = Math.min(getMaxEnergyStored(from) - getEnergyStored(from), Math.min(getMaxReceive(), maxReceive));
        int energyReceived = 1;
        DataTileEntity TE = (DataTileEntity)world.getTileEntity(pos);
        TE.energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return this.energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return 100000;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    public void update() {
        DataTileEntity TE = (DataTileEntity)world.getTileEntity(pos);
        TE.energy++;
    }
}

