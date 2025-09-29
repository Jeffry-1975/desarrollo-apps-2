
package Clases;

public class Pasaje {
    private int cantidad, categoria=1;
    private double importeCompra;
    private double descuento;
    private double importePagar;
    private double carameloRegalado;
    private double precioPasajeMañana = 37.5;
    private double precioPasajeNoche = 40;
    
    public Pasaje() {
    }

    public Pasaje(int cantidad, double importeCompra, double descuento, double importePagar, double carameloRegalado) {
        this.cantidad = cantidad;        
        this.importeCompra = importeCompra;
        this.descuento = descuento;
        this.importePagar = importePagar;
        this.carameloRegalado = carameloRegalado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public double getImporteCompra() {
        return importeCompra;
    }

    public void setImporteCompra(double importeCompra) {
        this.importeCompra = importeCompra;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getImportePagar() {
        return importePagar;
    }

    public void setImportePagar(double importePagar) {
        this.importePagar = importePagar;
    }

    public double getCarameloRegalado() {
        return carameloRegalado;
    }

    public void setCarameloRegalado(double carameloRegalado) {
        this.carameloRegalado = carameloRegalado;
    }

    public double getPrecioPasajeMañana() {
        return precioPasajeMañana;
    }

    public void setPrecioPasajeMañana(double precioPasajeMañana) {
        this.precioPasajeMañana = precioPasajeMañana;
    }

    public double getPrecioPasajeNoche() {
        return precioPasajeNoche;
    }

    public void setPrecioPasajeNoche(double precioPasajeNoche) {
        this.precioPasajeNoche = precioPasajeNoche;
    }
               
    public void calcularImporteCompra(){         
        switch (this.categoria){
            case 1:
                this.importeCompra = this.cantidad * this.precioPasajeMañana;
                break;
            case 2:
                this.importeCompra = this.cantidad * this.precioPasajeNoche;
                break;
        }
    }
    
    public void calcularDescuento(){        
        if (this.cantidad < 15) {
            this.descuento = this.importeCompra * .05;
        }else{
            this.descuento = this.importeCompra * .08;
        }
    }
    
    public void calcularImportePagar(){
        this.importePagar = this.importeCompra - this.descuento;
    }
    
    public void caramelosRegalados(){
        if (this.importePagar > 200) {
            this.carameloRegalado = this.cantidad * 2;
        }else{
            this.carameloRegalado = 0;
        }        
    }      
    
}
