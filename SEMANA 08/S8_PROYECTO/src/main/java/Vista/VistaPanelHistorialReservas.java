
package Vista;

import Modelo.ConexionBD;
import Modelo.Usuario;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class VistaPanelHistorialReservas extends javax.swing.JPanel {
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioCliente;
    
    
    public VistaPanelHistorialReservas(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioCliente) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioCliente = usuarioCliente;
        setPreferredSize(new Dimension(1200, 700));
        initComponents();
        configurarTabla();
        cargarHistorialCompleto();
    }
    
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Cancha");
        modeloTabla.addColumn("Deporte");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora Inicio");
        modeloTabla.addColumn("Hora Fin");
        modeloTabla.addColumn("Duración");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Fecha Creación");
        
        tablaHistorial.setModel(modeloTabla);
        
        sorter = new TableRowSorter<>(modeloTabla);
        tablaHistorial.setRowSorter(sorter);
        
        if (txtBuscarCancha != null) {
            txtBuscarCancha.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarEnTiempoReal(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarEnTiempoReal(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarEnTiempoReal(); }
            });
        }
        
        // Configurar combo boxes
        comboFiltroEstado.addActionListener(e -> cargarHistorialFiltrado());
        comboFiltroMes.addActionListener(e -> cargarHistorialFiltrado());
        comboFiltroAnio.addActionListener(e -> cargarHistorialFiltrado());
    }
    
    private void cargarHistorialCompleto() {
        modeloTabla.setRowCount(0);
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT r.id_reserva, c.nombre_cancha, c.deporte_cancha, " +
                        "r.fecha_reserva, r.horaInicio_reserva, r.horaFin_reserva, " +
                        "r.precio_reserva, r.estado_reserva, r.fecha_reserva as fecha_creacion " +
                        "FROM reserva r " +
                        "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                        "WHERE r.cod_usuario = ? " +
                        "ORDER BY r.fecha_reserva DESC, r.horaInicio_reserva DESC";
            
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, usuarioCliente.getCodUsuario());
            ResultSet rs = stmt.executeQuery();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            while (rs.next()) {
                // Calcular duración
                java.sql.Time horaInicio = rs.getTime("horaInicio_reserva");
                java.sql.Time horaFin = rs.getTime("horaFin_reserva");
                long diff = horaFin.getTime() - horaInicio.getTime();
                int duracionHoras = (int) (diff / (1000 * 60 * 60));
                
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_reserva"),
                    rs.getString("nombre_cancha"),
                    rs.getString("deporte_cancha"),
                    rs.getDate("fecha_reserva"),
                    timeFormat.format(horaInicio),
                    timeFormat.format(horaFin),
                    duracionHoras + " hora" + (duracionHoras != 1 ? "s" : ""),
                    String.format("S/.%.2f", rs.getDouble("precio_reserva")),
                    rs.getString("estado_reserva"),
                    dateFormat.format(rs.getDate("fecha_creacion"))
                });
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar historial: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarHistorialFiltrado() {
        modeloTabla.setRowCount(0);
        
        String estado = comboFiltroEstado.getSelectedItem().toString();
        String mes = comboFiltroMes.getSelectedItem().toString();
        String anio = comboFiltroAnio.getSelectedItem().toString();
        
        StringBuilder sql = new StringBuilder(
            "SELECT r.id_reserva, c.nombre_cancha, c.deporte_cancha, " +
            "r.fecha_reserva, r.horaInicio_reserva, r.horaFin_reserva, " +
            "r.precio_reserva, r.estado_reserva, r.fecha_reserva as fecha_creacion " +
            "FROM reserva r " +
            "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
            "WHERE r.cod_usuario = ?"
        );
        
        // Construir condiciones dinámicamente
        if (!estado.equals("TODOS")) {
            sql.append(" AND r.estado_reserva = ?");
        }
        
        if (!mes.equals("TODOS")) {
            sql.append(" AND MONTH(r.fecha_reserva) = ?");
        }
        
        if (!anio.equals("TODOS")) {
            sql.append(" AND YEAR(r.fecha_reserva) = ?");
        }
        
        sql.append(" ORDER BY r.fecha_reserva DESC, r.horaInicio_reserva DESC");
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            PreparedStatement stmt = conexion.prepareStatement(sql.toString());
            int paramIndex = 1;
            stmt.setString(paramIndex++, usuarioCliente.getCodUsuario());
            
            if (!estado.equals("TODOS")) {
                stmt.setString(paramIndex++, estado);
            }
            
            if (!mes.equals("TODOS")) {
                stmt.setInt(paramIndex++, comboFiltroMes.getSelectedIndex()); // Índice coincide con mes numérico
            }
            
            if (!anio.equals("TODOS")) {
                stmt.setInt(paramIndex++, Integer.parseInt(anio));
            }
            
            ResultSet rs = stmt.executeQuery();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            while (rs.next()) {
                // Calcular duración
                java.sql.Time horaInicio = rs.getTime("horaInicio_reserva");
                java.sql.Time horaFin = rs.getTime("horaFin_reserva");
                long diff = horaFin.getTime() - horaInicio.getTime();
                int duracionHoras = (int) (diff / (1000 * 60 * 60));
                
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_reserva"),
                    rs.getString("nombre_cancha"),
                    rs.getString("deporte_cancha"),
                    rs.getDate("fecha_reserva"),
                    timeFormat.format(horaInicio),
                    timeFormat.format(horaFin),
                    duracionHoras + " hora" + (duracionHoras != 1 ? "s" : ""),
                    String.format("S/.%.2f", rs.getDouble("precio_reserva")),
                    rs.getString("estado_reserva"),
                    dateFormat.format(rs.getDate("fecha_creacion"))
                });
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar historial filtrado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filtrarEnTiempoReal() {
        String texto = txtBuscarCancha.getText();
        if (texto.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1)); // Columna 1 = Cancha
        }
    }
    
    private void limpiarFiltros() {
        comboFiltroEstado.setSelectedIndex(0);
        comboFiltroMes.setSelectedIndex(0);
        comboFiltroAnio.setSelectedIndex(0);
        txtBuscarCancha.setText("");
        sorter.setRowFilter(null);
        cargarHistorialCompleto();
    }
    
    private void exportarACSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar historial como CSV");
        fileChooser.setSelectedFile(new java.io.File("historial_reservas.csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            
            try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
                // Escribir encabezados
                for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                    writer.print(modeloTabla.getColumnName(i));
                    if (i < modeloTabla.getColumnCount() - 1) {
                        writer.print(",");
                    }
                }
                writer.println();
                
                // Escribir datos
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                        Object valorObj = modeloTabla.getValueAt(i, j);
                        String valor = valorObj != null ? valorObj.toString() : "";
                        // Escapar comas y comillas
                        if (valor.contains(",") || valor.contains("\"")) {
                            valor = "\"" + valor.replace("\"", "\"\"") + "\"";
                        }
                        writer.print(valor);
                        if (j < modeloTabla.getColumnCount() - 1) {
                            writer.print(",");
                        }
                    }
                    writer.println();
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Historial exportado exitosamente a:\n" + file.getAbsolutePath(), 
                    "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al exportar: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void verDetallesReserva() {
        int fila = tablaHistorial.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para ver detalles", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Convertir índice de la vista al índice del modelo
        int modelRow = tablaHistorial.convertRowIndexToModel(fila);
        
        int idReserva = (int) modeloTabla.getValueAt(modelRow, 0);
        String cancha = modeloTabla.getValueAt(modelRow, 1).toString();
        String deporte = modeloTabla.getValueAt(modelRow, 2).toString();
        String fecha = modeloTabla.getValueAt(modelRow, 3).toString();
        String horaInicio = modeloTabla.getValueAt(modelRow, 4).toString();
        String horaFin = modeloTabla.getValueAt(modelRow, 5).toString();
        String duracion = modeloTabla.getValueAt(modelRow, 6).toString();
        String precio = modeloTabla.getValueAt(modelRow, 7).toString();
        String estado = modeloTabla.getValueAt(modelRow, 8).toString();
        String fechaCreacion = modeloTabla.getValueAt(modelRow, 9).toString();
        
        // Obtener más detalles de la base de datos
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT c.descripcion_cancha, c.capacidad_cancha " +
                        "FROM reserva r " +
                        "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                        "WHERE r.id_reserva = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();
            
            String descripcion = "";
            int capacidad = 0;
            
            if (rs.next()) {
                descripcion = rs.getString("descripcion_cancha");
                capacidad = rs.getInt("capacidad_cancha");
            }
            
            // Crear mensaje detallado
            String mensaje = String.format(
                "📋 DETALLES DE LA RESERVA #%d\n\n" +
                "🏟️ Cancha: %s\n" +
                "⚽ Deporte: %s\n" +
                "📅 Fecha: %s\n" +
                "⏰ Horario: %s - %s (%s)\n" +
                "💰 Precio: %s\n" +
                "📊 Estado: %s\n" +
                "📝 Creado: %s\n\n" +
                "📖 Descripción: %s\n" +
                "👥 Capacidad: %d personas",
                idReserva, cancha, deporte, fecha, horaInicio, horaFin, duracion,
                precio, estado, fechaCreacion, descripcion, capacidad
            );
            
            JOptionPane.showMessageDialog(this, mensaje, 
                "Detalles de Reserva #" + idReserva, JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar detalles: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volverAlPanel() {
        cardLayout.show(cardPanel, "PanelCliente");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        panelFiltros = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        comboFiltroEstado = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaHistorial = new javax.swing.JTable();
        btnLimpiarFiltros = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtBuscarCancha = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        comboFiltroMes = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        comboFiltroAnio = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();

        panelSuperior.setBackground(new java.awt.Color(70, 207, 107));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Historial Completo de Reservas");

        btnVolver.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(70, 207, 107));
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
                .addGap(257, 257, 257)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuperiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(btnVolver))
                .addGap(15, 15, 15))
        );

        panelFiltros.setBackground(new java.awt.Color(194, 241, 205));
        panelFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros Avanzados", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 102, 51))); // NOI18N
        panelFiltros.setPreferredSize(new java.awt.Dimension(400, 0));

        jLabel8.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(68, 165, 54));
        jLabel8.setText("Estado:");

        comboFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "Pendiente", "Confirmada", "Cancelada" }));

        tablaHistorial.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaHistorial.setGridColor(new java.awt.Color(213, 228, 240));
        tablaHistorial.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaHistorial.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaHistorialMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaHistorial);

        btnLimpiarFiltros.setBackground(new java.awt.Color(102, 204, 0));
        btnLimpiarFiltros.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnLimpiarFiltros.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiarFiltros.setText("Limpiar Filtros");
        btnLimpiarFiltros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarFiltrosActionPerformed(evt);
            }
        });

        btnExportar.setBackground(new java.awt.Color(51, 153, 0));
        btnExportar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnExportar.setForeground(new java.awt.Color(255, 255, 255));
        btnExportar.setText("Exportar a CSV");
        btnExportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(68, 165, 54));
        jLabel9.setText("Buscar Cancha:");

        jLabel10.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(68, 165, 54));
        jLabel10.setText("Mes:");

        comboFiltroMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Dicembre" }));

        jLabel11.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(68, 165, 54));
        jLabel11.setText("Mes:");

        comboFiltroAnio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "2025", "2024", "2023", "2022" }));

        btnFiltrar.setBackground(new java.awt.Color(51, 153, 0));
        btnFiltrar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnFiltrar.setForeground(new java.awt.Color(255, 255, 255));
        btnFiltrar.setText("Filtrar");
        btnFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFiltrosLayout = new javax.swing.GroupLayout(panelFiltros);
        panelFiltros.setLayout(panelFiltrosLayout);
        panelFiltrosLayout.setHorizontalGroup(
            panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(20, 20, 20)
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboFiltroEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBuscarCancha, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelFiltrosLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboFiltroMes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLimpiarFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFiltrosLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(comboFiltroAnio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 223, Short.MAX_VALUE))
                    .addGroup(panelFiltrosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFiltrosLayout.setVerticalGroup(
            panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFiltrosLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(comboFiltroMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(comboFiltroAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtBuscarCancha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, 1088, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volverAlPanel();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void tablaHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaHistorialMouseClicked
        if (evt.getClickCount() == 2) {
            verDetallesReserva();
        }
    }//GEN-LAST:event_tablaHistorialMouseClicked

    private void btnLimpiarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarFiltrosActionPerformed
        limpiarFiltros();
    }//GEN-LAST:event_btnLimpiarFiltrosActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        exportarACSV();
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        cargarHistorialFiltrado();
    }//GEN-LAST:event_btnFiltrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnLimpiarFiltros;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboFiltroAnio;
    private javax.swing.JComboBox<String> comboFiltroEstado;
    private javax.swing.JComboBox<String> comboFiltroMes;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTable tablaHistorial;
    private javax.swing.JTextField txtBuscarCancha;
    // End of variables declaration//GEN-END:variables
}
