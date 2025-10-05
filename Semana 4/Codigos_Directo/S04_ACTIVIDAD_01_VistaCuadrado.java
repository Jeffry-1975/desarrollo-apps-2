
package Vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;

public class VistaCuadrado extends javax.swing.JFrame {
    private DefaultListCellRenderer listRenderer;

    public VistaCuadrado() {
        initComponents();
        formulario();
        inicializarElementos();
    }
    
    private void formulario(){
        this.setTitle("Area y Perimetro - Figuras geometricas");
        this.setLocationRelativeTo(null); 
        this.setResizable(false); 
        this.getContentPane().setBackground(new Color(255, 255, 255));
        this.setSize(new Dimension(500,400)); 
        this.panelIngreso.setBackground(new Color(255, 255, 255)); 
        this.panelOperaciones.setBackground(new Color(255, 255, 255));
        this.panelResultado.setBackground(new Color(255, 255, 255));
        this.txtLado.requestFocus();
    }
    private void inicializarElementos(){
        listRenderer = new DefaultListCellRenderer();        
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        cboCategoria.setRenderer(listRenderer);
        this.cboCategoria.addItem("Cuadrado");
        this.cboCategoria.addItem("Triangulo");
        this.cboCategoria.addItem("Circulo");
        
        // Configurar visibilidad inicial
        actualizarCamposFigura();
        
        // Agregar listener para cambiar entre figuras
        cboCategoria.addActionListener(e -> actualizarCamposFigura());
        
        this.cboCategoria.requestFocus();        
    }
    
    private void actualizarCamposFigura() {
        String figura = (String) cboCategoria.getSelectedItem();
        if ("Cuadrado".equals(figura)) {
            lbllado.setText("Lado:");
            lbllado.setVisible(true);
            txtLado.setVisible(true);
            lblbase.setVisible(false);
            txtbase.setVisible(false);
            lblaltura.setVisible(false);
            txtaltura.setVisible(false);
            lblradio.setVisible(false);
            txtradio.setVisible(false);
        } else if ("Triangulo".equals(figura)) {
            lbllado.setText("Lado 1:");
            lbllado.setVisible(true);
            txtLado.setVisible(true);
            lblbase.setVisible(true);
            txtbase.setVisible(true);
            lblaltura.setVisible(true);
            txtaltura.setVisible(true);
            lblbase.setText("Base:");
            lblaltura.setText("Altura:");
            lblradio.setVisible(false);
            txtradio.setVisible(false);
        } else if ("Circulo".equals(figura)) {
            lbllado.setVisible(false);
            txtLado.setVisible(false);
            lblbase.setVisible(false);
            txtbase.setVisible(false);
            lblaltura.setVisible(false);
            txtaltura.setVisible(false);
            lblradio.setVisible(true);
            txtradio.setVisible(true);
        }
    }
    public void valIniciales(){
        this.panelIngreso.setVisible(true);
        this.panelOperaciones.setVisible(true);
        this.panelResultado.setVisible(true);
        this.btnCalcular.setEnabled(false);
        this.btnNuevo.setEnabled(false);
        this.btnSalir.setEnabled(true);
        String seleccion = (String) this.cboCategoria.getSelectedItem();
    }
    
    public void valCalcular(){
        this.panelIngreso.setVisible(true);
        this.panelOperaciones.setVisible(true);
        this.panelResultado.setVisible(true);
        this.btnCalcular.setEnabled(false);
        this.btnNuevo.setEnabled(true);
        this.btnSalir.setEnabled(true);
    }
    
    public void valNuevo(){
        this.panelIngreso.setVisible(true);
        this.panelOperaciones.setVisible(true);
        this.panelResultado.setVisible(true);        
        this.btnCalcular.setEnabled(true);
        this.btnNuevo.setEnabled(true);
        this.btnSalir.setEnabled(true);
        
        // Limpiar los campos
        this.limpiarCampos();
        
        // Actualizar visibilidad según la figura seleccionada
        actualizarCamposFigura();
    }
    
