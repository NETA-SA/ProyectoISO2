package es.uclm.library.business.entity;

public enum CodigoPostal {
    _45600("Talavera"),
    _12345("Madrid"),
    _67890("Barcelona");

    private final String location;

    CodigoPostal(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
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