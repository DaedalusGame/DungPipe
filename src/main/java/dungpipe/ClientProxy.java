package dungpipe;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy implements IProxy {
    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void registerItemModel(Block block)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        registerItemModel(DungPipe.DUNG_PIPE);
        registerItemModel(DungPipe.SEWER_PIPE);
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }
}
