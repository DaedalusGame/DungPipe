package dungpipe.tileentity;

import dungpipe.blocks.BlockDungPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityDungPipe extends TileEntity implements ITickable {
    @Override
    public void update() {
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof BlockDungPipe) {
            EnumFacing facing = state.getValue(BlockDungPipe.FACING);
            IItemHandler handler = getContainer(pos.offset(facing),facing);
            if(handler != null)
            {
                ItemStack stack = ItemStack.EMPTY;
                for(int i = 0; i < handler.getSlots(); i++)
                    if(!(stack = handler.extractItem(i,1,false)).isEmpty())
                        break;

                if(!stack.isEmpty())
                {
                    EntityItem item = new EntityItem(this.getWorld(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
                    item.motionX = 0;
                    item.motionY = 0;
                    item.motionZ = 0;
                    item.setDefaultPickupDelay();
                    world.spawnEntity(item);
                }
            }
        }
    }

    public IItemHandler getContainer(BlockPos pos, EnumFacing facing)
    {
        TileEntity te = world.getTileEntity(pos);

        if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,facing.getOpposite()))
            return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
        return null;
    }
}
