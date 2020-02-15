package com.matthyfamily.rtg;

import com.matthyfamily.Reference;
import com.matthyfamily.tileentity.DataTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RTG extends Block implements IEnergyStorage, ITickable {
    protected int energy = 0;
    public RTG() {
        super(Material.ROCK);
        this.energy = 0;
        setUnlocalizedName(Reference.MODID + ".rtg");
        setRegistryName("rtg");
    }

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new DataTileEntity();
    }

    private DataTileEntity getTE(World world, BlockPos pos) {
        return (DataTileEntity) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = easyGetTE();
        if (!world.isRemote) {
            // We only count on the server side.
            if (side == state.getValue(FACING)) {
                TextComponentTranslation component = new TextComponentTranslation("FE: " + this.energy);
                component.getStyle().setColor(TextFormatting.GREEN);
                player.sendStatusMessage(component, true);
            }
        }
        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() - 2;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
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
        ((DataTileEntity) te).energy += energyReceived;
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

    @Override
    public void update() {
        this.energy++;
    }
}