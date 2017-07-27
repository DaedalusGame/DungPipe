package dungpipe.blocks;

import dungpipe.tileentity.TileEntitySewerPipe;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class BlockSewerPipe extends BlockDungPipe {
    public static final PropertyBool EXTENDED = PropertyBool.create("extended");

    public BlockSewerPipe(String name,Material material) {
        super(name,material);
        this.setDefaultState(getDefaultState().withProperty(EXTENDED,false));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySewerPipe();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(EXTENDED,(meta & 4) > 0).withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(EXTENDED) ? 4 : 0) | (state.getValue(FACING).getHorizontalIndex());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, EXTENDED, FACING);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        BlockPos checkpos = pos.offset(facing);
        TileEntity te = worldIn.getTileEntity(checkpos);

        if((te == null || !te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,facing.getOpposite())) && worldIn.isSideSolid(checkpos,facing.getOpposite(),false))
            return state.withProperty(EXTENDED,true);

        return state.withProperty(EXTENDED,false);
    }
}
