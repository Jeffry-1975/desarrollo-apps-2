
package DAO;

import Modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;
    
    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    // Método registrarUsuario actualizado
    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (cod_usuario, dni_usuario, password_usuario, " +
                    "nombre_usuario, apellido_usuario, email_usuario, telefono_usuario, rol_usuario) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getCodUsuario());
            stmt.setInt(2, usuario.getDniUsuario());
            stmt.setString(3, usuario.getPasswordUsuario()); // NUEVO CAMPO
            stmt.setString(4, usuario.getNombreUsuario());
            stmt.setString(5, usuario.getApellidoUsuario());
            stmt.setString(6, usuario.getEmailUsuario());
            stmt.setString(7, usuario.getTelefonoUsuario());
            stmt.setString(8, usuario.getRolUsuario().toString());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para validar login con código y contraseña
    public Usuario validarLogin(String codigo, String password) {
        String sql = "SELECT * FROM usuario WHERE cod_usuario = ? AND password_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    // Verificar si email ya existe
    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email_usuario = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Verificar si DNI ya existe
    public boolean existeDNI(int dni) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE dni_usuario = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, dni);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    // Agregar este método en UsuarioDAO
    public boolean existeCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE cod_usuario = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    } 
    
    // Obtener último código de usuario
    public String obtenerUltimoCodigo(String prefijo) {
        String sql = "SELECT cod_usuario FROM usuario WHERE cod_usuario LIKE ? ORDER BY cod_usuario DESC LIMIT 1";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, prefijo + "%");
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("cod_usuario");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }    
    
    // Mapear ResultSet a objeto Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setCodUsuario(rs.getString("cod_usuario"));
        usuario.setDniUsuario(rs.getInt("dni_usuario"));
        usuario.setPasswordUsuario(rs.getString("password_usuario"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setApellidoUsuario(rs.getString("apellido_usuario"));
        usuario.setEmailUsuario(rs.getString("email_usuario"));
        usuario.setTelefonoUsuario(rs.getString("telefono_usuario"));
        usuario.setRolUsuario(Usuario.RolUsuario.valueOf(rs.getString("rol_usuario")));
        return usuario;
    }
}
