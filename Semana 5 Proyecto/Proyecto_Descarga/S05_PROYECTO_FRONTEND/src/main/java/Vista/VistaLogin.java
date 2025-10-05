
package Vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class VistaLogin extends javax.swing.JFrame {

    public VistaLogin() {
        initComponents();
        formulario();
        setupCustomUI();
    }
    
    
    private void formulario(){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Login - Sistema de Gestión de Notas");
        this.setLocationRelativeTo(null); //APARECE LA VENTANA CENTRADA
        this.setResizable(false); //EVITA QUE SE ESTIRE O CONTRAIGA LA VENANA
        this.mainPanel.setLayout(new BorderLayout()); // Cambiamos a BorderLayout
        //this.mainPanel.setLayout(new GridLayout(1, 2, 0, 0));
        this.setSize(new Dimension(700,500)); //AJUSTA EL TAMAÑO DE LA VENTANA(EJE X-HORIZONTAL; EJE Y-VERTICAL)
        this.mainPanel.removeAll();
        
        //panelImagen
        
        panelImagen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradiente moderno
                GradientPaint gp = new GradientPaint(0, 0, new Color(99, 110, 250),0, getHeight(), new Color(139, 92, 246));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Círculos decorativos
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(-50, -50, 200, 200);
                g2d.fillOval(250, 350, 250, 250);
                g2d.fillOval(100, 200, 150, 150);
            }
        };
        
        panelImagen.setLayout(null);
        
        JLabel iconoGrande = new JLabel("👤", SwingConstants.CENTER);
        iconoGrande.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
        iconoGrande.setForeground(Color.WHITE);
        iconoGrande.setBounds(100, 150, 250, 150);
        panelImagen.add(iconoGrande);
        
        JLabel lblBienvenida = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBounds(50, 300, 350, 40);
        panelImagen.add(lblBienvenida);
        
        JLabel lblMensaje = new JLabel("Inicia sesión para continuar", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setForeground(new Color(255, 255, 255, 200));
        lblMensaje.setBounds(50, 345, 350, 30);
        panelImagen.add(lblMensaje);                
            
        //panelDatos      
        
        panelDatos.setBackground(Color.WHITE); //EL COLOR DE FONDO DEL PANEL CALCULAR
        
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        txtContraseña.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 221, 225), 2, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        chkMostrarPassword.setBackground(Color.WHITE);
        chkMostrarPassword.setFocusPainted(false);
        
        btnInicioSesion.setFocusPainted(false);
        btnInicioSesion.setBorder(new EmptyBorder(12, 0, 12, 0));
        
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setBorder(new EmptyBorder(12, 0, 12, 0));      
        
        panelImagen.setPreferredSize(new Dimension(450,0));
        
        //agregar paneles a mainPanel
        mainPanel.add(panelImagen, BorderLayout.WEST);
        mainPanel.add(panelDatos);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
        );
        pack();
                
        this.mainPanel.revalidate();
        this.mainPanel.repaint();   
        txtUsuario.requestFocus();
    }
    
    private void setupCustomUI() {
        // Efecto hover en botón Login
        btnInicioSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnInicioSesion.setBackground(new Color(79, 90, 230));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnInicioSesion.setBackground(new Color(99, 110, 250));
            }
        });
        
        // Efecto hover en botón Registrar
        btnRegistrarse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRegistrarse.setForeground(new Color(79, 90, 230));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistrarse.setForeground(new Color(99, 110, 250));
            }
        });
        
        // Efecto focus en campos de texto
        txtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(99, 110, 250), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 221, 225), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        txtContraseña.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtContraseña.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(99, 110, 250), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                txtContraseña.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 221, 225), 2, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        // Evento mostrar/ocultar contraseña
        chkMostrarPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chkMostrarPassword.isSelected()) {
                    txtContraseña.setEchoChar((char) 0);
                } else {
                    txtContraseña.setEchoChar('•');
                }
            }
        });
        
        // Evento botón Login
        btnInicioSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        // Evento botón Registrar
        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVentanaRegistro();
            }
        });
        
        // Enter en password ejecuta login
        txtContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }
    
    private void realizarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtContraseña.getPassword());
        
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos", 
                "Campos vacíos", 
                JOptionPane.WARNING_MESSAGE);
            txtUsuario.requestFocus();
            return;
        }
        
        // Aquí implementarías la lógica de autenticación con base de datos
        JOptionPane.showMessageDialog(this, 
            "¡Login exitoso!\nBienvenido: " + usuario, 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar campos
        txtUsuario.setText("");
        txtContraseña.setText("");
    }   
    
    private void abrirVentanaRegistro() {
        VistaRegistro vistaRegistro = new VistaRegistro(this);
        vistaRegistro.setVisible(true);
        this.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelImagen = new javax.swing.JPanel();
        panelDatos = new javax.swing.JPanel();
        btnRegistrarse = new javax.swing.JButton();
        btnInicioSesion = new javax.swing.JButton();
        chkMostrarPassword = new javax.swing.JCheckBox();
        txtContraseña = new javax.swing.JPasswordField();
        lblContraseña = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );
        panelImagenLayout.setVerticalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnRegistrarse.setBackground(new java.awt.Color(153, 153, 255));
        btnRegistrarse.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnRegistrarse.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarse.setText("Registrarse");
        btnRegistrarse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnInicioSesion.setBackground(new java.awt.Color(102, 102, 255));
        btnInicioSesion.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        btnInicioSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnInicioSesion.setText("Iniciar Sesión");
        btnInicioSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        chkMostrarPassword.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        chkMostrarPassword.setForeground(new java.awt.Color(102, 102, 255));
        chkMostrarPassword.setText("Mostrar Contraseña");
        chkMostrarPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtContraseña.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblContraseña.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblContraseña.setForeground(new java.awt.Color(102, 102, 255));
        lblContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pass.png"))); // NOI18N
        lblContraseña.setText("Contraseña:");

        txtUsuario.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(102, 102, 255));
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/avatar.png"))); // NOI18N
        lblUsuario.setText("Usuario:");

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Ingrese sus Credenciales");

        jLabel1.setFont(new java.awt.Font("Segoe Print", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("INICIAR SESIÓN");

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkMostrarPassword)
                            .addComponent(lblContraseña))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtContraseña, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDatosLayout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(btnInicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelDatosLayout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel1)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContraseña)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkMostrarPassword)
                .addGap(18, 18, 18)
                .addComponent(btnInicioSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegistrarse)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInicioSesion;
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JCheckBox chkMostrarPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelImagen;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
