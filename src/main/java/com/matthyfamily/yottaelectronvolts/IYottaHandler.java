/*
 * (C) 2014-2018 Team CoFH / CoFH / Cult of the Full Hub
 * http://www.teamcofh.com
 */
package com.matthyfamily.yottaelectronvolts;

import net.minecraft.util.EnumFacing;


public interface IYottaHandler extends IYottaConnection {
    int getEnergyStored(EnumFacing from);

    int getMaxEnergyStored(EnumFacing from);

    int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate);
}
