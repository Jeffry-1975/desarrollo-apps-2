
package com.s04_clase_01;

import Controlador.ControladorCuadrado;
import Modelo.Cuadrado;
import Vista.VistaCuadrado;

public class S04_CLASE_01 {

    public static void main(String[] args) {
        Cuadrado modelo = new Cuadrado();
        VistaCuadrado vista = new VistaCuadrado();
        ControladorCuadrado controlador = new ControladorCuadrado(modelo,vista);
        vista.setVisible(true);
    }
}
