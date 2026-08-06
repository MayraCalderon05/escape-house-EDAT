package modelo;

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
        return "Codigo: " + this.codigo + "\n"+
                "Nombre: " + this.nombre + "\n"+
                "Planta: " + this.planta + "\n"+
                "Metros Cuadrados: " + this.metrosCuadrados + "\n"+
                "Salida al exterior: " + this.salidaAlExterior + "\n";
    }

    public boolean equals(Habitacion habitacion) {
        return habitacion.codigo == this.codigo;
    }
}
