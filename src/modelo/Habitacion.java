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
}
