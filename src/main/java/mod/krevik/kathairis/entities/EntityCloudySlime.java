package mod.krevik.kathairis.entities;

import com.google.common.collect.Sets;
import mod.krevik.kathairis.KItems;
import mod.krevik.kathairis.entities.ai.EntityAIAttackTarget;
import mod.krevik.kathairis.util.KatharianLootTables;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;
public class EntityCloudySlime extends EntityTameable implements EntityFlying
{
    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityCloudySlime.class, DataSerializers.VARINT);
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(KItems.cloud_Essence);

    public EntityCloudySlime(World worldIn)
    {
        super(worldIn);
        this.setSize(1.4F, 1.4F);
        this.moveHelper = new EntityFlyHelper(this);
        this.setTamed(false);
    }

    protected void initEntityAI()
    {
    	super.initEntityAI();
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F));
        this.tasks.addTask(4, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollow(this, 1.0D, 3.0F, 7.0F));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(1, new EntityAIAttackTarget<>(this, this.getAttackTarget()));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));

    }

    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6000000059604645D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000000298023224D);
    }

    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    public float getEyeHeight()
    {
        return this.height * 0.6F;
    }

    @Override
    public void tick() {
        super.tick();
        EntityLivingBase target = this.getAttackTarget();
        if(target!=null&&!this.isSitting()) {
            Vec3d vec3d = target.getEyePosition(1.0F);
            EntityCloudySlime.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
        }
        if(!this.isTamed()) {
            this.setAttackTarget(this.getRevengeTarget());
        }
        if(this.getOwner()!=null) {
            if(this.getAttackTarget() instanceof EntityTameable) {
                EntityTameable et = (EntityTameable) this.getAttackTarget();
                if(this.getOwner()==et.getOwner()) {
                    this.setAttackTarget(null);
                }
            }
        }

    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem()))
        {
        	if(this.world.getDayTime()>0&&this.world.getDayTime()<1000) {
	            if (!player.isCreative())
	            {
	                itemstack.shrink(1);
	            }

	            if (!this.world.isRemote)
	            {
	                if (this.rand.nextInt(8) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
	                {
	                    this.setTamedBy(player);
	                    this.playTameEffect(true);
	                    this.world.setEntityState(this, (byte)7);
	                }
	                else
	                {
	                    this.playTameEffect(false);
	                    this.world.setEntityState(this, (byte)6);
	                }
	            }
        	}
            return true;
        	
        }
        else
        {
            if (!this.world.isRemote && this.isTamed() && this.isOwner(player))
            {
                if(itemstack.getItem().equals(KItems.cloud_Essence)){
                    if(this.getHealth()<this.getMaxHealth()){
                        this.heal(3.0f);
                        this.playTameEffect(true);
                        if (!player.isCreative())
                        {
                            itemstack.shrink(1);
                        }
                    }
                }else {
                    this.aiSit.setSitting(!this.isSitting());
                }
            }

            return super.processInteract(player, hand);
        }
    }


    public boolean isBreedingItem(ItemStack stack)
    {
        return false;
    }

    public void fall(float distance, float damageMultiplier)
    {
    	
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    	
    }


    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        return false;
    }

    @Nullable
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    @Nullable
    public SoundEvent getAmbientSound()
    {
        return getAmbientSound(this.rand);
    }

    private static SoundEvent getAmbientSound(Random random)
    {
    	return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return null;
    }

    protected SoundEvent getDeathSound()
    {
        return null;
    }

    protected float getSoundPitch()
    {
        return getPitch(this.rand);
    }

    private static float getPitch(Random random)
    {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    public SoundCategory getSoundCategory()
    {
        return SoundCategory.NEUTRAL;
    }

    public boolean canBePushed()
    {
        return true;
    }

    protected void collideWithEntity(Entity entityIn)
    {
        if (!(entityIn instanceof EntityPlayer))
        {
            super.collideWithEntity(entityIn);
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isInvulnerableTo(source))
        {
            return false;
        }
        else
        {
            if (this.aiSit != null)
            {
                this.aiSit.setSitting(false);
            }
            if(source==DamageSource.IN_WALL){
                if(this.getOwner()!=null&&getOwner().isAlive()){
                    setPosition(getOwner().posX,getOwner().posY,getOwner().posZ);
                }else{
                    BlockPos tmp = new BlockPos(posX,world.getHeight(Heightmap.Type.MOTION_BLOCKING,getPosition()).getY()+1,posZ);
                    setPosition(tmp.getX(),tmp.getY(),tmp.getZ());
                }
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    public int getVariant()
    {
        return MathHelper.clamp(this.getDataManager().get(VARIANT).intValue(), 0, 4);
    }

    public void setVariant(int p_191997_1_)
    {
        this.getDataManager().set(VARIANT, Integer.valueOf(p_191997_1_));
    }

    protected void registerData()
    {
        super.registerData();
        this.getDataManager().register(VARIANT, Integer.valueOf(0));
    }

    public void writeAdditional(NBTTagCompound compound)
    {
        super.writeAdditional(compound);
        compound.setInt("Variant", this.getVariant());
    }

    public void readAdditional(NBTTagCompound compound)
    {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return KatharianLootTables.LOOT_CLOUDYSLIME;
    }

    public boolean isFlying()
    {
        return !this.onGround;
    }
}