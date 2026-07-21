package estructuras.lineales;
/*
 ***************************** AUTORES ************************************
    Torres Sendra Morena, Legajo FAI-5853
    Coggiola Sacha Naim, Legajo FAI-5767
    Calderon Mayra, Legajo FAI-5785
 */


class Nodo {
    private Object elem;
    private Nodo enlace;

    public Nodo(Object elemento, Nodo enlace) {
        this.elem = elemento;
        this.enlace = enlace;
    }

    //Observadores
    public Object getElem() {
        return this.elem;
    }

    public Nodo getEnlace() {
        return this.enlace;
    }

    //modificadores
    public  void setElem(Object elem) {
        this.elem = elem;
    }

    public void  setEnlace(Nodo enlace) {
        this.enlace = enlace;
    }
}

