package enumeration;

import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author Viacheslav Oleshko.
 */
public enum ForecastHour implements StringRepresentable {
    HOUR_0    ("000"),
    HOUR_1    ("001"),
    HOUR_2    ("002"),
    HOUR_3    ("003"),
    HOUR_4    ("004"),
    HOUR_5    ("005"),
    HOUR_6    ("006"),
    HOUR_7    ("007"),
    HOUR_8    ("008"),
    HOUR_9    ("009"),
    HOUR_10   ("010"),
    HOUR_11   ("011"),
    HOUR_12   ("012"),
    HOUR_13   ("013"),
    HOUR_14   ("014"),
    HOUR_15   ("015"),
    HOUR_16   ("016"),
    HOUR_17   ("017"),
    HOUR_18   ("018"),
    HOUR_19   ("019"),
    HOUR_20   ("020"),
    HOUR_21   ("021"),
    HOUR_22   ("022"),
    HOUR_23   ("023"),
    HOUR_24   ("024"),
    HOUR_25   ("025"),
    HOUR_26   ("026"),
    HOUR_27   ("027"),
    HOUR_28   ("028"),
    HOUR_29   ("029"),
    HOUR_30   ("030"),
    HOUR_31   ("031"),
    HOUR_32   ("032"),
    HOUR_33   ("033"),
    HOUR_34   ("034"),
    HOUR_35   ("035"),
    HOUR_36   ("036"),
    HOUR_37   ("037"),
    HOUR_38   ("038"),
    HOUR_39   ("039"),
    HOUR_40   ("040"),
    HOUR_41   ("041"),
    HOUR_42   ("042"),
    HOUR_43   ("043"),
    HOUR_44   ("044"),
    HOUR_45   ("045"),
    HOUR_46   ("046"),
    HOUR_47   ("047"),
    HOUR_48   ("048"),
    HOUR_49   ("049"),
    HOUR_50   ("050"),
    HOUR_51   ("051"),
    HOUR_52   ("052"),
    HOUR_53   ("053"),
    HOUR_54   ("054"),
    HOUR_55   ("055"),
    HOUR_56   ("056"),
    HOUR_57   ("057"),
    HOUR_58   ("058"),
    HOUR_59   ("059"),
    HOUR_60   ("060"),
    HOUR_61   ("061"),
    HOUR_62   ("062"),
    HOUR_63   ("063"),
    HOUR_64   ("064"),
    HOUR_65   ("065"),
    HOUR_66   ("066"),
    HOUR_67   ("067"),
    HOUR_68   ("068"),
    HOUR_69   ("069"),
    HOUR_70   ("070"),
    HOUR_71   ("071"),
    HOUR_72   ("072"),
    HOUR_73   ("073"),
    HOUR_74   ("074"),
    HOUR_75   ("075"),
    HOUR_76   ("076"),
    HOUR_77   ("077"),
    HOUR_78   ("078"),
    HOUR_79   ("079"),
    HOUR_80   ("080"),
    HOUR_81   ("081"),
    HOUR_82   ("082"),
    HOUR_83   ("083"),
    HOUR_84   ("084"),
    HOUR_85   ("085"),
    HOUR_86   ("086"),
    HOUR_87   ("087"),
    HOUR_88   ("088"),
    HOUR_89   ("089"),
    HOUR_90   ("090"),
    HOUR_91   ("091"),
    HOUR_92   ("092"),
    HOUR_93   ("093"),
    HOUR_94   ("094"),
    HOUR_95   ("095"),
    HOUR_96   ("096"),
    HOUR_97   ("097"),
    HOUR_98   ("098"),
    HOUR_99   ("099"),
    HOUR_100  ("100"),
    HOUR_101  ("101"),
    HOUR_102  ("102"),
    HOUR_103  ("103"),
    HOUR_104  ("104"),
    HOUR_105  ("105"),
    HOUR_106  ("106"),
    HOUR_107  ("107"),
    HOUR_108  ("108"),
    HOUR_109  ("109"),
    HOUR_110  ("110"),
    HOUR_111  ("111"),
    HOUR_112  ("112"),
    HOUR_113  ("113"),
    HOUR_114  ("114"),
    HOUR_115  ("115"),
    HOUR_116  ("116"),
    HOUR_117  ("117"),
    HOUR_118  ("118"),
    HOUR_119  ("119"),
    HOUR_120  ("120"),
    HOUR_123  ("123"),
    HOUR_126  ("126"),
    HOUR_129  ("129"),
    HOUR_132  ("132"),
    HOUR_135  ("135"),
    HOUR_138  ("138"),
    HOUR_141  ("141"),
    HOUR_144  ("144"),
    HOUR_147  ("147"),
    HOUR_150  ("150"),
    HOUR_153  ("153"),
    HOUR_156  ("156"),
    HOUR_159  ("159"),
    HOUR_162  ("162"),
    HOUR_165  ("165"),
    HOUR_168  ("168"),
    HOUR_171  ("171"),
    HOUR_174  ("174"),
    HOUR_177  ("177"),
    HOUR_180  ("180"),
    HOUR_183  ("183"),
    HOUR_186  ("186"),
    HOUR_189  ("189"),
    HOUR_192  ("192"),
    HOUR_195  ("195"),
    HOUR_198  ("198"),
    HOUR_201  ("201"),
    HOUR_204  ("204"),
    HOUR_207  ("207"),
    HOUR_210  ("210"),
    HOUR_213  ("213"),
    HOUR_216  ("216"),
    HOUR_219  ("219"),
    HOUR_222  ("222"),
    HOUR_225  ("225"),
    HOUR_228  ("228"),
    HOUR_231  ("231"),
    HOUR_234  ("234"),
    HOUR_237  ("237"),
    HOUR_240  ("240"),
    HOUR_252  ("252"),
    HOUR_264  ("264"),
    HOUR_276  ("276"),
    HOUR_288  ("288"),
    HOUR_300  ("300"),
    HOUR_312  ("312"),
    HOUR_324  ("324"),
    HOUR_336  ("336"),
    HOUR_348  ("348"),
    HOUR_360  ("360"),
    HOUR_372  ("372"),
    HOUR_384  ("384");

    private String forecastHour;
    private static HashMap<String, ForecastHour> forecastHoursMap = new HashMap<>();

    static {
        for (ForecastHour forecastHour : ForecastHour.values()) {
            forecastHoursMap.put(forecastHour.forecastHour, forecastHour);
        }
    }

    ForecastHour(String forecastHour) {
        this.forecastHour = forecastHour;
    }

    @Override
    public String asString() {
        return this.forecastHour;
    }

    public static List<ForecastHour> getRange(ForecastHour startHour, ForecastHour endHour) {
        int startIdx = startHour.ordinal();
        int endIdx = endHour.ordinal() + 1;
        return IntStream.range(startIdx, endIdx).mapToObj(idx -> ForecastHour.values()[idx]).collect(toList());
    }

    public static ForecastHour fromString(String str) {
        return forecastHoursMap.get(str);
    }
}
