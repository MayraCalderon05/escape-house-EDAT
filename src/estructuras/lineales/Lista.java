package estructuras.lineales;
//estructura dinamica
public class Lista {
    private Nodo cabecera;
    private int longitud;

    public Lista(){
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object elemento, int pos) {
        boolean exito = false;
        Nodo nuevoNodo = new Nodo(elemento, null);

        if (pos > 0 && pos <= this.longitud+1) {
            if (pos == 1) {
                nuevoNodo.setEnlace(this.cabecera);
                this.cabecera = nuevoNodo;
            } else {
                //devuelve el nodo en la posicion anterior
                Nodo aux = recorrer(this.cabecera, pos - 1);
                nuevoNodo.setEnlace(aux.getEnlace());
                aux.setEnlace(nuevoNodo);
            }
            this.longitud++;
            exito = true;
        }

        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = false;

        if (pos > 0 && pos <= this.longitud) {
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                Nodo aux = recorrer(this.cabecera, pos - 1); // nodo en pos-1
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            this.longitud--;
            exito = true;
        }
        return exito;
    }

    //el metodo devuelve el nodo anterior del elemento que se busca en una posicion
    private Nodo recorrer(Nodo aux, int pos) {
        int contador = 1;
        while (contador < pos && aux != null) {
            aux = aux.getEnlace();
            contador++;
        }
        return aux; // devuelve el nodo en la posición pedida
    }

    public int longitud () {
        return this.longitud;
    }

    public Object recuperar(int pos) {
        Object resultante = null;

        if (pos > 0 && pos <= this.longitud) {
            Nodo aux = recorrer(this.cabecera, pos);
            resultante = aux.getElem();
        }

        return resultante;
    }

    public int localizar(Object elemento) {
        int posicion = -1;
        boolean encontrado = false;
        Nodo aux = this.cabecera;

        int contador = 1;
        while (!encontrado && contador <= this.longitud) {
            if (aux.getElem().equals(elemento)) {
                posicion = contador;
                encontrado = true;
            } else {
                contador++;
                aux = aux.getEnlace();
            }
        }
        return posicion;  // devuelve -1 si no lo encontró
    }

    public void vaciar () {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }


    public Lista clone () {
        Lista copia = new Lista();

        if (this.cabecera != null){
            Nodo auxOriginal = this.cabecera;
            Nodo auxCopia = new Nodo(this.cabecera.getElem(), null);
            copia.cabecera = auxCopia;
            copia.longitud = this.longitud;

            auxOriginal = auxOriginal.getEnlace();
            while (auxOriginal != null){
                Nodo nuevo = new Nodo(auxOriginal.getElem(), null);
                auxCopia.setEnlace(nuevo);

                auxCopia = nuevo;
                auxOriginal = auxOriginal.getEnlace();
            }
        }
        return copia;
    }

    @Override

    public String toString(){
        StringBuilder resultado = new StringBuilder("[");
        if (this.cabecera != null){
            Nodo aux = this.cabecera;
            while (aux != null){
                resultado.append(aux.getElem().toString());
                aux = aux.getEnlace();
                if (aux != null){
                    resultado.append(",");
                }
            }
        }
        resultado.append("]");
        return resultado.toString();
    }

}
