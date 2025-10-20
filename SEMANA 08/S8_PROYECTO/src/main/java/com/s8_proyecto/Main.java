
package com.s8_proyecto;

import Modelo.ConexionBD;
import Vista.VistaLogin;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            ConexionBD.obtenerConexion().close();
            System.out.println("CONEXION EXITOSA");
            
            // Abrir interfaz gráfica>
            SwingUtilities.invokeLater(() -> {
                new VistaLogin().setVisible(true);
            });
            
        } catch (Exception e) {
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error de conexion a la base de datos:\n" + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
