package modelo;

import estructuras.lineales.Lista;
import estructuras.tdaEspecifico.tablaBusquedaAVL.DiccionarioAvl;
//pendiente: CRUD de desafíos
public class Habitacion {

    private int codigo;
    private String nombre;
    private int planta;
    private int metrosCuadrados;
    private boolean salidaAlExterior;
    private DiccionarioAvl desafios;

    //Constructores
    public Habitacion(int codigo, String nombre, int planta, int metrosCuadrados, boolean salidaAlExterior) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.salidaAlExterior = salidaAlExterior;
        this.desafios = new DiccionarioAvl();
    }
    public Habitacion(int codigo, String nombre, int planta, int metrosCuadrados, boolean salidaAlExterior, DiccionarioAvl desafios) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.salidaAlExterior = salidaAlExterior;
        this.desafios = desafios;
    }
    public Habitacion(int codigo, String nombre, int planta, int metrosCuadrados) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.salidaAlExterior = false;
        this.desafios = new DiccionarioAvl();
    }

    //Observadores
    public int getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public int getPlanta() {
        return planta;
    }
    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }
    public boolean getSalidaAlExterior() {
        return salidaAlExterior;
    }
    public DiccionarioAvl getDesafios() {
        return desafios;
    }

    //Modificadores
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPlanta(int planta) {
        this.planta = planta;
    }
    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }
    public void setSalidaAlExterior(boolean salidaAlExterior) {
        this.salidaAlExterior = salidaAlExterior;
    }
    public void setDesafios(DiccionarioAvl desafios){
        this.desafios = desafios;
    }

    //propios del tipo
    public String toString() {
        String rta = "No";
        if (this.salidaAlExterior) rta = "Si";
        return "Codigo: " + this.codigo + "\n"+
                "Nombre: " + this.nombre + "\n"+
                "Planta: " + this.planta + "\n"+
                "Metros Cuadrados: " + this.metrosCuadrados + "\n"+
                "Salida al exterior: " + rta + "\n";
    }

    public boolean equals(Habitacion habitacion) {
        return habitacion.codigo == this.codigo;
    }

    //CRUD Desafios
    //Create
    public boolean agregarDesafio(int puntaje, String nombre, String tipo) {
        return this.desafios.insertar(puntaje,new Desafio(puntaje,nombre,tipo));    //Crear un nuevo Desafio y lo añade al diccionario de la Habitacion
    }
    //Read
    public String obtenerNombreDesafio(int puntaje){
        String nombre = "Desafio no encontrado";
        Desafio buscado = (Desafio) this.desafios.obtenerInfo(puntaje); //Busca el desafio que tiene la clave ingresada
        if(buscado != null){                    //Pregunta si se encontro el desafio
            nombre = buscado.getNombre();
        }
        return nombre;
    }
    public String obtenerTipoDesafio(int puntaje){
        String nombre = "Desafio no encontrado";
        Desafio buscado = (Desafio) this.desafios.obtenerInfo(puntaje); //Busca el desafio que tiene la clave ingresada
        if(buscado != null){                    //Pregunta si se encontro el desafio
            nombre = buscado.getTipo();
        }
        return nombre;
    }
    public Desafio getDesafio(int puntaje){
        return (Desafio) this.desafios.obtenerInfo(puntaje);
    }
    public Lista getListaClaves(){
        return this.desafios.listarClaves();
    }
    public Lista getListaDatos(){
        return this.desafios.listarDatos();
    }
    //Update
    public boolean cambiarNombreDesafio(int puntaje, String nombreDesafio) {
        boolean exito = false;                  //Si no se encuentra el desafio, devuelve false
        Desafio buscado = (Desafio) this.desafios.obtenerInfo(puntaje); //Busca el desafio que tiene la clave ingresada
        if(buscado != null){                    //Pregunta si se encontro el desafio
            buscado.setNombre(nombreDesafio);   //Si se encontro, le cambia el nombre
            exito = true;                       //Devuelve true
        }
        return exito;
    }
    public boolean cambiarTipoDesafio(int puntaje, String tipoDesafio) {
        boolean exito = false;                  //Si no se encuentra el desafio, devuelve false
        Desafio buscado = (Desafio) this.desafios.obtenerInfo(puntaje); //Busca el desafio que tiene la clave ingresada
        if(buscado != null){                    //Pregunta si se encontro el desafio
            buscado.setNombre(tipoDesafio);   //Si se encontro, le cambia el nombre
            exito = true;                       //Devuelve true
        }
        return exito;
    }
    //Delete
    public boolean sacarDesafio(int puntaje) {
        return this.desafios.eliminar(puntaje);
    }
}
