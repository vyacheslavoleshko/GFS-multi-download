package enumeration;

/**
 * @author Viacheslav Oleshko.
 */
public enum Level implements StringRepresentable{
    ALL                                                      ("all_lev"),
    LEV_0_0P1_M_BELOW_GROUND                                 ("lev_0-0.1_m_below_ground"),
    LEV_0P1_TO_0P4_M_BELOW_GROUND                            ("lev_0.1-0.4_m_below_ground"),
    LEV_0P33_TO_1_SIGMA_LAYER                                ("lev_0.33-1_sigma_layer"),
    LEV_0P4_TO_1_M_BELOW_GROUND                              ("lev_0.4-1_m_below_ground"),
    LEV_0P44_TO_0P72_SIGMA_LAYER                             ("lev_0.44-0.72_sigma_layer"),
    LEV_0P44_TO_1_SIGMA_LAYER                                ("lev_0.44-1_sigma_layer"),
    LEV_0P72_TO_0P94_SIGMA_LAYER                             ("lev_0.72-0.94_sigma_layer"),
    LEV_0P995_SIGMA_LEVEL                                    ("lev_0.995_sigma_level"),
    LEV_0c_ISOTHERM                                          ("lev_0C_isotherm"),
    LEV_1_MB                                                 ("lev_1_mb"),
    LEV_1000_MB                                              ("lev_1000_mb"),
    LEV_100_M_ABOVE_GROUND                                   ("lev_100_m_above_ground"),
    LEV_100_MB                                               ("lev_100_mb"),
    LEV_10_M_ABOVE_GROUND                                    ("lev_10_m_above_ground"),
    LEV_10_MB                                                ("lev_10_mb"),
    LEV_1_TO_2_M_BELOW_GROUND                                ("lev_1-2_m_below_ground"),
    LEV_150_MB                                               ("lev_150_mb"),
    LEV_180_TO_0_MB_ABOVE_GROUND                             ("lev_180-0_mb_above_ground"),
    LEV_1829_M_ABOVE_MEAN_SEA_LEVEL                          ("lev_1829_m_above_mean_sea_level"),
    LEV_2_MB                                                 ("lev_2_mb"),
    LEV_200_MB                                               ("lev_200_mb"),
    LEV_20_MB                                                ("lev_20_mb"),
    LEV_250_MB                                               ("lev_250_mb"),
    LEV_255_TO_0_MB_ABOVE_GROUND                             ("lev_255-0_mb_above_ground"),
    LEV_2743_M_ABOVE_MEAN_SEA_LEVEL                          ("lev_2743_m_above_mean_sea_level"),
    LEV_2_M_ABOVE_GROUND                                     ("lev_2_m_above_ground"),
    LEV_3000_TO_0_M_ABOVE_GROUND                             ("lev_3000-0_m_above_ground"),
    LEV_3_MB                                                 ("lev_3_mb"),
    LEV_300_MB                                               ("lev_300_mb"),
    LEV_30_TO_0_MB_ABOVE_GROUND                              ("lev_30-0_mb_above_ground"),
    LEV_30_MB                                                ("lev_30_mb"),
    LEV_350_MB                                               ("lev_350_mb"),
    LEV_3658_M_ABOVE_MEAN_SEA_LEVEL                          ("lev_3658_m_above_mean_sea_level"),
    LEV_400_MB                                               ("lev_400_mb"),
    LEV_450_MB                                               ("lev_450_mb"),
    LEV_5_MB                                                 ("lev_5_mb"),
    LEV_500_MB                                               ("lev_500_mb"),
    LEV_50_MB                                                ("lev_50_mb"),
    LEV_550_MB                                               ("lev_550_mb"),
    LEV_6000_TO_0_M_ABOVE_GROUND                             ("lev_6000-0_m_above_ground"),
    LEV_600_MB                                               ("lev_600_mb"),
    LEV_650_MB                                               ("lev_650_mb"),
    LEV_7_MB                                                 ("lev_7_mb"),
    LEV_700_MB                                               ("lev_700_mb"),
    LEV_70_MB                                                ("lev_70_mb"),
    LEV_750_MB                                               ("lev_750_mb"),
    LEV_800_MB                                               ("lev_800_mb"),
    LEV_80_M_ABOVE_GROUND                                    ("lev_80_m_above_ground"),
    LEV_850_MB                                               ("lev_850_mb"),
    LEV_900_MB                                               ("lev_900_mb"),
    LEV_925_MB                                               ("lev_925_mb"),
    LEV_950_MB                                               ("lev_950_mb"),
    LEV_975_MB                                               ("lev_975_mb"),
    LEV_BOUNDARY_LAYER_CLOUD_LAYER                           ("lev_boundary_layer_cloud_layer"),
    LEV_CONVECTIVE_CLOUD_BOTTOM_LEVEL                        ("lev_convective_cloud_bottom_level"),
    LEV_CONVECTIVE_CLOUD_LAYER                               ("lev_convective_cloud_layer"),
    LEV_CONVECTIVE_CLOUD_TOP_LEVEL                           ("lev_convective_cloud_top_level"),
    LEV_ENTIRE_ATMOSPHERE                                    ("lev_entire_atmosphere"),
    LEV_ENTIRE_ATMOSPHERE_CONSIDERED_AS_A_SINGLE_LAYER       ("lev_entire_atmosphere_%5C%28considered_as_a_single_layer%5C%29"),
    LEV_HIGH_CLOUD_BOTTOM_LEVEL                              ("lev_high_cloud_bottom_level"),
    LEV_HIGH_CLOUD_LAYER                                     ("lev_high_cloud_layer"),
    LEV_HIGH_CLOUD_TOP_LEVEL                                 ("lev_high_cloud_top_level"),
    LEV_HIGHEST_TROPOSPHERIC_FREEZING_LEVEL                  ("lev_highest_tropospheric_freezing_level"),
    LEV_LOW_CLOUD_BOTTOM_LEVEL                               ("lev_low_cloud_bottom_level"),
    LEV_LOW_CLOUD_LAYER                                      ("lev_low_cloud_layer"),
    LEV_LOW_CLOUD_TOP_LEVEL                                  ("lev_low_cloud_top_level"),
    LEV_MAX_WIND                                             ("lev_max_wind"),
    LEV_MEAN_SEA_LEVEL                                       ("lev_mean_sea_level"),
    LEV_MIDDLE_CLOUD_BOTTOM_LEVEL                            ("lev_middle_cloud_bottom_level"),
    LEV_MIDDLE_CLOUD_LAYER                                   ("lev_middle_cloud_layer"),
    LEV_MIDDLE_CLOUD_TOP_LEVEL                               ("lev_middle_cloud_top_level"),
    LEV_PLANETARY_BOUNDARY_LAYER                             ("lev_planetary_boundary_layer"),
    LEV_pv_MINUS2E_06_kM_2_KG_S_SURFACE                      ("lev_PV%3D-2e-06_%5C%28Km%5C%5E2%2Fkg%2Fs%5C%29_surface"),
    LEV_pv_2E_06_kM_2_KG_S_SURFACE                           ("lev_PV%3D2e-06_%5C%28Km%5C%5E2%2Fkg%2Fs%5C%29_surface"),
    LEV_SURFACE                                              ("lev_surface"),
    LEV_TOP_OF_ATMOSPHERE                                    ("lev_top_of_atmosphere"),
    LEV_TROPOPAUSE                                           ("lev_tropopause");

    private String level;

    Level(String level) {
        this.level = level;
    }


    @Override
    public String asString() {
        return this.level;
    }
}
