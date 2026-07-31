package estructuras.tdaEspecifico.tablaBusquedaHash;

import estructuras.lineales.Lista;
import tdaDominio.Equipo;

public class DiccionarioEquipos {
    private int TAM;
    private NodoHashDicc[] tabla;
    private int cant;
    //numero primo mas cercano al tamaño pero menor o igual a el
    private int numPrimo;

    public DiccionarioEquipos(int tamanio){
        this.TAM = tamanio;
        this.tabla = new NodoHashDicc[TAM];
        this.cant = 0;
        this.numPrimo = calcularNumeroPrimo();
    }

    public boolean insertar(String clave, Equipo info){
        //more
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
    private int calcularPos(String clave){
        char caracter;
        int pos = 0;
        for (int i = 0; i < clave.length(); i++){
            caracter = clave.charAt(i);
            pos += caracter;
        }
        pos = pos % this.numPrimo;
        return pos;
    }
    private boolean eliminarAux(String clave, NodoHashDicc n, int pos){
        boolean exito = true;
        //compruebo que el n no sea nulo porque CAPAZ justo en esa posicion no hay nada
        if (n != null){
            NodoHashDicc anterior = null;
            while (n != null && !clave.equals(n.getNombreEquipo())){
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
    public boolean eliminar(String clave){
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
            if(clave.equals(aux.getNombreEquipo())){        //Pregunta si la clave del nodo es la misma que la pasada por parametro
                buscado = aux.getDato();                    //Si lo encuentra devuelve el dato (Equipo) del nodo encontrado
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
            if(clave.equals(aux.getNombreEquipo())){        //Pregunta si la clave del nodo es la misma que la pasada por parametro
                encontrado = true;                          //Si lo encuentra devuelve true
            }else{
                aux = aux.getEnlace();                      //Pasa al siguiente nodo
            }
        }
        return encontrado;
    }

    public Lista listarClaves(){
        //more
    }

    public Lista listarDatos(){
        //more
    }

    public boolean esVacio(){
        return this.cant == 0;
    }

    public DiccionarioEquipos clone(){
        //may
    }

    public void vaciar(){
        for(int i = 0; i < this.cant; i++){
            this.tabla[i] = null;
        }
    }

    public String toString(){
        //may
    }
}
