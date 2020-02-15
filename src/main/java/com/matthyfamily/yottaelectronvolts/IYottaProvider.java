package com.matthyfamily.yottaelectronvolts;

import net.minecraft.util.EnumFacing;

public interface IYottaProvider extends IYottaHandler {
    int extractEnergy(EnumFacing from, int maxExtract, boolean simulate);
}