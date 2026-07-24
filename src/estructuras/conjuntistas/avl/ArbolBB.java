package estructuras.conjuntistas.avl;
import estructuras.lineales.Lista;


public class ArbolBB {
    NodoABB raiz;

    public ArbolBB () {
        this.raiz = null;
    }

    public boolean pertenece (Comparable buscado) {
        boolean exito = false;
        if (this.raiz != null){
            NodoABB encontrado = encontrarNodo(buscado, this.raiz);
            if (encontrado != null){
                exito = true;
            }
        }
        return exito;
    }
    private NodoABB encontrarNodo (Comparable buscado, NodoABB n) {
        NodoABB res = null;

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

    public boolean insertar (Comparable elem) {
        boolean exito = true;
        if (this.raiz == null){
            this.raiz = new NodoABB(elem);
        } else {
            exito = insertarAux(this.raiz, elem);
        }
        return exito;
    }
    private boolean insertarAux (NodoABB n, Comparable elem) {
        //precond: n no es nulo
        boolean exito = true;

        if (((elem.compareTo(n.getElem())) == 0)){
            //elemento repetido
            exito = false;
        } else if (((elem.compareTo(n.getElem())) < 0)) {
            if (n.getIzquierdo() != null) {
                exito = insertarAux(n.getIzquierdo(), elem);
            } else {
                n.setIzquierdo(new NodoABB(elem));
            }
        } else {
            if (n.getDerecho() != null){
                exito = insertarAux(n.getDerecho(), elem);
            } else {
                n.setDerecho(new NodoABB(elem));
            }
        }
        return exito;
    }

    public boolean eliminar (Comparable elem) {
        boolean exito = true;
        if (this.raiz == null) {
            exito = false;
        } else {
            // hago esto por si no lo encuentra y que no haga el proceso de eliminar si no esta
            if (encontrarNodo(elem, this.raiz) != null){
                exito = eliminarAux(this.raiz, elem);
            } else {
                exito = false;
            }
        }
        return exito;
    }
    private boolean eliminarAux (NodoABB n, Comparable elem){
        boolean exito = true;
        char hijo;
        NodoABB candidato;

        NodoABB padre = encontrarPadre(n, elem);

        if (padre != null) {
            if ((padre.getIzquierdo() != null) &&
                    (padre.getIzquierdo().getElem().compareTo(elem)) == 0) {
                hijo = 'I';
                candidato = padre.getIzquierdo();
            } else {
                hijo = 'D';
                candidato = padre.getDerecho();
            }

            //Caso 1: el nodo a liminar es una hoja, es decir, no tiene hijos
            if ((candidato.getIzquierdo()==null)
                    && (candidato.getDerecho() == null)) {
                casoUnoEliminar(padre, hijo);

                //Caso 2: el nodo a eliminar tiene un solo hijo
            } else if (((candidato.getIzquierdo()==null) && (candidato.getDerecho() != null)) ||
                    ((candidato.getIzquierdo()!=null) && (candidato.getDerecho() == null))){
                casoDosEliminar(candidato, padre, hijo);
            } else {
                //Caso 3: el nodo a eliminar tiene 2 hijos
                casoTresEliminar(candidato, padre, hijo);
            }
        } else if (this.raiz != null) {
            NodoABB candidatoA = mayorRamaIzq(this.raiz);
            if (candidatoA != null) {
                this.raiz.setElem(candidatoA.getElem());
                boolean eliminado = eliminarAux(n, candidatoA.getElem());
            } else{
                NodoABB candidatoB = menorRamaDer(this.raiz);
                if (candidatoB != null) {
                    this.raiz.setElem(candidatoB.getElem());
                    boolean eliminado = eliminarAux(n, candidatoB.getElem());
                } else {
                    this.raiz = null; // si era una hoja
                }
            }

        } else {
            exito = false;
        }
        return exito;
    }
    private NodoABB encontrarPadre (NodoABB n, Comparable elem){
        NodoABB padre = null;
        if (n != null){
            NodoABB hi = n.getIzquierdo();
            NodoABB hd = n.getDerecho();

            if ((hi != null) && ((hi.getElem().compareTo(elem)) == 0) ||
                    ((hd != null)) && ((hd.getElem().compareTo(elem)) == 0)) {
                padre = n;
            } else {
                if ((n.getElem().compareTo(elem)) < 0){
                    padre = encontrarPadre(n.getDerecho(), elem);
                } else {
                    padre = encontrarPadre(n.getIzquierdo(), elem);
                }
            }

        }

        return padre;
    }
    private void casoUnoEliminar (NodoABB padreNodo, char hijo) {
        enlazar(padreNodo, null, hijo);
    }
    private void casoDosEliminar (NodoABB n, NodoABB padre, char hijo){
        NodoABB aux;
        if (n.getIzquierdo() != null) {
            aux = n.getIzquierdo();
        } else {
            aux = n.getDerecho();
        }

        enlazar(padre, aux, hijo);
    }
    private void casoTresEliminar (NodoABB n, NodoABB padre, char hijo) {
        NodoABB candidatoA = mayorRamaIzq(n);
        if (candidatoA != null) {
            boolean eliminado = eliminarAux(n, candidatoA.getElem());
            candidatoA.setIzquierdo(n.getIzquierdo());
            candidatoA.setDerecho(n.getDerecho());
            enlazar(padre, candidatoA, hijo);
        } else {
            NodoABB candidatoB = menorRamaDer(n);
            if (candidatoB != null) {
                boolean eliminado = eliminarAux(n, candidatoB.getElem());
                candidatoB.setIzquierdo(n.getIzquierdo());
                candidatoB.setDerecho(n.getDerecho());
                enlazar(padre, candidatoB, hijo);
            }
        }


    }
    private NodoABB mayorRamaIzq(NodoABB n) {
        NodoABB mayor = null;
        if (n.getIzquierdo() != null) {
            NodoABB hi = n.getIzquierdo();
            while (hi != null) {
                mayor = hi;
                hi = hi.getDerecho();
            }
        }
        return mayor;
    }
    private NodoABB menorRamaDer(NodoABB n){
        NodoABB menor = null;
        if (n.getDerecho() != null) {
            NodoABB hd = n.getDerecho();
            while (hd != null) {
                menor = hd;
                hd = hd.getIzquierdo();
            }
        }
        return menor;
    }
    private void enlazar(NodoABB padre, NodoABB prox, char hijo) {
        if (hijo == 'I'){
            padre.setIzquierdo(prox);
        } else {
            padre.setDerecho(prox);
        }
    }

    public Lista listar (){
        Lista l = new Lista();
        listarAux(l, this.raiz);
        return l;
    }
    private void listarAux(Lista l, NodoABB n){
        if (n != null){
            listarAux(l, n.getIzquierdo());
            l.insertar(n.getElem(), l.longitud()+1);
            listarAux(l, n.getDerecho());
        }
    }

    public Lista listarRango (Comparable min, Comparable max) {
        Lista l = new Lista();
        listarRangoAux(min, max, this.raiz, l);
        return l;
    }
    private void listarRangoAux (Comparable min, Comparable max, NodoABB n, Lista l){
        if (n != null ){
            if ((n.getElem().compareTo(min)) > 0){
                listarRangoAux(min, max, n.getIzquierdo(), l);
            }
            if (((n.getElem().compareTo(min)) >= 0) && (n.getElem().compareTo(max)) <= 0) {
                l.insertar(n.getElem(), l.longitud()+1);
            }
            if ((n.getElem().compareTo(max)) < 0){
                listarRangoAux(min, max, n.getDerecho(), l);
            }

        }

    }

    public Comparable minimoElem() {
        NodoABB actual = null;
        if (this.raiz != null) {
            actual = this.raiz;

            while (actual.getIzquierdo() != null) {
                actual = actual.getIzquierdo();
            }
        }
        return actual.getElem();
    }

    public Comparable maximoElem() {
        NodoABB actual = null;
        if (this.raiz != null) {
            actual = this.raiz;

            while (actual.getDerecho() != null) {
                actual = actual.getDerecho();
            }
        }
        return actual.getElem();
    }


    //-------------propios
    private NodoABB buscarElemento(Comparable elem, NodoABB n){
        NodoABB encontrado = null;

        if (n != null){
            if (elem.compareTo(n.getElem()) == 0){
                encontrado = n;
            } else if (elem.compareTo(n.getElem()) < 0){
                encontrado = buscarElemento(elem, n.getIzquierdo());
            } else {
                encontrado = buscarElemento(elem, n.getDerecho());
            }
        }

        return encontrado;
    }
   private void eliminarElemSubarbol(NodoABB n){ // recibe el nodo encontrado
       NodoABB padreMayor = null;
       NodoABB mayor = n.getIzquierdo();

       while (mayor.getDerecho() != null) {
           padreMayor = mayor;
           mayor = mayor.getDerecho();
       }

       if (padreMayor != null) {
           padreMayor.setDerecho(mayor.getIzquierdo());
       } else {
           n.setIzquierdo(mayor.getIzquierdo());
       }
   }
   public boolean eliminarElemAnterior(Comparable e){
        boolean exito = false;

        if (this.raiz != null){
            NodoABB elem = buscarElemento(e, this.raiz);

            if (elem != null){
                if(elem.getIzquierdo() != null){
                    eliminarElemSubarbol(elem);
                    exito = true;
                }
            }
        }
        return exito;
   }

   public boolean eliminarMinimo(){
        boolean exito = false;
        NodoABB n = null;

        if (this.raiz != null){
            n = this.raiz;
            NodoABB padreN = null;

            while (n.getIzquierdo() != null){
                padreN = n;
                n = n.getIzquierdo();
            }

            if (padreN != null){
                padreN.setIzquierdo(n.getDerecho());
            } else {
                this.raiz = this.raiz.getDerecho();
            }
            exito = true;
        }
        return exito;
   }

   private NodoABB buscarNodo(Comparable elem, NodoABB n){
        NodoABB encontrado = null;

        if (n != null){
            if (elem.compareTo(n.getElem()) == 0){
                encontrado = n;
            } else if (elem.compareTo(n.getElem()) < 0){
                encontrado = buscarNodo(elem, n.getIzquierdo());
            } else {
                encontrado = buscarNodo(elem, n.getDerecho());
            }
        }
        return encontrado;
   }

   private void invertir(NodoABB encontrado, NodoABB nuevo){
        NodoABB hijo;
        if (encontrado != null) {
            if (encontrado.getIzquierdo() != null) {
                hijo = new NodoABB(encontrado.getIzquierdo().getElem());
                nuevo.setDerecho(hijo);
                invertir(encontrado.getIzquierdo(), nuevo.getDerecho());
            }
            if (encontrado.getDerecho() != null) {
                hijo = new NodoABB(encontrado.getDerecho().getElem());
                nuevo.setIzquierdo(hijo);
                invertir(encontrado.getDerecho(), nuevo.getIzquierdo());
            }
        }
   }

   public ArbolBB clonarParteInvertida(Comparable elem){
        ArbolBB clon = new ArbolBB();

        if (this.raiz != null){
            NodoABB n = buscarNodo(elem, this.raiz);

            if (n != null){
                clon.raiz = new NodoABB(n.getElem());
                invertir(n, clon.raiz);
            }
        }
        return clon;
   }

    private NodoABB encontrarSubArbol(Comparable min, NodoABB n){
        NodoABB nodo = null;

        if (n != null){
            if (n.getElem().compareTo(min) < 0){
                // n es menor, el candidato está a la derecha
                nodo = encontrarSubArbol(min, n.getDerecho());
            } else {
                // n es >= min, pero puede haber uno más chico a la izquierda
                nodo = encontrarSubArbol(min, n.getIzquierdo());
                if (nodo == null){
                    nodo = n; // n es el primero válido
                }
            }
        }
        return nodo;
    }
   private void listarMayores(NodoABB n, Lista l){

        if (n != null){
            listarMayores(n.getIzquierdo(), l);
            l.insertar(n.getElem(), 1);
            listarMayores(n.getDerecho(), l);
        }
   }
   public Lista listarMayoresIgual(Comparable elem){
        Lista l = new Lista();

        if (this.raiz != null){
            NodoABB sub = encontrarSubArbol(elem, this.raiz);

            if (sub != null){
                if (elem.compareTo(sub.getElem()) == 0){
                    l.insertar(sub.getElem(), 1);
                    listarMayores(sub.getDerecho(), l);
                } else {
                    listarMayores(sub, l);
                }
            }
        }

        return l;
   }

   private void listarMenoresAux(Comparable max, NodoABB n, Lista l){
        if (n != null){
            if (n.getElem().compareTo(max) < 0){
                listarMenoresAux(max, n.getIzquierdo(), l);
                l.insertar(n.getElem(), l.longitud()+1);
                listarMenoresAux(max, n.getDerecho(), l);
            } else {
                listarMenoresAux(max, n.getIzquierdo(), l);
            }
        }
   }

   public Lista listarMenores(Comparable elem){
        Lista l = new Lista();

        if (this.raiz != null){
            listarMenoresAux(elem, this.raiz, l);
        }
        return l;
   }
}
