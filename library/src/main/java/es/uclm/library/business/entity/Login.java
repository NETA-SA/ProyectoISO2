package es.uclm.library.business.entity;

public class Login {
    private String idUsuario;
    private String pass;

    // Getters y Setters
    public String getUsername() {
        return idUsuario;
    }

    public void setUsername(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String pass) {
        this.pass = pass;
    }
}
