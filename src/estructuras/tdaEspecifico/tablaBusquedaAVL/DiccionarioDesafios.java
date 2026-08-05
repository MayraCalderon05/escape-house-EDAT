package estructuras.tdaEspecifico.tablaBusquedaAVL;

import estructuras.lineales.Lista;

public class DiccionarioDesafios {
    private NodoAVLDicc raiz;

    //constructor
    public DiccionarioDesafios(){
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
        //more
    }

    public boolean existeClave(Comparable clace){
        //sacha
    }

    public Lista listarClaves(){
        //sacha
    }

    public Lista listarDatos(){
        //more
    }

    public DiccionarioDesafios clone(){
        //more
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
            res.append("Padre: ").append(n.getInfo().toString()).append(System.lineSeparator());

            //hijo izq
            res.append("Hijo izquierdo: ");
            if (n.getHijoIzquierdo() != null){
                res.append(n.getHijoIzquierdo().getInfo().toString());
            } else {
                res.append("nulo");
            }
            res.append(System.lineSeparator());

            //hijo der
            res.append("Hijo derecho: ");
            if (n.getHijoDerecho() != null){
                res.append(n.getHijoDerecho().getInfo().toString());
            } else {
                res.append("nulo");
            }

            res.append(System.lineSeparator());
            res.append("------------------------").append(System.lineSeparator());

            res.append(toStringAux(n.getHijoIzquierdo()));
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
