package estructuras.grafo;
//grafo etiquetado no dirigido
public class Grafo {
    private NodoVert inicio;

    public Grafo (){
        this.inicio = null;
    }

    public boolean insertarVertice(Object vertice){
        return insertarAux(inicio, vertice);
    }

    private boolean insertarAux(NodoVert nodo, Object buscado){
        boolean insertado = true;
        NodoVert aux;
        if(nodo != null){
            aux = nodo.getSigVertice();
           while(aux!=null){
                nodo = aux;
                aux = aux.getSigVertice();
           }
           nodo.setSigVertice(new NodoVert(buscado));
        }else{
            this.inicio = new NodoVert(buscado);
        }
        return insertado;
    }

    private boolean eliminarArcosAux(NodoVert origen, Object destino){
        boolean borrado = false;
        NodoAdy aux = origen.getPrimerAdy();
        NodoAdy anterior = null;

        while (aux != null && !borrado){
            if (aux.getVertice().getElem().equals(destino)){
                //si es el primer adyacente
                if (anterior == null){
                    origen.setPrimerAdy(aux.getSigAdyacente());
                } else {
                    //si ya habian adyacentes antes
                    anterior.setSigAdyacente(aux.getSigAdyacente());
                }
                borrado = true;

            } else {
                anterior = aux;
                aux = aux.getSigAdyacente();
            }
        }

        return borrado;
    }
    // ya casi lo tengo no me lo toquen que estoy por conectar las neuronas
    private void limpiarAdyacencias(Object vertice, NodoVert n){
        //recorro la lista de adyacentes

        while (n != null){
            eliminarArcosAux(n, vertice);
            n = n.getSigVertice();
        }

    }
    private boolean eliminarVerticeAux(Object vertice){
        boolean eliminado = true;
        if (this.inicio != null){
            NodoVert nodo = this.inicio;
            NodoVert anterior = null;
            while (nodo != null && !nodo.getElem().equals(vertice)){
                anterior = nodo;
                nodo = nodo.getSigVertice();
            }

            //en ambos casos si setea con un nulo no pasa nada, podría ser el ultimo y el set igual mandaría un nulo
            //si el anterior es nulo es porque el que encontró era el primero
            if (anterior == null){
                this.inicio = this.inicio.getSigVertice();
            } else {
                //si realmente encontró al nodo que estaba buscando
                if (nodo != null){
                    anterior.setSigVertice(nodo.getSigVertice());
                } else {
                    //si largo falso es porque el nodo era nulo entonces no lo encontró
                    eliminado = false;
                }
            }
        } else {
            //no tiene vertices, no hace nada y tira falsp
            eliminado = false;
        }

        return eliminado;
    }
    public boolean eliminarVertice(Object vertice){
        boolean exito = eliminarVerticeAux(vertice);
        if (exito){
            limpiarAdyacencias(vertice, this.inicio);
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado){
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)){
            aux = aux.getSigVertice();
        }
        return aux;
    }
    public boolean existeVertice(Object vertice){
        boolean existe = false;

        NodoVert aux = ubicarVertice(vertice);

        if (aux != null){
            existe = true;
        }


        return existe;
    }

    public boolean insertarArco(Object origen, Object destino, int nuevaEtiqueta){


    }

    //este modulo disuelve el arco de un solo lado
    private boolean disolverArco(Object verticeA, Object verticeB){
        boolean eliminado = false;
        NodoVert origen = ubicarVertice(verticeA);
        //si existe el vertice que busca
        if (origen != null){
            eliminado = eliminarArcosAux(origen, verticeB);
        }
        return eliminado;
    }
    public boolean eliminarArco(Object verticeA, Object verticeB){
        boolean eliminado = disolverArco(verticeA, verticeB);
        if (eliminado) {
            //por eso aca lo llamo 2 veces, porque el arco esta en ambas listas de adyacencia
            eliminado = disolverArco(verticeB, verticeA);
        }
        return eliminado;
    }

    public boolean existeArco(Object verticeA, Object verticeB){

    }

    public boolean esVacio(){
        return this.inicio == null;
    }


}
