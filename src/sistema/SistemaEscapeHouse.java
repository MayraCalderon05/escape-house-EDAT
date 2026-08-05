package sistema;

import estructuras.auxiliares.Vecino;
import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.tdaEspecifico.tablaBusquedaAVL.DiccionarioAvl;
import estructuras.tdaEspecifico.tablaBusquedaHash.DiccionarioHash;
import modelo.*;

import java.util.HashMap;

public class SistemaEscapeHouse {
    //asumo que en lo que es el sistema los datos ya vienen limpios, se validan en el main

    private Grafo planoCasa;
    private DiccionarioAvl habitaciones;
    private DiccionarioHash equipos;
    private final Habitacion entrada;
    //el primer tipo de parametro corresponde a la clave del equipo
    private HashMap<String, Lista> desafiosResueltosPorEquipo;

    //definicion de puntajes como reglas para nuestro juego
    private static int PUNTAJE_FACIL = 200;
    private static int PUNTAJE_MEDIO = 400;
    private static int PUNTAJE_DIFICIL = 600;

    //constructor
    public SistemaEscapeHouse( ){
        this.planoCasa = new Grafo();
        this.habitaciones = new DiccionarioAvl();
        this.equipos = new DiccionarioHash(17);
        this.desafiosResueltosPorEquipo = new HashMap<>();
        //* PENDIENTE  cargar las habitaciones, plano y desafíos antes de asignar la primera habitación


        this.entrada = asignarPrimerHabitacion();
    }

    //asignaciones principales
    //el metodo no puede ser static porque usa una variable de instancia
    private Habitacion asignarPrimerHabitacion(){
        return (Habitacion) this.habitaciones.obtenerInfo(0);
    }
    private int calcularPuntajeExigido(int dificultad){
        int puntaje;

        switch (dificultad){
            case 3:
                puntaje = PUNTAJE_DIFICIL;
                break;
            case 2:
                puntaje = PUNTAJE_MEDIO;
                break;
            default:
                puntaje = PUNTAJE_FACIL;
                break;
        }
        return puntaje;
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
    //CREATE
    public boolean crearEquipo(String nombre, int dificultad){
        boolean exito = false;

        int puntajeExigido = calcularPuntajeExigido(dificultad);
        Equipo nuevoEquipo = new Equipo(nombre, puntajeExigido, this.entrada);
        exito = this.equipos.insertar(nombre, nuevoEquipo);


        return exito;
    }
    //READ
    private Equipo obtenerEquipo(String nombre){
        return (Equipo) this.equipos.obtenerInfo(nombre);
    }
    public String mostrarInfoEquipo(String nombre){
        return (obtenerEquipo(nombre)).toString();
    }
    //UPDATE
    public boolean actualizarEquipo(String nombre, int nuevaDificultad){
        boolean exito = false;
        Equipo encontrado = obtenerEquipo(nombre);

        if (encontrado != null){
            int nuevoPuntajeExigido = calcularPuntajeExigido(nuevaDificultad);
            encontrado.setPuntajeParaSalida(nuevoPuntajeExigido);
            exito = true;
        }
        return exito;
    }
    //DELETE
    public boolean eliminarEquipo(String nombre){
        return equipos.eliminar(nombre);
    }


    // consultas sobre habitaciones
    public Lista habitacionesContiguas(int codigoHabitacion){

        //lista que se va a devolver
        Lista habitacionesConSusPuntajes = new Lista();
        //nodos adyacentes de la habitación
        Lista vecinos = planoCasa.obtenerVecinos(codigoHabitacion);
        Habitacion habitacion;
        int puntaje;


        while (!vecinos.esVacia()){
            StringBuilder info = new StringBuilder();

            //voy recuperando la posición 1 para recuperar el nombre y codigo de la habitacion
            Vecino adyacente = (Vecino) vecinos.recuperar(1);
            habitacion = (Habitacion) adyacente.getElemento();
            puntaje = adyacente.getEtiqueta();

            info.append(habitacion.getCodigo()).append(" - ").append(habitacion.getNombre());
            info.append(" (Se necesitan: ").append(puntaje).append(" puntos).");

            //agrego la información a la lista
            habitacionesConSusPuntajes.insertar(info.toString(), habitacionesConSusPuntajes.longitud()+1);
            //saco el elemento de la lista original
            vecinos.eliminar(1);
        }

        return habitacionesConSusPuntajes;

    }

    //consultas sobre desafíos

    //consultas sobre equipos

    //consultas generales
}
