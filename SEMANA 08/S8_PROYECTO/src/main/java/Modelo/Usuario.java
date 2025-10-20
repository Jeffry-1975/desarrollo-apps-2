
package Modelo;

public class Usuario {
    private String codUsuario;
    private int dniUsuario;
    private String passwordUsuario; // NUEVO CAMPO
    private String nombreUsuario;
    private String apellidoUsuario;
    private String emailUsuario;
    private String telefonoUsuario;
    private RolUsuario rolUsuario;
    
    // Enum para el rol
    public enum RolUsuario {
        CLIENTE,
        ADMIN
    }
    
    // Constructores
    public Usuario() {
    }
    
    public Usuario(String codUsuario, int dniUsuario, String passwordUsuario, String nombreUsuario, 
                  String apellidoUsuario, String emailUsuario, String telefonoUsuario, 
                  RolUsuario rolUsuario) {
        this.codUsuario = codUsuario;
        this.dniUsuario = dniUsuario;
        this.passwordUsuario = passwordUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.emailUsuario = emailUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.rolUsuario = rolUsuario;
    }
    
    // Getters y Setters
    public String getCodUsuario() {
        return codUsuario;
    }
    
    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }
    
    public int getDniUsuario() {
        return dniUsuario;
    }
    
    public void setDniUsuario(int dniUsuario) {
        this.dniUsuario = dniUsuario;
    }
    
    public String getPasswordUsuario() {
        return passwordUsuario;
    }
    
    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }
    
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }
    
    public String getEmailUsuario() {
        return emailUsuario;
    }
    
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    
    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }
    
    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }
    
    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }
    
    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
    
    // Método para validar datos básicos
    public boolean validarDatos() {
        return codUsuario != null && !codUsuario.isEmpty() &&
               dniUsuario > 0 &&
               nombreUsuario != null && !nombreUsuario.isEmpty() &&
               apellidoUsuario != null && !apellidoUsuario.isEmpty() &&
               emailUsuario != null && !emailUsuario.isEmpty() &&
               telefonoUsuario != null && !telefonoUsuario.isEmpty() &&
               rolUsuario != null;
    }
    
    // Método para generar código de usuario automáticamente
    public static String generarCodigoUsuario(String tipo) {
        // Lógica para generar código (ej: "CLI001", "ADM001")
        // Esto lo puedes adaptar según tu necesidad
        String prefix = tipo.equals("ADMIN") ? "ADM" : "CLI";
        // Aquí deberías consultar la base para el siguiente número
        return prefix + "001"; // Temporal
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "codUsuario='" + codUsuario + '\'' +
                ", dniUsuario=" + dniUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", apellidoUsuario='" + apellidoUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", telefonoUsuario='" + telefonoUsuario + '\'' +
                ", rolUsuario=" + rolUsuario +
                '}';
    }
}
