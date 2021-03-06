package mod.krevik.kathairis.blocks.plants;

import mod.krevik.kathairis.KBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockKatharianMiniGrass extends BlockKatharianPlant {

    protected static final AxisAlignedBB MYSTICMINIGRASS_AABB = new AxisAlignedBB(0, 0.0D, 0, 1D, 0.4D, 1D);

    public BlockKatharianMiniGrass(String Name) {
        super(Name);
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return null;
    }

    @Override
    public boolean isReplaceable(IBlockState p_196253_1_, BlockItemUseContext p_196253_2_) {
        return true;
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.create(MYSTICMINIGRASS_AABB);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
                spawnAsEntity(worldIn, pos, new ItemStack(KBlocks.KATHARIAN_MINI_GRASS, 1));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }
}
