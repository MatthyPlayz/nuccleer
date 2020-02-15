package com.matthyfamily.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class DataTileEntity extends TileEntity {

    public int energy = 0;

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
}

