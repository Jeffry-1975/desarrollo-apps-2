    package com.s04_actividad_01;

import Controlador.Controlador_figuras;
import Modelo.Circulo;
import Modelo.Cuadrado;
import Modelo.Triangulo;
import Vista.VistaCuadrado;

public class S04_ACTIVIDAD_01 {
    public static void main(String[] args) {
        Cuadrado modeloCuadrado = new Cuadrado();
        Triangulo modeloTriangulo = new Triangulo();
        Circulo modeloCirculo = new Circulo();
        VistaCuadrado vista = new VistaCuadrado();
        Controlador_figuras controlador = new Controlador_figuras(modeloCuadrado, modeloTriangulo, modeloCirculo, vista);
        vista.setVisible(true);
    }
}