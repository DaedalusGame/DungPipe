package dungpipe.tileentity;

import dungpipe.DungPipe;
import dungpipe.blocks.BlockDungPipe;
import dungpipe.blocks.BlockSewerPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;

public class TileEntitySewerPipeRenderer extends FastTESR<TileEntitySewerPipe> {
    private ResourceLocation textureLocation = new ResourceLocation(DungPipe.MODID,"blocks/metal_pipe_outlet");

    @Override
    public void renderTileEntityFast(TileEntitySewerPipe te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        Minecraft minecraft = Minecraft.getMinecraft();
        BlockPos pos = te.getPos();
        World world = te.getWorld();
        IBlockState state = world.getBlockState(pos).getActualState(world,pos);
        if(state.getBlock() instanceof BlockSewerPipe && state.getValue(BlockSewerPipe.EXTENDED)) {
            EnumFacing facing = state.getValue(BlockDungPipe.FACING);
            double epsilon = 1.001;
            buffer.setTranslation(x + facing.getFrontOffsetX()*epsilon, y + facing.getFrontOffsetY()*epsilon, z + facing.getFrontOffsetZ()*epsilon);
            BlockPos lightPos = pos.offset(facing, 2);
            int brightness = world.getCombinedLight(lightPos, 0);
            TextureAtlasSprite sprite = minecraft.getTextureMapBlocks().getTextureExtry(textureLocation.toString());
            drawTexturedQuad(buffer, sprite, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, brightness, facing);
        }
    }

    @Override
    protected void bindTexture(ResourceLocation location) {
        super.bindTexture(location);
    }

    private static void drawTexturedQuad(BufferBuilder renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, int brightness, EnumFacing facing) {
        if (sprite == null) {
            sprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
        }
        int light1 = brightness >> 0x10 & 0xFFFF;
        int light2 = brightness & 0xFFFF;
        int r = 255;
        int g = 255;
        int b = 255;
        int a = 255;
        double minU;
        double maxU;
        double minV;
        double maxV;

        double size = 16F;

        double x2 = x + w;
        double y2 = y + h;
        double z2 = z + d;

        double xt1 = x % 1D;
        double xt2 = xt1 + w;
        while (xt2 > 1D) xt2 -= 1D;
        double yt1 = y % 1D;
        double yt2 = yt1 + h;
        while (yt2 > 1D) yt2 -= 1D;
        double zt1 = z % 1D;
        double zt2 = zt1 + d;
        while (zt2 > 1D) zt2 -= 1D;

        switch (facing) {
            case DOWN:
            case UP:
                minU = sprite.getInterpolatedU(xt1 * size);
                maxU = sprite.getInterpolatedU(xt2 * size);
                minV = sprite.getInterpolatedV(zt1 * size);
                maxV = sprite.getInterpolatedV(zt2 * size);
                break;
            case NORTH:
            case SOUTH:
                minU = sprite.getInterpolatedU(xt2 * size);
                maxU = sprite.getInterpolatedU(xt1 * size);
                minV = sprite.getInterpolatedV(yt1 * size);
                maxV = sprite.getInterpolatedV(yt2 * size);
                break;
            case WEST:
            case EAST:
                minU = sprite.getInterpolatedU(zt2 * size);
                maxU = sprite.getInterpolatedU(zt1 * size);
                minV = sprite.getInterpolatedV(yt1 * size);
                maxV = sprite.getInterpolatedV(yt2 * size);
                break;
            default:
                minU = sprite.getMinU();
                maxU = sprite.getMaxU();
                minV = sprite.getMinV();
                maxV = sprite.getMaxV();
        }

        switch (facing) {
            case DOWN:
                renderer.pos(x, y, z).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y, z).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                break;
            case UP:
                renderer.pos(x, y2, z).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y2, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                break;
            case NORTH:
                renderer.pos(x, y, z).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y2, z).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y, z).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                break;
            case SOUTH:
                renderer.pos(x, y, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                break;
            case WEST:
                renderer.pos(x, y, z).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x, y2, z).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                break;
            case EAST:
                renderer.pos(x2, y, z).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
                renderer.pos(x2, y, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
                break;
        }
    }

}
