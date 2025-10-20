
package Vista;

import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaPanelAdmin extends javax.swing.JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioAdmin;

    public VistaPanelAdmin(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioAdmin) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioAdmin = usuarioAdmin;
        setPreferredSize(new Dimension(800, 400));
        initComponents();
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
        btnGestionCanchas = new javax.swing.JButton();
        btnGestionReservas = new javax.swing.JButton();
        btnGestionClientes = new javax.swing.JButton();

        panelSuperior.setBackground(new java.awt.Color(70, 130, 180));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Panel Administrador");

        btnCerrarSesion.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(0, 102, 204));
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bienvenido: "+usuarioAdmin.getNombreUsuario()+" "+usuarioAdmin.getApellidoUsuario());

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitulo)
                .addGap(98, 98, 98)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
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

        panelCentral.setBackground(new java.awt.Color(213, 228, 240));

        btnGestionCanchas.setBackground(new java.awt.Color(70, 130, 180));
        btnGestionCanchas.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        btnGestionCanchas.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionCanchas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancha-de-futbol.png"))); // NOI18N
        btnGestionCanchas.setText("GESTIÓN DE CANCHAS");
        btnGestionCanchas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionCanchas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionCanchasActionPerformed(evt);
            }
        });

        btnGestionReservas.setBackground(new java.awt.Color(70, 130, 180));
        btnGestionReservas.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        btnGestionReservas.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionReservas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reserva-en-linea.png"))); // NOI18N
        btnGestionReservas.setText("GESTIÓN DE RESERVAS");
        btnGestionReservas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionReservasActionPerformed(evt);
            }
        });

        btnGestionClientes.setBackground(new java.awt.Color(70, 130, 180));
        btnGestionClientes.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        btnGestionClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/USERS.png"))); // NOI18N
        btnGestionClientes.setText("GESTIÓN DE USUARIOS");
        btnGestionClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGestionClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGestionCanchas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionReservas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnGestionCanchas)
                .addGap(18, 18, 18)
                .addComponent(btnGestionReservas)
                .addGap(18, 18, 18)
                .addComponent(btnGestionClientes)
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

    private void btnGestionCanchasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionCanchasActionPerformed
        VistaPanelGestionCanchas vistapanelCanchas = new VistaPanelGestionCanchas(cardLayout, cardPanel, usuarioAdmin);
        cardPanel.add(vistapanelCanchas, "GestionCanchas");
        cardLayout.show(cardPanel, "GestionCanchas");
    }//GEN-LAST:event_btnGestionCanchasActionPerformed

    private void btnGestionReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionReservasActionPerformed
        VistaPanelGestionReservas vistapanelReservas = new VistaPanelGestionReservas(cardLayout, cardPanel, usuarioAdmin);
        cardPanel.add(vistapanelReservas, "GestionReservas");
        cardLayout.show(cardPanel, "GestionReservas");
    }//GEN-LAST:event_btnGestionReservasActionPerformed

    private void btnGestionClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionClientesActionPerformed
        VistaPanelGestionClientes vistapanelClientes = new VistaPanelGestionClientes(cardLayout, cardPanel, usuarioAdmin);
        cardPanel.add(vistapanelClientes, "GestionClientes");
        cardLayout.show(cardPanel, "GestionClientes");
    }//GEN-LAST:event_btnGestionClientesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnGestionCanchas;
    private javax.swing.JButton btnGestionClientes;
    private javax.swing.JButton btnGestionReservas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelSuperior;
    // End of variables declaration//GEN-END:variables
}
