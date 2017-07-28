package dungpipe;

import dungpipe.blocks.BlockDungPipe;
import dungpipe.blocks.BlockSewerPipe;
import dungpipe.tileentity.TileEntityDungPipe;
import dungpipe.tileentity.TileEntitySewerPipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = DungPipe.MODID, version = DungPipe.VERSION)
@Mod.EventBusSubscriber
public class DungPipe
{
    public static final String MODID = "dungpipe";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "dungpipe.ClientProxy",serverSide = "dungpipe.ServerProxy")
    public static IProxy proxy;

    @GameRegistry.ObjectHolder("dungpipe:dung_pipe")
    public static BlockDungPipe DUNG_PIPE;
    @GameRegistry.ObjectHolder("dungpipe:sewer_pipe")
    public static BlockSewerPipe SEWER_PIPE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerTileEntity(TileEntityDungPipe.class, MODID + ":" + "dungPipe");
        GameRegistry.registerTileEntity(TileEntitySewerPipe.class, MODID + ":" + "sewerPipe");

        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DUNG_PIPE,3)," sw","s s"," s ",'s',"slabWood",'w',"plankWood"));
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SEWER_PIPE,1),"pss",'s',"ingotIron",'p',new ItemStack(DUNG_PIPE)));

        proxy.preInit();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockDungPipe("dung_pipe",Material.WOOD));
        event.getRegistry().register(new BlockSewerPipe("sewer_pipe",Material.IRON));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(DUNG_PIPE).setRegistryName(DUNG_PIPE.getRegistryName()));
        event.getRegistry().register(new ItemBlock(SEWER_PIPE).setRegistryName(SEWER_PIPE.getRegistryName()));
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
