package mod.krevik.kathairis.entities;

import mod.krevik.kathairis.KItems;
import mod.krevik.kathairis.blocks.BlockKatharianCloud;
import mod.krevik.kathairis.entities.ai.EntityAIPanicNew;
import mod.krevik.kathairis.util.KatharianEntityTypes;
import mod.krevik.kathairis.util.KatharianLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityCloudOister extends EntityAmbientCreature
{
    private static final DataParameter<Integer> timeUntilNextPearl = EntityDataManager.createKey(EntityCloudOister.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> panic = EntityDataManager.createKey(EntityCloudOister.class, DataSerializers.BOOLEAN);
    
    public int timeUntilNextPearl() {
    	return this.getDataManager().get(timeUntilNextPearl).intValue();
    }
    public void setTimeUntilNextPearl(int x) {
    	this.getDataManager().set(timeUntilNextPearl, Integer.valueOf(x));
    }
    
    public boolean panic() {
    	return this.getDataManager().get(panic).booleanValue();
    }
    
    public void setPanic(boolean trueorfalse) {
    	this.getDataManager().set(panic, Boolean.valueOf(trueorfalse));
    }

    public EntityCloudOister(World worldIn)
    {
        super(KatharianEntityTypes.CLOUD_OISTER,worldIn);
        this.setSize(0.6F, 0.6F);
        this.experienceValue=5;
        this.setTimeUntilNextPearl(this.rand.nextInt(6000) + 6000);
        this.setPanic(false);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(1, new EntityAIPanicNew(this,1D));

    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.getDataManager().register(timeUntilNextPearl, 24000);
        this.getDataManager().register(panic, Boolean.valueOf(false));
    }

    int jumpTimer=0;
    int k = 50+rand.nextInt(300);
    int panicTimer=0;
    
    @Override
    public void tick() {
    	super.tick();
        this.setTimeUntilNextPearl(this.timeUntilNextPearl()-1);
        if (!this.world.isRemote && this.timeUntilNextPearl() <= 0)
        {
            this.entityDropItem(KItems.cloud_Pearl, 1);
            this.setTimeUntilNextPearl(this.rand.nextInt(6000) + 6000);
        }
        this.fallDistance=0;
        if(this.getRevengeTarget()!=null) {
            this.setPanic(true);
        }

        if(!panic()) {
            jumpTimer++;
            if(jumpTimer>k) {
                k=50+rand.nextInt(300);
                jumpTimer=0;
                motionY+=0.5;
                //IMessage message = new PacketCloudOisterClient((float)posX,(float)posY,(float)posZ);
                //KathairisPacketHandler.CHANNEL.sendToAll(message);
            }
            if(!this.onGround) {
                if(this.getRNG().nextInt(100)==0) {
                    double destPosX=this.posX-this.getRNG().nextInt(6)+this.getRNG().nextInt(6);
                    double destPosZ=this.posZ-this.getRNG().nextInt(6)+this.getRNG().nextInt(6);
                    this.getNavigator().setPath(this.getNavigator().getPathToPos(new BlockPos(destPosX,posY,destPosZ)), 1);
                    motionX=(destPosX-this.posX)*0.15;
                    motionZ=(destPosZ-this.posZ)*0.15;
                }
            }
        }else {
            panicTimer++;
            if(panicTimer>200+rand.nextInt(100)) {
                this.setPanic(false);
                panicTimer=0;
                this.setRevengeTarget(null);
            }

        }
    }

    /*public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEFINED;
    }*/

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return KatharianLootTables.LOOT_CLOUDOISTER;
    }

    /*public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);
    	this.motionY=0.5;
		IMessage message = new PacketCloudOisterClient((int)posX,(int)posY,(int)posZ);
		KathairisPacketHandler.CHANNEL.sendToAll(message);
        return flag;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
		IMessage message = new PacketCloudOisterClient((int)posX,(int)posY,(int)posZ);
		KathairisPacketHandler.CHANNEL.sendToAll(message);
    	return super.attackEntityFrom(source, amount);
    }*/

    @Override
    public void writeAdditional(NBTTagCompound compound)
    {
        super.writeAdditional(compound);
        compound.putInt("timeUntilNextPearl", this.timeUntilNextPearl());
        compound.putBoolean("panic", panic());
    }

    @Override
    public void readAdditional(NBTTagCompound compound)
    {
        super.readAdditional(compound);
        this.setTimeUntilNextPearl(compound.getInt("timeUntilNextPearl"));
        this.setPanic(compound.getBoolean("panic"));
    }

    @Override
    public boolean canSpawn(IWorld p_205020_1_, boolean p_205020_2_) {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
        Block block = this.world.getBlockState(blockpos.down()).getBlock();
        return block instanceof BlockKatharianCloud;
    }
    
}