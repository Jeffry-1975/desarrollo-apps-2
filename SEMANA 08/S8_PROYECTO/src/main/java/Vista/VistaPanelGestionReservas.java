
package Vista;

import Modelo.Usuario;
import Modelo.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VistaPanelGestionReservas extends javax.swing.JPanel {
    private DefaultTableModel modeloTabla;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioAdmin;
    
    public VistaPanelGestionReservas(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioAdmin) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioAdmin = usuarioAdmin;
        setPreferredSize(new Dimension(900, 600));
        initComponents();
        configurarTabla(); 
        cargarTodasLasReservas();
    }
    
    private void configurarTabla() {
        comboFiltroEstado.addActionListener(e -> filtrarReservas());
        // Crear el modelo con las columnas
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Cliente");
        modeloTabla.addColumn("Cancha");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora Inicio");
        modeloTabla.addColumn("Hora Fin");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Estado");

        // Asignar el modelo a la tabla
        tablaReservas.setModel(modeloTabla);
    }
    
    
    private void cargarTodasLasReservas() {
        modeloTabla.setRowCount(0);
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT r.id_reserva, u.nombre_usuario, u.apellido_usuario, " +
                        "c.nombre_cancha, r.fecha_reserva, r.horaInicio_reserva, " +
                        "r.horaFin_reserva, r.precio_reserva, r.estado_reserva " +
                        "FROM reserva r " +
                        "JOIN usuario u ON r.cod_usuario = u.cod_usuario " +
                        "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                        "ORDER BY r.id_reserva ASC";
            
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_reserva"),
                    rs.getString("nombre_usuario") + " " + rs.getString("apellido_usuario"),
                    rs.getString("nombre_cancha"),
                    rs.getDate("fecha_reserva"),
                    rs.getTime("horaInicio_reserva"),
                    rs.getTime("horaFin_reserva"),
                    rs.getDouble("precio_reserva"),
                    rs.getString("estado_reserva")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filtrarReservas() {
        String filtro = comboFiltroEstado.getSelectedItem().toString();
        
        if (filtro.equals("TODAS")) {
            cargarTodasLasReservas();
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT r.id_reserva, u.nombre_usuario, u.apellido_usuario, " +
                        "c.nombre_cancha, r.fecha_reserva, r.horaInicio_reserva, " +
                        "r.horaFin_reserva, r.precio_reserva, r.estado_reserva " +
                        "FROM reserva r " +
                        "JOIN usuario u ON r.cod_usuario = u.cod_usuario " +
                        "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                        "WHERE r.estado_reserva = ? " +
                        "ORDER BY r.id_reserva ASC";
            
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, filtro);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_reserva"),
                    rs.getString("nombre_usuario") + " " + rs.getString("apellido_usuario"),
                    rs.getString("nombre_cancha"),
                    rs.getDate("fecha_reserva"),
                    rs.getTime("horaInicio_reserva"),
                    rs.getTime("horaFin_reserva"),
                    rs.getDouble("precio_reserva"),
                    rs.getString("estado_reserva")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar reservas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarEstadoBotones() {
        int fila = tablaReservas.getSelectedRow();
        boolean haySeleccion = fila != -1;
        
        if (haySeleccion) {
            String estado = modeloTabla.getValueAt(fila, 7).toString();
            boolean esPendiente = estado.equals("Pendiente");
            
            btnAprobar.setEnabled(esPendiente);
            btnRechazar.setEnabled(esPendiente);
            
            // Cambiar colores según estado
            if (esPendiente) {
                btnAprobar.setBackground(new Color(60, 179, 113));
                btnRechazar.setBackground(new Color(220, 53, 69));
            } else {
                btnAprobar.setBackground(Color.GRAY);
                btnRechazar.setBackground(Color.GRAY);
            }
        } else {
            btnAprobar.setEnabled(false);
            btnRechazar.setEnabled(false);
            btnAprobar.setBackground(Color.GRAY);
            btnRechazar.setBackground(Color.GRAY);
        }
    }    
    
    private void aprobarReserva() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para aprobar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idReserva = (int) modeloTabla.getValueAt(fila, 0);
        String cliente = modeloTabla.getValueAt(fila, 1).toString();
        String cancha = modeloTabla.getValueAt(fila, 2).toString();
        String fecha = modeloTabla.getValueAt(fila, 3).toString();
        
        // Verificar que la reserva esté pendiente
        String estado = modeloTabla.getValueAt(fila, 7).toString();
        if (!estado.equals("Pendiente")) {
            JOptionPane.showMessageDialog(this, 
                "Solo se pueden aprobar reservas con estado 'Pendiente'", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de APROBAR la reserva?\n\n" +
            "ID: " + idReserva + "\n" +
            "Cliente: " + cliente + "\n" +
            "Cancha: " + cancha + "\n" +
            "Fecha: " + fecha, 
            "Confirmar Aprobación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cambiarEstadoReserva(idReserva, "Confirmada", "aprobada");
        }
    }
    
    private void rechazarReserva() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para rechazar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idReserva = (int) modeloTabla.getValueAt(fila, 0);
        String cliente = modeloTabla.getValueAt(fila, 1).toString();
        String cancha = modeloTabla.getValueAt(fila, 2).toString();
        String fecha = modeloTabla.getValueAt(fila, 3).toString();
        
        // Verificar que la reserva esté pendiente
        String estado = modeloTabla.getValueAt(fila, 7).toString();
        if (!estado.equals("Pendiente")) {
            JOptionPane.showMessageDialog(this, 
                "Solo se pueden rechazar reservas con estado 'Pendiente'", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de RECHAZAR la reserva?\n\n" +
            "ID: " + idReserva + "\n" +
            "Cliente: " + cliente + "\n" +
            "Cancha: " + cancha + "\n" +
            "Fecha: " + fecha, 
            "Confirmar Rechazo", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cambiarEstadoReserva(idReserva, "Cancelada", "rechazada");
        }
    }
    
    private void cambiarEstadoReserva(int idReserva, String nuevoEstado, String accion) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "UPDATE reserva SET estado_reserva = ? WHERE id_reserva = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idReserva);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                String mensaje = "Reserva " + accion + " exitosamente";
                JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Recargar las reservas según el filtro actual
                String filtroActual = comboFiltroEstado.getSelectedItem().toString();
                if (filtroActual.equals("TODAS")) {
                    cargarTodasLasReservas();
                } else {
                    filtrarReservas();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volverAlPanel() {
        cardLayout.show(cardPanel, "PanelAdmin");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        panelCentral = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaReservas = new javax.swing.JTable();
        btnAprobar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        comboFiltroEstado = new javax.swing.JComboBox<>();

        panelSuperior.setBackground(new java.awt.Color(70, 130, 180));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Gestión de Reservas");

        btnVolver.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(0, 102, 204));
        btnVolver.setText("Volver");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(235, 235, 235)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuperiorLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(btnVolver))
                .addContainerGap())
        );

        panelCentral.setBackground(new java.awt.Color(213, 228, 240));

        tablaReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Deporte", "Capacidad", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaReservas.setGridColor(new java.awt.Color(213, 228, 240));
        tablaReservas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaReservas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaReservasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaReservas);

        btnAprobar.setBackground(new java.awt.Color(184, 219, 193));
        btnAprobar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnAprobar.setForeground(new java.awt.Color(51, 51, 51));
        btnAprobar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aprobar.png"))); // NOI18N
        btnAprobar.setText("Aprobar Reserva");
        btnAprobar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 5, true));
        btnAprobar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprobarActionPerformed(evt);
            }
        });

        btnRechazar.setBackground(new java.awt.Color(229, 193, 198));
        btnRechazar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnRechazar.setForeground(new java.awt.Color(0, 51, 51));
        btnRechazar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/rechazar.png"))); // NOI18N
        btnRechazar.setText("Rechazar Reserva");
        btnRechazar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 0), 5, true));
        btnRechazar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });

        comboFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "Pendiente", "Confirmada", "Cancelada" }));

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(btnAprobar, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnRechazar, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAprobar)
                    .addComponent(btnRechazar))
                .addGap(24, 24, 24))
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

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volverAlPanel();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void tablaReservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaReservasMouseClicked
        actualizarEstadoBotones();
    }//GEN-LAST:event_tablaReservasMouseClicked

    private void btnAprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprobarActionPerformed
        aprobarReserva();
    }//GEN-LAST:event_btnAprobarActionPerformed

    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarActionPerformed
        rechazarReserva();
    }//GEN-LAST:event_btnRechazarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAprobar;
    private javax.swing.JButton btnRechazar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboFiltroEstado;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTable tablaReservas;
    // End of variables declaration//GEN-END:variables
}
