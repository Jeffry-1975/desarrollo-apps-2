
package Vista;

import Modelo.Usuario;
import Modelo.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VistaPanelGestionClientes extends javax.swing.JPanel {
    private DefaultTableModel modeloTabla;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Usuario usuarioAdmin;
    
    public VistaPanelGestionClientes(CardLayout cardLayout, JPanel cardPanel, Usuario usuarioAdmin) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.usuarioAdmin = usuarioAdmin;
        setPreferredSize(new Dimension(1000, 700));
        initComponents();
        configurarTabla();
        cargarUsuarios();
    }
    
    private void configurarTabla() {
        //comboFiltroRol.addActionListener(e -> filtrarUsuarios());
        // Crear el modelo con las columnas
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("DNI");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Rol");

        // Asignar el modelo a la tabla
        tablaUsuarios.setModel(modeloTabla);
    }
    
    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT cod_usuario, dni_usuario, nombre_usuario, apellido_usuario, " +
                        "email_usuario, telefono_usuario, rol_usuario " +
                        "FROM usuario ORDER BY rol_usuario, cod_usuario";
            
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("cod_usuario"),
                    rs.getInt("dni_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("apellido_usuario"),
                    rs.getString("email_usuario"),
                    rs.getString("telefono_usuario"),
                    rs.getString("rol_usuario")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filtrarUsuarios() {
        String filtro = comboFiltroRol.getSelectedItem().toString();
        
        if (filtro.equals("TODOS")) {
            cargarUsuarios();
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT cod_usuario, dni_usuario, nombre_usuario, apellido_usuario, " +
                        "email_usuario, telefono_usuario, rol_usuario " +
                        "FROM usuario WHERE rol_usuario = ? ORDER BY cod_usuario";
            
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, filtro);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("cod_usuario"),
                    rs.getInt("dni_usuario"),
                    rs.getString("nombre_usuario"),
                    rs.getString("apellido_usuario"),
                    rs.getString("email_usuario"),
                    rs.getString("telefono_usuario"),
                    rs.getString("rol_usuario")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar usuarios: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearUsuario() {
        if (!validarFormulario()) return;
        
        String codigo = txtCodigo.getText().trim();
        String dniStr = txtDNI.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String rol = comboRol.getSelectedItem().toString();
        
        try {
            int dni = Integer.parseInt(dniStr);
            
            // Verificar si el código ya existe
            if (existeCodigoUsuario(codigo)) {
                JOptionPane.showMessageDialog(this, 
                    "El código de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el DNI ya existe
            if (existeDNIUsuario(dni)) {
                JOptionPane.showMessageDialog(this, 
                    "El DNI ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el email ya existe
            if (existeEmailUsuario(email)) {
                JOptionPane.showMessageDialog(this, 
                    "El email ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear usuario con contraseña por defecto (DNI como contraseña inicial)
            String passwordDefault = dniStr; // Usar DNI como contraseña inicial
            
            try (Connection conexion = ConexionBD.obtenerConexion()) {
                String sql = "INSERT INTO usuario (cod_usuario, dni_usuario, password_usuario, " +
                            "nombre_usuario, apellido_usuario, email_usuario, telefono_usuario, rol_usuario) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                
                stmt.setString(1, codigo);
                stmt.setInt(2, dni);
                stmt.setString(3, passwordDefault);
                stmt.setString(4, nombre);
                stmt.setString(5, apellido);
                stmt.setString(6, email);
                stmt.setString(7, telefono);
                stmt.setString(8, rol);
                
                int filas = stmt.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Usuario creado exitosamente\nContraseña inicial: " + dniStr, 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarUsuarios();
                    nuevoUsuario();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarUsuario() {
        if (!validarFormulario()) return;
        
        String codigo = txtCodigo.getText().trim();
        String dniStr = txtDNI.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String rol = comboRol.getSelectedItem().toString();
        
        // No permitir que el admin se modifique a sí mismo
        if (codigo.equals(usuarioAdmin.getCodUsuario())) {
            JOptionPane.showMessageDialog(this, 
                "No puede modificar sus propios datos desde aquí", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int dni = Integer.parseInt(dniStr);
            
            try (Connection conexion = ConexionBD.obtenerConexion()) {
                // Verificar si el DNI ya existe en otro usuario
                if (existeDNIUsuario(dni, codigo)) {
                    JOptionPane.showMessageDialog(this, 
                        "El DNI ya está registrado por otro usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Verificar si el email ya existe en otro usuario
                if (existeEmailUsuario(email, codigo)) {
                    JOptionPane.showMessageDialog(this, 
                        "El email ya está registrado por otro usuario", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String sql = "UPDATE usuario SET dni_usuario=?, nombre_usuario=?, apellido_usuario=?, " +
                            "email_usuario=?, telefono_usuario=?, rol_usuario=? WHERE cod_usuario=?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                
                stmt.setInt(1, dni);
                stmt.setString(2, nombre);
                stmt.setString(3, apellido);
                stmt.setString(4, email);
                stmt.setString(5, telefono);
                stmt.setString(6, rol);
                stmt.setString(7, codigo);
                
                int filas = stmt.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarUsuarios();
                    nuevoUsuario();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarUsuario() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim() + " " + txtApellido.getText().trim();
        String rol = comboRol.getSelectedItem().toString();
        
        // No permitir eliminar al propio admin
        if (codigo.equals(usuarioAdmin.getCodUsuario())) {
            JOptionPane.showMessageDialog(this, 
                "No puede eliminarse a sí mismo", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si el usuario tiene reservas activas (solo para clientes)
        if (rol.equals("CLIENTE") && tieneReservasActivas(codigo)) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar al cliente " + nombre + 
                " porque tiene reservas activas. Primero cancele sus reservas.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar al usuario " + nombre + " (Código: " + codigo + ")?", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conexion = ConexionBD.obtenerConexion()) {
                String sql = "DELETE FROM usuario WHERE cod_usuario = ?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, codigo);
                
                int filas = stmt.executeUpdate();
                if (filas > 0) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarUsuarios();
                    nuevoUsuario();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarDatosUsuario(String codigo) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM usuario WHERE cod_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                txtCodigo.setText(rs.getString("cod_usuario"));
                txtDNI.setText(String.valueOf(rs.getInt("dni_usuario")));
                txtNombre.setText(rs.getString("nombre_usuario"));
                txtApellido.setText(rs.getString("apellido_usuario"));
                txtEmail.setText(rs.getString("email_usuario"));
                txtTelefono.setText(rs.getString("telefono_usuario"));
                comboRol.setSelectedItem(rs.getString("rol_usuario"));
                
                // Deshabilitar creación y habilitar actualización/eliminación
                btnCrear.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                
                // Si es el propio admin, deshabilitar botones de modificación
                if (codigo.equals(usuarioAdmin.getCodUsuario())) {
                    btnActualizar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                    JOptionPane.showMessageDialog(this, 
                        "Está viendo sus propios datos. No puede modificarlos desde aquí.", 
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarFormulario() {
        if (txtCodigo.getText().trim().isEmpty() || txtDNI.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int dni = Integer.parseInt(txtDNI.getText().trim());
            if (txtDNI.getText().trim().length() != 8) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe ser un número de 8 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!txtEmail.getText().trim().contains("@") || !txtEmail.getText().trim().contains(".")) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un email válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean existeCodigoUsuario(String codigo) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE cod_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
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
    
    private boolean existeDNIUsuario(int dni) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE dni_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
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
    
    private boolean existeDNIUsuario(int dni, String codigoExcluir) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE dni_usuario = ? AND cod_usuario != ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, dni);
            stmt.setString(2, codigoExcluir);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean existeEmailUsuario(String email) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE email_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
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
    
    private boolean existeEmailUsuario(String email, String codigoExcluir) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE email_usuario = ? AND cod_usuario != ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, codigoExcluir);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean tieneReservasActivas(String codigoUsuario) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT COUNT(*) FROM reserva WHERE cod_usuario = ? AND estado_reserva IN ('Pendiente', 'Confirmada')";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, codigoUsuario);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        comboRol.setSelectedIndex(0);
        
        btnCrear.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        tablaUsuarios.clearSelection();
    }
    
    private void nuevoUsuario(){
        txtCodigo.setText("");
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        comboRol.setSelectedIndex(0);
        txtCodigo.setEnabled(true);
        txtCodigo.requestFocus();
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
        jSplitPane1 = new javax.swing.JSplitPane();
        panelFormulario = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboRol = new javax.swing.JComboBox<>();
        btnNuevo = new javax.swing.JButton();
        btnCrear = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        panelTabla = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        comboFiltroRol = new javax.swing.JComboBox<>();

        panelSuperior.setBackground(new java.awt.Color(70, 130, 180));
        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(70, 130, 180), 1, true));
        panelSuperior.setAutoscrolls(true);

        lblTitulo.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Gestión de Usuarios");

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
                .addGap(233, 233, 233)
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

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(10);

        panelFormulario.setBackground(new java.awt.Color(213, 228, 240));
        panelFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Usuario", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N
        panelFormulario.setPreferredSize(new java.awt.Dimension(400, 0));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(70, 130, 180));
        jLabel1.setText("Código Usuario:");

        jLabel2.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(70, 130, 180));
        jLabel2.setText("DNI:");

        jLabel3.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(70, 130, 180));
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(70, 130, 180));
        jLabel4.setText("Apellido:");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(70, 130, 180));
        jLabel5.setText("Email");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(70, 130, 180));
        jLabel6.setText("Rol:");

        comboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CLIENTE", "ADMIN" }));

        btnNuevo.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(0, 102, 204));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 5, true));
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnCrear.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(0, 102, 204));
        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/save.png"))); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 5, true));
        btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(0, 102, 204));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eli.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 5, true));
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(0, 102, 204));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/act.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 5, true));
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(70, 130, 180));
        jLabel8.setText("Teléfono:");

        javax.swing.GroupLayout panelFormularioLayout = new javax.swing.GroupLayout(panelFormulario);
        panelFormulario.setLayout(panelFormularioLayout);
        panelFormularioLayout.setHorizontalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelFormularioLayout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelFormularioLayout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCrear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelFormularioLayout.createSequentialGroup()
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFormularioLayout.createSequentialGroup()
                                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                    .addComponent(txtDNI)
                                    .addComponent(txtCodigo)
                                    .addComponent(txtApellido)
                                    .addComponent(txtEmail)
                                    .addComponent(txtTelefono))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        panelFormularioLayout.setVerticalGroup(
            panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormularioLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnCrear))
                .addGap(18, 18, 18)
                .addGroup(panelFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnEliminar))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(panelFormulario);

        panelTabla.setBackground(new java.awt.Color(213, 228, 240));
        panelTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuarios Registrados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe Print", 1, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaUsuarios.setGridColor(new java.awt.Color(213, 228, 240));
        tablaUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaUsuarios);

        jLabel7.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(70, 130, 180));
        jLabel7.setText("Filtrar por:");

        comboFiltroRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "CLIENTE", "ADMIN" }));

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(comboFiltroRol, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboFiltroRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(panelTabla);

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

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volverAlPanel();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoUsuario();
        btnCrear.setEnabled(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearUsuario();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarUsuario();
        btnCrear.setEnabled(true);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarUsuario();
        btnCrear.setEnabled(true);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tablaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMouseClicked
        if (tablaUsuarios.getSelectedRow() != -1) {
            int fila = tablaUsuarios.getSelectedRow();
            String codigo = modeloTabla.getValueAt(fila, 0).toString();
            cargarDatosUsuario(codigo);
        }
        comboFiltroRol.addActionListener(e -> filtrarUsuarios());
        txtCodigo.setEnabled(false);
        btnCrear.setEnabled(false);
    }//GEN-LAST:event_tablaUsuariosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> comboFiltroRol;
    private javax.swing.JComboBox<String> comboRol;
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
    private javax.swing.JPanel panelFormulario;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
