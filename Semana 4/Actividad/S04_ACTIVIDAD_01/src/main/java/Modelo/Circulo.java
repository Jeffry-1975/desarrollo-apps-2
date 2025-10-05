package Modelo;

public class Circulo {
    private double radio;
    private final double PI = Math.PI;

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }
    
    public double calcularArea() {
        return PI * Math.pow(radio, 2);
    }
    
    public double calcularPerimetro() {
        return 2 * PI * radio;
    }
}