    public double obtenerLado(){
        try {
            return Double.parseDouble(this.txtLado.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public double obtenerBase() {
        try {
            return Double.parseDouble(this.txtbase.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public double obtenerAltura() {
        try {
            return Double.parseDouble(this.txtaltura.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public double obtenerRadio() {
        try {
            return Double.parseDouble(this.txtradio.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public void mostrarResultados(double area, double perimetro){
        this.txtResultado.setText("\tResultados");
        this.txtResultado.append("\n\t-------------");
        this.txtResultado.append("\n\tÁrea: "+String.format("%.2f", area));
        this.txtResultado.append("\n\tPerímetro: "+String.format("%.2f", perimetro));               
    }
    
    public void limpiarCampos(){
        this.txtLado.setText("");
        this.txtbase.setText("");
        this.txtaltura.setText("");
        this.txtradio.setText("");
        this.txtResultado.setText("");
        this.txtLado.requestFocus();
    }    
    
    public void agregarCalcularListener (ActionListener listener){
        this.btnCalcular.addActionListener(listener);        
    }
    
    public void agregarNuevoListener(ActionListener listener) {
        this.btnNuevo.addActionListener(listener);        
    }
    
    public void agregarSalirListener(ActionListener listener) {
        this.btnSalir.addActionListener(listener);        
    }   
    
    public String obtenerCategoriaSeleccionada() {
        return (String) cboCategoria.getSelectedItem();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panelResultado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultado = new javax.swing.JTextArea();
        panelIngreso = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox<>();
        panelOperaciones = new javax.swing.JPanel();
        btnCalcular = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtLado = new javax.swing.JTextField();
        lbllado = new javax.swing.JLabel();
        lblaltura = new javax.swing.JLabel();
        txtaltura = new javax.swing.JTextField();
        lblbase = new javax.swing.JLabel();
        txtbase = new javax.swing.JTextField();
        lblradio = new javax.swing.JLabel();
        txtradio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        txtResultado.setColumns(20);
        txtResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtResultado.setForeground(new java.awt.Color(51, 51, 255));
        txtResultado.setRows(5);
        jScrollPane1.setViewportView(txtResultado);

        javax.swing.GroupLayout panelResultadoLayout = new javax.swing.GroupLayout(panelResultado);
        panelResultado.setLayout(panelResultadoLayout);
        panelResultadoLayout.setHorizontalGroup(
            panelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelResultadoLayout.setVerticalGroup(
            panelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(50, 149, 255));
        jLabel2.setText("Escoga una figura Geometrica:");

        cboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelIngresoLayout = new javax.swing.GroupLayout(panelIngreso);
        panelIngreso.setLayout(panelIngresoLayout);
        panelIngresoLayout.setHorizontalGroup(
            panelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(83, 83, 83)
                .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelIngresoLayout.setVerticalGroup(
            panelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresoLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(panelIngresoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnCalcular.setBackground(new java.awt.Color(50, 100, 204));
        btnCalcular.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCalcular.setForeground(new java.awt.Color(255, 255, 255));
        btnCalcular.setMnemonic('C');
        btnCalcular.setText("Calcular");
        btnCalcular.setToolTipText("Clic para calcular");
        btnCalcular.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(0, 215, 100));
        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setMnemonic('N');
        btnNuevo.setText("Nuevo");
        btnNuevo.setToolTipText("Clic para nuevo");
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 20, 60));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setMnemonic('S');
        btnSalir.setText("Salir");
        btnSalir.setToolTipText("Clic para salir");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("Operaciones");

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel1)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        panelOperacionesLayout.setVerticalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalcular)
                    .addComponent(btnNuevo)
                    .addComponent(btnSalir))
                .addGap(25, 25, 25))
        );

        txtLado.setMinimumSize(new java.awt.Dimension(150, 25));
        txtLado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLadoActionPerformed(evt);
            }
        });
        txtLado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLadoKeyTyped(evt);
            }
        });

        lbllado.setText("Ingrese el lado");

        lblaltura.setText("Ingrese la altura");

        txtaltura.setMinimumSize(new java.awt.Dimension(150, 25));
        txtaltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtalturaActionPerformed(evt);
            }
        });

