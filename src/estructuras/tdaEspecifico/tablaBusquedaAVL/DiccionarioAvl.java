package estructuras.tdaEspecifico.tablaBusquedaAVL;

import estructuras.lineales.Lista;

public class DiccionarioAvl {
    private NodoAVLDicc raiz;

    //constructor
    public DiccionarioAvl(){
        this.raiz = null;
    }

    //metodos
    private NodoAVLDicc encontrarNodo(Comparable buscado, NodoAVLDicc n){
        NodoAVLDicc res = null;

        if (n != null){
            if ((n.getClave().compareTo(buscado)) == 0){
                res = n;
            } else if ((n.getClave().compareTo(buscado)) < 0){
                res = encontrarNodo(buscado, n.getHijoDerecho());
            } else {
                res = encontrarNodo(buscado, n.getHijoIzquierdo());
            }
        }
        return res;
    }

    public boolean insertar(Comparable clave, Object info){
        //may
        boolean exito = false;
        if (this.raiz == null){
            this.raiz = new NodoAVLDicc(clave, info);
            exito = true;
        } else if (encontrarNodo(clave, this.raiz) == null){
            //el insertar cuando complete la inserción del nodo va a balancearse y devolver la raiz que quedo, por eso se setea
            this.raiz = insertarAux(this.raiz, clave, info);

        };

    }
    private NodoAVLDicc insertarAux(NodoAVLDicc n, Comparable clave, Object info){
        //se verifica que el desafio no exista previamente
        Comparable claveAux = n.getClave();
        NodoAVLDicc hijo;

        //si está del lado derecho
        if ((claveAux.compareTo(clave)) < 0){
            if (n.getHijoDerecho() != null){
                //si el hijo derecho no es nulo, me aseguro de seguir bajando por si sigue siendo mayor
                hijo = insertarAux(n.getHijoDerecho(), clave, info);
                n.setHijoDerecho(hijo);
            } else {
                n.setHijoDerecho(new NodoAVLDicc(clave, info));
            }
        } else {
            if (n.getHijoIzquierdo() != null){
                hijo = insertarAux(n.getHijoIzquierdo(), clave, info);
                n.setHijoIzquierdo(hijo);
            } else {
                n.setHijoIzquierdo(new NodoAVLDicc(clave, info));
            }
        }

        n.recalcularAltura();
        //hasta ahora solo inserta el nodo, pero aca se balancea
        n = balancear(n);
        return n;
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
                    info = buscarNodo(clave, n.getHijoIzquierdo());
                }else {
                    //si no es igual la clave y es mayor, busca al nodo por su hijo der
                    info = buscarNodo(clave, n.getHijoDerecho());
                }

            }
        }
        return info;
    }

    public boolean existeClave(Comparable clave){
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

    public DiccionarioAvl clone(){
        DiccionarioAvl clone = new DiccionarioAvl();
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
        StringBuilder res = new StringBuilder("[");

        if (this.raiz != null){
            res.append(System.lineSeparator()).append(toStringAux(this.raiz));
        }
        res.append("]");

        return res.toString();
    }
    private String toStringAux(NodoAVLDicc n){
        StringBuilder res = new StringBuilder();

        if (n != null){
            //primero el subárbol izquierdo
            res.append(toStringAux(n.getHijoIzquierdo()));

            //despues el nodo actual
            res.append("1: ").append(n.getInfo().toString()).append(System.lineSeparator());

            //hijo izq
            res.append("1.1: ");
            if (n.getHijoIzquierdo() != null){
                res.append(n.getHijoIzquierdo().getInfo().toString());
            } else {
                res.append("nulo");
            }
            res.append(System.lineSeparator());

            //hijo der
            res.append("1.2: ");
            if (n.getHijoDerecho() != null){
                res.append(n.getHijoDerecho().getInfo().toString());
            } else {
                res.append("nulo");
            }

            res.append(System.lineSeparator());
            res.append("------------------------").append(System.lineSeparator());

            //despues el subárbol derecho
            res.append(toStringAux(n.getHijoDerecho()));
        }

        return res.toString();
    }


    private int balance(NodoAVLDicc n){
        int balance;
        int alturaIzq = -1;
        int alturaDer = -1;

        if (n != null){
            if (n.getHijoIzquierdo() != null){
                alturaIzq = n.getHijoIzquierdo().getAltura();
            }
            if (n.getHijoDerecho() != null){
                alturaDer = n.getHijoDerecho().getAltura();
            }
        }
        balance = alturaIzq - alturaDer;
        return balance;
    }
    private NodoAVLDicc rotarIzquierda(NodoAVLDicc pivote){
        NodoAVLDicc hijo = pivote.getHijoDerecho();
        NodoAVLDicc temporal = hijo.getHijoIzquierdo();
        hijo.setHijoIzquierdo(pivote);
        pivote.setHijoDerecho(temporal);
        pivote.recalcularAltura();
        hijo.recalcularAltura();

        //retorna la nueva raiz
        return hijo;
    }
    private NodoAVLDicc rotarDerecha(NodoAVLDicc pivote){
        NodoAVLDicc hijo = pivote.getHijoIzquierdo();
        NodoAVLDicc temporal = hijo.getHijoDerecho();
        hijo.setHijoDerecho(pivote);
        pivote.setHijoIzquierdo(temporal);
        pivote.recalcularAltura();
        hijo.recalcularAltura();

        //retorna la nueva raiz
        return hijo;
    }

    private NodoAVLDicc balancear(NodoAVLDicc n){
        if (n != null) {
            int balanceN = balance(n);

            //si esta desbalanceado hacia la izquierda
            if (balanceN == 2) {
                int balanceH = balance(n.getHijoIzquierdo());

                //si el hijo esta desbalanceado hacia la izq
                if ((balanceH == 1) || (balanceH == 0)) {
                    //roto a la derecha
                    n = rotarDerecha(n);
                } else {
                    //si el hijo esta desbalanceado hacia la der
                    //rotacion doble izq-der
                    n.setHijoIzquierdo(rotarIzquierda(n.getHijoIzquierdo()));
                    n = rotarDerecha(n);
                }
                //si esta n desbalanceado hacia la der
            } else if (balanceN == -2) {
                int balanceH = balance(n.getHijoDerecho());

                //si el hijo esta desbalanceado hacia la izq
                if ((balanceH == -1) || (balanceH == 0)) {
                    //roto a la izquierda
                    n = rotarIzquierda(n);
                } else {
                    //si el hijo esta desbalanceado hacia la der
                    //rotacion doble der-izq
                    n.setHijoDerecho(rotarIzquierda(n.getHijoIzquierdo()));
                    n = rotarIzquierda(n);
                }
            }
        }
        return n;
    }
}
