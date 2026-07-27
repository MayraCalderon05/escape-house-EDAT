package estructuras;

public class Desafio {
    private int puntaje;
    private String tipo;

    //Constructores
    public Desafio(int puntaje, String tipo) {
        this.puntaje = puntaje;
        this.tipo = tipo;
    }
    public Desafio(int puntaje) {
        this.puntaje = puntaje;
        this.tipo = "";
    }

    //Modificadores
    public void setTipo(String tipo) {
        this.tipo = tipo;       //El puntaje no tiene set porque es el atributo clave de la clase Desafio
    }

    //Observadores
    public int getPuntaje() {
        return puntaje;
    }
    public String getTipo() {
        return tipo;
    }
    public String toString() {
        return "El desafio es de tipo "+this.tipo+", y otorga un puntaje de "+this.puntaje+".";
    }
    public boolean equals(Desafio desafio) {
        return this.puntaje == desafio.puntaje;
    }

    //Propios del tipo
    public boolean puntajeDentroDeRango(int min, int max) {
        return (this.puntaje >= min && this.puntaje <= max);    //Metodo que devuelve true/false dependiendo si el puntaje de la instancia de Desafio esta dentro de un rango o no
    }
}
