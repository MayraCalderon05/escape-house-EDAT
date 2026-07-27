package estructuras.tdaEspecifico.tablaBusquedaHash;

import tdaDominio.Equipo;

public class NodoHashDicc {
    private String nombreEquipo;
    private Equipo info;
    private NodoHashDicc enlace;

    //Constructores
    public NodoHashDicc(String clave, Equipo info, NodoHashDicc enlace){
        this.nombreEquipo = clave;
        this.info = info;
        this.enlace = enlace;
    }

    //Observadores
    public String getNombreEquipo(){
        return this.nombreEquipo;
    }

    public Equipo getDato(){
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
