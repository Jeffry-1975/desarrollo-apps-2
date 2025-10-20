
package Vista;

import DAO.UsuarioDAO;
import Modelo.ConexionBD;
import Modelo.Usuario;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaRegistro extends javax.swing.JPanel {
    private JDialog dialogParent; // Referencia al diálogo que contiene este panel
    
    // Cambiar constructor
    public VistaRegistro(JDialog parent) {
        this.dialogParent = parent;
        initComponents();
        formulario();
        setupCustomUI();
    }
    
     public VistaRegistro() {
        initComponents();
        formulario();
        
    }
    
    private void formulario(){        
        this.mainPanel.setLayout(new BorderLayout()); // Cambiamos a BorderLayout
        //this.mainPanel.setLayout(new GridLayout(1, 2, 0, 0));
        this.setSize(new Dimension(950,600)); //AJUSTA EL TAMAÑO DE LA VENTANA(EJE X-HORIZONTAL; EJE Y-VERTICAL)
        this.mainPanel.removeAll();
        
        //panelImagen
        
        panelImagen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradiente moderno
                GradientPaint gp = new GradientPaint(0, 0, new Color(139, 92, 246), 
                                                      0, getHeight(), new Color(236, 72, 153));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Círculos decorativos
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.fillOval(-80, -80, 250, 250);
                g2d.fillOval(280, 400, 300, 300);
                g2d.fillOval(120, 250, 180, 180);
                g2d.fillOval(50, 500, 150, 150);
            }
        };
        
        panelImagen.setLayout(null);
        
        JLabel iconoGrande = new JLabel("✨", SwingConstants.CENTER);
        iconoGrande.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        iconoGrande.setForeground(Color.WHITE);
        iconoGrande.setBounds(50, 130, 275, 120);
        panelImagen.add(iconoGrande);
        
        JLabel lblBienvenida = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBounds(0, 270, 375, 40);
        panelImagen.add(lblBienvenida);
        
        JLabel lblMensaje = new JLabel("Únete a nuestra plataforma", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(new Color(255, 255, 255, 200));
        lblMensaje.setBounds(0, 315, 375, 30);
        panelImagen.add(lblMensaje);               
            
        //panelDatos      
        
        panelDatos.setBackground(Color.WHITE); //EL COLOR DE FONDO DEL PANEL CALCULAR
        
        txtCodigo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));  
        
        txtDni.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        )); 
        
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        )); 
        
        chkMostrarPassword.setBackground(Color.WHITE);
        chkMostrarPassword.setFocusPainted(false);
        
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));  
        
        txtApellido.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        )); 
        
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        txtTelefono.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        /*cboRol.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));*/
        
        
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(new EmptyBorder(12, 0, 12, 0));
        
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(new EmptyBorder(12, 0, 12, 0));      
        
        panelImagen.setPreferredSize(new Dimension(400,0));
        panelDatos.setPreferredSize(new Dimension(400,500));
        
        //agregar paneles a mainPanel
        mainPanel.add(panelImagen, BorderLayout.WEST);
        mainPanel.add(panelDatos, BorderLayout.EAST);        
        
                
        this.mainPanel.revalidate();
        this.mainPanel.repaint();          
    }
    
    private void setupCustomUI() {
        // Efectos hover en campos de texto
        addFocusEffect(txtCodigo);
        addFocusEffect(txtDni);
        addFocusEffect(txtPassword);
        addFocusEffect(txtNombre);
        addFocusEffect(txtApellido);
        addFocusEffect(txtEmail);
        addFocusEffect(txtTelefono);
        
    }
    
    private void addFocusEffect(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(139, 92, 246), 2, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 221, 225), 2, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
    }
    
    private void registrarUsuario() {
        // Obtener datos
        String codigo = txtCodigo.getText().trim();
        String dniStr = txtDni.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String rol = (String) cboRol.getSelectedItem();
        
        // Validar campos vacíos
        if (codigo.isEmpty() || dniStr.isEmpty() || password.isEmpty() || 
            nombre.isEmpty() || apellido.isEmpty() || 
            email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }        
        
        // Validar longitud de contraseña
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe tener al menos 6 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar DNI
        int dni;
        try {
            dni = Integer.parseInt(dniStr);
            if (dniStr.length() != 8) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El DNI debe ser un número de 8 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar formato de código
        String prefijoEsperado = rol.equals("ADMIN") ? "ADM" : "CLI";
        if (!codigo.startsWith(prefijoEsperado)) {
            JOptionPane.showMessageDialog(this, 
                "El código debe empezar con: " + prefijoEsperado, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar email
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un email válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            
            // Verificar si el código ya existe
            if (usuarioDAO.existeCodigo(codigo)) {
                JOptionPane.showMessageDialog(this, 
                    "El código de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el DNI ya existe
            if (usuarioDAO.existeDNI(dni)) {
                JOptionPane.showMessageDialog(this, 
                    "El DNI ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el email ya existe
            if (usuarioDAO.existeEmail(email)) {
                JOptionPane.showMessageDialog(this, 
                    "El email ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear objeto Usuario
            Usuario usuario = new Usuario(
                codigo, dni, password, nombre, apellido, email, telefono, Usuario.RolUsuario.valueOf(rol)
            );
            
            // Registrar en BD
            if (usuarioDAO.registrarUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, 
                    "Usuario registrado exitosamente!\nCódigo: " + codigo + 
                    "\nGuarde su código para iniciar sesión", 
                    "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                volverALogin(); // Volver automáticamente al login después del registro
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error de conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDni.setText("");
        txtPassword.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        cboRol.setSelectedIndex(0);
    }
    
    private void volverALogin() {
        // Cerrar el diálogo de registro
        if (dialogParent != null) {
            dialogParent.dispose();
        } else {
            // Fallback: buscar el JDialog o JFrame padre
            Container parent = this.getParent();
            while (parent != null && !(parent instanceof JDialog)) {
                parent = parent.getParent();
            }
            if (parent instanceof JDialog) {
                ((JDialog) parent).dispose();
            }
        }
        limpiarCampos();
        
    }
    
    public void limpiarFormulario() {
        limpiarCampos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelDatos = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        lblContraseña = new javax.swing.JLabel();
        lblNombre1 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblNombre2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        lblNombre3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre5 = new javax.swing.JLabel();
        cboRol = new javax.swing.JComboBox<>();
        txtTelefono = new javax.swing.JTextField();
        lblEmail2 = new javax.swing.JLabel();
        chkMostrarPassword = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        panelImagen = new javax.swing.JPanel();

        btnVolver.setBackground(new java.awt.Color(255, 153, 153));
        btnVolver.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnRegistrar.setBackground(new java.awt.Color(204, 0, 153));
        btnRegistrar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Crear Cuenta");
        btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtNombre.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(204, 0, 153));
        lblNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblNombre.setText("Nombres:");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 153));
        jLabel1.setText("REGISTRO DE USUARIO");

        lblEmail.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(204, 0, 153));
        lblEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/correo-electronico.png"))); // NOI18N
        lblEmail.setText("Email:");

        txtPassword.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblContraseña.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblContraseña.setForeground(new java.awt.Color(204, 0, 153));
        lblContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pass.png"))); // NOI18N
        lblContraseña.setText("Contraseña:");

        lblNombre1.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblNombre1.setForeground(new java.awt.Color(204, 0, 153));
        lblNombre1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblNombre1.setText("Apellidos:");

        txtApellido.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblNombre2.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblNombre2.setForeground(new java.awt.Color(204, 0, 153));
        lblNombre2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblNombre2.setText("Dni:");

        txtDni.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblNombre3.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblNombre3.setForeground(new java.awt.Color(204, 0, 153));
        lblNombre3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblNombre3.setText("Codigo:");

        txtCodigo.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblNombre5.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblNombre5.setForeground(new java.awt.Color(204, 0, 153));
        lblNombre5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblNombre5.setText("Rol:");

        cboRol.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        cboRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "CLIENTE" }));

        txtTelefono.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblEmail2.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblEmail2.setForeground(new java.awt.Color(204, 0, 153));
        lblEmail2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/telefono.png"))); // NOI18N
        lblEmail2.setText("Teléfono:");

        chkMostrarPassword.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        chkMostrarPassword.setForeground(new java.awt.Color(204, 0, 153));
        chkMostrarPassword.setText("Mostrar Contraseña");
        chkMostrarPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chkMostrarPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMostrarPasswordActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Rellene sus Credenciales");

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelDatosLayout.createSequentialGroup()
                                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre3)
                                    .addComponent(lblNombre)
                                    .addComponent(lblEmail))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblContraseña)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(12, 12, 12)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelDatosLayout.createSequentialGroup()
                                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre1)
                                    .addComponent(lblNombre2)
                                    .addComponent(lblEmail2)
                                    .addComponent(lblNombre5))
                                .addGap(149, 149, 149))
                            .addComponent(txtDni)
                            .addComponent(txtApellido)
                            .addComponent(txtTelefono)
                            .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addComponent(chkMostrarPassword)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(82, 82, 82))
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre3)
                    .addComponent(lblNombre2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(lblNombre1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(lblEmail2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContraseña)
                    .addComponent(lblNombre5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMostrarPassword)
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnVolver))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );
        panelImagenLayout.setVerticalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        registrarUsuario();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        volverALogin();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void chkMostrarPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMostrarPasswordActionPerformed
        if (chkMostrarPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('•');
        }
    }//GEN-LAST:event_chkMostrarPasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cboRol;
    private javax.swing.JCheckBox chkMostrarPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmail2;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblNombre2;
    private javax.swing.JLabel lblNombre3;
    private javax.swing.JLabel lblNombre5;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelImagen;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
