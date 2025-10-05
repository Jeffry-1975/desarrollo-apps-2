
package Controlador;

import Modelo.Cuadrado;
import Vista.VistaCuadrado;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorCuadrado {
    private final Cuadrado modelo;
    private final VistaCuadrado vista;

    public ControladorCuadrado(Cuadrado modelo, VistaCuadrado vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        this.vista.agregarCalcularListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.valCalcular();
                vista.setSize(new Dimension(370,300));
                calcular();
            }
        });
        
        this.vista.agregarNuevoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.valNuevo();
                vista.setSize(new Dimension(370,170));
                nuevo();
            }
        });
        
        this.vista.agregarSalirListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });
    }
    
    public void calcular(){
        double lado = vista.obtenerLado();
        modelo.setLado(lado);
        double area = modelo.calcularArea();
        double perimetro = modelo.calcularPerimetro();
        vista.mostrarResultados(area, perimetro);
    }
    
    public void nuevo(){
        vista.limpiarCampos();
    }
    
    public void salir(){
        int r = JOptionPane.showOptionDialog(null, "Estas seguro de salir..?", "Área - Cuadrado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si Salgo", "No Salgo"}, "No salgo");
        if (r == 0) {
            System.exit(0);
        }
    }
    
    
}
