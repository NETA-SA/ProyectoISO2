package es.uclm.library.business.entity;

public enum CodigoPostal {
    _45600("Talavera"),
    _12345("Madrid"),
    _67890("Barcelona"),
    _02001("ALBACETE"),
    _02002("ALBACETE"),
    _02003("ALBACETE"),
    _02004("ALBACETE"),
    _02005("ALBACETE"),
    _02006("ALBACETE"),
    _02007("ALBACETE"),
    _02008("ALBACETE"),
    _02070("ALBACETE"),
    _02071("ALBACETE"),
    _02080("ALBACETE"),
    _02090("ALBACETE"),
    _02091("ALBACETE"),
    _02092("ALBACETE"),
    _02099("ALBACETE"),
    _13001("CIUDAD REAL"),
    _13002("CIUDAD REAL"),
    _13003("CIUDAD REAL"),
    _13004("CIUDAD REAL"),
    _13005("CIUDAD REAL"),
    _13070("CIUDAD REAL"),
    _13071("CIUDAD REAL"),
    _13080("CIUDAD REAL"),
    _13090("CIUDAD REAL"),
    _13091("CIUDAD REAL"),
    _13092("CIUDAD REAL"),
    _13099("CIUDAD REAL"),
    _16001("CUENCA"),
    _16002("CUENCA"),
    _16003("CUENCA"),
    _16004("CUENCA"),
    _16070("CUENCA"),
    _16071("CUENCA"),
    _16080("CUENCA"),
    _16090("CUENCA"),
    _16091("CUENCA"),
    _16092("CUENCA"),
    _16099("CUENCA"),
    _45001("TOLEDO"),
    _45002("TOLEDO"),
    _45003("TOLEDO"),
    _45004("TOLEDO"),
    _45005("TOLEDO"),
    _45070("TOLEDO"),
    _45071("TOLEDO"),
    _45080("TOLEDO"),
    _45090("TOLEDO"),
    _45091("TOLEDO"),
    _45092("TOLEDO"),
    _45099("TOLEDO"),
    _45700("CONSUEGRA"),
    _45710("MADRIDEJOS"),
    _45720("VILLACAÑAS"),
    _45730("VILLAFRANCA DE LOS CABALLEROS"),
    _45740("MIGUEL ESTEBAN"),
    _45750("LA PUEBLA DE ALMORADIEL"),
    _45760("LOS YÉBENES"),
    _45770("URDA"),
    _45780("TEMBLEQUE"),
    _45790("TURLEQUE");

    private final String location;

    CodigoPostal(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getCode() {
        return name().substring(1);
    }

    public static CodigoPostal fromCode(String code) {
        for (CodigoPostal cp : CodigoPostal.values()) {
            if (cp.name().substring(1).equals(code)) {
                return cp;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + code);
    }
}