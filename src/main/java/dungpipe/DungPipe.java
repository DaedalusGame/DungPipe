package dungpipe;

import dungpipe.blocks.BlockDungPipe;
import dungpipe.blocks.BlockSewerPipe;
import dungpipe.tileentity.TileEntityDungPipe;
import dungpipe.tileentity.TileEntitySewerPipe;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = DungPipe.MODID, version = DungPipe.VERSION)
public class DungPipe
{
    public static final String MODID = "dungpipe";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "dungpipe.ClientProxy",serverSide = "dungpipe.ServerProxy")
    public static IProxy proxy;

    public static BlockDungPipe DUNG_PIPE;
    public static BlockSewerPipe SEWER_PIPE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        DUNG_PIPE = new BlockDungPipe("dung_pipe",Material.WOOD);
        SEWER_PIPE = new BlockSewerPipe("sewer_pipe",Material.IRON);

        GameRegistry.register(DUNG_PIPE);
        GameRegistry.register(SEWER_PIPE);
        GameRegistry.register(new ItemBlock(DUNG_PIPE).setRegistryName(DUNG_PIPE.getRegistryName()));
        GameRegistry.register(new ItemBlock(SEWER_PIPE).setRegistryName(SEWER_PIPE.getRegistryName()));

        GameRegistry.registerTileEntity(TileEntityDungPipe.class, MODID + ":" + "dungPipe");
        GameRegistry.registerTileEntity(TileEntitySewerPipe.class, MODID + ":" + "sewerPipe");

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DUNG_PIPE,3)," sw","s s"," s ",'s',"slabWood",'w',"plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SEWER_PIPE,1),"pss",'s',"ingotIron",'p',new ItemStack(DUNG_PIPE)));

        proxy.preInit();
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
