
package Vista;

import Modelo.Usuario;
import Modelo.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Modelo.Usuario;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class VistaPanelCliente extends javax.swing.JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioCliente;
    
    public VistaPanelCliente(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioCliente) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioCliente = usuarioCliente;
        setPreferredSize(new Dimension(1000, 700));
        initComponents();
    }
    
    private void abrirNuevaReserva() {
        VistaPanelNuevaReserva vistapanelNuevaReserva = new VistaPanelNuevaReserva(cardLayout, cardPanel, usuarioCliente);
        cardPanel.add(vistapanelNuevaReserva, "NuevaReserva");
        cardLayout.show(cardPanel, "NuevaReserva");
    }
    
    private void abrirMisReservas() { //falta editar
        VistaPanelMisReservas vistapanelMisReservas = new VistaPanelMisReservas(cardLayout, cardPanel, usuarioCliente);
        cardPanel.add(vistapanelMisReservas, "MisReservas");
        cardLayout.show(cardPanel, "MisReservas");
    }
    
    private void abrirHistorialReservas() {
        VistaPanelHistorialReservas vistapanelHistorial = new VistaPanelHistorialReservas(cardLayout, cardPanel, usuarioCliente);
        cardPanel.add(vistapanelHistorial, "HistorialReservas");
        cardLayout.show(cardPanel, "HistorialReservas");
    }
    
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cerrar sesión?", 
            "Confirmar Cierre de Sesión", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Cerrar ventana actual
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
            
            // Volver a abrir el login
            new VistaLogin().setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();
        btnNuevaReserva = new javax.swing.JButton();
        btnMisReservas = new javax.swing.JButton();
        btnHistorialReservas = new javax.swing.JButton();

        panelSuperior.setBackground(new java.awt.Color(70, 207, 107));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Panel Cliente");

        btnCerrarSesion.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(70, 207, 107));
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bienvenido: "+usuarioCliente.getNombreUsuario()+" "+usuarioCliente.getApellidoUsuario());

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitulo)
                .addGap(98, 98, 98)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addContainerGap())
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuperiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(jLabel1)
                    .addComponent(btnCerrarSesion))
                .addGap(15, 15, 15))
        );

        panelCentral.setBackground(new java.awt.Color(194, 241, 205));

        btnNuevaReserva.setBackground(new java.awt.Color(70, 207, 107));
        btnNuevaReserva.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        btnNuevaReserva.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reserva.png"))); // NOI18N
        btnNuevaReserva.setText("NUEVA RESERVA");
        btnNuevaReserva.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaReservaActionPerformed(evt);
            }
        });

        btnMisReservas.setBackground(new java.awt.Color(70, 207, 107));
        btnMisReservas.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        btnMisReservas.setForeground(new java.awt.Color(255, 255, 255));
        btnMisReservas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reservado.png"))); // NOI18N
        btnMisReservas.setText("MIS RESERVAS");
        btnMisReservas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMisReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMisReservasActionPerformed(evt);
            }
        });

        btnHistorialReservas.setBackground(new java.awt.Color(70, 207, 107));
        btnHistorialReservas.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        btnHistorialReservas.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorialReservas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/historial-de-transacciones.png"))); // NOI18N
        btnHistorialReservas.setText("MI HISTORIAL");
        btnHistorialReservas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorialReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialReservasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevaReserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMisReservas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHistorialReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnNuevaReserva)
                .addGap(18, 18, 18)
                .addComponent(btnMisReservas)
                .addGap(18, 18, 18)
                .addComponent(btnHistorialReservas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        cerrarSesion();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaReservaActionPerformed
        abrirNuevaReserva();
    }//GEN-LAST:event_btnNuevaReservaActionPerformed

    private void btnMisReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMisReservasActionPerformed
        abrirMisReservas();
    }//GEN-LAST:event_btnMisReservasActionPerformed

    private void btnHistorialReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialReservasActionPerformed
        abrirHistorialReservas();
    }//GEN-LAST:event_btnHistorialReservasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnHistorialReservas;
    private javax.swing.JButton btnMisReservas;
    private javax.swing.JButton btnNuevaReserva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelSuperior;
    // End of variables declaration//GEN-END:variables
}
