
package Modelo;

public class Cuadrado {
    private double lado;
    private double area;
    private double perimetro;
    public Cuadrado() {
    }

    public Cuadrado(double lado) {
        this.lado = lado;
    }

    public double getLado() {
        return lado;
    }

    public void setLado(double lado) {
        this.lado = lado;
    }
    
    public double calcularArea(){
        return lado * lado;
    }
    
    public double calcularPerimetro(){
        return 4 * lado;
    }

    public double getArea() {
        return this.area;
    }

    public double getPerimetro() {
        return this.perimetro;
    }
    
    
}
