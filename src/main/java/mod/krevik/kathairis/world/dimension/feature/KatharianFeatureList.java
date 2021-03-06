package mod.krevik.kathairis.world.dimension.feature;

import mod.krevik.kathairis.world.dimension.feature.desert.*;
import mod.krevik.kathairis.world.dimension.feature.forest.FeatureForestCandleBush;
import mod.krevik.kathairis.world.dimension.feature.forest.FeatureSteppedSucculent;
import mod.krevik.kathairis.world.dimension.feature.plainfields.FeaturePlainFields;
import mod.krevik.kathairis.world.dimension.feature.swamp.FeatureBasicSwamp;
import mod.krevik.kathairis.world.dimension.feature.tree.*;
import mod.krevik.kathairis.world.dimension.surface_builders.KatharianSwampSurfaceBuilder;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class KatharianFeatureList {

    public static final WorldCarver<ProbabilityConfig> KATHARIAN_CAVE_WORLD_CARVER = new KatharianWorldCaveCarver();

    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE = new KatharianTreeFeature(true);
    public static final AbstractTreeFeature<NoFeatureConfig> BASIC_STANDARD_TREE = new FeatureBasicKatharianTree(true, false);
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE_1 = new FeatureKatharianTallTree1();
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE_2 = new FeatureKatharianTallTree2();
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_TREE_3 = new FeatureKatharianTallTree3();
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_HUGE_TREE_1 = new FeatureKatharianTreeHuge1();
    public static final Feature<NoFeatureConfig> KATHARIAN_CLOUD = new FeatureKatharianCloud();
    public static final Feature<NoFeatureConfig> KATHARIAN_CACTUS = new FeatureKatharianCactus();
    public static final Feature<NoFeatureConfig> KATHARIAN_DEAD_BUSH = new FeatureKatharianDeadBush();
    public static final Feature<NoFeatureConfig> KATHARIAN_ROCK_MUSHROOM = new FeatureRockMushrooms();
    public static final Feature<NoFeatureConfig> KATHARIAN_PLAIN_FIELDS = new FeaturePlainFields();
    public static final Feature<NoFeatureConfig> KATHARIAN_CLOUD_MINI_ISLAND = new FeatureKatharianFloatingMiniIsland();
    public static final Feature<NoFeatureConfig> KATHARIAN_CRYSTAL_CHAMBER = new FeatureCrystalChamber();
    public static final Feature<NoFeatureConfig> KATHARIAN_ROCKTUS = new FeatureKatharianRocktus();
    public static final Feature<NoFeatureConfig> SAND_LAYERS = new FeatureDesertSandLayers();
    public static final Feature<NoFeatureConfig> STEPPED_SUCCULENT = new FeatureSteppedSucculent();
    public static final AbstractTreeFeature<NoFeatureConfig> KATHARIAN_SWAMP_TALL_TREE_1 = new FeatureKatharianSwampTallTree1();
    public static final Feature<NoFeatureConfig> BASIC_SWAMP_FEATURE = new FeatureBasicSwamp();
    public static final Feature<NoFeatureConfig> PYRAMID = new FeaturePyramid();
    public static final Feature<NoFeatureConfig> SMALL_ROCK = new FeatureDesertSmallRocks();
    public static final Feature<NoFeatureConfig> KATHARIAN_FOREST_BUSH = new FeatureKatharianForestBush();
    public static final Feature<BushConfig> KATHARIAN_FOREST_CANDLE_BUSH = new FeatureForestCandleBush();

    public static final ISurfaceBuilder<SurfaceBuilderConfig> KATHARIAN_SWAMP_SURFACE_BUILDER = new KatharianSwampSurfaceBuilder();
    public static final ISurfaceBuilder<SurfaceBuilderConfig> KATHARIAN_DESERT_EDGE_SURFACE_BUILDER = new KatharianDesertEdgeSurfaceBuilder();
    public static final ISurfaceBuilder<SurfaceBuilderConfig> KATHARIAN_SOFT_SAND_LAKES_SURFACE_BUILDER = new KatharianSoftSandLakesSurfaceBuilder();

}
