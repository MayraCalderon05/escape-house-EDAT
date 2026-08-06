package estructuras.auxiliares;

public class ParAuxiliar {

    private Object elemento;
    private int etiqueta;

    public ParAuxiliar(Object vertice, int etiqueta) {
        this.elemento = vertice;
        this.etiqueta = etiqueta;
    }
    public Object getElemento() { return elemento; }
    public int getEtiqueta() { return etiqueta; }
    public void setElemento(Object nuevo){
        this.elemento = nuevo;
    }
    public void setEtiqueta(int nueva){
        this.etiqueta = nueva;
    }

}
