package estructuras.grafo;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;

    public NodoAdy(NodoVert nuevoVertice){
        this.vertice = nuevoVertice;
        this.sigAdyacente = null;
        this.etiqueta = 0;
    }
    public NodoAdy(NodoVert nuevoVertice, int nuevaEtiqueta){
        this.vertice = nuevoVertice;
        this.sigAdyacente = null;
        this.etiqueta = nuevaEtiqueta;
    }
    public NodoAdy(NodoVert nuevoVertice, NodoAdy nuevoSigAdyacente, int nuevaEtiqueta){
            this.vertice = nuevoVertice;
            this.sigAdyacente = nuevoSigAdyacente;
            this.etiqueta = nuevaEtiqueta;
    }

    //* GETTERS
    public NodoVert getVertice(){
        return this.vertice;
    }
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    public int getEtiqueta(){
        return this.etiqueta;
    }

    //* SETTERS
    public void setVertice( NodoVert nuevoVertice){
        this.vertice = nuevoVertice;
    }
    public void setSigAdyacente( NodoAdy nuevoSigAdyacente){
        this.sigAdyacente = nuevoSigAdyacente;
    }
    public void setEtiqueta(int nuevaEtiqueta){
        this.etiqueta = nuevaEtiqueta;
    }
}
