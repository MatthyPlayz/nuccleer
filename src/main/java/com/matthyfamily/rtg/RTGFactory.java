package com.matthyfamily.rtg;

import net.minecraftforge.energy.CapabilityEnergy;

import java.util.concurrent.Callable;

public class RTGFactory implements Callable<CapabilityEnergy> {

    @Override
    public CapabilityEnergy call() throws Exception {
        return (CapabilityEnergy) new RTG().getCapability(CapabilityEnergy.ENERGY, null);
    }
}
