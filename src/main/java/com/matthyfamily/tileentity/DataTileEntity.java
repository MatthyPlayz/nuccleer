package com.matthyfamily.tileentity;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DataTileEntity extends TileEntity implements IEnergyStorage, ITickable, ICapabilityProvider {

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

    public EnergyStorage energyStorage;

    public boolean canConnectEnergy(EnumFacing from) {
        return false;
    }
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        this.energy++;
        return 1;
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
        this.energy++;
    }
    @CapabilityInject(IEnergyStorage.class)
    Capability<IEnergyStorage> ITEM_HANDLER_CAPABILITY = null;

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this.ITEM_HANDLER_CAPABILITY.getDefaultInstance());
        }

        return null;
    }
}

