package estructuras.conjuntistas.avl;

public class ArbolVL extends ArbolBB{

    @Override
    public boolean insertar(Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(elem);
        } else if (encontrarNodo(elem, (NodoAVL) this.raiz) == null) {
            this.raiz = insertarAux((NodoAVL) this.raiz, elem);
        } else {
            exito = false; //ya existe el elemento
        }
        return exito;
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable elem) {
        // precondicion: elem no está en el árbol entonces con el encontrar nodo lo aseguras
        if (elem.compareTo(n.getElem()) < 0) {
            if (n.getIzquierdo() != null) {
                NodoAVL hijo = insertarAux(n.getIzquierdo(), elem);
                n.setIzquierdo(hijo);
            } else {
                n.setIzquierdo(new NodoAVL(elem));
            }
        } else {
            if (n.getDerecho() != null) {
                NodoAVL hijo = insertarAux(n.getDerecho(), elem);
                n.setDerecho(hijo);
            } else {
                n.setDerecho(new NodoAVL(elem));
            }
        }
        n.recalcularAltura();
        n = balancear(n);
        return n;
    }
    private NodoAVL encontrarNodo (Comparable buscado, NodoAVL n) {
        NodoAVL res = null;

        if (n != null){
            if ((n.getElem().compareTo(buscado)) == 0) {
                res = n;
            } else if ((n.getElem().compareTo(buscado)) < 0) {
                res = encontrarNodo(buscado, n.getDerecho());
            } else {
                res = encontrarNodo(buscado, n.getIzquierdo());
            }
        }

        return res;
    }

    public boolean eliminar (Comparable elem) {
        boolean exito = false;
        if (this.raiz != null) {
            // hago esto por si no lo encuentra y que no haga el proceso de eliminar si no esta
            if (encontrarNodo(elem, (NodoAVL) this.raiz) != null){
                this.raiz = eliminarAux((NodoAVL) this.raiz, elem);
                exito = true;
            }
        }
        return exito;
    }
    private NodoAVL eliminarAux (NodoAVL n, Comparable elem){

        if (n != null){
            if ((elem.compareTo(n.getElem())) < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), elem));
            } else if ((elem.compareTo(n.getElem())) > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), elem));
            } else {
                //encontre el nodo
                if ((n.getIzquierdo() == null) && (n.getDerecho() == null)) {
                    n = null;
                } else if (((n.getIzquierdo()) == null) || ((n.getDerecho()) == null)) {
                    n = casoDosEliminar(n);
                } else {
                    n = casoTresEliminar(n);
                }

                if (n != null) {
                    n.recalcularAltura();
                    n = balancear(n);
                }
            }
        } else {
            n = null;
        }

        return n;
    }

    private NodoAVL casoDosEliminar (NodoAVL n){
        NodoAVL aux;
        if (n.getIzquierdo() != null) {
            aux = n.getIzquierdo();
        } else {
            aux = n.getDerecho();
        }

        return aux;
    }
    private NodoAVL casoTresEliminar (NodoAVL n) {
        NodoAVL candidatoA = mayorRamaIzq(n);
        n.setElem(candidatoA.getElem());
        n.setIzquierdo(eliminarAux(n.getIzquierdo(), candidatoA.getElem()));
        return n;
    }
    private NodoAVL mayorRamaIzq(NodoAVL n) {
        NodoAVL mayor = null;
        if (n.getIzquierdo() != null) {
            NodoAVL hi = n.getIzquierdo();
            while (hi != null) {
                mayor = hi;
                hi = hi.getDerecho();
            }
        }
        return mayor;
    }
    private NodoAVL menorRamaDer(NodoAVL n){
        NodoAVL menor = null;
        if (n.getDerecho() != null) {
            NodoAVL hd = n.getDerecho();
            while (hd != null) {
                menor = hd;
                hd = hd.getIzquierdo();
            }
        }
        return menor;
    }

    private int balance(NodoAVL n){
        int balance;
        int alturaIzq = -1;
        int alturaDer= -1;
        if (n != null){
            if (n.getIzquierdo() != null){
                alturaIzq = n.getIzquierdo().getAltura();
            }
            if (n.getDerecho() != null){
                alturaDer = n.getDerecho().getAltura();
            }
        }
        balance = alturaIzq - alturaDer;
        return balance;
    }
    private NodoAVL balancear(NodoAVL n){
        if (n != null){
            int balanceN = balance(n);

            //si esta debalanceado hacia la izq
            if (balanceN == 2) {
                int balanceH = balance(n.getIzquierdo());

                //si esta desbalanceado hacia la izq
                if ((balanceH == 1) || (balanceH == 0)){
                    //roto a la derecha
                    n = rotarDerecha(n);
                } else{
                    //si esta desbalanceado hacia la der
                    //rotacion doble izq-der
                    n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
                    n = rotarDerecha(n);
                }
            } else if (balanceN == -2){
                //si esta desbalanceado hacia la der
                int balanceH = balance(n.getDerecho());
                //si esta desbalanceado hacia la izq
                if ((balanceH == -1) || (balanceH == 0)){
                    n = rotarIzquierda(n);
                } else {
                    //si esta desbalanceado hacia la derecha
                    n.setDerecho(rotarDerecha(n.getDerecho()));
                    n = rotarIzquierda(n);
                }
            }
        }
        return n;
    }
    private NodoAVL rotarIzquierda(NodoAVL pivote) {
        NodoAVL h = pivote.getDerecho();
        NodoAVL temp = h.getIzquierdo();
        h.setIzquierdo(pivote);
        pivote.setDerecho(temp);
        pivote.recalcularAltura();
        h.recalcularAltura();
        //retorna la nueva raiz
        return  h;
    }
    private NodoAVL rotarDerecha(NodoAVL pivote) {
        NodoAVL h = pivote.getIzquierdo();
        NodoAVL temp = h.getDerecho();
        h.setDerecho(pivote);
        pivote.setIzquierdo(temp);
        pivote.recalcularAltura();
        h.recalcularAltura();
        //retorna la nueva raiz
        return h;
    }

}
