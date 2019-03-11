package mod.krevik.kathairis.world.dimension.feature;

import mod.krevik.kathairis.world.dimension.feature.desert.FeatureKatharianCactus;
import mod.krevik.kathairis.world.dimension.feature.desert.FeatureRockMushrooms;
import mod.krevik.kathairis.world.dimension.feature.plainfields.FeaturePlainFields;
import mod.krevik.kathairis.world.dimension.feature.tree.*;
import mod.krevik.kathairis.world.dimension.feature.tree.KatharianTreeFeature;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.*;

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

}