
import sistema.SistemaEscapeHouse;

import java.io.IOException;
import java.util.Scanner;

public class escapeHouse {
    private final static SistemaEscapeHouse sistema;

    static {
        try {
            sistema = new SistemaEscapeHouse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main() throws IOException{
        Scanner sc = new Scanner(System.in);
        int opcion;
        boolean continuar;
        do {
            System.out.println("\nBIENVENIDO/A A ESCAPE HOUSE\n" +
                    "\nSeleccione una opción:\n" +
                    "1. Jugar\n" +
                    "2. Configurar el juego\n");
            System.out.print("\nIngrese su opción:\n");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    do {
                        continuar = menuJugar(sc);
                    } while (continuar);
                    break;
                case 2:
                    do {
                        continuar = menuConfiguracion(sc);
                    } while (continuar);
                    break;
                default:
                    System.out.println("Opcion no valida, por favor escriba 1 o 2");
                    break;
                
            }

            String respuesta = enviarConfirmacion(sc, "Desea realizar otra operacion?");
            continuar = mensajeConfirmacion(respuesta);
            if (!continuar) {
                System.out.println("Operación cancelada");
            }
        } while (continuar);
    }

    //menu Jugar
    public static boolean menuJugar(Scanner sc) throws IOException {
        int opcion;
        String nombre;
        System.out.println(
                "Seleccione una opción:\n" +
                        "1. Cargar un nuevo equipo, si no tiene uno, y empezar a jugar\n" +
                        "2. Ingresar el nombre de un equipo, si ya tiene uno, y seguir jugando\n");
        System.out.print("\nIngrese su opción:\n");
        opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nombre del equipo: ");
                nombre = sc.nextLine();
                System.out.println("Seleccione la dificultad del juego: \n1. Dificultad facil: 200 \n2. Dificultad media: 400 \n3. Dificultad dificultad: 600");
                opcion = sc.nextInt();
                sc.nextLine();
                if (sistema.crearEquipo(nombre, opcion)) {
                    System.out.println(sistema.mostrarInfoEquipo(nombre));
                } else {
                    System.out.println("No se ha podido crear el nuevo equipo");
                }
                break;
            case 2:
                System.out.println("Ingrese el nombre del equipo: ");
                nombre = sc.nextLine();
                System.out.println(sistema.mostrarInfoEquipo(nombre));
                System.out.println("¿Quiere continuar jugando con este equipo?\n1. ¡¡¡A jugar!!!\n2. No, quiero volver al menu anterior");
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    menuJugarAux(sc, nombre);
                }
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1 o 2");
                break;
        }
        return mensajeConfirmacion(enviarConfirmacion(sc,"Desea realizar otra operacion?"));
    }
    private static void menuJugarAux(Scanner sc, String nombreEquipo){
        int opcion;
        System.out.println("Seleccione una opción:\n1. Consultar sobre habitaciones\n2. Consultar sobre desafios\n3. Consultar sobre Equipo\n");
        System.out.print("\nIngrese su opción:\n");
        opcion = sc.nextInt();
        do {
            switch (opcion) {
                case 1:
                    menuConsultaHabitacion(sc, nombreEquipo);
                    break;
                case 2:
                    menuConsultaDesafio(sc, nombreEquipo);
                    break;
                case 3:
                    menuConsultaEquipo(sc, nombreEquipo);
                    break;
                default:
                    System.out.println("Opcion no valida, por favor escriba 1, 2 o 3");
                    break;
            }
        }while(mensajeConfirmacion(enviarConfirmacion(sc,"Desea realizar otra operacion?")));
    }
    private static void menuConsultaHabitacion(Scanner sc, String nombreEquipo){
        int opcion;
        int codigoHabitacion;
        int codigoHabDestino;
        int codigoHabExcluir;
        int puntos;
        System.out.println("Seleccione una opción:" +
                "\n1. Mostrar habitacion" +
                "\n2. Mostrar todas las habitaciones contiguas y el puntaje necesario para pasar a ellas" +
                "\n3. Mostrar si es posible llegar desde una habitacion a otra" +
                "\n4. Mostrar el puntaje minimo que se requiere acumular para ir de una habitacion a otra, y el camino a tomar para hacerlo" +
                "\n5. Mostrar todas las formas de ir de una habitacion a otra, sin pasar por una habitacion en especifico, y que no requiera mas de una cantidad de puntos\n");
        System.out.print("\nIngrese su opción:\n");
        opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el numero de la habitacion la cual desea mostrar la informacion: ");
                codigoHabitacion = sc.nextInt();
                System.out.println(sistema.mostrarHabitacion(codigoHabitacion));
                break;
            case 2:
                System.out.println("Ingrese el numero de la habitacion la cual desea mostrar sus habitaciones contiguas: ");
                codigoHabitacion = sc.nextInt();
                System.out.println(sistema.habitacionesContiguas(codigoHabitacion).toString());
                break;
            case 3:
                System.out.println("Ingrese el numero de la habitacion de la cual desea partir: ");
                codigoHabitacion = sc.nextInt();
                System.out.println("Ingrese el numero de la habitacion a la cual desea llegar:");
                codigoHabDestino = sc.nextInt();
                System.out.println("Ingrese la cantidad de puntos con la cual quiere realizar el recorrido:");
                puntos = sc.nextInt();
                if (sistema.esPosibleLLegar(codigoHabitacion, codigoHabDestino, puntos)){
                    System.out.println("Si es posible llegar desde " + codigoHabitacion + " hasta " + codigoHabDestino + " acumulando " + puntos + " puntos");
                }else {
                    System.out.println("No es posible llegar desde " + codigoHabitacion + " hasta " + codigoHabDestino + " acumulando " + puntos + " puntos");
                }
                break;
            case 4:
                System.out.println("Ingrese el numero de la habitacion de la cual desea partir: ");
                codigoHabitacion = sc.nextInt();
                System.out.println("Ingrese el numero de la habitacion a la cual desea llegar:");
                codigoHabDestino = sc.nextInt();
                System.out.println(sistema.minimoPuntaje(codigoHabitacion, codigoHabDestino));
                break;
            case 5:
                System.out.println("Ingrese el numero de la habitacion de la cual desea partir: ");
                codigoHabitacion = sc.nextInt();
                System.out.println("Ingrese el numero de la habitacion a la cual desea llegar:");
                codigoHabDestino = sc.nextInt();
                System.out.println("Ingrese la cantidad de puntos con la cual quiere realizar el recorrido:");
                puntos = sc.nextInt();
                System.out.println("Ingrese el codigo de la habitacion por la cual no desea pasar:");
                codigoHabExcluir = sc.nextInt();
                sistema.sinPasarPor(codigoHabitacion,codigoHabDestino,puntos,codigoHabExcluir);
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2, 3, 4 o 5");
                break;
        }
    }

    //menu Configuracion
    public static boolean menuConfiguracion(Scanner sc) throws IOException {

        System.out.println("\nBIENVENIDA/O A LA CONFIGURACION DEL JUEGO\n" +
                "\nSeleccione una opción:\n" +
                "1. Configurar las habitaciones\n" +
                "2. Configurar los equipos\n" +
                "3. Configurar los desafios\n");
        System.out.print("\nIngrese su opción:\n");
        int opcion = sc.nextInt();
        boolean continuarConfiguracion;
        switch (opcion){
            case 1:
                do {
                    continuarConfiguracion = configuracionHabitacion(sc);
                }while(continuarConfiguracion);
                break;
            case 2:
                do {
                    continuarConfiguracion = configuracionEquipo(sc);
                }while (continuarConfiguracion);
                break;
            case 3:
                do{
                    continuarConfiguracion = configuracionDesafio(sc);
                }while(continuarConfiguracion);
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2 o 3");
                break;
        }
        String respuesta = enviarConfirmacion(sc,"Desea realizar otra operacion?");
        continuarConfiguracion = mensajeConfirmacion(respuesta);
        if (!continuarConfiguracion){
            System.out.println("Operación cancelada");
        }
        return continuarConfiguracion;
    }

    //configuracion de habitacion
    public static boolean configuracionHabitacion( Scanner sc) throws IOException {
        System.out.println("\nBIENVENIDA/O A LA CONFIGURACION DE HABITACION\n" +
                "\nSeleccione una opción:\n" +
                "1. Agregar habitacion\n" +
                "2. Ver habitacion\n" +
                "3. Modificar habitacion\n"+
                "4. Eliminar habitacion\n");
        System.out.print("\nIngrese su opción:\n");
        int opcion = sc.nextInt();
        boolean continuarConfHab;
        switch (opcion){
            case 1:
                do{
                    continuarConfHab = agregarHabitacion(sc);
                }while (continuarConfHab);
                break;
            case 2:
                do{
                    continuarConfHab = verHabitacion(sc);
                }while (continuarConfHab);
                break;
            case 3:
                do{
                    continuarConfHab = modificarHabitacion(sc);
                }while (continuarConfHab);
                break;
            case 4:
                do{
                    continuarConfHab = borrarHabitacion(sc);
                }while (continuarConfHab);
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2, 3 o 4");
                break;
        }
        String respuesta = enviarConfirmacion(sc,"Desea realizar otra operacion?");
        continuarConfHab = mensajeConfirmacion(respuesta);
        if (!continuarConfHab){
            System.out.println("Operación cancelada");
        }
        return continuarConfHab;
    }
    private static void menuConsultaDesafio(Scanner sc, String nombreEquipo){
        int opcion;
        int codigoHabitacion;
        int puntaje;
        int puntajeMax;
        String tipo;
        System.out.println("Seleccione una opción:" +
                "\n1. Mostrar desafio de una habitacion" +
                "\n2. Mostrar desafios resueltos" +
                "\n3. Verificar si ya se resolvio un desafio de una habitacion" +
                "\n4. Mostrar los desafios de un tipo, y con un puntaje dentro un rango, de una habitacion\n");
        System.out.print("\nIngrese su opción:\n");
        opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println("Ingrese el numero de la habitacion a la cual pertenece el desafio: ");
                codigoHabitacion = sc.nextInt();
                System.out.println("Ingrese el puntaje del desafio del cual desea mostrar la informacion:");
                puntaje = sc.nextInt();
                System.out.println(sistema.mostrarDesafio(puntaje, codigoHabitacion));
                break;
            case 2:
                System.out.println(sistema.mostrarDesafiosResueltos(nombreEquipo));
                break;
            case 3:
                System.out.println("Ingrese el numero de la habitacion a la cual pertenece el desafio: ");
                codigoHabitacion = sc.nextInt();
                System.out.println("Ingrese el puntaje del desafio del cual desea saber si ya resolvio su equipo:");
                puntaje = sc.nextInt();
                if (sistema.verificarDesafíoResuelto(nombreEquipo,puntaje,codigoHabitacion)){
                    System.out.println("El desafio "+sistema.obtenerNombreDesafio(codigoHabitacion,puntaje) + " en la habitacion N°"+codigoHabitacion + " ya ha sido resuelto por el equipo " + nombreEquipo);
                }else {
                    System.out.println("El desafio "+sistema.obtenerNombreDesafio(codigoHabitacion,puntaje) + " en la habitacion N°"+codigoHabitacion + " aun no ha sido resuelto por el equipo " + nombreEquipo);
                }
                break;
            case 4:
                System.out.println("Ingrese el numero de la habitacion a la cual pertenece el desafio: ");
                codigoHabitacion = sc.nextInt();
                System.out.println("Ingrese el puntaje minimo (incluido):");
                puntaje = sc.nextInt();
                System.out.println("Ingrese el puntaje maximo (incluido):");
                puntajeMax = sc.nextInt();
                System.out.println("Ingrese el tipo de desafio que quiera buscar:");
                tipo = sc.nextLine();
                System.out.println(sistema.mostrarDesafiosTipo(codigoHabitacion,puntaje,puntajeMax, tipo).toString());
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2, 3 o 4");
                break;
        }
    }
    private static void menuConsultaEquipo(Scanner sc, String nombreEquipo){
        int opcion;
        int codigoHabitacion;
        int puntaje;
        System.out.println("Seleccione una opción:" +
                "\n1. Mostrar la informacion del equipo" +
                "\n2. Mostrar los desafios que se pueden resolver para pasar a una habitacion" +
                "\n3. ¡¡Jugar un desafio!!" +
                "\n4. Cambio de habitacion" +
                "\n5. ¿El equipo puede salir?\n");
        System.out.print("\nIngrese su opción:\n");
        opcion = sc.nextInt();
        switch (opcion) {
            case 1:
                System.out.println(sistema.mostrarInfoEquipo(nombreEquipo));
                break;
            case 2:
                System.out.println("Ingrese el numero de la habitacion la cual desea poder ingresar: ");
                codigoHabitacion = sc.nextInt();
                System.out.println(sistema.posiblesDesafios(nombreEquipo,codigoHabitacion));
                break;
            case 3:
                System.out.println("Ingrese el puntaje del desafio del cual desea jugar:");
                puntaje = sc.nextInt();
                if (sistema.jugarDesafio(nombreEquipo,sistema.obtenerCodigoHab(nombreEquipo),puntaje)){
                    System.out.println("!!Desafio completado¡¡");
                }else {
                    System.out.println("No se ha podido resolver el desafio");
                }
                break;
            case 4:
                System.out.println("Ingrese el numero de la habitacion a la cual desea moverse: ");
                codigoHabitacion = sc.nextInt();
                if (sistema.cambiarDeHabitación(nombreEquipo,codigoHabitacion)){
                    System.out.println("¡¡Ha cambiado de habitacion!!");
                }else {
                    System.out.println("No fue posible cambiar de habitacion");
                }
                break;
            case 5:
                if (sistema.puedeSalir(nombreEquipo)){
                    System.out.println("¡¡Si es posible salir!!");
                }else{
                    System.out.println("¡¡No se puede salir aun!!");
                }
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2, 3, 4 o 5");
                break;
        }
    }


    public static boolean agregarHabitacion( Scanner sc) throws IOException {
        boolean continuar;
        String nombre, respuesta;
        int planta, mtsCuadrados;
        boolean seCreo;

        System.out.println("Ingrese el nombre de la nueva habitacion:");
        nombre = sc.nextLine();
        System.out.println("ingrese en que planta se encuentra la habitacion: (ej: 0, 1,-1)");
        planta = sc.nextInt();
        System.out.println("ingrese los metros cuadrados de la habitacion; (ingrese un numero entero)");
        mtsCuadrados = sc.nextInt();
        seCreo = sistema.crearHabitacion(nombre, planta, mtsCuadrados);

        if (!seCreo){
            System.out.println("No se pudo crear la habitacion");
            respuesta = enviarConfirmacion(sc,"Desea volver a intentarlo?");
        }else {
            respuesta = enviarConfirmacion(sc,"Desea crear otra habitacion?");
        }
        continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;

    }

    public static boolean verHabitacion( Scanner sc){

        System.out.println("ingrese el codigo de la habitacion que desea ver:");
        int codigo = sc.nextInt();
        System.out.println(sistema.mostrarHabitacion(codigo));

        String respuesta = enviarConfirmacion(sc,"Desea ver otra habitacion?");
        boolean continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;
    }

    public static boolean modificarHabitacion( Scanner sc) throws IOException {
        boolean continuar = true;
        String respuesta;
        String nombre;
        int planta, mtsCuadrados;
        System.out.println("\nQue desea modificar de la habitacion?\n" +
                "\nSeleccione una opción:\n" +
                "1. El nombre\n" +
                "2. La planta\n" +
                "3. Los metros cuadrados\n"+
                "4. Eliminar habitacion\n");
        System.out.print("\nIngrese su opción:\n");
        int opcion = sc.nextInt();
        System.out.println("Ingrese el codigo de la habitacion");
        int codigo = sc.nextInt();
        switch (opcion){
            case 1:
                do {
                    System.out.println("Ingrese el nuevo nombre:");
                    nombre = sc.nextLine();
                    continuar = sistema.actualizarNombreHab(codigo,nombre);
                    if (continuar){
                        System.out.println("Habitacion modificada");
                        continuar = false;
                    }else {
                        System.out.println("Habitacion no valida");
                        respuesta = enviarConfirmacion(sc,"Desea intentarlo de nuevo?");
                        continuar =  mensajeConfirmacion(respuesta);
                    }
                }while(continuar);
                break;
            case 2:
                do {
                    System.out.println("Ingrese la nueva planta:");
                    planta = sc.nextInt();
                    continuar = sistema.actualizarPlantaHab(codigo,planta);
                    if (continuar){
                        System.out.println("Habitacion modificada");
                        continuar = false;
                    }else {
                        System.out.println("Habitacion no valida");
                        respuesta = enviarConfirmacion(sc,"Desea intentarlo de nuevo?");
                        continuar =  mensajeConfirmacion(respuesta);
                    }
                }while (continuar);
                break;
            case 3:
                do {
                    System.out.println("Ingrese los metros cuadrados:");
                    mtsCuadrados = sc.nextInt();
                    continuar = sistema.actualizarMtsCuadrHab(codigo,mtsCuadrados);
                    if (continuar){
                        System.out.println("Habitacion modificada");
                        continuar = false;
                    }else {
                        System.out.println("Habitacion no valida");
                        respuesta = enviarConfirmacion(sc,"Desea intentarlo de nuevo?");
                        continuar =  mensajeConfirmacion(respuesta);
                    }
                }while (continuar);
            default:
                System.out.println("Opcion no valida por favor escriba 1, 2 o 3");
                break;
        }
        respuesta = enviarConfirmacion(sc,"Desea configurar otro atributo de la habitacion?");
        continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;
    }

    public static boolean borrarHabitacion( Scanner sc) throws IOException {
        System.out.println("ingrese el codigo de la habitacion que desea borrar:");
        int codigo = sc.nextInt();
        boolean seElimino;
        String respuesta;
        boolean continuar;

        seElimino = sistema.eliminarHabitacion(codigo);
        if (seElimino){
            System.out.println("Habitacion eliminada");
            respuesta = enviarConfirmacion(sc,"Desea eliminar otra habitacion habitacion?");
        }else {
            System.out.println("No se pudo eliminar la habitacion");
            respuesta = enviarConfirmacion(sc,"Desea intentarlo otra vez?");
        }
        continuar =  mensajeConfirmacion(respuesta);
        return continuar;
    }

    //configuracion de Equipo
    public static boolean configuracionEquipo( Scanner sc) throws IOException {
        System.out.println("\nBIENVENIDA/O A LA CONFIGURACION DE EQUIPO\n" +
                "\nSeleccione una opción:\n" +
                "1. Ver equipo\n" +
                "2. Modificar equipo\n" +
                "3. Eliminar equipo\n");
        System.out.print("\nIngrese su opción:\n");
        int opcion = sc.nextInt();
        boolean continuarConfEquipo;
        switch (opcion){
            case 1:
                do{
                    continuarConfEquipo = verEquipo(sc);
                }while (continuarConfEquipo);
                break;
            case 2:
                do{
                    continuarConfEquipo = modificarEquipo(sc);
                }while (continuarConfEquipo);
                break;
            case 3:
                do{
                    continuarConfEquipo = borrarEquipo(sc);
                }while (continuarConfEquipo);
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2 o 3");
                break;
        }
        String respuesta = enviarConfirmacion(sc,"Desea realizar otra operacion?");
        continuarConfEquipo = mensajeConfirmacion(respuesta);
        if (!continuarConfEquipo){
            System.out.println("Operación cancelada");
        }
        return continuarConfEquipo;
    }

    public static boolean verEquipo( Scanner sc){

        System.out.println("Ingrese el nombre del equipo");
        String nombre = sc.nextLine();
        System.out.println(sistema.mostrarInfoEquipo(nombre));

        String respuesta = enviarConfirmacion(sc,"Desea ver otro equipo?");
        boolean continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;

    }

    public static  boolean modificarEquipo( Scanner sc) throws IOException {
        String respuesta;
        boolean continuar;

        System.out.println("Ingrese el nombre del equipo");
        String nombre = sc.nextLine();
        System.out.println("Ingrese la nueva dificultad del equipo");
        int nuevaDificultad = sc.nextInt();
        boolean seModifico = sistema.actualizarEquipo(nombre, nuevaDificultad);
        if(seModifico){
            System.out.println("Equipo modificado");
            respuesta = enviarConfirmacion(sc,"Desea modificar otro equipo?");
        }else {
            System.out.println("No se pudo modificar el equipo");
            respuesta = enviarConfirmacion(sc,"Desea intentarlo otra vez?");
        }
        continuar =  mensajeConfirmacion(respuesta);

        return continuar;
    }

    public static boolean borrarEquipo( Scanner sc) throws IOException {
        String respuesta;
        boolean continuar;
        System.out.println("Ingrese el nombre del equipo");
        String nombre = sc.nextLine();
        boolean seElimino = sistema.eliminarEquipo(nombre);
        if(seElimino){
            System.out.println("Equipo eliminado");
            respuesta = enviarConfirmacion(sc,"Desea eliminar otro equipo?");
        }else{
            System.out.println("No se pudo eliminar el equipo");
            respuesta = enviarConfirmacion(sc,"Desea intentarlo otra vez?");
        }
        continuar =  mensajeConfirmacion(respuesta);

        return continuar;
    }

    //configuracion de DESAFIO
    public static boolean configuracionDesafio( Scanner sc) throws IOException {
        System.out.println("\nBIENVENIDA/O A LA CONFIGURACION DE DESAFIO\n" +
                "\nSeleccione una opción:\n" +
                "1. Agregar habitacion\n" +
                "2. Ver habitacion\n" +
                "3. Modificar habitacion\n"+
                "4. Eliminar habitacion\n");
        System.out.print("\nIngrese su opción:\n");
        int opcion = sc.nextInt();
        boolean continuarConfDesafio;
        switch (opcion){
            case 1:
                do{
                    continuarConfDesafio = agregarDesafio(sc);
                }while (continuarConfDesafio);
                break;
            case 2:
                do{
                    continuarConfDesafio = verDesafio(sc);
                }while (continuarConfDesafio);
                break;
            case 3:
                do{
                    continuarConfDesafio = modificarDesafio(sc);
                }while (continuarConfDesafio);
                break;
            case 4:
                do{
                    continuarConfDesafio = borrarDesafio(sc);
                }while (continuarConfDesafio);
                break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1, 2, 3 o 4");
                break;
        }
        String respuesta = enviarConfirmacion(sc,"Desea realizar otra operacion?");
        continuarConfDesafio = mensajeConfirmacion(respuesta);
        if (!continuarConfDesafio){
            System.out.println("Operación cancelada");
        }
        return continuarConfDesafio;
    }

    public static boolean agregarDesafio(Scanner sc) throws IOException {
        boolean continuar;
        String nombre, tipo, respuesta;
        int puntaje, codigoHab;
        boolean seCreo;

        System.out.println("Ingrese el codigo de la habitacion donde se va agregar el desafio:");
        codigoHab = sc.nextInt();
        System.out.println("Ingrese el puntaje del desafio:");
        puntaje = sc.nextInt();
        System.out.println("ingrese el nombre del desafio:");
        nombre = sc.nextLine();
        System.out.println("ingrese el tipo de desafio");
        tipo = sc.nextLine();
        seCreo = sistema.crearDesafio(codigoHab,puntaje,nombre,tipo);

        if (!seCreo){
            System.out.println("No se pudo crear el desafio");
            respuesta = enviarConfirmacion(sc,"Desea volver a intentarlo?");
        }else {
            respuesta = enviarConfirmacion(sc,"Desea crear otro desafio?");
        }
        continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;

    }

    public static boolean verDesafio(Scanner sc){

        System.out.println("ingrese el puntaje del desafio:");
        int puntaje = sc.nextInt();

        System.out.println("ingrese el codigo de la habitacion del desafio:");
        int codigo = sc.nextInt();

        System.out.println(sistema.mostrarDesafio(puntaje,codigo));


        String respuesta = enviarConfirmacion(sc,"Desea ver otro Desafio?");
        boolean continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;
    }

    public static boolean modificarDesafio(Scanner sc) throws IOException {
        boolean continuar = true;
        String respuesta;
        String nombre, tipo;
        System.out.println("\nQue desea modificar del desafio?\n" +
                "\nSeleccione una opción:\n" +
                "1. El nombre\n" +
                "2. El tipo\n");
        System.out.print("\nIngrese su opción:\n");
        int opcion = sc.nextInt();
        System.out.println("Ingrese el puntaje del desafio a modificar:");
        int puntaje = sc.nextInt();
        System.out.println("Ingrese el codigo de la habitacion del desafio:");
        int codigo = sc.nextInt();
        switch (opcion){
            case 1:
                do {
                    System.out.println("Ingrese el nuevo nombre:");
                    nombre = sc.nextLine();
                    continuar = sistema.cambiarNombreDesafio(codigo,puntaje,nombre);
                    if (continuar){
                        System.out.println("Desafio modificada");
                        continuar = false;
                    }else {
                        System.out.println("Desafio no valido");
                        respuesta = enviarConfirmacion(sc,"Desea intentarlo de nuevo?");
                        continuar =  mensajeConfirmacion(respuesta);
                    }
                }while(continuar);
                break;
            case 2:
                do {
                    System.out.println("Ingrese el nuevo tipo:");
                    tipo = sc.nextLine();
                    continuar = sistema.cambiarTipoDesafio(codigo,puntaje,tipo);
                    if (continuar){
                        System.out.println("Desafio modificado");
                        continuar = false;
                    }else {
                        System.out.println("Desafio no valido");
                        respuesta = enviarConfirmacion(sc,"Desea intentarlo de nuevo?");
                        continuar =  mensajeConfirmacion(respuesta);
                    }
                }while (continuar);
                break;
            default:
                System.out.println("Opcion no valida por favor escriba 1 o 2");
                break;
        }
        respuesta = enviarConfirmacion(sc,"Desea configurar otro atributo del desafio?");
        continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
        return continuar;
    }

    public static boolean borrarDesafio(Scanner sc) throws IOException {

        System.out.println("Ingrese el puntaje del desafio que desea eliminar:");
        int puntaje = sc.nextInt();
        System.out.println("ingrese el codigo de la habitacion que desea eliminar:");
        int codigo = sc.nextInt();
        boolean seElimino;
        String respuesta;
        boolean continuar;

        seElimino = sistema.sacarDesafio(codigo,puntaje);
        if (seElimino){
            System.out.println("Desafio eliminado");
            respuesta = enviarConfirmacion(sc,"Desea eliminar otro desafio?");
        }else {
            System.out.println("No se pudo eliminar el desafio");
            respuesta = enviarConfirmacion(sc,"Desea intentarlo otra vez?");
        }
        continuar =  mensajeConfirmacion(respuesta);
        return continuar;
    }






    // metodo para procesar la respuesta de confirmacion de seguir o no en el menu
    private static boolean mensajeConfirmacion(String rta) {
        boolean confirmacion = rta.equalsIgnoreCase("S");
        return confirmacion;
    }

    // metodo para enviar un mensaje de confirmacion de si seguir en el menu y
    // obtener la respuesta del usuario
    private static String enviarConfirmacion(Scanner sc, String mensaje) {
        System.out.println(mensaje + "s/n");
        return sc.nextLine();
    }
}

