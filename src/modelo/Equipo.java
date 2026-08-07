package modelo;

import estructuras.lineales.Lista;

import java.util.HashMap;

public class Equipo {
    private String nombreEquipo;
    private int puntajeParaSalida;
    private int puntajeAcumulado;
    private Habitacion habitacionActual;
    private int puntajeAcumuladoEnHabitacion;
    private HashMap<Integer, Lista> desafiosResueltos;

    //constructores
    public Equipo(String nombre){
        this.nombreEquipo = nombre;
        this.puntajeParaSalida = 0;
        this.puntajeAcumulado = 0;
        this.habitacionActual = null;
        this.puntajeAcumuladoEnHabitacion = 0;
        this.desafiosResueltos = new HashMap<Integer, Lista>();
    }
    public Equipo(String nombre, int puntParaSalir){
        this.nombreEquipo = nombre;
        this.puntajeParaSalida = puntParaSalir;
        this.puntajeAcumulado = 0;
        this.habitacionActual = null;
        this.puntajeAcumuladoEnHabitacion = 0;
        this.desafiosResueltos = new HashMap<Integer, Lista>();
    }
    public Equipo(String nombre, int puntParaSalir, Habitacion actual){
        this.nombreEquipo = nombre;
        this.puntajeParaSalida = puntParaSalir;
        this.puntajeAcumulado = 0;
        this.habitacionActual = actual;
        this.puntajeAcumuladoEnHabitacion = 0;
        this.desafiosResueltos = new HashMap<Integer, Lista>();
    }
    public Equipo(String nombre, int puntParaSalir, int puntAcum, Habitacion habActual, int puntActual){
        this.nombreEquipo = nombre;
        this.puntajeParaSalida = puntParaSalir;
        this.puntajeAcumulado = puntAcum;
        this.habitacionActual = habActual;
        this.puntajeAcumuladoEnHabitacion = puntActual;
        this.desafiosResueltos = new HashMap<Integer, Lista>();
    }

    //getters
    public String getNombreEquipo() {
        return this.nombreEquipo;
    }
    public int getPuntajeParaSalida() {
        return this.puntajeParaSalida;
    }
    public int getPuntajeAcumulado() {
        return this.puntajeAcumulado;
    }
    public Habitacion getHabitacionActual() {
        return this.habitacionActual;
    }
    public int getPuntajeAcumuladoEnHabitacion() {
        return this.puntajeAcumuladoEnHabitacion;
    }
    public HashMap<Integer, Lista> getDesafiosResueltos(){ return this.desafiosResueltos; }

    //setters
    public void setPuntajeParaSalida(int puntaje) {
        this.puntajeParaSalida = puntaje;
    }
    public void acumularPuntaje(int puntaje) {
        this.puntajeAcumulado += puntaje;
    }
    public void descontarPuntaje(int puntaje) {
        this.puntajeAcumulado -= puntaje;
    }
    public void cambiarHabitacionActual(Habitacion hab) {
        this.habitacionActual = hab;
    }
    public void agregarDesafioResuelto(int codigoHabitacion, Desafio unDesafio){
        Lista desafios = this.desafiosResueltos.get(codigoHabitacion);
        if (desafios == null){
            desafios = new Lista();
            this.desafiosResueltos.put(codigoHabitacion, desafios);
        }
        desafios.insertar(unDesafio, desafios.longitud()+1);
    }

    public void acumularPuntajeEnHabitacion(int puntaje) {
        this.puntajeAcumuladoEnHabitacion += puntaje;
    }
    public void reiniciarPuntajeEnHabitacion( ) {
        this.puntajeAcumuladoEnHabitacion = 0;
    }

    public String toString() {
        return "Nombre: "+this.nombreEquipo+"\n" +
                "Puntaje para la salida: "+this.puntajeParaSalida+"\n"+
                "Puntaje acumulado en total: "+this.puntajeAcumulado+"\n"+
                "Habitacion actual: "+this.habitacionActual+"\n"+
                "Puntaje aucmulado en la habitacion: "+this.puntajeAcumuladoEnHabitacion+"\n";
    }

    public boolean equals(Equipo otroEquipo) {
        return this.nombreEquipo.equals(otroEquipo.nombreEquipo);
    }
}
