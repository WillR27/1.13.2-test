package mod.krevik.kathairis.entities;

/*
public class EntityKatharianArrow extends EntityTippedArrow
{
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(net.minecraft.entity.projectile.EntityTippedArrow.class, DataSerializers.VARINT);
    private PotionType potion = PotionTypes.EMPTY;
    private final Set<PotionEffect> customPotionEffects = Sets.newHashSet();
    private boolean fixedColor;

    public EntityKatharianArrow(World worldIn)
    {
        super(worldIn);
        setDamage(getDamage()+1.0D);
        setNoGravity(true);
    }

    public EntityKatharianArrow(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }



    public EntityKatharianArrow(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }

    public void setPotionEffect(ItemStack stack)
    {
        if (stack.getItem() == KCore.katharian_arrow)
        {
            this.potion = PotionUtils.getPotionFromItem(stack);
            Collection<PotionEffect> collection = PotionUtils.getFullEffectsFromItem(stack);

            if (!collection.isEmpty())
            {
                for (PotionEffect potioneffect : collection)
                {
                    this.customPotionEffects.add(new PotionEffect(potioneffect));
                }
            }

            int i = getCustomColor(stack);

            if (i == -1)
            {
                this.refreshColor();
            }
            else
            {
                this.setFixedColor(i);
            }
        }
        else if (stack.getItem() == Items.ARROW)
        {
            this.potion = PotionTypes.EMPTY;
            this.customPotionEffects.clear();
            this.getDataManager().set(COLOR, Integer.valueOf(-1));
        }
    }


    public static int getCustomColor(ItemStack p_191508_0_)
    {
        NBTTagCompound nbttagcompound = p_191508_0_.getTagCompound();
        return nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99) ? nbttagcompound.getInteger("CustomPotionColor") : -1;
    }

    private void refreshColor()
    {
        this.fixedColor = false;
        this.getDataManager().set(COLOR, Integer.valueOf(PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects))));
    }

    public void addEffect(PotionEffect effect)
    {
        this.customPotionEffects.add(effect);
        this.getDataManager().set(COLOR, Integer.valueOf(PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects))));
    }

    protected void registerData()
    {
        super.registerData();
        this.getDataManager().register(COLOR, Integer.valueOf(-1));
    }

    /**
     * Called to update the entity's position/logic.
     */
    /*@Override public void tick()
    {
        super.tick();
        motionY+=0.04;
    }

    private void spawnPotionParticles(int particleCount)
    {
        int i = this.getColor();

        if (i != -1 && particleCount > 0)
        {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for (int j = 0; j < particleCount; ++j)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, d0, d1, d2);
            }
        }
    }

    public int getColor()
    {
        return this.getDataManager().get(COLOR).intValue();
    }

    private void setFixedColor(int p_191507_1_)
    {
        this.fixedColor = true;
        this.getDataManager().set(COLOR, Integer.valueOf(p_191507_1_));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    /*public void writeAdditional(NBTTagCompound compound)
    {
        super.writeAdditional(compound);

        if (this.potion != PotionTypes.EMPTY && this.potion != null)
        {
            compound.setString("Potion", PotionType.REGISTRY.getNameForObject(this.potion).toString());
        }

        if (this.fixedColor)
        {
            compound.setInteger("Color", this.getColor());
        }

        if (!this.customPotionEffects.isEmpty())
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (PotionEffect potioneffect : this.customPotionEffects)
            {
                nbttaglist.appendTag(potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }

            compound.setTag("CustomPotionEffects", nbttaglist);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    /*public void readAdditional(NBTTagCompound compound)
    {
        super.readAdditional(compound);

        if (compound.hasKey("Potion", 8))
        {
            this.potion = PotionUtils.getPotionTypeFromNBT(compound);
        }

        for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromTag(compound))
        {
            this.addEffect(potioneffect);
        }

        if (compound.hasKey("Color", 99))
        {
            this.setFixedColor(compound.getInteger("Color"));
        }
        else
        {
            this.refreshColor();
        }
    }

    protected void arrowHit(EntityLivingBase living)
    {
        super.arrowHit(living);

        for (PotionEffect potioneffect : this.potion.getEffects())
        {
            living.addPotionEffect(new PotionEffect(potioneffect.getPotion(), Math.max(potioneffect.getDuration() / 8, 1), potioneffect.getAmplifier(), potioneffect.getIsAmbient(), potioneffect.doesShowParticles()));
        }

        if (!this.customPotionEffects.isEmpty())
        {
            for (PotionEffect potioneffect1 : this.customPotionEffects)
            {
                living.addPotionEffect(potioneffect1);
            }
        }
    }

    protected ItemStack getArrowStack()
    {
        if (this.customPotionEffects.isEmpty() && this.potion == PotionTypes.EMPTY)
        {
            return new ItemStack(KCore.katharian_arrow);
        }
        else
        {
            ItemStack itemstack = new ItemStack(KCore.katharian_arrow);
            PotionUtils.addPotionToItemStack(itemstack, this.potion);
            PotionUtils.appendEffects(itemstack, this.customPotionEffects);

            if (this.fixedColor)
            {
                NBTTagCompound nbttagcompound = itemstack.getTagCompound();

                if (nbttagcompound == null)
                {
                    nbttagcompound = new NBTTagCompound();
                    itemstack.setTagCompound(nbttagcompound);
                }

                nbttagcompound.setInteger("CustomPotionColor", this.getColor());
            }

            return itemstack;
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
   /* @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 0)
        {
            int i = this.getColor();

            if (i != -1)
            {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;

                for (int j = 0; j < 20; ++j)
                {
                    this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, d0, d1, d2);
                }
            }
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
}*/