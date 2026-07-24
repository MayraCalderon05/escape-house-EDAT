package estructuras.lineales;

public class Cola {
    private Nodo frente;
    private Nodo fin;

    //Constructor
    public Cola() {
        frente = null;
        fin = null;
    }

    //Metodos

    public boolean poner(Object elem){
        Nodo newNodo = new Nodo(elem,null);
        //primero me fijo que el frente no se null
        //porque si es null no hay ningun nodo en la cola y frente y fin van a apuntar al mismo nodo
        if (this.frente == null) {
            this.frente = newNodo;
            this.fin = newNodo;
        }else {
            this.fin.setEnlace(newNodo);
            this.fin = newNodo;
        }
        return true;
    }

    public boolean sacar(){
        boolean exito = false;
        if (this.frente != null){
            if (this.frente == this.fin){
                this.frente = null;
                this.fin = null;
                exito = true;
            }else
                this.frente = this.frente.getEnlace();
            exito = true;

        }
        return exito;
    }

    public Object obtenerFrente(){
        Object elem = null;
        if (this.frente != null){
            elem = this.frente.getElem();
        }
        return elem;
    }

    public boolean esVacia(){
        return this.frente == null;
    }



    public void vaciar(){
        this.frente = null;
        this.fin = null;
    }

    @Override
    public Cola clone(){
        Cola nuevaCola = new Cola();
        if (this.frente != null){
            Nodo aux = new Nodo(this.frente.getElem(), null);
            nuevaCola.frente = aux;
            Nodo enlace = frente.getEnlace();
            while (enlace != null){
                aux.setEnlace(new Nodo(enlace.getElem(), null));
                enlace = enlace.getEnlace();
                aux = aux.getEnlace();
            }
            nuevaCola.fin = aux;
        }
        return nuevaCola;
    }

    public String toString(){
        String res = "";
        if(this.frente != null){
            res = "[";
            Nodo aux = this.frente;
            while (aux != null){
                res += aux.getElem();
                aux = aux.getEnlace();
                if (aux != null){
                    res += ",";
                }

            }

        }else{
            res = "[";
        }
        return res + "]";
    }


}
