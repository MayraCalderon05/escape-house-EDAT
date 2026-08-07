/*
import sistema.SistemaEscapeHouse;
import java.util.Scanner;

public class escapeHouse {

public static void main() {


    SistemaEscapeHouse sistema = new SistemaEscapeHouse();
    Scanner sc = new Scanner(System.in);

    int opcion;
    boolean continuar;
    do{
        System.out.println("\nBIENVENIDO/A A ESCAPE HOUSE\n" +
                "\nSeleccione una opción:\n" +
                "1. Jugar\n" +
                "2. Configurar el juego\n");
        System.out.print("\nIngrese su opción:\n");
        opcion = sc.nextInt();

        switch (opcion){
            case 1:
                    do{
                        continuar = menuJugar(sc);
                    }while (continuar);
                    break;
            case 2:
                    do{
                        continuar = menuConfiguracion(sc);
                    }while(continuar);
                    break;
            default:
                System.out.println("Opcion no valida, por favor escriba 1 o 2");
                break;;
        }

        String respuesta = enviarConfirmacion(sc,"Desea realizar otra operacion?");
        continuar = mensajeConfirmacion(respuesta);
        if (!continuar){
            System.out.println("Operación cancelada");
        }
    }while (continuar);


    }

    //menu Jugar
    public boolean menuJugar(Scanner sc){
        int opcion;
        do{

        }
    }

    //menu Configuracion
    public boolean menuConfiguracion(Scanner sc){

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
        String rta = sc.next();

        return rta;
    }
}

*
     */