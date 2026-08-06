package sistema;

import estructuras.auxiliares.ParAuxiliar;
import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.tdaEspecifico.tablaBusquedaAVL.DiccionarioAvl;
import estructuras.tdaEspecifico.tablaBusquedaHash.DiccionarioHash;
import modelo.*;

import java.util.HashMap;

public class SistemaEscapeHouse {
    //asumo que en lo que es el sistema los datos ya vienen limpios, se validan en el main

    private Grafo planoCasa;
    private DiccionarioAvl habitaciones;
    private DiccionarioHash equipos;
    private final Habitacion entrada;
    //el primer tipo de parametro corresponde a la clave del equipo
    private HashMap<String, Lista> desafiosResueltosPorEquipo;
    private int numHabitacion;

    //definicion de puntajes como reglas para nuestro juego
    private static int PUNTAJE_FACIL = 200;
    private static int PUNTAJE_MEDIO = 400;
    private static int PUNTAJE_DIFICIL = 600;

    //constructor
    public SistemaEscapeHouse( ){
        this.planoCasa = new Grafo();
        this.habitaciones = new DiccionarioAvl();
        this.equipos = new DiccionarioHash(17);
        this.desafiosResueltosPorEquipo = new HashMap<>();
        //* PENDIENTE  cargar las habitaciones, plano y desafíos antes de asignar la primera habitación


        this.entrada = asignarPrimerHabitacion();
    }

    //asignaciones principales
    //el metodo no puede ser static porque usa una variable de instancia
    private Habitacion asignarPrimerHabitacion(){
        return (Habitacion) this.habitaciones.obtenerInfo(0);
    }
    private int calcularPuntajeExigido(int dificultad){
        int puntaje;

        switch (dificultad){
            case 3:
                puntaje = PUNTAJE_DIFICIL;
                break;
            case 2:
                puntaje = PUNTAJE_MEDIO;
                break;
            default:
                puntaje = PUNTAJE_FACIL;
                break;
        }
        return puntaje;
    }


    //CRUD Habitaciones

    private Habitacion validarHabitacionEditable(int codigoHabitacion){
        //recupero la lista de todos los equipos en el juego
        Lista todosLosEquipos = this.equipos.listarDatos();
        //obtengo el objeto Habitacion a partir de su clave
        Habitacion habitacion = (Habitacion) habitaciones.obtenerInfo(codigoHabitacion);

        //primero compruebo que la habitacion existe
        if (habitacion != null){
            //luego compruebo que no sea de salida ni de entrada
            if (!habitacion.getSalidaAlExterior() && entrada.getCodigo() != codigoHabitacion){
                //luego recorro toda la lista de equipos para comprobar que no hay ningun equipo en esa habiracion
                while (habitacion != null && !todosLosEquipos.esVacia()){
                    Equipo equipo = (Equipo) todosLosEquipos.recuperar(1);
                    todosLosEquipos.eliminar(1);
                    //si encontro un equipo que esta en la habitacion a eliminar, no se puede eliminar
                    if (equipo.getHabitacionActual().getCodigo() == codigoHabitacion ){
                        habitacion = null;
                    }
                }
            } else {
                habitacion = null;
            }
        }

        return habitacion;
    }

    //CREAD
    public boolean crearHabitacion( String nombre, int planta, int metrosCuadrados){
            boolean exito = false;
            numHabitacion = numHabitacion + 1;
            Habitacion nuevaHabitacion = new Habitacion(numHabitacion, nombre, planta, metrosCuadrados);
            boolean insertoDicc = habitaciones.insertar(numHabitacion, nuevaHabitacion);
            boolean insertoGrafo = planoCasa.insertarVertice(nuevaHabitacion);
            exito = insertoGrafo && insertoDicc;

            return exito;
    }

