
package Vista;

import Modelo.ConexionBD;
import Modelo.Usuario;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class VistaPanelMisReservas extends javax.swing.JPanel {
    private DefaultTableModel modeloTabla;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioCliente;
    private int reservaSeleccionadaId;
    
    public VistaPanelMisReservas(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioCliente) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioCliente = usuarioCliente;
        setPreferredSize(new Dimension(1100, 700));
        initComponents();
        configurarSpinners();
        configurarTabla();
        cargarMisReservas();
    }
    
    private void configurarSpinners() {
        // Configurar spinner de hora inicio
        SpinnerDateModel horaInicioModel = new SpinnerDateModel();
        spinnerHoraInicioEditar.setModel(horaInicioModel);
        JSpinner.DateEditor horaInicioEditor = new JSpinner.DateEditor(spinnerHoraInicioEditar, "HH:mm");
        spinnerHoraInicioEditar.setEditor(horaInicioEditor);

        // Configurar spinner de hora fin
        SpinnerDateModel horaFinModel = new SpinnerDateModel();
        spinnerHoraFinEditar.setModel(horaFinModel);
        JSpinner.DateEditor horaFinEditor = new JSpinner.DateEditor(spinnerHoraFinEditar, "HH:mm");
        spinnerHoraFinEditar.setEditor(horaFinEditor);
        spinnerHoraFinEditar.setEnabled(false); // Se calcula automáticamente

        // Establecer fecha por defecto: hoy
        java.util.Date fechaHoy = new java.util.Date();
        spinnerFechaEditar.setDate(fechaHoy);

        // Establecer hora por defecto: 14:00
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        spinnerHoraInicioEditar.setValue(cal.getTime());

        // Configurar listeners para actualización automática
        spinnerHoraInicioEditar.addChangeListener(e -> actualizarHoraFinEdicion());
        spinnerDuracionEditar.addChangeListener(e -> actualizarHoraFinEdicion());
    }
    
    private void configurarTabla() {
        comboFiltroEstado.addActionListener(e -> cargarMisReservas());
        // Crear el modelo con las columnas
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Cancha");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora Inicio");
        modeloTabla.addColumn("Hora Fin");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Estado");

        // Asignar el modelo a la tabla
        tablaReservas.setModel(modeloTabla);

        // Ocultar columna ID (columna 0)
        tablaReservas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaReservas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaReservas.getColumnModel().getColumn(0).setWidth(0);
    }   
        
    private void cargarMisReservas() {
        modeloTabla.setRowCount(0);
        
        String filtro = comboFiltroEstado.getSelectedItem().toString();
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql;
            PreparedStatement stmt;
            
            if (filtro.equals("TODAS")) {
                sql = "SELECT r.id_reserva, c.nombre_cancha, r.fecha_reserva, " +
                      "r.horaInicio_reserva, r.horaFin_reserva, r.precio_reserva, " +
                      "r.estado_reserva, r.fecha_reserva as fecha_creacion " +
                      "FROM reserva r " +
                      "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                      "WHERE r.cod_usuario = ? " +
                      "ORDER BY r.fecha_reserva DESC, r.horaInicio_reserva DESC";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, usuarioCliente.getCodUsuario());
            } else {
                sql = "SELECT r.id_reserva, c.nombre_cancha, r.fecha_reserva, " +
                      "r.horaInicio_reserva, r.horaFin_reserva, r.precio_reserva, " +
                      "r.estado_reserva, r.fecha_reserva as fecha_creacion " +
                      "FROM reserva r " +
                      "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                      "WHERE r.cod_usuario = ? AND r.estado_reserva = ? " +
                      "ORDER BY r.fecha_reserva DESC, r.horaInicio_reserva DESC";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, usuarioCliente.getCodUsuario());
                stmt.setString(2, filtro);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_reserva"),
                    rs.getString("nombre_cancha"),
                    rs.getDate("fecha_reserva"),
                    rs.getTime("horaInicio_reserva"),
                    rs.getTime("horaFin_reserva"),
                    String.format("S/.%.2f", rs.getDouble("precio_reserva")),
                    rs.getString("estado_reserva"),
                    rs.getDate("fecha_creacion")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reservas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarEstadoBotones() {
        int fila = tablaReservas.getSelectedRow();
        boolean haySeleccion = fila != -1;
        
        if (haySeleccion) {
            String estado = modeloTabla.getValueAt(fila, 6).toString();
            boolean esPendiente = estado.equals("Pendiente");
            boolean esConfirmada = estado.equals("Confirmada");
            
            btnEditar.setEnabled(esPendiente);
            btnCancelar.setEnabled(esPendiente || esConfirmada);
            
            // Cambiar colores según estado
            if (esPendiente) {
                btnEditar.setBackground(new Color(255, 193, 7));
                btnCancelar.setBackground(new Color(220, 53, 69));
            } else if (esConfirmada) {
                btnEditar.setBackground(Color.GRAY);
                btnCancelar.setBackground(new Color(220, 53, 69));
            } else {
                btnEditar.setBackground(Color.GRAY);
                btnCancelar.setBackground(Color.GRAY);
            }
        } else {
            btnEditar.setEnabled(false);
            btnCancelar.setEnabled(false);
            btnEditar.setBackground(Color.GRAY);
            btnCancelar.setBackground(Color.GRAY);
        }
    }
    
    private void editarReserva() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            reservaSeleccionadaId = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            String estado = modeloTabla.getValueAt(fila, 6).toString();

            if (!estado.equals("Pendiente")) {
                JOptionPane.showMessageDialog(this, 
                    "Solo se pueden editar reservas con estado 'Pendiente'", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cargar datos de la reserva seleccionada en el formulario de edición
            cargarDatosReservaEnFormulario(reservaSeleccionadaId);
            setPanelEdicionHabilitado(true);

            System.out.println("Editando reserva ID: " + reservaSeleccionadaId);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener ID de la reserva", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosReservaEnFormulario(int idReserva) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT r.*, c.nombre_cancha, c.precio_hora FROM reserva r " +
                        "JOIN cancha c ON r.cod_cancha = c.cod_cancha " +
                        "WHERE r.id_reserva = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Llenar directamente los campos del formulario
                txtIdReserva.setText(String.valueOf(idReserva));
                txtCanchaEditar.setText(rs.getString("nombre_cancha"));

                // Fecha - CORREGIDO
                java.sql.Date fechaSql = rs.getDate("fecha_reserva");
                if (fechaSql != null) {
                    spinnerFechaEditar.setDate(new java.util.Date(fechaSql.getTime()));
                }

                // Hora Inicio - CORREGIDO
                java.sql.Time horaInicioSql = rs.getTime("horaInicio_reserva");
                if (horaInicioSql != null) {
                    Calendar calInicio = Calendar.getInstance();
                    calInicio.setTime(horaInicioSql);
                    spinnerHoraInicioEditar.setValue(calInicio.getTime());
                }

                // Calcular duración - CORREGIDO
                java.sql.Time horaFinSql = rs.getTime("horaFin_reserva");
                if (horaInicioSql != null && horaFinSql != null) {
                    long diff = horaFinSql.getTime() - horaInicioSql.getTime();
                    int duracionHoras = (int) (diff / (1000 * 60 * 60));
                    spinnerDuracionEditar.setValue(duracionHoras);
                } else {
                    spinnerDuracionEditar.setValue(1); // Duración por defecto
                }

                // Actualizar hora fin
                actualizarHoraFinEdicion();

                // Precio
                txtPrecioEditar.setText(String.format("%.2f", rs.getDouble("precio_reserva")));

                System.out.println("Datos cargados - ID: " + idReserva + ", Cancha: " + rs.getString("nombre_cancha"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de la reserva: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void actualizarHoraFinEdicion() {
        if (spinnerHoraInicioEditar.getValue() != null && spinnerDuracionEditar.getValue() != null) {
            try {
                LocalTime horaInicio = getHoraInicioFromSpinnerEditar();
                int duracion = (int) spinnerDuracionEditar.getValue();
                LocalTime horaFin = horaInicio.plusHours(duracion);
                setHoraFinInSpinnerEditar(horaFin);

                calcularPrecioEdicion();
            } catch (Exception e) {
                System.err.println("Error en actualizarHoraFinEdicion: " + e.getMessage());
            }
        }
    }
    
    private void calcularPrecioEdicion() {
        try {
            String cancha = txtCanchaEditar.getText();
            if (cancha == null || cancha.trim().isEmpty()) {
                return;
            }

            // Obtener precio por hora de la base de datos
            try (Connection conexion = ConexionBD.obtenerConexion()) {
                String sql = "SELECT precio_hora FROM cancha WHERE nombre_cancha = ?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, cancha.trim());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    double precioHora = rs.getDouble("precio_hora");
                    int duracion = (int) spinnerDuracionEditar.getValue();
                    double precioTotal = precioHora * duracion;
                    txtPrecioEditar.setText(String.format("%.2f", precioTotal));
                }
            }
        } catch (Exception e) {
            System.err.println("Error en calcularPrecioEdicion: " + e.getMessage());
        }
    }
    
    private void guardarCambiosReserva() {
        if (!validarFormularioEdicion()) return;
        
        LocalDate fecha = getFechaFromSpinnerEditar();
        LocalTime horaInicio = getHoraInicioFromSpinnerEditar();
        LocalTime horaFin = getHoraFinFromSpinnerEditar();
        double precio = Double.parseDouble(txtPrecioEditar.getText().trim());
        
        // Validaciones
        if (fecha.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, 
                "No se pueden programar reservas para fechas pasadas", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (horaInicio.isBefore(LocalTime.of(8, 0)) || horaFin.isAfter(LocalTime.of(22, 0))) {
            JOptionPane.showMessageDialog(this, 
                "El horario de funcionamiento es de 8:00 a 22:00", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            // Verificar disponibilidad (excluyendo la reserva actual)
            if (!estaDisponibleParaEdicion(reservaSeleccionadaId, fecha.toString(), 
                                         horaInicio.toString(), horaFin.toString())) {
                JOptionPane.showMessageDialog(this, 
                    "La cancha no está disponible en el horario seleccionado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Actualizar reserva
            String sql = "UPDATE reserva SET fecha_reserva = ?, horaInicio_reserva = ?, " +
                        "horaFin_reserva = ?, precio_reserva = ? WHERE id_reserva = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            stmt.setString(1, fecha.toString());
            stmt.setString(2, horaInicio.toString());
            stmt.setString(3, horaFin.toString());
            stmt.setDouble(4, precio);
            stmt.setInt(5, reservaSeleccionadaId);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Reserva actualizada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioEdicion();
                cargarMisReservas();
                setPanelEdicionHabilitado(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar reserva: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean estaDisponibleParaEdicion(int idReservaExcluir, String fecha, String horaInicio, String horaFin) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            // Obtener la cancha de la reserva actual
            String sqlCancha = "SELECT cod_cancha FROM reserva WHERE id_reserva = ?";
            PreparedStatement stmtCancha = conexion.prepareStatement(sqlCancha);
            stmtCancha.setInt(1, idReservaExcluir);
            ResultSet rsCancha = stmtCancha.executeQuery();
            
            if (rsCancha.next()) {
                String cancha = rsCancha.getString("cod_cancha");
                
                String sql = "SELECT COUNT(*) FROM reserva " +
                            "WHERE cod_cancha = ? AND fecha_reserva = ? " +
                            "AND id_reserva != ? " + // Excluir la reserva actual
                            "AND estado_reserva IN ('Pendiente', 'Confirmada') " +
                            "AND ((horaInicio_reserva <= ? AND horaFin_reserva > ?) " +
                            "OR (horaInicio_reserva < ? AND horaFin_reserva >= ?) " +
                            "OR (horaInicio_reserva >= ? AND horaFin_reserva <= ?))";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, cancha);
                stmt.setString(2, fecha);
                stmt.setInt(3, idReservaExcluir);
                stmt.setString(4, horaInicio);
                stmt.setString(5, horaInicio);
                stmt.setString(6, horaFin);
                stmt.setString(7, horaFin);
                stmt.setString(8, horaInicio);
                stmt.setString(9, horaFin);
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void cancelarReserva() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una reserva para cancelar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idReserva = (int) modeloTabla.getValueAt(fila, 0);
        String cancha = modeloTabla.getValueAt(fila, 1).toString();
        String fecha = modeloTabla.getValueAt(fila, 2).toString();
        String estado = modeloTabla.getValueAt(fila, 6).toString();
        
        String mensajeConfirmacion;
        if (estado.equals("Confirmada")) {
            mensajeConfirmacion = "¿Está seguro de cancelar la reserva CONFIRMADA?\n\n" +
                                 "Esta acción notificará al administrador.";
        } else {
            mensajeConfirmacion = "¿Está seguro de cancelar la reserva?";
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            mensajeConfirmacion + "\n\n" +
            "ID: " + idReserva + "\n" +
            "Cancha: " + cancha + "\n" +
            "Fecha: " + fecha, 
            "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conexion = ConexionBD.obtenerConexion()) {
                String sql = "UPDATE reserva SET estado_reserva = 'Cancelada' WHERE id_reserva = ?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setInt(1, idReserva);
                
                int filas = stmt.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, "Reserva cancelada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarMisReservas();
                    limpiarFormularioEdicion();
                    setPanelEdicionHabilitado(false);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cancelar reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validarFormularioEdicion() {
        if (spinnerFechaEditar.getDate()== null || spinnerHoraInicioEditar.getValue() == null ||
            txtPrecioEditar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Double.parseDouble(txtPrecioEditar.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormularioEdicion() {
        // Limpiar campos específicos del formulario de edición
        txtIdReserva.setText("");
        txtCanchaEditar.setText("");
        spinnerFechaEditar.setDate(new java.util.Date());

        // Establecer hora por defecto: 14:00
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        spinnerHoraInicioEditar.setValue(cal.getTime());

        spinnerDuracionEditar.setValue(1);
        txtPrecioEditar.setText("50.00");

        // Actualizar hora fin
        actualizarHoraFinEdicion();
    }
    
    private void setPanelEdicionHabilitado(boolean habilitado) {
        spinnerFechaEditar.setEnabled(habilitado);
        spinnerHoraInicioEditar.setEnabled(habilitado);
        spinnerDuracionEditar.setEnabled(habilitado);
        spinnerHoraFinEditar.setEnabled(habilitado);
        txtPrecioEditar.setEnabled(habilitado);
        btnGuardarCambios.setEnabled(habilitado);
        btnLimpiarFormulario.setEnabled(habilitado);

        // Los campos de solo lectura
        txtIdReserva.setEnabled(false); // Siempre deshabilitado (solo lectura)
        txtCanchaEditar.setEnabled(false); // No se puede cambiar la cancha
    }
    
    private LocalDate getFechaFromSpinnerEditar() {
        try {
            java.util.Date fecha = spinnerFechaEditar.getDate();
            if (fecha == null) {
                return null;
            }
            return fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }
    
    private LocalTime getHoraInicioFromSpinnerEditar() {
        try {
            java.util.Date hora = (java.util.Date) spinnerHoraInicioEditar.getValue();
            return hora.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        } catch (Exception e) {
            return LocalTime.of(14, 0); // Hora por defecto
        }
    }
    
    private LocalTime getHoraFinFromSpinnerEditar() {
        try {
            java.util.Date hora = (java.util.Date) spinnerHoraFinEditar.getValue();
            return hora.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
        } catch (Exception e) {
            return LocalTime.of(15, 0); // Hora por defecto
        }
    }
    
    private void setHoraInicioInSpinnerEditar(LocalTime hora) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.HOUR_OF_DAY, hora.getHour());
        cal.set(java.util.Calendar.MINUTE, hora.getMinute());
        spinnerHoraInicioEditar.setValue(cal.getTime());
    }
    
    private void setHoraFinInSpinnerEditar(LocalTime hora) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hora.getHour());
            cal.set(Calendar.MINUTE, hora.getMinute());
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            spinnerHoraFinEditar.setValue(cal.getTime());
        } catch (Exception e) {
            System.err.println("Error en setHoraFinInSpinnerEditar: " + e.getMessage());
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
        jSplitPane1 = new javax.swing.JSplitPane();
        panelFormulario = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        comboFiltroEstado = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaReservas = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        panelDisponibilidad = new javax.swing.JPanel();
        btnGuardarCambios = new javax.swing.JButton();
        btnLimpiarFormulario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdReserva = new javax.swing.JTextField();
        txtCanchaEditar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        spinnerFechaEditar = new org.jdesktop.swingx.JXDatePicker();
        spinnerHoraInicioEditar = new javax.swing.JSpinner();
        spinnerDuracionEditar = new javax.swing.JSpinner();
        spinnerHoraFinEditar = new javax.swing.JSpinner();
        txtPrecioEditar = new javax.swing.JTextField();

        panelSuperior.setBackground(new java.awt.Color(70, 207, 107));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Mis Reservas");

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
                .addGap(267, 267, 267)
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

        jSplitPane1.setDividerLocation(650);
        jSplitPane1.setDividerSize(10);

        panelFormulario.setBackground(new java.awt.Color(194, 241, 205));
        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mis Reservas", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 102, 51))); // NOI18N
        panelFormulario.setPreferredSize(new java.awt.Dimension(400, 0));

        jLabel8.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(68, 165, 54));
        jLabel8.setText("Filtrar por:");

        comboFiltroEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "Pendiente", "Confirmada", "Cancelada" }));

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

        btnActualizar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(70, 207, 107));
        btnActualizar.setText("Actualizar Lista");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(0, 153, 0));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 5, true));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 153, 0));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 5, true));
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(comboFiltroEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnEditar))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(panelFormulario);

        panelDisponibilidad.setBackground(new java.awt.Color(194, 241, 205));
        panelDisponibilidad.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Editar Reserva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 102, 0))); // NOI18N

        btnGuardarCambios.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnGuardarCambios.setForeground(new java.awt.Color(0, 153, 0));
        btnGuardarCambios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        btnGuardarCambios.setText("Guardar");
        btnGuardarCambios.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 5, true));
        btnGuardarCambios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        btnLimpiarFormulario.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnLimpiarFormulario.setForeground(new java.awt.Color(0, 153, 0));
        btnLimpiarFormulario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        btnLimpiarFormulario.setText("Limpiar");
        btnLimpiarFormulario.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 5, true));
        btnLimpiarFormulario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiarFormulario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarFormularioActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 165, 54));
        jLabel1.setText("ID Reserva:");

        jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(68, 165, 54));
        jLabel2.setText("Cancha:");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(68, 165, 54));
        jLabel3.setText("Hora Inicio:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(68, 165, 54));
        jLabel4.setText("Duración:");

        jLabel7.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(68, 165, 54));
        jLabel7.setText("Hora Fin:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(68, 165, 54));
        jLabel5.setText("Precio (S/.):");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(68, 165, 54));
        jLabel6.setText("Fecha");

        spinnerDuracionEditar.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4, 1));

        spinnerHoraFinEditar.setModel(new javax.swing.SpinnerNumberModel(12, 12, 23, 1));

        javax.swing.GroupLayout panelDisponibilidadLayout = new javax.swing.GroupLayout(panelDisponibilidad);
        panelDisponibilidad.setLayout(panelDisponibilidadLayout);
        panelDisponibilidadLayout.setHorizontalGroup(
            panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDisponibilidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDisponibilidadLayout.createSequentialGroup()
                        .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdReserva)
                            .addComponent(txtCanchaEditar)
                            .addComponent(spinnerFechaEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spinnerHoraInicioEditar)
                            .addComponent(spinnerDuracionEditar)
                            .addComponent(spinnerHoraFinEditar)
                            .addComponent(txtPrecioEditar, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panelDisponibilidadLayout.createSequentialGroup()
                        .addComponent(btnGuardarCambios, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiarFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDisponibilidadLayout.setVerticalGroup(
            panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDisponibilidadLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCanchaEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(spinnerFechaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spinnerHoraInicioEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerDuracionEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spinnerHoraFinEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecioEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiarFormulario)
                    .addComponent(btnGuardarCambios))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(panelDisponibilidad);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSuperior, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1088, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volverAlPanel();
    }//GEN-LAST:event_btnVolverActionPerformed
    
    private void btnLimpiarFormularioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarFormularioActionPerformed
        limpiarFormularioEdicion();
    }//GEN-LAST:event_btnLimpiarFormularioActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
        guardarCambiosReserva();
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void tablaReservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaReservasMouseClicked
        if (tablaReservas.getSelectedRow() != -1) {
            actualizarEstadoBotones();

            // Si es doble click, editar automáticamente
            if (evt.getClickCount() == 2) {
                editarReserva();
            }
        }
    }//GEN-LAST:event_tablaReservasMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarMisReservas();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editarReserva();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelarReserva();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JButton btnLimpiarFormulario;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboFiltroEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelDisponibilidad;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JSpinner spinnerDuracionEditar;
    private org.jdesktop.swingx.JXDatePicker spinnerFechaEditar;
    private javax.swing.JSpinner spinnerHoraFinEditar;
    private javax.swing.JSpinner spinnerHoraInicioEditar;
    private javax.swing.JTable tablaReservas;
    private javax.swing.JTextField txtCanchaEditar;
    private javax.swing.JTextField txtIdReserva;
    private javax.swing.JTextField txtPrecioEditar;
    // End of variables declaration//GEN-END:variables
}
