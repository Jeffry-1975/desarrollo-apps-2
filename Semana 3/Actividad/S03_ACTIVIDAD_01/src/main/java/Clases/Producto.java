
package Clases;

public class Producto {
    private int cantDocena;
    private double precio;

    public Producto() {
    }

    public Producto(int cantDocena, double precio) {
        this.cantDocena = cantDocena;
        this.precio = precio;
    }

    public int getCantDocena() {
        return cantDocena;
    }

    public void setCantDocena(int cantDocena) {
        this.cantDocena = cantDocena;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
        
    public double calcularImporteActual(){
        double importeActual;
        importeActual = cantDocena * precio;
        return importeActual;
    }
    
    public double calcularDescuento(){
        double desc;
        if (cantDocena < 10) {
            desc = calcularImporteActual() * 0.10;
        }else {
            desc = calcularImporteActual() * 0.20;            
        }
        return desc;
    }
    
    public double calcularImporteFinal(){
        double importeFinal;
        importeFinal = calcularImporteActual() - calcularDescuento();
        return importeFinal;
    }
    
    public int calculoRegaloLapicero(){
        int lapiceroRegalado;
        if (calcularImporteActual() < 200) {
            lapiceroRegalado = 0;
        }else{
            lapiceroRegalado = cantDocena * 2;
        }
        return lapiceroRegalado;
    }
    
    public double mostrarImporteActual(){
        return calcularImporteActual();
    }
    
    public double mostrarDescuento(){
        return calcularDescuento();
    }
    
    public double mostrarImporteFinal(){
        return calcularImporteFinal();
    }
    
    public int mostrarRegaloLapicero(){
        return calculoRegaloLapicero();
    }
    
}
