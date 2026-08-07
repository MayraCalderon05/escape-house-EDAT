import sistema.SistemaEscapeHouse;
import java.util.Scanner;

public class escapeHouse {
    private final static SistemaEscapeHouse sistema = new SistemaEscapeHouse();

    public static void main() {
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
                ;
            }

            String respuesta = enviarConfirmacion(sc, "Desea realizar otra operacion?");
            continuar = mensajeConfirmacion(respuesta);
            if (!continuar) {
                System.out.println("Operación cancelada");
            }
        } while (continuar);
    }

    //menu Jugar
    public static boolean menuJugar(Scanner sc) {
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

    //menu Configuracion
    public static boolean menuConfiguracion(Scanner sc){

        System.out.println(

        );
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
