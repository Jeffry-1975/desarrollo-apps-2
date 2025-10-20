//REVISAR LOGICA
package Vista;

import Modelo.Usuario;
import Modelo.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;

public class VistaPanelNuevaReserva extends javax.swing.JPanel {
    DefaultTableModel modeloTablaDisponibilidad = new DefaultTableModel();
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioCliente;
    
    public VistaPanelNuevaReserva(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioCliente) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioCliente = usuarioCliente;
        setPreferredSize(new Dimension(1000, 700));
        initComponents();
        configurarSpinners();
        configurarTabla();
        cargarCanchasDisponibles();
        verDisponibilidadAutomatica();
    }
    
    private void configurarSpinners() {
        // Configurar spinner de hora inicio
        SpinnerDateModel horaInicioModel = new SpinnerDateModel();
        spinnerHoraInicio.setModel(horaInicioModel);
        JSpinner.DateEditor horaInicioEditor = new JSpinner.DateEditor(spinnerHoraInicio, "HH:mm");
        spinnerHoraInicio.setEditor(horaInicioEditor);

        // Configurar spinner de hora fin
        SpinnerDateModel horaFinModel = new SpinnerDateModel();
        spinnerHoraFin.setModel(horaFinModel);
        JSpinner.DateEditor horaFinEditor = new JSpinner.DateEditor(spinnerHoraFin, "HH:mm");
        spinnerHoraFin.setEditor(horaFinEditor);
        spinnerHoraFin.setEnabled(false); // Se calcula automáticamente

        // Establecer fecha por defecto: hoy - FORMA CORRECTA
        java.util.Date fechaHoy = new java.util.Date();
        spinnerFecha.setDate(fechaHoy);

        // Establecer hora por defecto: 14:00
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        spinnerHoraInicio.setValue(cal.getTime());

        // Calcular hora fin inicial
        actualizarHoraFinYDisponibilidad();
    }
    
    private void configurarTabla() {
        // Crear el modelo con las columnas
        modeloTablaDisponibilidad = new DefaultTableModel();
        modeloTablaDisponibilidad.addColumn("Hora");
        modeloTablaDisponibilidad.addColumn("Disponibilidad");
        modeloTablaDisponibilidad.addColumn("Estado");
        
        // Asignar el modelo a la tabla
        tablaDisponibilidad.setModel(modeloTablaDisponibilidad);
        
        // Configurar listeners
        spinnerFecha.addActionListener(e -> verDisponibilidadAutomatica());
        comboCanchas.addActionListener(e -> verDisponibilidadAutomatica());
        spinnerHoraInicio.addChangeListener(e -> actualizarHoraFinYDisponibilidad());
        spinnerDuracion.addChangeListener(e -> actualizarHoraFinYDisponibilidad());

        // Listener para selección en tabla
        tablaDisponibilidad.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaDisponibilidad.getSelectedRow() != -1) {
                seleccionarHorarioDeTabla();
            }
        });
    }
    
    private void cargarCanchasDisponibles() {
        comboCanchas.removeAllItems();
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT cod_cancha, nombre_cancha, precio_hora FROM cancha WHERE estado_cancha = 'Disponible'";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String precioHora = String.format("%.2f", rs.getDouble("precio_hora"));
                comboCanchas.addItem(rs.getString("cod_cancha") + " - " + 
                                   rs.getString("nombre_cancha") + " (S/." + precioHora + "/hora)");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar canchas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarHoraFinYDisponibilidad() {
        if (spinnerHoraInicio.getValue() != null && spinnerDuracion.getValue() != null) {
            try {
                LocalTime horaInicio = getHoraInicioFromSpinner();
                int duracion = (int) spinnerDuracion.getValue();
                LocalTime horaFin = horaInicio.plusHours(duracion);
                setHoraFinInSpinner(horaFin);
                
                calcularPrecio();
                verDisponibilidadAutomatica();
            } catch (Exception e) {
                // Ignorar errores temporales
            }
        }
    }
    
    private void calcularPrecio() {
        if (comboCanchas.getSelectedItem() == null) return;
        
        try {
            String canchaSeleccionada = comboCanchas.getSelectedItem().toString();
            // Extraer precio por hora del texto del combo
            String[] partes = canchaSeleccionada.split("S/.");
            if (partes.length > 1) {
                String precioStr = partes[1].split("/")[0].trim();
                double precioHora = Double.parseDouble(precioStr);
                int duracion = (int) spinnerDuracion.getValue();
                double precioTotal = precioHora * duracion;
                txtPrecio.setText(String.format("%.2f", precioTotal));
            }
        } catch (Exception e) {
            // Si hay error, mantener el precio actual
        }
    }
    
    private void verDisponibilidadAutomatica() {
        if (comboCanchas.getSelectedItem() == null || spinnerFecha.getDate() == null) {
            return;
        }
        
        String canchaSeleccionada = comboCanchas.getSelectedItem().toString().split(" - ")[0];
        LocalDate fecha = getFechaFromSpinner();
        
        if (fecha != null) {
            cargarDisponibilidadDelDia(canchaSeleccionada, fecha);
        }
    }
    
    private void cargarDisponibilidadDelDia(String cancha, LocalDate fecha) {
        modeloTablaDisponibilidad.setRowCount(0);
        
        // Generar horarios del día (8:00 - 22:00)
        for (int hora = 8; hora <= 22; hora++) {
            LocalTime horaInicio = LocalTime.of(hora, 0);
            LocalTime horaFin = horaInicio.plusHours(1);
            
            boolean disponible = estaDisponible(cancha, fecha.toString(), 
                                              horaInicio.toString(), horaFin.toString());
            
            String estado = disponible ? "DISPONIBLE" : "OCUPADO";
            
            modeloTablaDisponibilidad.addRow(new Object[]{
                horaInicio.toString() + " - " + horaFin.toString(),
                disponible ? "✓ Libre" : "✗ Ocupado",
                estado
            });
        }
    }
    
    private void seleccionarHorarioDeTabla() {
        int fila = tablaDisponibilidad.getSelectedRow();
        if (fila != -1) {
            String horario = modeloTablaDisponibilidad.getValueAt(fila, 0).toString();
            String[] horas = horario.split(" - ");
            
            if (horas.length == 2) {
                try {
                    LocalTime horaInicio = LocalTime.parse(horas[0]);
                    setHoraInicioInSpinner(horaInicio);
                    // Por defecto 1 hora de duración
                    spinnerDuracion.setValue(1);
                } catch (Exception e) {
                    // Ignorar errores de parsing
                }
            }
        }
    }
    
    private void verDisponibilidadCompleta() {
        if (comboCanchas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una cancha", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // VERIFICAR QUE LA FECHA NO SEA NULL
        if (spinnerFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String canchaSeleccionada = comboCanchas.getSelectedItem().toString().split(" - ")[0];
        LocalDate fecha = getFechaFromSpinner();
        
        if (fecha == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT horaInicio_reserva, horaFin_reserva, estado_reserva FROM reserva " +
                        "WHERE cod_cancha = ? AND fecha_reserva = ? AND estado_reserva IN ('Pendiente', 'Confirmada') " +
                        "ORDER BY horaInicio_reserva";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, canchaSeleccionada);
            stmt.setString(2, fecha.toString());
            ResultSet rs = stmt.executeQuery();
            
            StringBuilder disponibilidad = new StringBuilder("Horarios ocupados para " + fecha + ":\n\n");
            boolean hayReservas = false;
            
            while (rs.next()) {
                hayReservas = true;
                disponibilidad.append(rs.getTime("horaInicio_reserva")).append(" - ")
                            .append(rs.getTime("horaFin_reserva"))
                            .append(" (").append(rs.getString("estado_reserva")).append(")\n");
            }
            
            if (!hayReservas) {
                disponibilidad.append("No hay reservas para esta fecha. Cancha disponible todo el día.");
            }
            
            JOptionPane.showMessageDialog(this, disponibilidad.toString(), 
                "Disponibilidad Completa - " + fecha, JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver disponibilidad: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearReserva() {
        if (!validarFormulario()) return;
        
        String canchaSeleccionada = comboCanchas.getSelectedItem().toString().split(" - ")[0];
        LocalDate fecha = getFechaFromSpinner();
        LocalTime horaInicio = getHoraInicioFromSpinner();
        LocalTime horaFin = getHoraFinFromSpinner();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        
        // Validar que no sea fecha pasada
        if (fecha.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(this, 
                "No se pueden hacer reservas para fechas pasadas", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar horario de funcionamiento
        if (horaInicio.isBefore(LocalTime.of(8, 0)) || horaFin.isAfter(LocalTime.of(22, 0))) {
            JOptionPane.showMessageDialog(this, 
                "El horario de funcionamiento es de 8:00 a 22:00", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            // Verificar disponibilidad
            if (!estaDisponible(canchaSeleccionada, fecha.toString(), 
                              horaInicio.toString(), horaFin.toString())) {
                JOptionPane.showMessageDialog(this, 
                    "La cancha no está disponible en el horario seleccionado", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String sql = "INSERT INTO reserva (cod_usuario, cod_cancha, fecha_reserva, " +
                        "horaInicio_reserva, horaFin_reserva, estado_reserva, precio_reserva) " +
                        "VALUES (?, ?, ?, ?, ?, 'Pendiente', ?)";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            
            stmt.setString(1, usuarioCliente.getCodUsuario());
            stmt.setString(2, canchaSeleccionada);
            stmt.setString(3, fecha.toString());
            stmt.setString(4, horaInicio.toString());
            stmt.setString(5, horaFin.toString());
            stmt.setDouble(6, precio);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Reserva creada exitosamente!\n\n" +
                    "Cancha: " + canchaSeleccionada + "\n" +
                    "Fecha: " + fecha + "\n" +
                    "Horario: " + horaInicio + " - " + horaFin + "\n" +
                    "Precio: S/." + precio + "\n\n" +
                    "Estado: Pendiente de aprobación", 
                    "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear reserva: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean estaDisponible(String cancha, String fecha, String horaInicio, String horaFin) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM reserva " +
                        "WHERE cod_cancha = ? AND fecha_reserva = ? " +
                        "AND estado_reserva IN ('Pendiente', 'Confirmada') " +
                        "AND ((horaInicio_reserva <= ? AND horaFin_reserva > ?) " +
                        "OR (horaInicio_reserva < ? AND horaFin_reserva >= ?) " +
                        "OR (horaInicio_reserva >= ? AND horaFin_reserva <= ?))";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, cancha);
            stmt.setString(2, fecha);
            stmt.setString(3, horaInicio);
            stmt.setString(4, horaInicio);
            stmt.setString(5, horaFin);
            stmt.setString(6, horaFin);
            stmt.setString(7, horaInicio);
            stmt.setString(8, horaFin);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean validarFormulario() {
        if (comboCanchas.getSelectedItem() == null || spinnerFecha.getDate() == null ||
            spinnerHoraInicio.getValue() == null || txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Verificar que la fecha no sea null
        if (getFechaFromSpinner() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        spinnerFecha.setDate(new java.util.Date()); // Usar java.util.Date explícitamente
        setHoraInicioInSpinner(LocalTime.of(14, 0));
        spinnerDuracion.setValue(1);
        txtPrecio.setText("50.00");
        modeloTablaDisponibilidad.setRowCount(0);
    }
    
    private LocalDate getFechaFromSpinner() {
        try {
            java.util.Date fecha = spinnerFecha.getDate();
            if (fecha == null) {
                return null;
            }
            return fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }
    
    private LocalTime getHoraInicioFromSpinner() {
        java.util.Date hora = (java.util.Date) spinnerHoraInicio.getValue();
        return hora.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
    }
    
    private LocalTime getHoraFinFromSpinner() {
        java.util.Date hora = (java.util.Date) spinnerHoraFin.getValue();
        return hora.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
    }
    
    private void setHoraInicioInSpinner(LocalTime hora) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hora.getHour());
        cal.set(Calendar.MINUTE, hora.getMinute());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        spinnerHoraInicio.setValue(cal.getTime());
    }
    
    private void setHoraFinInSpinner(LocalTime hora) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hora.getHour());
        cal.set(Calendar.MINUTE, hora.getMinute());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        spinnerHoraFin.setValue(cal.getTime());
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnVerDisponibilidad = new javax.swing.JButton();
        btnReservar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        comboCanchas = new javax.swing.JComboBox<>();
        spinnerDuracion = new javax.swing.JSpinner();
        spinnerHoraFin = new javax.swing.JSpinner();
        txtPrecio = new javax.swing.JTextField();
        btnCalcularPrecio = new javax.swing.JButton();
        spinnerHoraInicio = new javax.swing.JSpinner();
        spinnerFecha = new org.jdesktop.swingx.JXDatePicker();
        panelDisponibilidad = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDisponibilidad = new javax.swing.JTable();

        panelSuperior.setBackground(new java.awt.Color(70, 207, 107));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Nueva Reserva");

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
                .addGap(269, 269, 269)
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

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(10);

        panelFormulario.setBackground(new java.awt.Color(194, 241, 205));
        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la Reserva", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 102, 51))); // NOI18N
        panelFormulario.setPreferredSize(new java.awt.Dimension(400, 0));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 165, 54));
        jLabel1.setText("Cancha:");

        jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(68, 165, 54));
        jLabel2.setText("Fecha:");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(68, 165, 54));
        jLabel3.setText("Hora Inicio:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(68, 165, 54));
        jLabel4.setText("Duración:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(68, 165, 54));
        jLabel5.setText("Precio (S/.):");

        btnVerDisponibilidad.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnVerDisponibilidad.setForeground(new java.awt.Color(0, 153, 0));
        btnVerDisponibilidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        btnVerDisponibilidad.setText("Ver Disponibilidad");
        btnVerDisponibilidad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 5, true));
        btnVerDisponibilidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerDisponibilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDisponibilidadActionPerformed(evt);
            }
        });

        btnReservar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnReservar.setForeground(new java.awt.Color(0, 153, 0));
        btnReservar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        btnReservar.setText("Reservar");
        btnReservar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 5, true));
        btnReservar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(68, 165, 54));
        jLabel7.setText("Hora Fin:");

        spinnerDuracion.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4, 1));

        spinnerHoraFin.setModel(new javax.swing.SpinnerNumberModel(12, 12, 23, 1));

        btnCalcularPrecio.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        btnCalcularPrecio.setText("Calcular");
        btnCalcularPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFormularioLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCanchas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinnerDuracion)
                    .addComponent(spinnerHoraFin, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFormularioLayout.createSequentialGroup()
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCalcularPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                    .addComponent(spinnerFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinnerHoraInicio))
                .addGap(32, 32, 32))
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnVerDisponibilidad, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(btnReservar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboCanchas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spinnerFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spinnerHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinnerDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(spinnerHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(btnCalcularPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addComponent(btnVerDisponibilidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReservar)
                .addContainerGap(206, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(panelFormulario);

        panelDisponibilidad.setBackground(new java.awt.Color(194, 241, 205));
        panelDisponibilidad.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Disponibilidad de la Fecha Seleccionada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 102, 0))); // NOI18N

        tablaDisponibilidad.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDisponibilidad.setGridColor(new java.awt.Color(213, 228, 240));
        tablaDisponibilidad.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaDisponibilidad.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaDisponibilidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDisponibilidadMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDisponibilidad);

        javax.swing.GroupLayout panelDisponibilidadLayout = new javax.swing.GroupLayout(panelDisponibilidad);
        panelDisponibilidad.setLayout(panelDisponibilidadLayout);
        panelDisponibilidadLayout.setHorizontalGroup(
            panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
        );
        panelDisponibilidadLayout.setVerticalGroup(
            panelDisponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDisponibilidadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(panelDisponibilidad);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1))
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

    private void btnVerDisponibilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDisponibilidadActionPerformed
        verDisponibilidadCompleta();
        btnReservar.setEnabled(true);
    }//GEN-LAST:event_btnVerDisponibilidadActionPerformed

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarActionPerformed
        crearReserva();
    }//GEN-LAST:event_btnReservarActionPerformed

    private void tablaDisponibilidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDisponibilidadMouseClicked
        if (tablaDisponibilidad.getSelectedRow() != -1) {
            seleccionarHorarioDeTabla();
        }
        //btnCrear.setEnabled(false);
    }//GEN-LAST:event_tablaDisponibilidadMouseClicked

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volverAlPanel();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnCalcularPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularPrecioActionPerformed
        calcularPrecio();
    }//GEN-LAST:event_btnCalcularPrecioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcularPrecio;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnVerDisponibilidad;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboCanchas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelDisponibilidad;
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JSpinner spinnerDuracion;
    private org.jdesktop.swingx.JXDatePicker spinnerFecha;
    private javax.swing.JSpinner spinnerHoraFin;
    private javax.swing.JSpinner spinnerHoraInicio;
    private javax.swing.JTable tablaDisponibilidad;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
