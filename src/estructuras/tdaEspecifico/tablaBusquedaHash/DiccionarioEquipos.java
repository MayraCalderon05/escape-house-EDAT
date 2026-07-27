package estructuras.tdaEspecifico.tablaBusquedaHash;

import estructuras.lineales.Lista;
import tdaDominio.Equipo;

public class DiccionarioEquipos {
    private int TAM;
    private NodoHashDicc[] tabla;
    private int cant;

    public DiccionarioEquipos(int tamanio){
        this.TAM = tamanio;
        this.tabla = new NodoHashDicc[TAM];
        this.cant = 0;
    }

    public boolean insertar(String clave, Equipo info){
        //more
    }

    public boolean eliminar(String clave){
        //may
    }

    public Equipo obtenerInfo(String clave){
        //Sacha puto
    }

    public boolean existeClave(String clave){
        //sacha puto
    }

    public Lista listarClaves(){
        //more
    }

    public Lista listarDatos(){
        //more
    }

    public boolean esVacio(){
        return this.cant == 0;
    }

    public DiccionarioEquipos clone(){
        //may
    }

    public void vaciar(){
        for(int i = 0; i < this.cant; i++){
            this.tabla[i] = null;
        }
    }

    public String toString(){
        //may
    }
}
