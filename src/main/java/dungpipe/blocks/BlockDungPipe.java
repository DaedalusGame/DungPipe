package dungpipe.blocks;

import dungpipe.DungPipe;
import dungpipe.tileentity.TileEntityDungPipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDungPipe extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing",facing -> facing.getHorizontalIndex() >= 0);

    public BlockDungPipe(String name,Material material) {
        super(material, MapColor.AIR);
        this.setUnlocalizedName(name);
        this.setRegistryName(new ResourceLocation(DungPipe.MODID, name));
        this.setCreativeTab(CreativeTabs.REDSTONE);
        this.setHardness(2.0F).setResistance(1.0F);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);

        switch(facing)
        {
            case NORTH:
                return new AxisAlignedBB(5 / 16.0, 5 / 16.0, 0 / 16.0, 11 / 16.0, 11 / 16.0, 4 / 16.0);
            case SOUTH:
                return new AxisAlignedBB(5 / 16.0, 5 / 16.0, 12 / 16.0, 11 / 16.0, 11 / 16.0, 16 / 16.0);
            case WEST:
                return new AxisAlignedBB(0 / 16.0, 5 / 16.0, 5 / 16.0, 4 / 16.0, 11 / 16.0, 11 / 16.0);
            case EAST:
                return new AxisAlignedBB(12 / 16.0, 5 / 16.0, 5 / 16.0, 16 / 16.0, 11 / 16.0, 11 / 16.0);
        }
        return FULL_BLOCK_AABB;

        //return super.getBoundingBox(state, source, pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityDungPipe();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        EnumFacing placefacing = facing.getOpposite();
        if(placefacing.getAxis() == EnumFacing.Axis.Y) {
            int i = MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            placefacing = EnumFacing.getHorizontal(i);
        }

        return getDefaultState().withProperty(FACING,placefacing);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING).getHorizontalIndex());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
