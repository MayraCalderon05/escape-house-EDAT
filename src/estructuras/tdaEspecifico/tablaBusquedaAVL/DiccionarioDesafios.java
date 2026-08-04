package estructuras.tdaEspecifico.tablaBusquedaAVL;

import estructuras.lineales.Lista;

public class DiccionarioDesafios {
    private NodoAVLDicc raiz;

    //constructor
    public DiccionarioDesafios(){
        this.raiz = null;
    }

    //metodos
    public boolean insertar(Comparable clave, Object info){
        //may
    }

    public boolean eliminar(Comparable clave){
        //sacha
    }

    public Object obtenerInfo(Comparable clave){
        Object info = null;
        //si la raiz es nula no busca nada
        if (this.raiz != null){
            //llama a un metodo recursivo para encontrar la info
            info = buscarNodo(clave, this.raiz);
        }
        return info;
    }

    private Object buscarNodo(Comparable clave,  NodoAVLDicc n) {
        Object info = null;
        if (n != null) {
            //si la clave es la misma devuelve la info del nodo
            if (clave.compareTo(n.getClave()) == 0) {
                info = n.getInfo();
            }else {
                //si no es igual la clave y es menor, busca al nodo por su hijo izq
                if (clave.compareTo(n.getClave()) < 0) {
                    buscarNodo(clave, n.getHijoIzquierdo());
                }else {
                    //si no es igual la clave y es mayor, busca al nodo por su hijo der
                    buscarNodo(clave, n.getHijoDerecho());
                }

            }
        }
        return info;
    }

    public boolean existeClave(Comparable clace){
        //sacha
    }

    public Lista listarClaves(){
        //sacha
    }

    public Lista listarDatos(){
        Lista lista = new Lista();
        //llama a un metodo recursivo para listar los datos
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoAVLDicc n, Lista lista){
        //si el nodo no es nulo, es decir tiene algo que listar
        if (n != null) {
            //como el listado para que quede ordenado es en inorden
            //primero llama recursivamente a su subarbol izquierdo
            listarAux(n.getHijoIzquierdo(), lista);
            //luego cuando vuelve lista el nodo
            lista.insertar(n.getInfo(), lista.longitud()+1);
            //y por ultimo llama recursivamente a su subarbol derecho
            listarAux(n.getHijoDerecho(), lista);
        }
    }

    public DiccionarioDesafios clone(){
        DiccionarioDesafios clone = new DiccionarioDesafios();
        //si el arbol original tiene raiz, hay algo que copiar
        if (this.raiz != null){
            //clona recursivamente todo el arbol a partir de la raiz
            //y asigna el resultado como raiz del nuevo diccioanario
            clone.raiz = cloneAux(this.raiz);
        }
        return clone;
    }
    private NodoAVLDicc cloneAux(NodoAVLDicc n){
        NodoAVLDicc aux = null;
        //caso base: si el nodo es null, no hay nada que clonar y se corta la recursion
        if (n != null){
            //crea un nodo nuevo para copiar el nodo actual
            aux = new NodoAVLDicc(n.getClave(), n.getInfo(), null, null);
            //clona recursivamente su subarbol izquierdo y lo enlaza al nodo nuevo clonado
            aux.setHijoIzquierdo(cloneAux(n.getHijoIzquierdo()));
            //clona recursivamente su subarbol dererecho y lo enlaza al nodo nuevo clonado
            aux.setHijoDerecho(cloneAux(n.getHijoDerecho()));
        }
        return aux;
    }


    public boolean esVacio(){
        return this.raiz == null;
    }

    public String toString(){
        //may
    }
}
