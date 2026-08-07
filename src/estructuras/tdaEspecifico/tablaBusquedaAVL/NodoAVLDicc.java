package estructuras.tdaEspecifico.tablaBusquedaAVL;


import modelo.Equipo;

public class NodoAVLDicc {
    //la clave del desafío era el puntaje
    private Comparable clave;
    //el desafio supongo yo
    private Object info;
    private int altura;
    private NodoAVLDicc hijoIzquierdo;
    private NodoAVLDicc hijoDerecho;

    public NodoAVLDicc(Comparable clave, Object info, NodoAVLDicc hijoIzquierdo, NodoAVLDicc hijoDerecho){
        this.clave = clave;
        this.info = info;
        this.altura = 0;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoIzquierdo;
    }

    public NodoAVLDicc(Comparable clave, Object info){
        this.clave = clave;
        this.info = info;
        this.altura = 0;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    //Observadores
    public Comparable getClave(){
        return this.clave;
    }
    public Object getInfo(){
        return this.info;
    }
    public int getAltura(){
        return this.altura;
    }
    public NodoAVLDicc getHijoIzquierdo(){
        return this.hijoIzquierdo;
    }
    public NodoAVLDicc getHijoDerecho(){
        return this.hijoDerecho;
    }

    //Modificadores
    public void setHijoIzquierdo(NodoAVLDicc hijoIzquierdo){
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDerecho(NodoAVLDicc hijoDerecho){
        this.hijoDerecho = hijoDerecho;
    }
    public void setInfo(Equipo laInfo){
        this.info = laInfo;
    }
    //Propios del Tipo
    public void recalcularAltura(){
        int altIzq = -1;
        int altDer = -1;
        if (this.hijoIzquierdo != null) {
            altIzq = this.hijoIzquierdo.getAltura();
        }
        if (this.hijoDerecho != null){
            altDer = this.hijoDerecho.getAltura();
        }
        this.altura = 1 + Math.max(altIzq, altDer);
    }
}
