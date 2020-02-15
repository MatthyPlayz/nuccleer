package com.matthyfamily.yottaelectronvolts;

import net.minecraft.util.EnumFacing;

public interface IYottaReceiver extends IYottaHandler {
    int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate);
}
