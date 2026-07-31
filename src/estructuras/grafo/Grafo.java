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
        return insertarVerticeAux(inicio, vertice);
    }

    private boolean insertarVerticeAux(NodoVert nodo, Object buscado){
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
        boolean insertado = true;

        NodoVert nodoOrigen = ubicarVertice(origen);
        NodoVert nodoDestino = ubicarVertice(destino);

        if(nodoOrigen != null && nodoDestino != null){
            NodoAdy nuevoArco = new NodoAdy(nodoDestino, nuevaEtiqueta);
            if (nodoOrigen.getPrimerAdy() != null){
                NodoAdy aux = nodoOrigen.getPrimerAdy();
                while (aux.getSigAdyacente() != null){
                    aux = aux.getSigAdyacente();
                }
                aux.setSigAdyacente(nuevoArco);
            }else {
                nodoOrigen.setPrimerAdy(nuevoArco);
            }
        }else {
            insertado = false;
        }
        return insertado;
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

    private Lista caminoCortoAux(Object origen, Object destino, Lista camino){
        Lista visitados = new Lista();
        NodoVert nodoOrigen;
        NodoAdy ady;
        nodoOrigen = existenNodos(origen, destino);     //Verifica que existan los nodos origen y destino dentro del grafo, y retorna el nodo que contiene al objeto origen
        if(nodoOrigen != null){                         //Si encontro el nodo comienza a buscar el camino mas corto
            ady = nodoOrigen.getPrimerAdy();            //Saca el primer adyacente del nodo origen
            visitados.insertar(nodoOrigen.getElem(), visitados.longitud() + 1);     //Inserta el nodo origen a la lista, para no entrar en un bucle
            while(ady != null){                         //Mientras que queden nodos por recorrer en la lista de adyacencia del nodo origen
                camino = caminoMasCortoAux(ady.getVertice(), destino, visitados, camino);   //Se llama al modulo que busca el camino
                ady = ady.getSigAdyacente();            //Avanza al siguiente nodo adyacente
            }
        }
        return camino;
    }
    private Lista caminoMasCortoAux(NodoVert origen, Object destino, Lista visitados, Lista camino){
        Object aux = origen.getElem();
        NodoAdy ady = origen.getPrimerAdy();
        NodoVert nodoAdy;
        visitados.insertar(aux, visitados.longitud() + 1);                  //Se inserta el nodo "origen" en la lista de visitados
        if(visitados.longitud()<camino.longitud() || camino.esVacia()) {    //Mientras no se haya encontrado camino, o el recorrido actual sea mas corto que el camino guardado, se sigue buscando el camino
            if (aux.equals(destino)) {                                      //Si se llega al nodo destino
                camino = visitados.clone();                                 //Se almacena el nuevo camino mas corto
            } else {
                while (ady != null) {                                       //Si no es el nodo destino, se recorre la lista de adyacencia del nodo actual
                    nodoAdy = ady.getVertice();
                    if (visitados.localizar(nodoAdy.getElem()) < 0) {       //Se consulta si ya fue visitado ese vertice
                        camino = caminoMasCortoAux(nodoAdy, destino, visitados, camino);    //si no fue visitado el vertice, se hace la llamada recursiva, con el siguiente nodo adyacente
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        visitados.eliminar(visitados.longitud());                           //Se elimina el nodo "origen" de la lista de visitados, para poder recorrer otros caminos
        return camino;
    }
    public Lista caminoMasCorto(Object origen, Object destino){
        Lista camino = new Lista();
        if(this.inicio != null) {             //Si el grafo esta vacio devuelve la lista "camino" vacia.
            if (origen.equals(destino)) {     //Si el vertice de origen es igual al vertice de destino, entonces es el mismo vertice.
                camino.insertar(origen, 1);   //Y en tal caso, se devuelve el camino que contiene al unico vertice.
            } else {
                camino = caminoCortoAux(origen, destino, camino);     //En caso contrario, se llama al modulo que recorre el grafo y devuelve el camino más corto.
            }
        }
        return camino;
    }

    private Lista caminoLargoAux(Object origen, Object destino, Lista camino){
        Lista visitados = new Lista();
        NodoVert nodoOrigen;
        NodoAdy ady;
        nodoOrigen = existenNodos(origen, destino);     //Verifica que existan los nodos origen y destino dentro del grafo, y retorna el nodo que contiene al objeto origen
        if(nodoOrigen != null){                         //Si encontro el nodo comienza a buscar el camino mas corto
            ady = nodoOrigen.getPrimerAdy();            //Saca el primer adyacente del nodo origen
            visitados.insertar(nodoOrigen.getElem(), visitados.longitud() + 1);     //Inserta el nodo origen a la lista, para no entrar en un bucle
            while(ady != null){                         //Mientras que queden nodos por recorrer en la lista de adyacencia del nodo origen
                camino = caminoMasLargoAux(ady.getVertice(), destino, visitados, camino);   //Se llama al modulo que busca el camino
                ady = ady.getSigAdyacente();            //Avanza al siguiente nodo adyacente
            }
        }
        return camino;
    }
    private Lista caminoMasLargoAux(NodoVert origen, Object destino, Lista visitados, Lista camino){
        Object aux = origen.getElem();
        NodoAdy ady = origen.getPrimerAdy();
        NodoVert nodoAdy;
        visitados.insertar(aux, visitados.longitud() + 1);                      //Se inserta el nodo "origen" en la lista de visitados
        if (aux.equals(destino)) {                                              //Si se llega al nodo destino
            if(camino.esVacia() || visitados.longitud() > camino.longitud()) {  //Y el recorrido actual es más laroo que el camino guardado, o no hay un camino guardado
                camino = visitados.clone();                                     //Se almacena el nuevo camino mas largo
            }
        } else {
            while (ady != null) {                                               //Si no es el nodo destino, se recorre la lista de adyacencia del nodo actual
                nodoAdy = ady.getVertice();
                if (visitados.localizar(nodoAdy.getElem()) < 0) {               //Se consulta si ya fue visitado ese vertice
                    camino = caminoMasLargoAux(nodoAdy, destino, visitados, camino);    //si no fue visitado el vertice, se hace la llamada recursiva, con el siguiente nodo adyacente
                }
                ady = ady.getSigAdyacente();
            }
        }
        visitados.eliminar(visitados.longitud());                           //Se elimina el nodo "origen" de la lista de visitados, para poder recorrer otros caminos
        return camino;
    }
    public Lista caminoMasLargo(Object origen, Object destino){
        Lista camino = new Lista();
        if(this.inicio != null) {
            if (origen.equals(destino)) {
                camino.insertar(origen, 1);
            } else {
                camino = caminoLargoAux(origen, destino, camino);
            }
        }
        return camino;
    }

    public Lista listarEnProfundidad(){
            Lista visitados = new Lista();
            NodoVert aux = this.inicio;
            if(aux != null){
                //si el vertice no fue visitado todavia, avanza en listar
                if (visitados.localizar(aux.getElem()) < 0){
                    listarEnProfundidadAux(aux, visitados);
                }
                aux = aux.getSigVertice();
            }
            return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista visitados){
        if (nodo != null){
            //marca el vertice como visitado
            visitados.insertar(nodo.getElem(), visitados.longitud()+1);
            NodoAdy ady = nodo.getPrimerAdy();
            while (ady != null){
                //visita los adyacentes de nodo que todavia no fueron visitados
                if (visitados.localizar(ady.getVertice().getElem()) < 0){
                    listarEnProfundidadAux(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura(){
        Lista visitados = new Lista();
        NodoVert origen = this.inicio;

        if(origen != null){
            Cola aux = new Cola();
            aux.poner(origen.getElem());
            visitados.insertar(origen.getElem(), visitados.longitud()+1);
            while (!aux.esVacia()){
                NodoVert elem = (NodoVert) aux.obtenerFrente();
                aux.sacar();
                NodoAdy ady = elem.getPrimerAdy();
                while (ady != null){
                    if (visitados.localizar(ady.getVertice().getElem()) < 0){
                        aux.poner(ady.getVertice().getElem());
                        visitados.insertar(ady.getVertice().getElem(), visitados.longitud()+1);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return visitados;
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


    private void toStringAux(NodoVert n, StringBuilder resultado){
        if (n != null){
            resultado.append(n.getElem().toString()).append("-> [");
            NodoAdy adyN = n.getPrimerAdy();
            while (adyN != null){
                resultado.append(adyN.getVertice().getElem().toString());
                adyN = adyN.getSigAdyacente();
                if (adyN != null){
                    resultado.append(",");
                }
            }
            resultado.append("]");
            if (n.getSigVertice() != null){
                resultado.append(",");
            }
            resultado.append("\n");
            toStringAux(n.getSigVertice(), resultado);
        }
    }
    public String toString(){
        StringBuilder res = new StringBuilder("[");
        if (this.inicio != null){
            toStringAux(this.inicio, res);
        }
        res.append("]");
        return res.toString();
    }

}
