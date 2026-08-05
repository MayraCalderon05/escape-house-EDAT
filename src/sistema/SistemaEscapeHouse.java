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
