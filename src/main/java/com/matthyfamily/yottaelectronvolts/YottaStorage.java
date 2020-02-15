package com.matthyfamily.yottaelectronvolts;

import net.minecraft.nbt.NBTTagCompound;

public class YottaStorage {
    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;
    public YottaStorage(int capacity) {

        this(capacity, capacity, capacity);
    }

    public YottaStorage(int capacity, int maxTransfer) {

        this(capacity, maxTransfer, maxTransfer);
    }

    public YottaStorage(int capacity, int maxReceive, int maxExtract) {

        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }
    public YottaStorage readFromNBT(NBTTagCompound nbt) {

        this.energy = nbt.getInteger("YeV");

        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        if (energy < 0) {
            energy = 0;
        }
        nbt.setInteger("YeV", energy);
        return nbt;
    }    
    public YottaStorage setCapacity(int capacity) {

        this.capacity = capacity;

        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public YottaStorage setMaxTransfer(int maxTransfer) {

        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public YottaStorage setMaxReceive(int maxReceive) {

        this.maxReceive = maxReceive;
        return this;
    }

    public YottaStorage setMaxExtract(int maxExtract) {

        this.maxExtract = maxExtract;
        return this;
    }

    public int getMaxReceive() {

        return maxReceive;
    }

    public int getMaxExtract() {

        return maxExtract;
    }

    public void setEnergyStored(int energy) {

        this.energy = energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public void modifyEnergyStored(int energy) {

        this.energy += energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }
    public int receiveEnergy(int maxReceive, boolean simulate) {

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            energy += energyReceived;
        }
        return energyReceived;
    }

    public int extractEnergy(int maxExtract, boolean simulate) {

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            energy -= energyExtracted;
        }
        return energyExtracted;
    }

    public int getEnergyStored() {
        return energy;
    }

    public int getMaxEnergyStored() {

        return capacity;
    }
    public void addEnergyStored(int amt) {
        energy += amt;
    }
}
