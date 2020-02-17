package com.matthyfamily.rtg;

import com.matthyfamily.Reference;
import com.matthyfamily.tileentity.DataTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.matthyfamily.utils.RTGUtils.getTENoArgs;

public class RTG extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public RTG() {
        super(Material.ROCK);
        setUnlocalizedName(Reference.MODID + ".rtg");
        setRegistryName("rtg");
    }
    private DataTileEntity getTE(World world, BlockPos pos) {
        return (DataTileEntity) world.getTileEntity(pos);
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() - 2;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new DataTileEntity();
    }
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            // Get the tile entity
            DataTileEntity TE = getTENoArgs();
            // Make the component that gets outputted when sendStatusMessage is ran
            TextComponentTranslation component = new TextComponentTranslation("FE: " + TE.energy);
            // Make the color GREEEEEEEEEN
            component.getStyle().setColor(TextFormatting.GREEN);
            // Send the player the message (the 2nd parameter makes it be on the action bar)
            player.sendStatusMessage(component, true);
        }
        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
}