package com.matthyfamily.yottaelectronvolts;

public interface IYottaStorage {
    int receiveEnergy(int maxReceive);
    int extractEnergy(int maxExtract);
    int getEnergyStored();
    int getMaxEnergyStorable();
}