        lblbase.setText("Ingrese la base");

        txtbase.setMinimumSize(new java.awt.Dimension(150, 25));

        lblradio.setText("Ingrese el radio");

        txtradio.setMinimumSize(new java.awt.Dimension(150, 25));
        txtradio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtradioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbllado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLado, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtaltura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblaltura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtbase, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblbase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtradio, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblradio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbllado)
                    .addComponent(lblaltura)
                    .addComponent(lblbase)
                    .addComponent(lblradio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtradio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtaltura, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtbase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(panelIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        /*float lado;

        if (txtLado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un Valor");
        }else{
            lado = Float.parseFloat(txtLado.getText().trim());
            cua.setLado(lado);
            cua.hallarArea();
            this.txtResultado.setText(String.valueOf(cua.mostrarArea()));
            this.setSize(new Dimension(370,220));
            this.panelResultado.setVisible(true);
            this.btnNuevo.setEnabled(true);
            this.panelResultado.setBackground(new Color(246, 240, 255));
        }
        this.txtLado.requestFocus();
        this.btnCalcular.setEnabled(false);
        this.txtLado.setEnabled(false);*/
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        /*this.txtLado.setText("");
        this.txtResultado.setText("");
        this.setSize(new Dimension(370,170));
        this.panelResultado.setVisible(false);
        this.txtLado.requestFocus();
        this.btnNuevo.setEnabled(false);
        this.btnCalcular.setEnabled(false);
        this.txtLado.setEnabled(true);*/
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        /*int r = JOptionPane.showOptionDialog(this, "Estas seguro de salir..?", "Área - Cuadrado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si Salgo", "No Salgo"}, "No salgo");
        if (r == 0) {
            System.exit(0);
        }
        this.txtLado.requestFocus();*/
    }//GEN-LAST:event_btnSalirActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        valIniciales();
    }//GEN-LAST:event_formWindowActivated

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoriaActionPerformed

    private void txtradioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtradioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtradioActionPerformed

    private void txtalturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtalturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtalturaActionPerformed

    private void txtLadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLadoKeyTyped
        // TODO add your handling code here:
        this.btnCalcular.setEnabled(true);
        char keyChar = evt.getKeyChar();

        if (!Character.isDigit(keyChar) && keyChar != '.') {
            evt.consume();
        }

        if (keyChar == '.' && txtLado.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtLadoKeyTyped

    private void txtLadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLadoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCalcular.doClick();
        }
    }//GEN-LAST:event_txtLadoKeyPressed

    private void txtLadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLadoActionPerformed
        // TODO add your handling code here:
        this.btnCalcular.doClick();
        this.txtLado.setText("");
        this.txtLado.requestFocus();
        this.btnCalcular.setEnabled(false);
        this.btnNuevo.setEnabled(true);
    }//GEN-LAST:event_txtLadoActionPerformed

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
            java.util.logging.Logger.getLogger(VistaCuadrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCuadrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCuadrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCuadrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCuadrado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblaltura;
    private javax.swing.JLabel lblbase;
    private javax.swing.JLabel lbllado;
    private javax.swing.JLabel lblradio;
    private javax.swing.JPanel panelIngreso;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JPanel panelResultado;
    private javax.swing.JTextField txtLado;
    private javax.swing.JTextArea txtResultado;
    private javax.swing.JTextField txtaltura;
    private javax.swing.JTextField txtbase;
    private javax.swing.JTextField txtradio;
    // End of variables declaration//GEN-END:variables
}
