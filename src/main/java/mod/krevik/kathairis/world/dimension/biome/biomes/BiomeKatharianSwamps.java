package mod.krevik.kathairis.world.dimension.biome.biomes;


import mod.krevik.kathairis.KBlocks;
import mod.krevik.kathairis.world.dimension.feature.KatharianFeatureList;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.awt.*;

public class BiomeKatharianSwamps extends BiomeKatharianBiomeBase {

    public static final SurfaceBuilderConfig GRASS_WATER_SURFACE = new SurfaceBuilderConfig(KBlocks.KATHARIAN_GRASS.getDefaultState(), KBlocks.KATHARIAN_DIRT.getDefaultState(), Blocks.WATER.getDefaultState());


    public BiomeKatharianSwamps() {
        super((new Biome.BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder(KatharianFeatureList.KATHARIAN_SWAMP_SURFACE_BUILDER, GRASS_WATER_SURFACE)).precipitation(RainType.RAIN).category(Category.SWAMP).depth(-0.1F).scale(0.0000005F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).parent((String)null));
        //this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, createCompositeFeature(KatharianFeatureList.SWAMP_FLATENER, IFeatureConfig.NO_FEATURE_CONFIG, COUNT_RANGE, new CountRangeConfig(6, 0, 0, 128)));
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(KBlocks.MUD_BLOCK), LAVA_LAKE, new LakeChanceConfig(25)));
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.CLAY), LAVA_LAKE, new LakeChanceConfig(25)));
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.GRAVEL), LAVA_LAKE, new LakeChanceConfig(25)));
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, createCompositeFeature(Feature.LAKES, new LakesConfig(Blocks.WATER), LAVA_LAKE, new LakeChanceConfig(25)));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.RANDOM_FEATURE_LIST, new RandomDefaultFeatureListConfig(new Feature[]{KatharianFeatureList.KATHARIAN_SWAMP_TALL_TREE_1}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.2F, 0.1F}, KatharianFeatureList.KATHARIAN_SWAMP_TALL_TREE_1, IFeatureConfig.NO_FEATURE_CONFIG), AT_SURFACE_WITH_EXTRA, new AtSurfaceWithExtraConfig(3, 0.1F, 1)));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.RANDOM_FEATURE_LIST, new RandomDefaultFeatureListConfig(new Feature[]{KatharianFeatureList.BASIC_SWAMP_FEATURE}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.2F, 0.1F}, KatharianFeatureList.BASIC_SWAMP_FEATURE, IFeatureConfig.NO_FEATURE_CONFIG), AT_SURFACE_WITH_EXTRA, new AtSurfaceWithExtraConfig(2, 0.5F, 1)));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, createCompositeFeature(Feature.BUSH, new BushConfig(KBlocks.KATHARIAN_FUNGI), TWICE_SURFACE_WITH_CHANCE, new ChanceConfig(2)));
        this.baseGrassColor=new Color(Color.YELLOW.getRed(),Color.YELLOW.getGreen(),Color.YELLOW.getBlue());
    }
}
