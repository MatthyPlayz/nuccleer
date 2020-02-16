package com.matthyfamily.init;

import com.matthyfamily.Reference;
import com.matthyfamily.rtg.RTG;
import com.matthyfamily.tileentity.DataTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModBlocks {

    public static RTG rtg;

    public static void init() {
        rtg = new RTG();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(DataTileEntity.class, Reference.MODID + "_rtg");
        event.getRegistry().register(new RTG());
    }
    /*@SubscribeEvent
    public static void registerTileEntities(net.minecraftforge.registries.IForgeRegistryEntry<net.minecraft.tileentity.TileEntity> event) {
    }*/

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(rtg).setRegistryName(rtg.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(Item.getItemFromBlock(rtg));
    }

    public static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
    }
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent event) {
    }
}
