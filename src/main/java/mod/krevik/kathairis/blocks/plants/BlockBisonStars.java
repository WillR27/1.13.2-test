package mod.krevik.kathairis.blocks.plants;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockBisonStars extends BlockKatharianPlant {
    protected static final AxisAlignedBB BISON_STARS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1D, 0.125D, 1D);

    public BlockBisonStars(String Name) {
        super(Name);
    }

    @Override
    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.create(BISON_STARS_AABB);
    }

}
