package mod.krevik.kathairis.client.renderers;

import mod.krevik.kathairis.Ref;
import mod.krevik.kathairis.client.models.ModelLivingFlower;
import mod.krevik.kathairis.entities.EntityLivingFlower;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class RenderLivingFlower extends RenderLiving<EntityLivingFlower>
{
    public static final Factory FACTORY = new Factory();

    public RenderLivingFlower(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelLivingFlower(), 0F);
    }


    @Override
    protected ResourceLocation getEntityTexture(EntityLivingFlower entity)
    {
        return Ref.LivingFlowerLoc;
    }
    
    public static class Factory implements IRenderFactory<EntityLivingFlower> {

        @Override
        public Render<? super EntityLivingFlower> createRenderFor(RenderManager manager) {
            return new RenderLivingFlower(manager);
        }

    }


    @Override
    protected void preRenderCallback(EntityLivingFlower entity, float partialTickTime)
    {
        super.preRenderCallback(entity,partialTickTime);
        /*if(entity.getAnimationTime()>0){
            GlStateManager.pushMatrix();
            drawCircle((float)entity.posX,(float)entity.posZ,entity.getAnimationTime()/100,entity);
            GlStateManager.popMatrix();
        }*/
    }

    private void drawCircle(float x, float y, float radius,EntityLivingFlower entity)
    {
        GlStateManager.disableLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int i;
        int triangleAmount = 1000;
        float twicePi = (float)(2.0f * Math.PI);

        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        for(i = 0; i <= triangleAmount; i++)
        {
            bufferbuilder.pos(x,entity.posY+0.3f,y).color(0.0f, 0.8f, 0.0f, 0.6f).endVertex();
            bufferbuilder.pos(x+(radius * Math.cos(i*twicePi/triangleAmount)),entity.posY+0.3f,y+(radius*Math.sin(i*twicePi/triangleAmount))).color(0.0f, 0.8f, 0.0f, 0.6f).endVertex();
        }
        GlStateManager.enableLighting();
        tessellator.draw();
    }

    @Override
    protected void applyRotations(EntityLivingFlower entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }


}

