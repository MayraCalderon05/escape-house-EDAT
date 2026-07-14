package estructuras.grafo;

public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object nuevoElem){
        this.elem = nuevoElem;
        this.sigVertice = null;
        this.primerAdy = null;
    }
    public NodoVert(Object nuevoElem, NodoVert nuevoSigVert, NodoAdy nuevoPrimerAdy){
        this.elem = nuevoElem;
        this.sigVertice =  nuevoSigVert;
        this.primerAdy = nuevoPrimerAdy;
    }

    //* GETTERS
    public Object getElem(){
        return this.elem;
    }
    public NodoVert getSigVertice(){
        return this.sigVertice;
    }
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }

    //*SETTERS
    public void setElem(Object nuevoElem){
        this.elem = nuevoElem;
    }
    public void setSigVertice(NodoVert nuevoSigVertice){
        this.sigVertice = nuevoSigVertice;
    }
    public void setPrimerAdy(NodoAdy nuevoPrimerAdy){
        this.primerAdy = nuevoPrimerAdy;
    }
}
