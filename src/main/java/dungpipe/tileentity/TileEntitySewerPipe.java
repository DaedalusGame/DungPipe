package dungpipe.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

public class TileEntitySewerPipe extends TileEntityDungPipe {
    @Override
    public IItemHandler getContainer(BlockPos pos, EnumFacing facing) {
        IItemHandler container = super.getContainer(pos, facing);

        if(container == null) {
            IBlockState state = world.getBlockState(pos);

            if(state.isSideSolid(world,pos,facing.getOpposite()))
                container = super.getContainer(pos.offset(facing), facing);
        }

        return container;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
