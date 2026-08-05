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
            if (!habitacion.getSalidaAlExterior() && entrada.getCodigo() != codigoHabitacion){
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
    private boolean verificarPuntaje(int puntaje){
        return puntaje >= 0 && puntaje <= 100;
    }
    private boolean verificarString(String cadena){
        return cadena != null && !cadena.isEmpty();
    }
    private boolean verificarCodigo(int codigo){
        return codigo >= 0 && codigo <= 25;
    }
    //Create
    public String agregarDesafio(int codigoHab, int puntaje, String nombre, String tipo) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)&&verificarString(nombre)&&verificarString(tipo)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.agregarDesafio(puntaje, nombre, tipo)) {
                    cadena = "Desafio agregado correctamente";
                } else {
                    cadena = "No se ha podido agregar el desafio";
                }
            }
        }
        return cadena;
    }
    //Read
    public String obtenerNombreDesafio(int codigoHab, int puntaje){
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                cadena = habitacion.obtenerNombreDesafio(puntaje);
            }
        }
        return cadena;
    }
    public String obtenerTipoDesafio(int codigoHab, int puntaje){
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                cadena = habitacion.obtenerTipoDesafio(puntaje);
            }
        }
        return cadena;
    }
    //Update
    public String cambiarNombreDesafio(int codigoHab, int puntaje, String nombreDesafio) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)&&verificarString(nombreDesafio)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.cambiarNombreDesafio(puntaje, nombreDesafio)) {
                    cadena = "Se ha cambiado exitosamente el nombre del desafio";
                } else {
                    cadena = "No se ha podido cambiar el nombre del desafio";
                }
            }
        }
        return cadena;
    }
    public String cambiarTipoDesafio(int codigoHab, int puntaje, String tipoDesafio) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)&&verificarString(tipoDesafio)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.cambiarTipoDesafio(puntaje, tipoDesafio)) {
                    cadena = "Se ha cambiado exitosamente el tipo del desafio";
                } else {
                    cadena = "No se ha podido cambiar el tipo del desafio";
                }
            }
        }
        return cadena;
    }
    //Delete
    public String sacarDesafio(int codigoHab, int puntaje) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if (verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.sacarDesafio(puntaje)) {
                    cadena = "Se ha sacado exitosamente el desafio";
                }else{
                    cadena = "No se ha podido sacar el desafio";
                }
            }
        }
        return cadena;
    }

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
    public String mostrarHabitacion(int codigo){
        String cadena = "Habitación no encontrada";
        Habitacion buscado = (Habitacion) this.habitaciones.obtenerInfo(codigo);
        if(buscado != null){
            cadena = buscado.toString();
        }
        return cadena;
    }

    public String minimoPuntaje(int cod1, int cod2){
        Habitacion hab1 = (Habitacion) this.habitaciones.obtenerInfo(cod1);
        Habitacion hab2 = (Habitacion) this.habitaciones.obtenerInfo(cod2);
        StringBuilder cadena = new StringBuilder();
        cadena.append(this.planoCasa.caminoMasCorto(hab1,hab2).toString());
        cadena.append("\nEl puntaje requerido para pasar de la habitacion: ");
        cadena.append(hab1.getNombre()).append(" a la habitacion: ");
        cadena.append(hab2.getNombre()).append("es de: ");
        cadena.append(this.planoCasa.caminoMenorCosto(hab1,hab2));
        return cadena.toString();
    }
  
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
    public String mostrarDesafiosResueltos(String nombreEquipo){
        String resueltos = "Equipo no encontrado";
        Equipo buscado = (Equipo) this.equipos.obtenerInfo(nombreEquipo);
        if(buscado != null){
            //resueltos = buscado.listarDesafios().toString();
        }
        return resueltos;
    }

    //consultas sobre equipos
    public boolean jugarDesafio(String nombreEquipo, int codigoHab, int puntaje){
        Equipo equipo = (Equipo) this.equipos.obtenerInfo(nombreEquipo);
        Habitacion habitacion;
        Desafio desafio;
        Lista aux;
        boolean exito = false;
        if(equipo != null){
            habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if(habitacion != null){
                desafio = habitacion.getDesafio(puntaje);
                if(desafio != null){
                    equipo.acumularPuntaje(puntaje);
                    equipo.acumularPuntajeEnHabitacion(puntaje);
                    aux = this.desafiosResueltosPorEquipo.get(nombreEquipo);
                    aux.insertar(desafio, aux.longitud()+1);
                    this.desafiosResueltosPorEquipo.put(nombreEquipo,aux);
                    exito = true;
                }
            }
        }
        return exito;
    }

    //consultas generales
}