    //READ
    public String mostrarHabitacion(int codigo){
        String cadena = "Habitación no encontrada";
        Habitacion buscado = (Habitacion) this.habitaciones.obtenerInfo(codigo);
        if(buscado != null){
            cadena = buscado.toString();
        }
        return cadena;
    }

    //UPDATE
    public boolean actualizarNombreHab(int codigoHabitacion, String nuevoNombre){
        //busco el objeto de habitacion y valido si se puede editar
        Habitacion hab =  validarHabitacionEditable(codigoHabitacion);
        boolean exito = false;
        //si no es nula, se puede editar
        if (hab != null){
            hab.setNombre(nuevoNombre);
            exito = true;
        }
        return exito;
    }

    public boolean actualizarPlantaHab(int codigoHabitacion, int nuevaPlanta){
        Habitacion hab =  validarHabitacionEditable(codigoHabitacion);
        boolean exito = false;
        if (hab != null){
            hab.setPlanta(nuevaPlanta);
            exito = true;
        }
        return exito;
    }

    public boolean actualizarMtsCuadrHab(int codigoHabitacion, int nuevosMts){
        Habitacion hab =  validarHabitacionEditable(codigoHabitacion);
        boolean exito = false;
        if (hab != null){
            hab.setMetrosCuadrados(nuevosMts);
            exito = true;
        }
        return exito;
    }

    //DELETE
    public boolean eliminarHabitacion(int codigoHabitacion) {
        boolean exito = false;
        //obtengo el objeto Habitacion
        Habitacion habitacion = validarHabitacionEditable(codigoHabitacion);
        //si la habitacion es distinta de null, quiere decir que es posible eliminar la habitacion;
        if (habitacion != null){
            //elimino la habitacion del diccionario
            habitaciones.eliminar(codigoHabitacion);
            //elimino la habitacion en el grafo
            planoCasa.eliminarVertice(habitacion);
            exito = true;
        }
            return exito;
    }




