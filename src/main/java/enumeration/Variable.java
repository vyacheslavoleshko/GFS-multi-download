package enumeration;

/**
 * @author Viacheslav Oleshko
 */
public enum Variable implements StringRepresentable{
    ALL    ("all_var"),
    LFTX4  ("var_4LFTX"),
    WAVH5  ("var_5WAVH"),
    ABSV   ("var_ABSV"),
    ACPCP  ("var_ACPCP"),
    ALBDO  ("var_ALBDO"),
    APCP   ("var_APCP"),
    CAPE   ("var_CAPE"),
    CFRZR  ("var_CFRZR"),
    CICEP  ("var_CICEP"),
    CIN    ("var_CIN"),
    CLWMR  ("var_CLWMR"),
    CPOFP  ("var_CPOFP"),
    CPRAT  ("var_CPRAT"),
    CRAIN  ("var_CRAIN"),
    CSNOW  ("var_CSNOW"),
    CWAT   ("var_CWAT"),
    CWORK  ("var_CWORK"),
    DLWRF  ("var_DLWRF"),
    DPT    ("var_DPT"),
    DSWRF  ("var_DSWRF"),
    FLDCP  ("var_FLDCP"),
    GFLUX  ("var_GFLUX"),
    GUST   ("var_GUST"),
    HGT    ("var_HGT"),
    HINDEX ("var_HINDEX"),
    HLCY   ("var_HLCY"),
    HPBL   ("var_HPBL"),
    ICAHT  ("var_ICAHT"),
    ICEC   ("var_ICEC"),
    ICSEV  ("var_ICSEV"),
    LAND   ("var_LAND"),
    LANDN  ("var_LANDN"),
    LFTX   ("var_LFTX"),
    LHTFL  ("var_LHTFL"),
    MSLET  ("var_MSLET"),
    O3MR   ("var_O3MR"),
    PEVPR  ("var_PEVPR"),
    PLPL   ("var_PLPL"),
    POT    ("var_POT"),
    PRATE  ("var_PRATE"),
    PRES   ("var_PRES"),
    PRMSL  ("var_PRMSL"),
    PWAT   ("var_PWAT"),
    RH     ("var_RH"),
    SHTFL  ("var_SHTFL"),
    SNOD   ("var_SNOD"),
    SOILW  ("var_SOILW"),
    SPFH   ("var_SPFH"),
    SUNSD  ("var_SUNSD"),
    TCDC   ("var_TCDC"),
    TMAX   ("var_TMAX"),
    TMIN   ("var_TMIN"),
    TMP    ("var_TMP"),
    TOZNE  ("var_TOZNE"),
    TSOIL  ("var_TSOIL"),
    UFLX   ("var_UFLX"),
    UGRD   ("var_UGRD"),
    U_GWD  ("var_U-GWD"),
    ULWRF  ("var_ULWRF"),
    USTM   ("var_USTM"),
    USWRF  ("var_USWRF"),
    VFLX   ("var_VFLX"),
    VGRD   ("var_VGRD"),
    VIS    ("var_VIS"),
    V_GWD  ("var_V-GWD"),
    VRATE  ("var_VRATE"),
    VSTM   ("var_VSTM"),
    VVEL   ("var_VVEL"),
    VWSH   ("var_VWSH"),
    WATR   ("var_WATR"),
    WEASD  ("var_WEASD"),
    WILT   ("var_WILT");

    private String variableName;

    Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String asString() {
        return this.variableName;
    }
}
