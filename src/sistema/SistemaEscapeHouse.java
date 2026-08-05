package sistema;

import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.tdaEspecifico.tablaBusquedaAVL.DiccionarioAvl;
import estructuras.tdaEspecifico.tablaBusquedaHash.DiccionarioHash;
import modelo.*;
import persistencia.*;

import java.util.HashMap;

public class SistemaEscapeHouse {

    private Grafo planoCasa;
    private DiccionarioAvl habitaciones;
    private DiccionarioHash equipos;
    //el primer tipo de parametro corresponde a la clave del equipo
    private HashMap<String, Lista> desafiosResueltosPorEquipo;

    //constructor
    public SistemaEscapeHouse( ){
        this.planoCasa = new Grafo();
        this.habitaciones = new DiccionarioAvl();
        this.equipos = new DiccionarioHash(17);
        this.desafiosResueltosPorEquipo = new HashMap<>();
    }

    //CRUD Habitaciones

    //CRUD Desafíos

    // CRUD Equipos

    // consultas sobre habitaciones
    public String mostrarHabitacion(int codigo){
        Habitacion aux = (Habitacion) this.habitaciones.obtenerInfo(codigo);
        return aux.toString();
    }

    public String minimoPuntaje(int cod1, int cod2){
        Habitacion hab1 = (Habitacion) this.habitaciones.obtenerInfo(cod1);
        Habitacion hab2 = (Habitacion) this.habitaciones.obtenerInfo(cod2);
        return this.planoCasa.caminoMasCorto(hab1,hab2).toString() + "\n El puntaje requerido para pasar de la habitacion: "+hab1.getNombre()+" a la habitacion: "+hab2.getNombre()+ "es de: ";
    }

    //consultas sobre desafíos

    //consultas sobre equipos

    //consultas generales
}
