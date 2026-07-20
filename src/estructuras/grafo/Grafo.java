package estructuras.grafo;
import estructuras.lineales.*;
/// grafo etiquetado no dirigido
//! si A--B entonces:
//! en la lista de ady de A está B y en la lista de ady de B esta A

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
    private boolean disolverArco(Object origen, Object destino){
        boolean eliminado = false;
        NodoVert origenNodo = ubicarVertice(origen);
        //si existe el vertice que busca
        if (origen != null){
            eliminado = eliminarArcosAux(origenNodo, destino);
        }
        return eliminado;
    }
    public boolean eliminarArco(Object origen, Object destino){
        boolean eliminado = disolverArco(origen, destino);
        if (eliminado) {
            //por eso aca lo llamo 2 veces, porque el arco esta en ambas listas de adyacencia
            eliminado = disolverArco(destino, origen);
        }
        return eliminado;
    }

    //abierto a modificaciones
    private boolean existeArco(NodoVert origen, Object destino){
        boolean existe = false;
        NodoAdy aux = origen.getPrimerAdy();
        while (aux != null && !existe){
            if (aux.getVertice().getElem().equals(destino)){
                existe = true;
            } else {
                aux = aux.getSigAdyacente();
            }
        }
        return existe;
    }
    public boolean existeArco(Object origen, Object destino){
        boolean existe = false;
        NodoVert vertA = ubicarVertice(origen);
        if (vertA != null){
            existe = existeArco(vertA, destino);
        }
        return existe;
    }

    private boolean encontrarCamino(NodoVert origen, Object destino, Lista visitados){
         boolean encontrado = false;
         if (origen != null){
             //si en el que estoy parada es el destino
             if (origen.getElem().equals(destino)){
                 encontrado = true;
             } else {
                 //si no es el que estoy buscando
                 visitados.insertar(origen.getElem(), visitados.longitud()+1);
                 NodoAdy aux = origen.getPrimerAdy();
                 while (!encontrado && aux != null){
                     //si no esta en la lista
                     if (visitados.localizar(aux.getVertice().getElem()) < 0){
                         encontrado = encontrarCamino(aux.getVertice(), destino, visitados);
                     }
                     aux = aux.getSigAdyacente();
                 }
             }
         }
         return encontrado;
    }
    //si existen ambos te devuelve el punto de inicio, es decir, el origen
    private NodoVert existenNodos(Object origen, Object destino){
        NodoVert nodoOrigen = null;
        //controlo que no le haya mandado el mismo nodo 2 veces
        if (!(origen.equals(destino))){
            NodoVert auxOrigen = null;
            NodoVert auxDestino = null;
            NodoVert puntero = this.inicio;

            while ((auxOrigen == null || auxDestino == null) && puntero != null){
                if (puntero.getElem().equals(origen)) auxOrigen = puntero;
                if (puntero.getElem().equals(destino)) auxDestino = puntero;
                puntero = puntero.getSigVertice();
            }


            if (auxOrigen != null && auxDestino !=null){
                nodoOrigen = auxOrigen;
            }
        }

        return  nodoOrigen;
    }
    public boolean existeCamino(Object origen, Object destino){
        boolean existe = false;
        NodoVert origenNodo = existenNodos(origen, destino);

        if (origenNodo != null){
            Lista visitados = new Lista();
            existe = encontrarCamino(origenNodo, destino, visitados);
        }
        return existe;
    }

    private  void caminoCortoAux(Object origen, Object destino, Lista camino){
        NodoVert nodoOrigen;
        if(this.inicio !=null){
            nodoOrigen = existenNodos(origen,destino);
            if(nodoOrigen!=null){
                
            }
        }
    }
    public Lista caminoMasCorto(Object origen, Object destino){
        Lista camino = new Lista();
        caminoCortoAux(origen, destino, camino);
        return camino;
    }

    public Lista caminoMasLargo(Object verticeA, Object verticeB){

    }

    public Lista listarEnProfundidad(){

    }

    public Lista listarEnAnchura(){

    }

    public boolean esVacio(){
        return this.inicio == null;
    }

    private void cloneAux(NodoVert auxOriginal, NodoVert auxClon, Grafo clon){

        while (auxOriginal != null){

            //referencio al primer adyacente del original
            NodoAdy adyOriginal = auxOriginal.getPrimerAdy();
            if (adyOriginal != null){

                //obtengo el vertice al que referencia
                NodoVert referenciaOriginal = adyOriginal.getVertice();
                //busco el vertice en la lista de vertices del clon
                NodoVert referenciaClon = clon.ubicarVertice(referenciaOriginal.getElem());
                // lo seteo
                auxClon.setPrimerAdy(new NodoAdy(referenciaClon, adyOriginal.getEtiqueta()));

                NodoAdy adyClon = auxClon.getPrimerAdy();
                adyOriginal = adyOriginal.getSigAdyacente();

                while (adyOriginal != null){
                    referenciaOriginal = adyOriginal.getVertice();
                    referenciaClon = clon.ubicarVertice(referenciaOriginal.getElem());
                    adyClon.setSigAdyacente(new NodoAdy(referenciaClon, adyOriginal.getEtiqueta()));

                    adyOriginal = adyOriginal.getSigAdyacente();
                    adyClon = adyClon.getSigAdyacente();
                }

            }

            auxOriginal = auxOriginal.getSigVertice();
            auxClon = auxClon.getSigVertice();
        }


    }
    public Grafo clone(){
        Grafo clon = new Grafo();

        //? primero creo la lista de vertces para despues hacer la referencia en la lista de adyacencias
        if (this.inicio != null){
            NodoVert auxOriginal = this.inicio;
            NodoVert auxClon = new NodoVert(auxOriginal.getElem());
            clon.inicio = auxClon;

            auxOriginal = auxOriginal.getSigVertice();
            while (auxOriginal != null){
                NodoVert nuevo = new NodoVert(auxOriginal.getElem());

                auxClon.setSigVertice(nuevo);

                //avanzo en el original
                auxOriginal = auxOriginal.getSigVertice();
                //avanzo en la copia
                auxClon = auxClon.getSigVertice();
            }

            //? clono la lista de adyacencia
            cloneAux(this.inicio, clon.inicio, clon);

        }

        return clon;

    }

    public String toString(){

    }

}
