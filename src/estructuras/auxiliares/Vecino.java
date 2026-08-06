package estructuras.auxiliares;

public class Vecino {

    private Object elemento;
    private int etiqueta;

    public Vecino(Object vertice, int etiqueta) {
        this.elemento = vertice;
        this.etiqueta = etiqueta;
    }
    public Object getElemento() { return elemento; }
    public int getEtiqueta() { return etiqueta; }

}
