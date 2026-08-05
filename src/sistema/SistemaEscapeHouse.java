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

    private Habitacion esPosibleModificar(int codigoHabitacion){
        //recupero la lista de todos los equipos en el juego
        Lista todosLosEquipos = this.equipos.listarDatos();
        //obtengo el objeto Habitacion a partir de su clave
        Habitacion habitacion = (Habitacion) habitaciones.obtenerInfo(codigoHabitacion);

        //primero compruebo que la habitacion existe
        if (habitacion != null){
            //luego compruebo que no sea de salida ni de entrada
            if (!habitacion.getSalidaAlExterior() && habitacionDeEntrada.getCodigo != codigoHabitacion){
                //luego recorro toda la lista de equipos para comprobar que no hay ningun equipo en esa habiracion
                while (habitacion != null && !todosLosEquipos.esVacia()){
                    Equipo equipo = (Equipo) todosLosEquipos.recuperar(1);
                    todosLosEquipos.eliminar(1);
                    //si encontro un equipo que esta en la habitacion a eliminar, no se puede eliminar
                    if (equipo.getHabitacionActual().getCodigo() == codigoHabitacion ){
                        habitacion = null;
                    }
                }
            } else {
                habitacion = null;
            }
        }


        return habitacion;
    }

    public boolean eliminarHabitacion(int codigoHabitacion) {
        boolean exito = false;
        //obtengo el objeto Habitacion
        Habitacion habitacion = esPosibleModificar(codigoHabitacion);
        //si la habitacion es distinta de null, quiere decir que es posible eliminar la habitacion;
        if (habitacion != null){
            //elimino la habitacion del diccionario
            habitaciones.eliminar(codigoHabitacion);
            //elimino la habitacion en el grafo
            planoCasa.eliminarVertice(habitacion);
            exito = true;
        }
            return exito;
    }

    public boolean ModificarHabitacion(int codigoHabitacion, Habitacion aux){

    }

    //CRUD Desafíos

    // CRUD Equipos

    // consultas sobre habitaciones

    //consultas sobre desafíos

    //consultas sobre equipos

    //consultas generales
}
