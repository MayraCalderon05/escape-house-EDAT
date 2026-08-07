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
        boolean exito = true;
        if (this.raiz == null){
            this.raiz = new NodoAVLDicc(clave, info);
        } else if (encontrarNodo(clave, this.raiz) == null){
            //el insertar cuando complete la inserción del nodo va a balancearse y devolver la raiz que quedo, por eso se setea
            this.raiz = insertarAux(this.raiz, clave, info);
        } else {
            exito = false;
        }

        return exito;
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
        boolean exito = false;
        if(this.raiz != null){
            this.raiz = eliminarAux(this.raiz, clave);
            exito = true;
        }
        return exito;
    }
    private NodoAVLDicc eliminarAux(NodoAVLDicc n, Comparable clave){
        Comparable claveAux = n.getClave();
        if (n != null){
            if(claveAux.compareTo(clave) < 0){
                n.setHijoIzquierdo(eliminarAux(n.getHijoIzquierdo(), clave));
            } else if (claveAux.compareTo(clave) > 0) {
                n.setHijoDerecho(eliminarAux(n.getHijoDerecho(), clave));
            }else{
                if(n.getHijoDerecho() == null && n.getHijoIzquierdo() == null){
                    n = null;
                }else if(n.getHijoIzquierdo() != null||n.getHijoDerecho() != null){
                    n = eliminarCasoDos(n);
                }else{
                    n = eliminarCasoTres(n);
                }

                if(n != null){
                    n.recalcularAltura();
                    n = balancear(n);
                }
            }

        }
        return n;
    }
    private NodoAVLDicc eliminarCasoDos(NodoAVLDicc n){
        NodoAVLDicc aux;
        if(n.getHijoIzquierdo() != null){
            aux = n.getHijoIzquierdo();
        }else{
            aux = n.getHijoDerecho();
        }
        return aux;
    }
    private NodoAVLDicc eliminarCasoTres(NodoAVLDicc n){
        NodoAVLDicc candidato = mayorRamaIzquierda(n);
        if(candidato.equals(n.getHijoIzquierdo())){
            candidato.setHijoDerecho(n.getHijoDerecho());
        }else {
            candidato.setHijoDerecho(n.getHijoDerecho());
            candidato.setHijoIzquierdo(n.getHijoIzquierdo());
        }
        return candidato;
    }
    private NodoAVLDicc mayorRamaIzquierda(NodoAVLDicc n){
        NodoAVLDicc padre = n.getHijoIzquierdo();
        NodoAVLDicc hijo = padre.getHijoDerecho();
        if(hijo != null){
            while(hijo.getHijoDerecho() != null){
                padre = hijo;
                hijo = hijo.getHijoDerecho();
            }
            padre.setHijoDerecho(null);
        }else{
            hijo = padre;
        }
        return hijo;
    }

    public Object obtenerInfo(Comparable clave){
        Object info = null;
        //si la raiz es nula no busca nada
        if (this.raiz != null){
            //llama a un metodo recursivo para encontrar la info
            info = (encontrarNodo(clave, this.raiz)).getInfo();
        }
        return info;
    }

    public boolean existeClave(Comparable clave){
        //sacha
        boolean existe = false;
        if (this.raiz != null){
            existe = buscarClave(this.raiz, clave);
        }
        return existe;
    }

    private boolean buscarClave(NodoAVLDicc n, Comparable clave) {
        boolean existe = false;
        if (n != null) {
            if (clave.compareTo(n.getClave()) == 0) {       //Se compara la clave buscada con la del nodo
                existe = true;                              //Si es igual termina de recorrer y devuelve true
            }else{
                if (clave.compareTo(n.getClave()) < 0) {
                    existe = buscarClave(n.getHijoIzquierdo(), clave);  //Si la clave es menor a la clave del nodo, se llama recursivamente para recorrer el subarbol izquierdo
                }else{
                    existe = buscarClave(n.getHijoDerecho(), clave);    //Si la clave es mayor a la clave del nodo, se llama recursivamente para recorrer el subarbol derecho
                }
            }
        }
        return existe;
    }

    public Lista listarClaves(){
        //sacha
        Lista lista = new Lista();
        listarClavesAux(this.raiz, lista);
        return lista;
    }
    private void listarClavesAux(NodoAVLDicc n, Lista lista) {
        if(n != null){                                          //Se recorre el arbol avl en inorden para listar las claves en orden ascendente
            listarClavesAux(n.getHijoIzquierdo(), lista);       //Se llama recursivamente al subarbol izquierdo
            lista.insertar(n.getClave(), lista.longitud()+1);   //Cuando termine de recorrer los subarboles izquierdos, inserta en la lista
            listarClavesAux(n.getHijoDerecho(), lista);         //Por ultimo, se llama recursivamente para recorrer el subarbol derecho en inorden
        }
    }

    public Lista listarDatos(){
        Lista lista = new Lista();
        //llama a un metodo recursivo para listar los datos
        listarAux(this.raiz, lista);
        return lista;
    }

    private void listarAux(NodoAVLDicc n, Lista lista){
        //si el nodo no es nulo, es decir tiene alg que listar
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

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo){
        Lista lista = new Lista();
        if (raiz != null || elemMaximo.compareTo(elemMinimo) > 0) {
            listarRangoAux(this.raiz, lista, elemMinimo, elemMaximo);
        }
        return lista;
    }

    private void listarRangoAux(NodoAVLDicc n, Lista lista, Comparable elemMin, Comparable elemMax){
        if (n != null){
            if(n.getClave().compareTo(elemMin) >= 0){
                listarRangoAux(n.getHijoIzquierdo(), lista, elemMin, elemMax);
                lista.insertar(n.getClave(), lista.longitud()+1);
                if (n.getClave().compareTo(elemMax) <= 0) {
                    listarRangoAux(n.getHijoDerecho(), lista, elemMin, elemMax);
                }
            }
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
        if (this.raiz == null){
            return "El árbol está vacío.\n";
        }
        return toStringAux(this.raiz, "1");
    }
    private String toStringAux(NodoAVLDicc n, String ubicacion){
        StringBuilder res = new StringBuilder();

        if (n != null){
            //primero el subárbol izquierdo
            res.append(toStringAux(n.getHijoIzquierdo(), ubicacion + ".1"));

            //despues el nodo actual
            res.append(ubicacion).append(": ");
            res.append("[Clave: ").append(n.getClave());
            res.append(", Altura: ").append(n.getAltura());
            res.append("] ").append(n.getInfo().toString());
            res.append(System.lineSeparator());

            //despues el subárbol derecho
            res.append(toStringAux(n.getHijoDerecho(), ubicacion + ".2"));
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

                //si el hijo esta desbalanceado hacia la izq (mismo sentido que n)
                if ((balanceH == -1) || (balanceH == 0)) {
                    //roto a la izquierda
                    n = rotarIzquierda(n);
                } else {
                    //si el hijo esta desbalanceado hacia la der (sentido contrario a n)
                    //rotacion doble der-izq
                    n.setHijoDerecho(rotarDerecha(n.getHijoDerecho()));
                    n = rotarIzquierda(n);
                }
            }
        }
        return n;
    }
}