    //CRUD Desafíos
    private boolean verificarPuntaje(int puntaje){
        return puntaje >= 0 && puntaje <= 100;
    }
    private boolean verificarString(String cadena){
        return cadena != null && !cadena.isEmpty();
    }
    private boolean verificarCodigo(int codigo){
        return codigo >= 0 && codigo <= 25;
    }
    //Create
    public String agregarDesafio(int codigoHab, int puntaje, String nombre, String tipo) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)&&verificarString(nombre)&&verificarString(tipo)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.agregarDesafio(puntaje, nombre, tipo)) {
                    cadena = "Desafio agregado correctamente";
                } else {
                    cadena = "No se ha podido agregar el desafio";
                }
            }
        }
        return cadena;
    }
    //Read
    public String obtenerNombreDesafio(int codigoHab, int puntaje){
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                cadena = habitacion.obtenerNombreDesafio(puntaje);
            }
        }
        return cadena;
    }
    public String obtenerTipoDesafio(int codigoHab, int puntaje){
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                cadena = habitacion.obtenerTipoDesafio(puntaje);
            }
        }
        return cadena;
    }
    //Update
    public String cambiarNombreDesafio(int codigoHab, int puntaje, String nombreDesafio) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)&&verificarString(nombreDesafio)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.cambiarNombreDesafio(puntaje, nombreDesafio)) {
                    cadena = "Se ha cambiado exitosamente el nombre del desafio";
                } else {
                    cadena = "No se ha podido cambiar el nombre del desafio";
                }
            }
        }
        return cadena;
    }
    public String cambiarTipoDesafio(int codigoHab, int puntaje, String tipoDesafio) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if(verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)&&verificarString(tipoDesafio)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.cambiarTipoDesafio(puntaje, tipoDesafio)) {
                    cadena = "Se ha cambiado exitosamente el tipo del desafio";
                } else {
                    cadena = "No se ha podido cambiar el tipo del desafio";
                }
            }
        }
        return cadena;
    }
    //Delete
    public String sacarDesafio(int codigoHab, int puntaje) {
        String cadena = "Los datos ingresados no cumplen con el formato deseado";
        if (verificarCodigo(codigoHab)&&verificarPuntaje(puntaje)) {
            cadena = "Habitacion no encontrada";
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if (habitacion != null) {
                if (habitacion.sacarDesafio(puntaje)) {
                    cadena = "Se ha sacado exitosamente el desafio";
                }else{
                    cadena = "No se ha podido sacar el desafio";
                }
            }
        }
        return cadena;
    }

    // CRUD Equipos
    //CREATE
    public boolean crearEquipo(String nombre, int dificultad){
        boolean exito = false;

        int puntajeExigido = calcularPuntajeExigido(dificultad);
        Equipo nuevoEquipo = new Equipo(nombre, puntajeExigido, this.entrada);
        exito = this.equipos.insertar(nombre, nuevoEquipo);


        return exito;
    }
    //READ
    private Equipo obtenerEquipo(String nombre){
        return (Equipo) this.equipos.obtenerInfo(nombre);
    }
    public String mostrarInfoEquipo(String nombre){
        return (obtenerEquipo(nombre)).toString();
    }
    //UPDATE
    public boolean actualizarEquipo(String nombre, int nuevaDificultad){
        boolean exito = false;
        Equipo encontrado = obtenerEquipo(nombre);

        if (encontrado != null){
            int nuevoPuntajeExigido = calcularPuntajeExigido(nuevaDificultad);
            encontrado.setPuntajeParaSalida(nuevoPuntajeExigido);
            exito = true;
        }
        return exito;
    }
    //DELETE
    public boolean eliminarEquipo(String nombre){
        return equipos.eliminar(nombre);
    }


    // consultas sobre habitaciones


    public String minimoPuntaje(int cod1, int cod2){
        Habitacion hab1 = (Habitacion) this.habitaciones.obtenerInfo(cod1);
        Habitacion hab2 = (Habitacion) this.habitaciones.obtenerInfo(cod2);
        StringBuilder cadena = new StringBuilder();
        cadena.append(this.planoCasa.caminoMasCorto(hab1,hab2).toString());
        cadena.append("\nEl puntaje requerido para pasar de la habitacion: ");
        cadena.append(hab1.getNombre()).append(" a la habitacion: ");
        cadena.append(hab2.getNombre()).append("es de: ");
        cadena.append(this.planoCasa.caminoMenorCosto(hab1,hab2));
        return cadena.toString();
    }

    public Lista habitacionesContiguas(int codigoHabitacion){
        //lista que se va a devolver
        Lista habitacionesConSusPuntajes = new Lista();

        Lista vecinos = habitacionesContiguasAux(codigoHabitacion);

        while (!vecinos.esVacia()){
            Habitacion habitacion;
            int puntaje;
            StringBuilder info = new StringBuilder();

            //voy recuperando la posición 1 para recuperar el nombre y codigo de la habitacion
            ParAuxiliar adyacente = (ParAuxiliar) vecinos.recuperar(1);
            habitacion = (Habitacion) adyacente.getElemento();
            puntaje = adyacente.getEtiqueta();

            info.append(habitacion.getCodigo()).append(" - ").append(habitacion.getNombre());
            info.append(" (Se necesitan: ").append(puntaje).append(" puntos).");

            //agrego la información a la lista
            habitacionesConSusPuntajes.insertar(info.toString(), habitacionesConSusPuntajes.longitud()+1);
            //saco el elemento de la lista original
            vecinos.eliminar(1);
        }

        return habitacionesConSusPuntajes;
    }
    private Lista habitacionesContiguasAux(int codigoHabitacion){
        Lista vecinos = new Lista();

        Habitacion hab = (Habitacion) habitaciones.obtenerInfo(codigoHabitacion);
        if (hab != null){
            //nodos adyacentes de la habitación
            vecinos = planoCasa.obtenerVecinos(hab);

        }
        //devuelve habitacion - puntaje, es un par
        return vecinos;
    }

    public Lista sinPasarPor(int codigo1, int codigo2, int p, int codigoExcluido){
        Lista resultado = new Lista();

        Lista l = sinPasarPorAux(codigo1, codigo2, p, codigoExcluido);

        //por cada elemento "vecino"
        while (!l.esVacia()){
            ParAuxiliar elem = (ParAuxiliar) l.recuperar(1);
            Lista camino = (Lista) elem.getElemento();
            StringBuilder resVecino = new StringBuilder("Puntaje alcanzado con el siguiente camino: ");
            resVecino.append(elem.getEtiqueta()).append(System.lineSeparator());

            while (!camino.esVacia()){
                Habitacion actual = (Habitacion) camino.recuperar(1);
                resVecino.append("-").append(actual.getNombre()).append(System.lineSeparator());

                camino.eliminar(1);
            }

            //como no importa el orden de los caminos
            resultado.insertar(resVecino.toString(), 1);
            l.eliminar(1);
        }

        return resultado;
    }
    private Lista sinPasarPorAux(int codigo1, int codigo2, int p, int codigoExcluido){
        Lista caminos = new Lista();
        //busco las habitaciones por codigo
        Habitacion hab1 = (Habitacion) habitaciones.obtenerInfo(codigo1);
        Habitacion hab2 = (Habitacion) habitaciones.obtenerInfo(codigo2);
        Habitacion habExcluida = (Habitacion) habitaciones.obtenerInfo(codigoExcluido);

        if (hab1 != null && hab2 != null && habExcluida != null){
            caminos = planoCasa.caminosSinPasarPorConEtiquetaMenorA(hab1, hab2, p, habExcluida);
        }

        return caminos;
    }

    public boolean esPosibleLLegar(int codigoHab1, int codigoHab2, int puntos){
        boolean esPosible = false;
        //recupero los objetos de las habitaciones
        Habitacion hab1 =  (Habitacion) this.habitaciones.obtenerInfo(codigoHab1);
        Habitacion hab2 = (Habitacion) this.habitaciones.obtenerInfo(codigoHab2);
        //si ambas habitaciones existen continuo
        if (hab1 != null && hab2 != null){
            //luego llamo al camino con menor costo
            Lista caminoMasCorto = planoCasa.caminoMenorCosto(hab1,hab2);
            //verifico que el camino no sea vacio, ya que si el camino es vacio es porque no existe un camino y no cumple la condicion
            if (!caminoMasCorto.esVacia()){
                //recupero la longituc del camino
                int longitud = caminoMasCorto.longitud();
                //recupero los puntos necesarios para ese camino, que estan guardados en la ultima posicion de la lista
                int puntosMin = (int)  caminoMasCorto.recuperar(longitud);
                //si los puntos que acumulo son mayores o iguales, al minimo de puntos que deberia tener para llegar a ese camino, devuelve true
                if (puntosMin <= puntos){
                    esPosible = true;
                }
            }

        }
        return esPosible;
    }

    //consultas sobre desafíos
    public String mostrarDesafio(int codigoDesafio, int numHabitacion){
        String cadena = "No es posible mostrar el desafio";
        Habitacion habitacion = (Habitacion) habitaciones.obtenerInfo(numHabitacion);
        //me fijo que exista la habitacion
        if (habitacion != null){
            Desafio desafio = (Desafio) habitacion.getDesafios().obtenerInfo(codigoDesafio);
            //me fijo que exista el desafio
            if (desafio != null){
                cadena = desafio.toString();
            }
        }
        return cadena;
    }

    public String mostrarDesafiosResueltos(String nombreEquipo){
        String resueltos = "Equipo no encontrado";
        Equipo buscado = (Equipo) this.equipos.obtenerInfo(nombreEquipo);
        if(buscado != null){
            //resueltos = buscado.listarDesafios().toString();
        }
        return resueltos;
    }

    public boolean verificarDesafíoResuelto(String nombreEquipo, int puntajeDesafio, int codigoHabitacion){
        boolean verificado = false;
        if (equipos.existeClave(nombreEquipo)) {
            //si existe el equipo y si ese equipo está en el hash map es porque resolvió 1 desafío
            if (desafiosResueltosPorEquipo.containsKey(nombreEquipo)){
                //busco la lista
                Lista desafiosResueltos = desafiosResueltosPorEquipo.get(nombreEquipo);

                //busco la habitación donde está ese desafío
                Habitacion habitacion = (Habitacion) habitaciones.obtenerInfo(codigoHabitacion);
                if (habitacion != null){
                    Desafio desafio = habitacion.getDesafio(puntajeDesafio);

                    //si existe el desafío en esa habitación
                    if (desafio != null){
                        //si encuentra la posición donde está guardado el desafío, entonces está en la lista de resueltos
                        verificado = (desafiosResueltos.localizar(desafio) > 0);
                    }
                }
            }
        }

        return verificado;
    }
    public Lista mostrarDesafiosTipo(int numHabitacion, int puntaje1, int puntaje2, String tipoDesafio){
            Lista desafios = new Lista();
            Habitacion habitacion = (Habitacion) habitaciones.obtenerInfo(numHabitacion);
            //me fijo que la habitacion exista
            if (habitacion != null){
                //recupero una lista auxiliar con todos los desafios comprendidos en ese rango de puntaje
                Lista desafiosAux = habitacion.getDesafios().listarRango(puntaje1, puntaje2);
                //lo utilizo para contar cuantos elementos va teniendo la nueva lista
                int pos = 0;
                //recorre mientra la lista auxiliar temga algun elemento
                while (!desafiosAux.esVacia()){
                    //recupero el primer desafio de lista
                    Desafio desafio =  (Desafio) desafiosAux.recuperar(1);
                    //si el desafio es del mismo tipo que se pide, se agrega a la lista que se va retornar
                    //esto va a ir filtrando solo los desafios del tipo pedido
                    if (desafio.getTipo().equals(tipoDesafio)){
                        desafios.insertar(desafio,pos+1);
                    }
                    //una vez chequeado si el desafio es o no del tipo pedido, se eliminar de la lista auxiliar
                    desafiosAux.eliminar(1);
                }
            }
            return desafios;
    }

    //consultas sobre equipos
    public boolean jugarDesafio(String nombreEquipo, int codigoHab, int puntaje){
        Equipo equipo = (Equipo) this.equipos.obtenerInfo(nombreEquipo);
        Habitacion habitacion;
        Desafio desafio;
        Lista aux;
        boolean exito = false;
        if(equipo != null){
            habitacion = (Habitacion) this.habitaciones.obtenerInfo(codigoHab);
            if(habitacion != null){
                desafio = habitacion.getDesafio(puntaje);
                if(desafio != null){
                    equipo.acumularPuntaje(puntaje);
                    equipo.acumularPuntajeEnHabitacion(puntaje);
                    aux = this.desafiosResueltosPorEquipo.get(nombreEquipo);
                    aux.insertar(desafio, aux.longitud()+1);
                    this.desafiosResueltosPorEquipo.put(nombreEquipo,aux);
                    exito = true;
                }
            }
        }
        return exito;
    }

    public boolean cambiarDeHabitación(String nombreEquipo, int codigoHab){
        boolean exito = false;
        //verifico que exista el equipo
        Equipo equipo = (Equipo) equipos.obtenerInfo(nombreEquipo);
        if (equipo != null){
            //verifico que exista la habitación
            Habitacion habitacion = (Habitacion) habitaciones.obtenerInfo(codigoHab);

            if (habitacion != null){
                Lista posiblesHabitaciones = habitacionesContiguasAux(equipo.getHabitacionActual().getCodigo());

                ParAuxiliar par = buscarParPorHabitacion(posiblesHabitaciones, habitacion);

                if (par != null){
                    int puntajeExigido = par.getEtiqueta();

                    //si el equipo tiene el suficiente puntaje
                    if (equipo.getPuntajeAcumulado() >= puntajeExigido){
                        equipo.cambiarHabitacionActual(habitacion);
                        equipo.reiniciarPuntajeEnHabitacion();
                        exito = true;
                    }
                }
            }
        }
        return exito;

    }
    private ParAuxiliar buscarParPorHabitacion(Lista posiblesHabitaciones, Habitacion buscada){
        ParAuxiliar par = null;
        boolean encontrada = false;

        while (!encontrada && !posiblesHabitaciones.esVacia()){
            ParAuxiliar actual = (ParAuxiliar) posiblesHabitaciones.recuperar(1);

            if (actual != null && ((Habitacion) actual.getElemento()).equals(buscada)){
                encontrada = true;
                par = actual;
            } else {
                posiblesHabitaciones.eliminar(1);
            }
        }
        return par;
    }

    public StringBuilder posiblesDesafios(String nombreEquipo, int numHabitacion){
        StringBuilder resultado = new StringBuilder();
        Equipo equipo =  (Equipo) this.equipos.obtenerInfo(nombreEquipo);
        //me fijo que el equipo exista
        if (equipo != null){
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerInfo(numHabitacion);
            //me fijo que la habitacion exista
            if (habitacion != null){
                //recupero la habitacion donde esta el equipo
                Habitacion habitacionActual = equipo.getHabitacionActual();
                //recupero una lista de los adyacentes a esa habitacion
                Lista vecinos = habitacionesContiguasAux(habitacionActual.getCodigo());
                //luego busco si la habitacion enviada por parametro esta en la lista de adyacentes
                ParAuxiliar par = buscarParPorHabitacion(vecinos, habitacion);
                //si el par no es nulo la habitacion es adyacente y si es nulo no es adyacente
                if (par != null){
                    //recupera los puntos para pasar a la habitacion
                    int puntosNecesarios = par.getEtiqueta();
                    //recupera los desafios de esa habitacion
                    Lista desafiosHab = habitacionActual.getDesafios().listarDatos();
                    Desafio desafioActual;
                    //recorre hasta que el avl esta vacio
                    while (!desafiosHab.esVacia()){
                        //recupera el desafio actual
                        desafioActual = (Desafio) desafiosHab.recuperar(1);
                        //si los puntos del desafio actual son mayores o iguales a los puntos que le falta
                        //al equipo para pasar a la habitacion, se agrega al resultado
                        if ((desafioActual.getPuntaje() + equipo.getPuntajeAcumulado()) >= puntosNecesarios){
                            resultado.append(desafioActual.toString()).append("\n");
                        }
                        desafiosHab.eliminar(1);
                    }

                    if (resultado.length() == 0){
                        resultado.append("No se encontraron posibles resultados");
                    }
                }else {
                    resultado.append("La habitacion no es adyacente");
                }
            }else {
                resultado.append("No se encontraron posibles resultados");
            }
        }else {
            resultado.append("No se encontraron posibles resultados");
        }

        return resultado;
    }

    public boolean puedeSalir(String nombreEquipo){
        boolean exito = false;
        Equipo equipo =  (Equipo) this.equipos.obtenerInfo(nombreEquipo);
        //compruebo que el equipo exista
        if (equipo != null){
            //si el puntaje acumulado es mayor o igual a su puntaje para salida y se encuentra en una habitacion con salida
            //al exterior, devuelve true
            if (equipo.getPuntajeAcumulado() >= equipo.getPuntajeParaSalida() && equipo.getHabitacionActual().getSalidaAlExterior()){
                exito = true;
            }
        }
        return exito;
    }

    //consultas generales
}
