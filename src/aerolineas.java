import principal.TDA.*;

import java.time.LocalTime;
import java.util.Scanner;

public class aerolineas {
    public static void main(String[] args) {
        // Llamamos la carga de datos una sola vez al inicio
        Lectura.cargarTodo();

        Scanner sc = new Scanner(System.in);
        // mostramos el menu
        mostrarMenu(sc);

        sc.close();
    }

    // metodo para mostrar el menu
    public static void mostrarMenu(Scanner sc) {
        // iniciamos las variables globales
        Avion[] aviones = Lectura.aviones;
        Ruta[] rutas = Lectura.rutas;
        Vuelo[][] vuelos = Lectura.vuelos;
        boolean continuar = true;
        int opcion;
        do {
            System.out.println("\nSeleccione una opción:\n" +
                    "1. Agregar un nuevo avión\n" +
                    "2. Agregar un nuevo vuelo\n" +
                    "3. Marcar un vuelo como aterrizado\n" +
                    "4. Ver el promedio de pasajeros que efectivamente volaron\n" +
                    "5. Ver la lista de vuelos ordenada por distancia en km de forma ascendente\n" +
                    "6. Ver los datos de un avion\n" +
                    "7.Ver la matriz de vuelos\n" +
                    "8. Ver los vuelos que tengan una ruta comprendida entre un rango de distancias\n" +
                    "9. Ver la cantidad de horarios sin vuelos en la semana\n" +
                    "10. Ver el primer horario de un vuelo internacional de cada dia de la semana");
            System.out.print("\nIngrese su opción:\n");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    agregarAvion(aviones, sc);
                    break;
                case 2:
                    agregarVuelo(vuelos, aviones, rutas, sc);
                    break;
                case 3:
                    System.out.println("Ingrese el número de vuelo que desea marcar como aterrizado:");
                    String numVuelo = sc.next();
                    vueloAterrizado(vuelos, numVuelo);
                    break;
                case 4:
                    double promedio = promedioPasajeros(vuelos);
                    if (promedio == 0.0) {
                        System.out.println("No se han registrado vuelos aterrizados aún.");
                    } else {
                        System.out.println("El promedio de pasajeros que efectivamente volaron es de: " + promedio);

                    }
                    break;
                case 5:
                    ordenarXdistancia(vuelos, sc);
                    break;
                case 6:
                    System.out.println("Ingrese el ID del avión que desea consultar:");
                    String idAvion = sc.next();
                    String datosAvion = mostrarDatosAvion(aviones, idAvion);
                    System.out.println(datosAvion);
                    break;
                case 7:
                    mostrarMatrizVuelos(vuelos);
                    break;
                case 8:
                    encuentraRutas(rutas, sc);
                    break;
                case 9:
                    mostrarHorariosSinVuelos(vuelos);
                    break;
                case 10:
                    mostrarPrimerosVuelosInternacionales(vuelos);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 10.");
                    break;
            }
            String respuesta = enviarConfirmacion(sc, "Desea realizar otra operación?");
            continuar = mensajeConfirmacion(respuesta);
            if (!continuar) {
                System.out.println("Operación cancelada");
            }

        } while (continuar);
    }

    // * */ metodo para agregar un nuevo avion
    public static void agregarAvion(Avion[] aviones, Scanner sc) {
        String identificacion;
        String modelo;
        int cantAsientos;
        boolean continuar = true;

        do {
            System.out.println("Ingrese la identificacion del avion:");
            identificacion = sc.next();
            if (validarIdAvion(identificacion) && !existeAvion(aviones, identificacion)) {
                System.out.println("Ingrese el modelo del avion:");
                modelo = sc.next();
                do {
                    System.out.println("Ingrese la cantidad de asientos que tiene el avión");
                    cantAsientos = sc.nextInt();
                    // valores imposibles
                    if (cantAsientos > 0 && cantAsientos < 300) {
                        Avion nuevoAvion = new Avion(identificacion, modelo, cantAsientos);
                        System.out.println("Avion registrado con éxito");
                        guardarAvion(aviones, nuevoAvion);
                        System.out.println("Avion guardado con éxito");
                        continuar = false;
                    } else {
                        System.out.println("La cantidad de asientos es inválida");
                        String respuesta = enviarConfirmacion(sc, "Desea volver a intentarlo?");
                        continuar = mensajeConfirmacion(respuesta);
                        if (!continuar) {
                            System.out.println("Operación cancelada");
                        }
                    }
                } while (continuar);
            } else {
                if (!validarIdAvion(identificacion)) {
                    System.out.println("El identificador del avión no es válido");
                } else {
                    System.out.println("El avión con ID: " + identificacion + " ya existe.");
                }
                String respuesta = enviarConfirmacion(sc, "Desea volver a intentarlo?");
                continuar = mensajeConfirmacion(respuesta);
                if (!continuar) {
                    System.out.println("Operación cancelada");
                }
            }
        } while (continuar);
    }

    // metodo para guardar el avion en el arreglo
    public static void guardarAvion(Avion[] array, Avion nuevoAvion) {
        int i = 0;
        boolean noGuardado = true;
        // mientras la variable guardado sea verdadera
        while (i < array.length && noGuardado) {
            if (array[i] == null) {
                array[i] = nuevoAvion;
                noGuardado = false;
            } else {
                i++;
            }
        }
        if (noGuardado) {
            System.out.println("Error: No hay espacio disponible para guardar el avión.");
        }
    }

    // metodo para verificar si un avion ya existe
    public static boolean existeAvion(Avion[] aviones, String id) {
        boolean encontrado = false;
        if (Lectura.encontrarAvionPorId(aviones, id) != null) {
            encontrado = true;
        }

        return encontrado;
    }

    // metodo para validar el id del avion
    public static boolean validarIdAvion(String id) {
        // si no esta bien estructurado no entra en ningun condicional y retorna falso
        boolean valido = false;

        if (id == null)

            return false;

        // ?verifico que tenga el guion en el medio y que no se pase de caracteres y ahi
        // analizo el resto
        // necesito que tenga al menos 6 caracteres para que pueda ser valido
        // comprobar longitud mínima antes de acceder a posiciones concretas
        if (id.length() >= 6 && existeGuion(id)) {
            // la posicion 2 es del guion, no la toma en el substring
            String iniciales = id.substring(0, 2);
            // me refiero al resto de la cadena despues de las iniciales
            String resto;

            // analizo las primeras dos iniciales
            switch (iniciales) {
                case "LV":
                    char postGuion = id.charAt(3);
                    switch (postGuion) {
                        // id tipo LV-X
                        case 'X':
                            resto = id.substring(4);
                            valido = verificarNumeros(resto);
                            break;
                        case 'S':
                            char ultimaSigla = id.charAt(4);
                            // para id de tipo LV-SX
                            if (ultimaSigla == 'X') {
                                resto = id.substring(5);
                            } else {
                                // id tipo LV-S
                                resto = id.substring(4);
                            }
                            valido = verificarNumeros(resto);
                            break;
                        default:
                            // si solo es tipo LV verifico que el resto sean letras
                            resto = id.substring(3);

                            if (!verificarNumeros(resto)) {
                                valido = true;
                            }
                            break;
                    }
                    break;

                case "LQ":
                    resto = id.substring(3);
                    if (!verificarNumeros(resto)) {
                        valido = true;
                    }
                    break;
                default:
                    valido = false;
                    break;
            }
        }
        return valido;
    }

    // metodo para verificar si una cadena contiene solo numeros
    private static boolean verificarNumeros(String resto) {
        boolean valido = true;
        int i = 0;
        if (resto.length() <= 3) {
            while (valido && i < resto.length()) {
                char caracter = resto.charAt(i);

                // verifico que sea un digito numerico por CODIGO ASCII
                if (caracter < '0' || caracter > '9') {
                    valido = false;
                } else {
                    i++;
                }
            }
        } else {
            valido = false;
        }

        return valido;

    }

    // metodo para verificar si existe el guion en la posicion correcta
    private static boolean existeGuion(String id) {
        // proteger contra cadenas cortas
        if (id == null || id.length() <= 2)
            return false;
        return id.charAt(2) == '-';
    }

    // metodo para mostrar los datos de un avion por su id
    public static String mostrarDatosAvion(Avion[] array, String id) {
        String info = "";
        // validar primero si el id que se ingreso puede llegar a existir
        if (validarIdAvion(id)) {
            Avion encontrado = Lectura.encontrarAvionPorId(array, id);
            if (encontrado != null) {
                info = encontrado.toString();
            } else {
                info = "No se encontró ningún avión con el ID ingresado.";
            }
        } else {
            info = "El ID del avión ingresado no es válido.";
        }

        return info;
    }

    // metodo para verificar si un vuelo ya existe
    private static boolean existeVuelo(Vuelo[][] vuelos, String num) {
        boolean existe = false;
        int i = 0;
        int j = 0;
        do {
            while (j < vuelos[i].length) {
                if (vuelos[i][j] != null && vuelos[i][j].getNumeroVuelo().equals(num)) {
                    existe = true;
                }
                j++;
            }
            j = 0;
            i++;
        } while (!existe && i < vuelos.length);

        return existe;
    }

    // metodo para solicitar un avion existente
    private static Avion solicitarAvion(Avion[] aviones, Scanner sc) {
        String idAvion;
        Avion avionEncontrado = null;
        boolean continuar = true;

        do {
            System.out.println("Ingrese la identificación del avión:");
            idAvion = sc.next();

            avionEncontrado = Lectura.encontrarAvionPorId(aviones, idAvion);

            if (avionEncontrado == null) {
                String respuesta = enviarConfirmacion(sc,
                        "No se encontró el avión con ID: " + idAvion + ". Desea volver a intentarlo?");
                continuar = mensajeConfirmacion(respuesta);
            }

        } while (avionEncontrado == null && continuar);

        return avionEncontrado;
    }

    // metodo para solicitar una ruta existente
    private static Ruta solicitarRuta(Ruta[] rutas, Scanner sc) {
        String idRuta;
        Ruta rutaEncontrada = null;
        boolean continuar = true;

        do {
            System.out.println("Ingrese el ID de la ruta:");
            idRuta = sc.next();

            rutaEncontrada = Lectura.encontrarRutaPorId(rutas, idRuta);

            if (rutaEncontrada == null) {
                String respuesta = enviarConfirmacion(sc,
                        "No se encontró la ruta con ID: " + idRuta + ". Desea volver a intentarlo?");
                continuar = mensajeConfirmacion(respuesta);
            }

        } while (rutaEncontrada == null && continuar);

        return rutaEncontrada;

    }

    // metodo para solicitar dia de la semana
    public static String solicitarDia(Scanner sc) {
        boolean continua = true;
        String dia;
        int posI = -1;

        do {
            System.out.println("Ingrese el día de la semana:");
            dia = sc.next();
            posI = Lectura.obtenerIndiceDia(dia);

            if (posI == -1) {
                String rta = enviarConfirmacion(sc, "El día ingresado no es válido. Desea volver a intentarlo?");
                continua = mensajeConfirmacion(rta);
            }
        } while (continua && posI == -1);

        return dia;
    }

    // metodo para solicitar horario de salida del vuelo
    public static LocalTime solicitarHorario(Scanner sc) {
        boolean continua = true;
        String horarioSalidaStr;
        LocalTime horarioSalida;
        int posJ = -1;

        do {
            System.out.println("Ingrese el horario de salida (hh:mm):");
            horarioSalidaStr = sc.next();
            horarioSalida = LocalTime.parse(horarioSalidaStr);
            posJ = Lectura.obtenerIndiceHorario(horarioSalida);

            if (posJ == -1) {
                String rta = enviarConfirmacion(sc, "El horario ingresado no es válido. Desea volver a intentarlo?");
                continua = mensajeConfirmacion(rta);
            }
        } while (continua && posJ == -1);

        return horarioSalida;
    }

    // metodo para agregar un nuevo vuelo
    public static void agregarVuelo(Vuelo[][] vuelos, Avion[] aviones, Ruta[] rutas, Scanner sc) {
        String numeroVuelo;
        boolean continuaNumVuelo = true;

        do {
            System.out.println("Ingrese el número de vuelo:");
            numeroVuelo = sc.next();
            // valido que no exista
            if (!existeVuelo(vuelos, numeroVuelo)) {
                // que frene la primera repetitiva
                continuaNumVuelo = false;

                Avion avion = solicitarAvion(aviones, sc);
                if (avion != null) {

                    Ruta ruta = solicitarRuta(rutas, sc);
                    if (ruta != null) {
                        boolean continuar = true;

                        do {
                            String dia = solicitarDia(sc);
                            LocalTime horarioSalida = solicitarHorario(sc);

                            int posI = Lectura.obtenerIndiceDia(dia);
                            int posJ = Lectura.obtenerIndiceHorario(horarioSalida);
                            // busca que las posiciones sean validas por si el usuario cancela la operacion
                            if (posI != -1 && posJ != -1) {
                                if (vuelos[posI][posJ] != null) {
                                    String rta = enviarConfirmacion(sc,
                                            "Ya existe un vuelo en ese día y horario. desea volver a intentarlo?");
                                    continuar = mensajeConfirmacion(rta);
                                } else {
                                    vuelos[posI][posJ] = new Vuelo(numeroVuelo, avion, ruta, dia, horarioSalida);
                                    System.out.println("Vuelo agregado con éxito.");
                                    continuar = false;
                                }
                            } else {
                                System.out.println("Operación cancelada");
                                // no continua el bucle porque si la posición es -1 en ambas es porque
                                // cancelaron la operacion en el modulo
                                // ya que si el modulo tira dia u horarioSalida en nulo o invalido es porque el
                                // usuario se cansó
                                continuar = false;
                            }

                        } while (continuar);
                    } else {
                        System.out.println("Operación cancelada");
                    }
                } else {
                    System.out.println("Operación cancelada");
                }
            } else {
                String respuesta = enviarConfirmacion(sc, "Este vuelo ya existe. desea volver a intentar?");
                continuaNumVuelo = mensajeConfirmacion(respuesta);
                if (!continuaNumVuelo) {
                    System.out.println("Operación cancelada");
                }
            }
        } while (continuaNumVuelo);

    }

    // metodo para procesar la respuesta de confirmacion de seguir o no en el menu
    private static boolean mensajeConfirmacion(String rta) {
        boolean confirmacion;
        if (rta.equalsIgnoreCase("S")) {
            confirmacion = true;
        } else {
            confirmacion = false;
        }

        return confirmacion;
    }

    // metodo para enviar un mensaje de confirmacion de si seguir en el menu y
    // obtener la respuesta del usuario
    private static String enviarConfirmacion(Scanner sc, String mensaje) {
        System.out.println(mensaje + "s/n");
        String rta = sc.next();

        return rta;
    }
    // metodo de ordenamiento QUICK SORT para ordenar vuelos por distancia en km de
    // forma ascendente
    // primero pongo todos los vuelos en un arreglo para que sea mas facil

    // metodo recursivo para calcular el promedio de pasajeros que efectivamente
    // volaron
    public static double promedioPasajeros(Vuelo[][] vuelos) {
        double promedio = 0.0;
        int[] valores = cantidadAterrizados(vuelos, 0, 0, 0, 0);
        // para que no realice la división por cero comprueblo que se pueda dividir
        if (valores[1] > 0) {
            promedio = (double) (valores[0] / valores[1]);
        }
        return promedio;
    }

    private static int[] cantidadAterrizados(Vuelo[][] vuelos, int cantVuelos, int acumPasajeros, int i, int j) {
        int[] contador = new int[2]; // contador[0] = cantPasajeros || contador[1] = cantVuelos

        if (j >= vuelos[0].length) {
            j = 0;
            i++;
        }

        if (i < vuelos.length) {
            // Si hay un vuelo aterrizado, acumular
            if (vuelos[i][j] != null && vuelos[i][j].getAterrizado()) {
                acumPasajeros = acumPasajeros + vuelos[i][j].getAvion().getCantAsientos();
                cantVuelos++;
            }
            // continuar recursivamente, solo acumula si esta aterrizado
            contador = cantidadAterrizados(vuelos, cantVuelos, acumPasajeros, i, j + 1);
        } else {
            // si ya termino de recorrer la matriz
            // CASO BASE: retornar las variables acumuladas, sin sumar nada mas
            contador[0] = acumPasajeros;
            contador[1] = cantVuelos;
        }

        return contador;
    }

    // metodo para filtrar los vuelos por dia y devolver un arreglo con esos vuelos
    private static Vuelo[] filtrarDia(Vuelo[][] mat, String dia) {
        Vuelo[] listaVuelos = new Vuelo[cuentaElementos(mat, dia)];
        int fila = Lectura.obtenerIndiceDia(dia);
        int i = 0;
        for (int columna = 0; columna < mat[fila].length; columna++) {
            if (mat[fila][columna] != null) {
                listaVuelos[i] = mat[fila][columna];
                i++;
            }
        }
        return listaVuelos;
    }

    // metodo para definir el tamaño del arreglo
    private static int cuentaElementos(Vuelo[][] mat, String dia) {
        int indiceFila = Lectura.obtenerIndiceDia(dia);
        int i = 0;
        int contador = 0;

        while (i < mat[indiceFila].length) {
            if (mat[indiceFila][i] != null) {
                contador++;
            }
            i++;
        }

        return contador;
    }

    // metodo para obtener la distancia por el ID de ruta
    private static double obtenerDistanciaVuelo(Vuelo vuelo) {
        double distancia = 0.00;
        if (vuelo != null) {
            distancia = vuelo.getRuta().getDistancia();
        }
        return distancia;
    }

    // metodo para elegir un pivot segun la mediana
    private static int mediana(Vuelo[] arr, int inicio, int fin) {
        int indice;
        // en caso de que los parametros no sean validos
        if (inicio < 0 || fin >= arr.length || inicio > fin) {
            indice = -1;

        } else {
            int medio = (inicio + fin) / 2;
            double rutaInicio = obtenerDistanciaVuelo(arr[inicio]);
            double rutaMedio = obtenerDistanciaVuelo(arr[medio]);
            double rutaFin = obtenerDistanciaVuelo(arr[fin]);

            // rutaInicio < rutaMedio < rutaFin OR rutaFin < rutaMedio < rutaInicio
            // en caso de que rutaMedio este en el medio de los valores
            if (((rutaInicio <= rutaMedio) && (rutaMedio <= rutaFin)) ||
                    ((rutaFin <= rutaMedio) && (rutaMedio <= rutaInicio))) {
                indice = medio;

                // rutaMedio < rutaInicio < rutaFin OR rutaFin < rutaInicio < rutaMedio
                // en caso de que rutaInicio este en el medio de los valores
            } else if (((rutaMedio <= rutaInicio) && (rutaInicio <= rutaFin)) ||
                    ((rutaFin <= rutaInicio) && (rutaInicio <= rutaMedio))) {
                indice = inicio;
            } else {
                // que la rutaFin sea la que esta en el medio ya que no hay otro caso
                indice = fin;
            }
        }

        return indice;
    }

    // ahora si, ordeno
    // metodo para intercambar los lugares
    private static int particion(Vuelo[] arr, int inicio, int fin, int pivote) {
        // estos son los indices que se usan para ubicar los elementos
        int izq, der;
        Vuelo aux;
        izq = inicio;
        der = fin;

        double distPivote = obtenerDistanciaVuelo(arr[pivote]);

        while (izq <= der) {
            // busco el elemento que tenga mayor distancia en km que el pivote
            // desde la izquierda
            while (obtenerDistanciaVuelo(arr[izq]) < distPivote) {
                izq++;
            }

            // busco el elemento que tenga menor distancia en km que el pivote
            // desde la derecha
            while (obtenerDistanciaVuelo(arr[der]) > distPivote) {
                der--;
            }

            // intercambiar los lugares si no se cruzaron los indices izq y der
            if (izq <= der) {
                aux = arr[izq];
                arr[izq] = arr[der];
                arr[der] = aux;
                // este aumento es para que salga del bucle
                izq++;
                der--;
            }
        }
        // retorno la posicion donde se cruzan los indices ya que ahi se parte el
        // arreglo
        return izq;
    }

    // metodo quicksort de ordenamiento
    private static void quicksort(Vuelo[] lista, int inicio, int fin) {
        int indice, particion;

        // CASO BASE: inicio >= fin
        if (inicio < fin) {
            indice = mediana(lista, inicio, fin);
            // confirmo que el pivote sea valido
            if (indice >= 0) {
                particion = particion(lista, inicio, fin, indice);
                // ordena la parte izquierda
                quicksort(lista, inicio, particion - 1);
                // ordena la parte derecha
                quicksort(lista, particion, fin);
            }
        }

    }

    // metodo para ordenar los vuelos por distancia en km de forma ascendente
    public static void ordenarXdistancia(Vuelo[][] mat, Scanner sc) {
        System.out.println("ingrese el día del que desea obtener información");
        String dia = sc.next();
        if (Lectura.obtenerIndiceDia(dia) != -1) {
            Vuelo[] lista = filtrarDia(mat, dia);

            if (lista.length == 0) {
                System.out.println("No hay vuelos para el día " + dia);
            } else {
                quicksort(lista, 0, lista.length - 1);
                Lectura.escritura(lista);

            }
        } else {
            System.out.println("El día ingresado no es válido");
        }
    }

    // metodo para registrar un vuelo como aterrizado
    public static void vueloAterrizado(Vuelo[][] vuelos, String numeroVuelo) {
        boolean encontrado = false;
        int i = 0;
        int j = 0;
        System.out.println("Buscando vuelo: " + numeroVuelo);
        while (i < vuelos.length && !encontrado) {
            j = 0;
            while (j < vuelos[0].length && !encontrado) {
                if (vuelos[i][j] != null) {
                    if (vuelos[i][j].getNumeroVuelo().equals(numeroVuelo)) {
                        encontrado = true;
                        if (!vuelos[i][j].getAterrizado()) {
                            vuelos[i][j].setAterrizado(true);
                            actualizarAvionAterrizado(vuelos[i][j]);
                            System.out.println("El vuelo " + numeroVuelo + " ha sido marcado como aterrizado");
                        } else {
                            System.out.println("El vuelo " + numeroVuelo + " ya estaba marcado como aterrizado");
                        }
                    }
                }
                j++;
            }
            i++;
        }
        if (!encontrado) {
            System.out.println("Error: No se encontró ningún vuelo con el número \"" + numeroVuelo + "\".");
        }
    }

    // metodo para actualizar los datos del avion cuando un vuelo aterriza
    public static void actualizarAvionAterrizado(Vuelo vuelo) {
        // actualiza la cantidad de vuelos del avion
        int nuevosVuelos = vuelo.getAvion().getCantVuelos() + 1;
        vuelo.getAvion().setCantVuelos(nuevosVuelos);
        // actualiza los km recorridos del avion
        double nuevosKm = vuelo.getAvion().getKmRecorridos() + vuelo.getRuta().getDistancia();
        vuelo.getAvion().setKmRecorridos(nuevosKm);
    }

    // metodo para establecer el espacio del arreglo que busca las rutas
    private static int contarRutas(double distMin, double distMax, Ruta[] rutas) {
        int contador = 0;

        for (int i = 0; i < rutas.length; i++) {
            double distancia = rutas[i].getDistancia();
            if ((distancia >= distMin) && (distancia <= distMax)) {
                contador++;
            }

        }

        return contador;
    }

    // metodo para buscar rutas en un rango de distancia
    public static Ruta[] buscarRutas(double distMin, double distMax, Ruta[] rutas) {
        int tamanio = contarRutas(distMin, distMax, rutas);
        Ruta[] encontradas;
        if (tamanio == 0) {
            encontradas = new Ruta[0];
        } else {
            encontradas = new Ruta[tamanio];
            int j = 0;
            for (int i = 0; i < rutas.length; i++) {
                double distancia = rutas[i].getDistancia();
                if ((distancia >= distMin) && (distancia <= distMax)) {
                    encontradas[j] = rutas[i];
                    j++;
                }
            }
        }

        return encontradas;
    }

    // metodo para solicitar al usuario el rango de distancia y mostrar las rutas
    public static void encuentraRutas(Ruta[] rutas, Scanner sc) {
        boolean sigue = true;
        do {
            System.out.println("Ingrese la distancia mínima a partir de la que quiere buscar");
            int distMin = sc.nextInt();
            if (distMin >= 0) {
                System.out.println("Ingrese la distancia máxima hasta donde quiere buscar");
                int distMax = sc.nextInt();

                if (distMax < distMin) {
                    System.out.println("Error: La distancia máxima no puede ser menor que la distancia mínima.");
                    String respuesta = enviarConfirmacion(sc, "Desea realizar otra búsqueda?");
                    sigue = mensajeConfirmacion(respuesta);
                    if (!sigue) {
                        System.out.println("Operación cancelada");
                    }
                } else {
                    Ruta[] rutasEncontradas = buscarRutas(distMin, distMax, rutas);
                    if (rutasEncontradas.length > 0) {
                        for (int i = 0; i < rutasEncontradas.length; i++) {
                            System.out.println(rutasEncontradas[i].toString() + "\n");
                            sigue = false;
                        }
                    } else {
                        System.out.println("No se encontraron rutas en el rango de distancia ingresado");
                        String respuesta = enviarConfirmacion(sc, "Desea realizar otra búsqueda?");
                        sigue = mensajeConfirmacion(respuesta);
                        if (!sigue) {
                            System.out.println("Operación cancelada");
                        }
                    }
                }

            } else {
                System.out.println("La distancia mínima ingresada no es válida");
                String respuesta = enviarConfirmacion(sc, "Desea realizar otra búsqueda?");
                sigue = mensajeConfirmacion(respuesta);
                if (!sigue) {
                    System.out.println("Operación cancelada");
                }
            }
        } while (sigue);

    }

    // metodo recursivo para contar los horarios sin vuelos en la semana
    private static int calcularHorariosSinVuelos(Vuelo[][] mat, int i, int j) {
        int cantSinVuelos;
        // primer caso base, cuando el largo de la columna sea invalido
        if (j == mat[0].length) {
            j = 0;
            i++;
        }
        // paso recursivo: mientras que las filas sean validas
        if (i < mat.length) {
            if ((mat[i][j] == null)) {
                cantSinVuelos = 1 + calcularHorariosSinVuelos(mat, i, j + 1);
            } else {
                cantSinVuelos = calcularHorariosSinVuelos(mat, i, j + 1);
            }
            // si las filas no son validas, segundo caso base
        } else {
            cantSinVuelos = 0;
        }

        return cantSinVuelos;
    }

    // metodo para mostrar la cantidad de horarios sin vuelos en la semana
    public static void mostrarHorariosSinVuelos(Vuelo[][] mat) {
        int cant = calcularHorariosSinVuelos(mat, 0, 0);
        System.out.println("La cantidad de horarios sin vuelos en la semana es de: " + cant);
    }

    // metodo para mostrar la matriz de vuelos
    public static void mostrarMatrizVuelos(Vuelo[][] vuelos) {
        imprimirEncabezado();
        imprimirFilas(vuelos);
    }

    // metodo para imprimir los horarios de los vuelos
    private static void imprimirEncabezado() {
        String[] horarios = {
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00", "22:00"
        };

        // dejo un espacio para la columna de los dias
        System.out.print("     ");
        // imprimo cada horario con espacios entre sí
        for (int i = 0; i < horarios.length; i++) {
            System.out.print(horarios[i] + "  ");
        }
        // dejo una linea de espacios debajo del encabezado
        System.out.println();

        // horario.long * 7 es la cuenta que uso para calcular el ancho del numero de
        // vuelo
        // +5 es el espacio extra para la columna de los dias
        int longitudLinea = 5 + (horarios.length * 7);
        // de esta forma se imprime la longitud correcta
        for (int i = 0; i < longitudLinea; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    // metodo para imprimir todas las filas de la matriz
    private static void imprimirFilas(Vuelo[][] vuelos) {
        String[] dias = { "Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom" };

        for (int i = 0; i < vuelos.length; i++) {
            // uso print en vez de println para que no haga salto de linea y que imprima el
            // dia y la fila que le correpsonde
            System.out.print(dias[i] + "  ");
            imprimirFila(vuelos[i]);
            // aca si dejo un salto de linea para pasar a la siguiente fila
            System.out.println();
        }
    }

    // metodo para imprimir una fila de la matriz
    private static void imprimirFila(Vuelo[] fila) {

        String contenido;
        // si tiene un vuelo, que imprima el numero
        // si no, que imprima ----
        for (int j = 0; j < fila.length; j++) {
            if (fila[j] != null) {
                contenido = fila[j].getNumeroVuelo();
            } else {
                contenido = "----";
            }

            System.out.print(contenido);

            // para que cada vuelo tenga 7 espacios, segun el largo
            // del numero de vuelo o los guiones, calcula cuantos espacios dejar para cada
            // iteracion de j
            int espacios = 7 - contenido.length();
            for (int i = 0; i < espacios; i++) {
                System.out.print(" ");
            }
        }
    }

    // metodo para calcular el primer horario de un vuelo internacional de cada dia
    public static LocalTime[] primerVueloInternacional(Vuelo[][] vuelos) {
        LocalTime[] primerosHorarios = new LocalTime[7];
        boolean encontrado = false;
        int i = 0;
        int j = 0;
        while (i < vuelos.length) {
            j = 0;
            encontrado = false;
            while (j < vuelos[0].length && !encontrado) {
                if (vuelos[i][j] != null && vuelos[i][j].getRuta().getInternacional()) {
                    primerosHorarios[i] = vuelos[i][j].getHorarioSalida();
                    encontrado = true;
                } else {
                    j++;
                }
            }
            if (!encontrado) {
                primerosHorarios[i] = null;
            }
            i++;
        }
        return primerosHorarios;
    }

    // metodo para mostrar los primeros vuelos internacionales de cada dia
    public static void mostrarPrimerosVuelosInternacionales(Vuelo[][] vuelos) {
        String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" };
        LocalTime[] primerosHorarios = primerVueloInternacional(vuelos);

        for (int i = 0; i < primerosHorarios.length; i++) {
            if (primerosHorarios[i] != null) {
                System.out.println(
                        "El primer vuelo internacional del día " + dias[i] + " es a las " + primerosHorarios[i]);
            } else {
                System.out.println("No hay vuelos internacionales programados para el día " + dias[i]);
            }
        }
    }
}
