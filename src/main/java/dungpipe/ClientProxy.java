package dungpipe;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy implements IProxy {
    @Override
    public void preInit() {
        registerItemModel(DungPipe.DUNG_PIPE);
        registerItemModel(DungPipe.SEWER_PIPE);
    }

    private void registerItemModel(Block block)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }
}
