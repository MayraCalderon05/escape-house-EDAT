package modelo;

public class Desafio {
    private int puntaje;
    private String tipo;
    private String nombre;

    //Constructores
    public Desafio(int puntaje, String tipo, String nombre) {
        this.puntaje = puntaje;
        this.tipo = tipo;
        this.nombre = nombre;
    }
    public Desafio(int puntaje) {
        this.puntaje = puntaje;
        this.tipo = "";
        this.nombre = "";
    }

    //Modificadores
    public void setTipo(String tipo) {
        this.tipo = tipo;       //El puntaje no tiene set porque es el atributo clave de la clase Desafio
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Observadores
    public int getPuntaje() {
        return puntaje;
    }
    public String getTipo() {
        return tipo;
    }
    public String getNombre() {
        return nombre;
    }
    public String toString() {
        return "El desafio "+this.nombre+" es de tipo "+this.tipo+", y otorga un puntaje de "+this.puntaje+".";
    }
    public boolean equals(Desafio desafio) {
        return this.puntaje == desafio.puntaje;
    }

    //Propios del tipo
    public boolean puntajeDentroDeRango(int min, int max) {
        return (this.puntaje >= min && this.puntaje <= max);    //Metodo que devuelve true/false dependiendo si el puntaje de la instancia de Desafio esta dentro de un rango o no
    }
}
