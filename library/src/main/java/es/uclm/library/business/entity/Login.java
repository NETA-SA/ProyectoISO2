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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
