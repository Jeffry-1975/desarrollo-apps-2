
package Vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class VistaRegistro extends javax.swing.JFrame {
    private VistaLogin vistaLogin;
    public VistaRegistro(VistaLogin vistaLogin) {
        this.vistaLogin = vistaLogin;
        initComponents();
        formulario();
        setupCustomUI();
    }
    
    private void formulario(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Registro - Sistema de Gestión de Notas");
        this.setLocationRelativeTo(null); //APARECE LA VENTANA CENTRADA
        this.setResizable(false); //EVITA QUE SE ESTIRE O CONTRAIGA LA VENANA
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
        iconoGrande.setBounds(100, 180, 275, 120);
        panelImagen.add(iconoGrande);
        
        JLabel lblBienvenida = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBounds(50, 320, 375, 40);
        panelImagen.add(lblBienvenida);
        
        JLabel lblMensaje = new JLabel("Únete a nuestra plataforma", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(new Color(255, 255, 255, 200));
        lblMensaje.setBounds(50, 365, 375, 30);
        panelImagen.add(lblMensaje);               
            
        //panelDatos      
        
        panelDatos.setBackground(Color.WHITE); //EL COLOR DE FONDO DEL PANEL CALCULAR
        
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        txtContraseña.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        txtConfirmarContraseña.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        chkAceptarTerminos.setBackground(Color.WHITE);
        chkAceptarTerminos.setFocusPainted(false);
        
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(new EmptyBorder(12, 0, 12, 0));
        
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(new EmptyBorder(12, 0, 12, 0));      
        
        panelImagen.setPreferredSize(new Dimension(450,0));
        panelDatos.setPreferredSize(new Dimension(500,700));

        
        //agregar paneles a mainPanel
        mainPanel.add(panelImagen, BorderLayout.WEST);
        mainPanel.add(panelDatos, BorderLayout.EAST);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
        );
        pack();
                
        this.mainPanel.revalidate();
        this.mainPanel.repaint();  
        
        
    }
    
    private void setupCustomUI() {
        // Efectos hover en campos de texto
        addFocusEffect(txtNombre);
        addFocusEffect(txtUsuario);
        addFocusEffect(txtEmail);
        addFocusEffect(txtContraseña);
        addFocusEffect(txtConfirmarContraseña);
        
        // Efecto hover en botón Registrar
        btnRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegistrar.setBackground(new Color(124, 77, 231));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistrar.setBackground(new Color(139, 92, 246));
            }
        });
        
        // Efecto hover en botón Volver
        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnVolver.setForeground(new Color(124, 77, 231));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnVolver.setForeground(new Color(139, 92, 246));
            }
        });
        
        // Evento botón Registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRegistro();
            }
        });
        
        // Evento botón Volver
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlLogin();
            }
        });
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
    
    private void realizarRegistro() {
        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtContraseña.getPassword());
        String confirmPassword = new String(txtConfirmarContraseña.getPassword());
        
        // Validación de campos vacíos
        if (nombre.isEmpty() || usuario.isEmpty() || email.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            txtNombre.requestFocus();
            return;
        }
        
        // Validación de términos
        if (!chkAceptarTerminos.isSelected()) {
            mostrarError("Debe aceptar los términos y condiciones");
            return;
        }
        
        // Validación de contraseñas
        if (!password.equals(confirmPassword)) {
            mostrarError("Las contraseñas no coinciden");
            txtContraseña.setText("");
            txtConfirmarContraseña.setText("");
            txtContraseña.requestFocus();
            return;
        }
        
        // Validación longitud de contraseña
        if (password.length() < 6) {
            mostrarError("La contraseña debe tener al menos 6 caracteres");
            return;
        }
        
        // Validación de email básica
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarError("Por favor ingrese un correo electrónico válido");
            return;
        }
        
        // Aquí implementarías la lógica de registro en base de datos
        mostrarExito("¡Usuario registrado exitosamente!\n\n" +
            "Nombre: " + nombre + "\n" +
            "Usuario: " + usuario + "\n" +
            "Email: " + email);
        
        // Volver al login después del registro
        volverAlLogin();
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Error de validación", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            "Registro exitoso", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void volverAlLogin() {
        vistaLogin.setVisible(true);
        this.dispose();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelDatos = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();
        chkAceptarTerminos = new javax.swing.JCheckBox();
        lblUsuario = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        txtEmail = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        lblContraseña = new javax.swing.JLabel();
        lblConfirmarContraseña = new javax.swing.JLabel();
        txtConfirmarContraseña = new javax.swing.JPasswordField();
        panelImagen = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVolver.setBackground(new java.awt.Color(255, 153, 153));
        btnVolver.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnRegistrar.setBackground(new java.awt.Color(204, 0, 153));
        btnRegistrar.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Crear Cuenta");
        btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        chkAceptarTerminos.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        chkAceptarTerminos.setForeground(new java.awt.Color(204, 0, 153));
        chkAceptarTerminos.setText("Acepto los Términos y Condiciones");
        chkAceptarTerminos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblUsuario.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(204, 0, 153));
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N
        lblUsuario.setText("Usuario:");

        txtNombre.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(204, 0, 153));
        lblNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblNombre.setText("Nombre Completo:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Completa el formulario para crear tu cuenta");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 153));
        jLabel1.setText("REGISTRO DE USUARIO");

        lblEmail.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(204, 0, 153));
        lblEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/correo-electronico.png"))); // NOI18N
        lblEmail.setText("Email:");

        txtContraseña.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        txtUsuario.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblContraseña.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblContraseña.setForeground(new java.awt.Color(204, 0, 153));
        lblContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pass.png"))); // NOI18N
        lblContraseña.setText("Contraseña");

        lblConfirmarContraseña.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblConfirmarContraseña.setForeground(new java.awt.Color(204, 0, 153));
        lblConfirmarContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pass.png"))); // NOI18N
        lblConfirmarContraseña.setText("Confirmar Contraseña:");

        txtConfirmarContraseña.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosLayout.createSequentialGroup()
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail)
                            .addComponent(txtUsuario)
                            .addComponent(txtContraseña, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelDatosLayout.createSequentialGroup()
                                .addComponent(lblNombre)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtConfirmarContraseña))
                        .addContainerGap())
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkAceptarTerminos)
                            .addComponent(lblConfirmarContraseña)
                            .addComponent(lblContraseña)
                            .addComponent(lblEmail)
                            .addComponent(lblUsuario))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosLayout.createSequentialGroup()
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosLayout.createSequentialGroup()
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(panelDatosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)))
                        .addGap(79, 79, 79))))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContraseña)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConfirmarContraseña)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtConfirmarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAceptarTerminos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(btnRegistrar))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
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
                .addComponent(panelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaRegistro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new VistaRegistro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JCheckBox chkAceptarTerminos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblConfirmarContraseña;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelImagen;
    private javax.swing.JPasswordField txtConfirmarContraseña;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
