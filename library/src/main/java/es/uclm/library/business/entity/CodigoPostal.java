package es.uclm.library.business.entity;

public enum CodigoPostal {
    _45600("Talavera"),
    _12345("Madrid"),
    _67890("Barcelona"),
    _02001("Albacete"),
    _02002("Albacete"),
    _02003("Albacete"),
    _02004("Albacete"),
    _02005("Albacete"),
    _02006("Albacete"),
    _02007("Albacete"),
    _02008("Albacete"),
    _02070("Albacete"),
    _02071("Albacete"),
    _02080("Albacete"),
    _02090("Albacete"),
    _02091("Albacete"),
    _02092("Albacete"),
    _02099("Albacete"),
    _13001("Ciudad Real"),
    _13002("Ciudad Real"),
    _13003("Ciudad Real"),
    _13004("Ciudad Real"),
    _13005("Ciudad Real"),
    _13070("Ciudad Real"),
    _13071("Ciudad Real"),
    _13080("Ciudad Real"),
    _13090("Ciudad Real"),
    _13091("Ciudad Real"),
    _13092("Ciudad Real"),
    _13099("Ciudad Real"),
    _16001("Cuenca"),
    _16002("Cuenca"),
    _16003("Cuenca"),
    _16004("Cuenca"),
    _16070("Cuenca"),
    _16071("Cuenca"),
    _16080("Cuenca"),
    _16090("Cuenca"),
    _16091("Cuenca"),
    _16092("Cuenca"),
    _16099("Cuenca"),
    _45001("Toledo"),
    _45002("Toledo"),
    _45003("Toledo"),
    _45004("Toledo"),
    _45005("Toledo"),
    _45070("Toledo"),
    _45071("Toledo"),
    _45080("Toledo"),
    _45090("Toledo"),
    _45091("Toledo"),
    _45092("Toledo"),
    _45099("Toledo"),
    _45700("Consuegra"),
    _45710("Madridejos"),
    _45720("Villacañas"),
    _45730("Villafranca de los Caballeros"),
    _45740("Miguel Esteban"),
    _45750("La Puebla de Almoradiel"),
    _45760("Los Yébenes"),
    _45770("Urda"),
    _45780("Tembleque"),
    _45790("Turleque");

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