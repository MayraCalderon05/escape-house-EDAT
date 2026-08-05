package estructuras.tdaEspecifico.tablaBusquedaHash;

import estructuras.lineales.Lista;

public class DiccionarioHash {
    private int TAM;
    private NodoHashDicc[] tabla;
    private int cant;
    //numero primo mas cercano al tamaño pero menor o igual a el
    private int numPrimo;

    public DiccionarioHash(int tamanio){
        this.TAM = tamanio;
        this.tabla = new NodoHashDicc[TAM];
        this.cant = 0;
        this.numPrimo = calcularNumeroPrimo();
    }

    public boolean insertar(Object clave, Object info){
        //busco la posicion del elemento
        int pos = calcularPos(clave);
        //me posiciono en la tabla
        NodoHashDicc aux = tabla[pos];
        boolean encontrado = false;
        //recorro la lista para comprobar que el elemento no existe en la tabla
        while (!encontrado && aux != null){
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        //si no lo encontró, lo crea y lo inserta al principio de la lista
        if (!encontrado){
            this.tabla[pos] = new NodoHashDicc(clave, info,this.tabla[pos]);
            this.cant++;
        }
        return !encontrado;
    }

    private boolean esPrimo(int n){
        boolean primo = true;
        //el 2 es primo pero el 0 y el 1 no porque el primo tiene exactamente 2 divisores
        if (n < 2){
            primo = false;
        } else if (n > 2){
            int i = 2;
            //un numero se comprueba q es primo si los divisores que llegan hasta raiz de n no son divisores de n
            //math.ceil redondea para arriba
            int limite = (int) Math.ceil(Math.sqrt(n));
            while ( primo && i <= limite ){
                if (n % i == 0){
                    primo = false;
                }
                i++;
            }
        }
         return primo;
    }
    private int calcularNumeroPrimo( ){
        int num = TAM;

        //retocedo hasta encontrar un primo
        while (!esPrimo(num)){
            num--;
        }
        return num;
    }
    private int calcularPos(Object clave){
        char caracter;
        int pos = 0;
        String claveAux = (String) clave;
        for (int i = 0; i < claveAux.length(); i++){
            caracter = claveAux.charAt(i);
            pos += caracter;
        }
        pos = pos % this.numPrimo;
        return pos;
    }
    private boolean eliminarAux(Object clave, NodoHashDicc n, int pos){
        boolean exito = true;
        //compruebo que el n no sea nulo porque CAPAZ justo en esa posicion no hay nada
        if (n != null){
            NodoHashDicc anterior = null;
            while (n != null && !clave.equals(n.getClave())){
                anterior = n;
                n = n.getEnlace();
            }

            if (anterior == null){
                //si no entro en el while, es el primero, seteo al segundo la primera posicion
                this.tabla[pos] = this.tabla[pos].getEnlace();
                //si el n no llego al nulo, es decir al que esta despues del ultimo, es que algo encontro
            } else if (n != null){
                //si es el ultimo tambien se cubre con este caso
                anterior.setEnlace(n.getEnlace());
            } else {
                exito = false;
            }
        } else {
            exito = false;
        }

        return exito;

    }
    public boolean eliminar(Object clave){
        //may
        boolean exito = false;
        if (this.cant > 0){
            //primero busco la posicion del arreglo en la que está
            //debido al calculo la posicion siempre esta dentro del rango
            int pos = calcularPos(clave);

            //recorro la posicion de la tabla
            exito = eliminarAux(clave, this.tabla[pos], pos);

            if (exito){
                this.cant--;
            }

        }
        return exito;
    }

    public Object obtenerInfo(Object clave){
        NodoHashDicc aux = this.tabla[calcularPos(clave)];  //Busca el nodo cabecera en la tabla hash
        Object buscado = null;                              //Si no encuentra el nodo devuelve null
        while (aux != null){                                //Recorre el diccionario y busca al nodo que tenga la clave pasada por parametro
            if(clave.equals(aux.getClave())){        //Pregunta si la clave del nodo es la misma que la pasada por parametro
                buscado = aux.getInfo();                    //Si lo encuentra devuelve el dato (Equipo) del nodo encontrado
            }else{
                aux = aux.getEnlace();                      //Pasa al siguiente nodo
            }
        }
        return buscado;
    }

    public boolean existeClave(Object clave){
        NodoHashDicc aux = this.tabla[calcularPos(clave)];  //Busca el nodo cabecera en la tabla hash
        boolean encontrado = false;                         //Devuelve false si no encuentra un nodo con la clave
        while (aux != null){                                //Recorre el diccionario y busca al nodo que tenga la clave pasada por parametro
            if(clave.equals(aux.getClave())){        //Pregunta si la clave del nodo es la misma que la pasada por parametro
                encontrado = true;                          //Si lo encuentra devuelve true
            }else{
                aux = aux.getEnlace();                      //Pasa al siguiente nodo
            }
        }
        return encontrado;
    }

    public Lista listarClaves(){
        Lista lista = new Lista();
        if (this.cant > 0){
            for (int i = 0; i < this.tabla.length; i++){
                if (this.tabla[i] != null){
                    NodoHashDicc aux = this.tabla[i];
                    while (aux != null){
                        lista.insertar(aux.getClave(), lista.longitud()+1);
                        aux = aux.getEnlace();
                    }
                }
            }

        }
        return lista;
    }

    public Lista listarDatos(){
        Lista lista = new Lista();
        //si el diccionario no esta vacio
        if (this.cant > 0){
            //recorre toda la tabla
            for (int i = 0; i < this.tabla.length; i++){
                //si  en esa posicion hay un elemento
                if (this.tabla[i] != null){
                    NodoHashDicc aux = this.tabla[i];
                    //recorre la lista hasta que no haya mas elementos
                    while (aux != null){
                        //inserta el elemento a la lista
                        lista.insertar(aux.getInfo(), lista.longitud()+1);
                        //busca el siguiente elemento de la lista
                        aux = aux.getEnlace();
                    }
                }
            }

        }
        return lista;
    }

    public boolean esVacio(){
        return this.cant == 0;
    }

    private void clonarColisiones(DiccionarioHash copia, int i){
        NodoHashDicc auxOriginal = this.tabla[i];
        NodoHashDicc auxCopia = new NodoHashDicc(auxOriginal.getClave(), auxOriginal.getInfo(), null);
        copia.tabla[i] = auxCopia;

        auxOriginal = auxOriginal.getEnlace();

        while (auxOriginal != null){
            NodoHashDicc nuevo = new NodoHashDicc(auxOriginal.getClave(), auxOriginal.getInfo(), null);
            auxCopia.setEnlace(nuevo);

            auxOriginal = auxOriginal.getEnlace();
            auxCopia = nuevo;
        }
    }
    private void clonarArr(DiccionarioHash copia){

        for (int i = 0; i < this.TAM; i++){
            if (this.tabla[i] != null){
                clonarColisiones(copia, i);
            }
        }
    }
    public DiccionarioHash clone(){
        //may
        DiccionarioHash copia = new DiccionarioHash(this.TAM);
        if (this.cant > 0){
            copia.cant = this.cant;
            clonarArr(copia);
        }
        return copia;
    }

    public void vaciar(){
        for(int i = 0; i < this.cant; i++){
            this.tabla[i] = null;
        }
    }

    /* mi idea es
    0:
        obj1,
        obj2
    1:
        / /
    2:
        obj3,
        obj4,
        obj5
    */
    private String toStringAux(NodoHashDicc n){
        StringBuilder cadena = new StringBuilder();
        while (n != null){
            cadena.append(n.toString());
            n = n.getEnlace();

            if (n != null){
                cadena.append(",\n   ");
            }
        }

        return cadena.toString();
    }
    public String toString(){
        StringBuilder res = new StringBuilder("[\n");
        for (int i = 0; i < this.TAM; i++) {
            res.append(i).append("\n   ");
            NodoHashDicc actual = tabla[i];
            if (actual != null){
                res.append(toStringAux(actual));
            } else {
                res.append("//");
            }
            res.append("\n");
        }
        res.append("\n]");
        return res.toString();
    }
}
