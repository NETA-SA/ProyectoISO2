package es.uclm.library.business.entity;

public enum CodigoPostal {
    CP_45600("Talavera"),
    CP_12345("Madrid"),
    CP_67890("Barcelona"),
    CP_02001("ALBACETE"),
    CP_02002("ALBACETE"),
    CP_02003("ALBACETE"),
    CP_02004("ALBACETE"),
    CP_2005("ALBACETE"),
    CP_02006("ALBACETE"),
    CP_02007("ALBACETE"),
    CP_02008("ALBACETE"),
    CP_02070("ALBACETE"),
    CP_02071("ALBACETE"),
    CP_02080("ALBACETE"),
    CP_02090("ALBACETE"),
    CP_02091("ALBACETE"),
    CP_02092("ALBACETE"),
    CP_02099("ALBACETE"),
    CP_13001("CIUDAD REAL"),
    CP_13002("CIUDAD REAL"),
    CP_13003("CIUDAD REAL"),
    CP_13004("CIUDAD REAL"),
    CP_13005("CIUDAD REAL"),
    CP_13070("CIUDAD REAL"),
    CP_13071("CIUDAD REAL"),
    CP_13080("CIUDAD REAL"),
    CP_13090("CIUDAD REAL"),
    CP_13091("CIUDAD REAL"),
    CP_13092("CIUDAD REAL"),
    CP_13099("CIUDAD REAL"),
    CP_16001("CUENCA"),
    CP_16002("CUENCA"),
    CP_16003("CUENCA"),
    CP_16004("CUENCA"),
    CP_16070("CUENCA"),
    CP_16071("CUENCA"),
    CP_16080("CUENCA"),
    CP_16090("CUENCA"),
    CP_16091("CUENCA"),
    CP_16092("CUENCA"),
    CP_16099("CUENCA"),
    CP_45001("TOLEDO"),
    CP_45002("TOLEDO"),
    CP_45003("TOLEDO"),
    CP_45004("TOLEDO"),
    CP_45005("TOLEDO"),
    CP_45070("TOLEDO"),
    CP_45071("TOLEDO"),
    CP_45080("TOLEDO"),
    CP_45090("TOLEDO"),
    CP_45091("TOLEDO"),
    CP_45092("TOLEDO"),
    CP_45099("TOLEDO"),
    CP_45700("CONSUEGRA"),
    CP_45710("MADRIDEJOS"),
    CP_45720("VILLACAÑAS"),
    CP_45730("VILLAFRANCA DE LOS CABALLEROS"),
    CP_45740("MIGUEL ESTEBAN"),
    CP_45750("LA PUEBLA DE ALMORADIEL"),
    CP_45760("LOS YÉBENES"),
    CP_45770("URDA"),
    CP_45780("TEMBLEQUE"),
    CP_45790("TURLEQUE");

    private final String location;

    CodigoPostal(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getCode() {
        return name().substring(3);
    }
    public static CodigoPostal fromCode(String code) {
        for (CodigoPostal cp : CodigoPostal.values()) {
            if (cp.name().substring(3).equals(code)) { // Cambia el 1 por 3 para saltar "CP_"
                return cp;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }
}