package estructuras.tdaEspecifico.tablaBusquedaHash;

import tdaDominio.Equipo;

public class NodoHashDicc {
    private Object clave;
    private Object info;
    private NodoHashDicc enlace;

    //Constructores
    public NodoHashDicc(Object clave, Object info, NodoHashDicc enlace){
        this.clave = clave;
        this.info = info;
        this.enlace = enlace;
    }

    //Observadores
    public Object getClave(){
        return this.clave;
    }

    public Object getInfo(){
        return this.info;
    }

    public NodoHashDicc getEnlace(){
        return this.enlace;
    }

    //Modificadores
    public void setDato(Equipo laInfo){
        this.info = laInfo;
    }

    public void setEnlace(NodoHashDicc enlace){
        this.enlace = enlace;
    }
}
