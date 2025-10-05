package Controlador;

import Modelo.Circulo;
import Modelo.Cuadrado;
import Modelo.Triangulo;
import Vista.VistaCuadrado;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class Controlador_figuras {
    private final Cuadrado modeloCuadrado;
    private final Triangulo modeloTriangulo;
    private final Circulo modeloCirculo;
    private final VistaCuadrado vista;

    public Controlador_figuras(Cuadrado modeloCuadrado, Triangulo modeloTriangulo, Circulo modeloCirculo, VistaCuadrado vista) {
        this.modeloCuadrado = modeloCuadrado;
        this.modeloTriangulo = modeloTriangulo;
        this.modeloCirculo = modeloCirculo;
        this.vista = vista;
        
        this.vista.agregarCalcularListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.valCalcular();
                vista.setSize(new Dimension(500, 400));
                calcular();
            }
        });
        
        this.vista.agregarNuevoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.valNuevo();
                vista.setSize(new Dimension(500, 400));
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

    public void ejecutarLogica(String figuraSeleccionada) {
        try {
            switch (figuraSeleccionada) {
                case "Cuadrado" -> {
                    double lado = vista.obtenerLado();
                    this.modeloCuadrado.setLado(lado);
                    double areaC = this.modeloCuadrado.calcularArea();
                    double perimetroC = this.modeloCuadrado.calcularPerimetro();
                    vista.mostrarResultados(areaC, perimetroC);
                    return;
                }
                
                case "Triangulo" -> {
                    double base = vista.obtenerBase();
                    double altura = vista.obtenerAltura();
                    // Para simplificar, asumimos un triángulo isósceles
                    this.modeloTriangulo.setBase(base);
                    this.modeloTriangulo.setAltura(altura);
                    // Calculamos los lados iguales usando el teorema de Pitágoras
                    double ladoIgual = Math.sqrt(Math.pow(base/2, 2) + Math.pow(altura, 2));
                    this.modeloTriangulo.setLado1(base);
                    this.modeloTriangulo.setLado2(ladoIgual);
                    this.modeloTriangulo.setLado3(ladoIgual);
                    
                    double areaT = this.modeloTriangulo.calcularArea();
                    double perimetroT = this.modeloTriangulo.calcularPerimetro();
                    vista.mostrarResultados(areaT, perimetroT);
                    return;
                }
                
                case "Circulo" -> {
                    double radio = vista.obtenerRadio();
                    this.modeloCirculo.setRadio(radio);
                    double areaCirculo = this.modeloCirculo.calcularArea();
                    double perimetroCirculo = this.modeloCirculo.calcularPerimetro();
                    vista.mostrarResultados(areaCirculo, perimetroCirculo);
                    return;
                }
                
                default -> JOptionPane.showMessageDialog(vista, 
                    "Selección de figura no válida.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, 
                "Por favor ingrese valores numéricos válidos.", 
                "Error de entrada", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void calcular() {
        String seleccion = this.vista.obtenerCategoriaSeleccionada();
        ejecutarLogica(seleccion);
    }
    
    public void nuevo() {
        vista.limpiarCampos();
    }
    
    public void salir() {
        int r = JOptionPane.showOptionDialog(
            null, 
            "¿Estás seguro de salir?", 
            "Área - Figuras", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            new Object[]{"Sí, salir", "No, quedarme"}, 
            "No, quedarme"
        );
        if (r == 0) {
            System.exit(0);
        }
    }
}