package persistencia;
import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.tdaEspecifico.tablaBusquedaAVL.DiccionarioAvl;
import estructuras.tdaEspecifico.tablaBusquedaHash.DiccionarioHash;
import modelo.Desafio;
import modelo.Equipo;
import modelo.Habitacion;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Lectura {
    private Grafo plano;
    private DiccionarioAvl habitaciones;
    private DiccionarioHash equipos;
    private HashMap<String, Lista> desafiosResueltosPorEquipo;
    private Escritura escribir;
    private final String ruta = "C:\\facultad develop\\edat\\tpo final estructuras\\src\\sistema\\informacionBase\\salida\\insesionesCorrectas.txt";

    //el sistema maneja sus estructuras
    public Lectura(Grafo g, DiccionarioAvl h, DiccionarioHash e, HashMap<String, Lista> de){
        this.plano = g;
        this.habitaciones = h;
        this.equipos = e;
        this.desafiosResueltosPorEquipo = de;
        this.escribir = new Escritura();
    }

    private void leerInformacion() throws IOException{
        String habitacionesUrl = "C:\\facultad develop\\edat\\tpo final estructuras\\src\\sistema\\informacionBase\\sistema.txt";
        String linea = "";
        int posicion = 0;

        BufferedReader lectura = new BufferedReader(new FileReader(habitacionesUrl));

        while ((linea = lectura.readLine()) != null) {
            char identificatorio = linea.charAt(0);
            //toma la linea hasta el primer ; y en la posición siguiente crea el string
            String resto = linea.substring(linea.indexOf(';') + 1);

            switch (identificatorio) {
                case 'H':
                    procesarHabitacion(resto);
                case 'E':
                    procesarEquipo(resto);
                case 'D':
                    procesarDesafios(resto);
                case 'P':
                    procesarGrafo(resto);
                default:
            }
        }
    }

    private void procesarHabitacion(String linea) throws IOException{
        //* H;codigo;nombre;planta;m2;sitienesalida
        //le debería pasar la linea sin el identificatorio
        StringTokenizer st = new StringTokenizer(linea, ";");

        //trim saca los espacio atras y adelante
        int codigo = Integer.parseInt(st.nextToken().trim());
        String nombre = st.nextToken().trim();
        int planta = Integer.parseInt(st.nextToken().trim());
        int metrosCuadrados = Integer.parseInt(st.nextToken().trim());
        boolean salida = Boolean.parseBoolean(st.nextToken().trim());

        Habitacion h = new Habitacion(codigo, nombre, planta, metrosCuadrados, salida);
        //inserto solo el vertice en el arco, el grafo se arma en un metodo aparte
        if ((this.habitaciones.insertar(codigo, h)) && (this.plano.insertarVertice(h))) {
            this.escribir.escribirTxt("Habitación "+ Integer.toString(codigo) +" cargada correctamente",this.ruta);
        } else {
            this.escribir.escribirTxt("Ha habido un error al ingresar la habitacion "+ Integer.toString(codigo),this.ruta);
        }
    }

    private void procesarEquipo(String linea) throws IOException{
        //* E;nombre;puntaje exigido;puntaje total acumulado;habitación en la que se encuentran actualmente;puntaje actua en la habitacion
        //le debería pasar la linea sin el identificatorio
        StringTokenizer st = new StringTokenizer(linea, ";");

        //trim saca los espacio atras y adelante
        String nombre = st.nextToken().trim();
        int puntExigido = Integer.parseInt(st.nextToken().trim());
        int puntAcum = Integer.parseInt(st.nextToken().trim());
        Habitacion hab = (Habitacion) this.habitaciones.obtenerInfo(Integer.parseInt(st.nextToken().trim()));
        int puntActual = Integer.parseInt(st.nextToken().trim());

        Equipo equipo = new Equipo(nombre, puntExigido, puntAcum, hab, puntActual);

        if (equipos.insertar(nombre, equipo)) {
            this.escribir.escribirTxt("Equipo "+ nombre +" cargado correctamente",this.ruta);
        } else {
            this.escribir.escribirTxt("Ha habido un error al ingresar el equipo "+ nombre,this.ruta);
        }
    }

    private void procesarDesafios(String linea) throws IOException{
        //* D;puntaje;habitacion;nombre;tipo
        //le debería pasar la linea sin el identificatorio
        StringTokenizer st = new StringTokenizer(linea, ";");

        //trim saca los espacio atras y adelante
        int puntaje = Integer.parseInt(st.nextToken().trim());
        Habitacion hab = (Habitacion) this.habitaciones.obtenerInfo(Integer.parseInt(st.nextToken().trim()));
        String nombre = st.nextToken().trim();
        String tipo = st.nextToken().trim();

        if (hab.agregarDesafio(puntaje,nombre,tipo)) {
            this.escribir.escribirTxt("Desafio cargado correctamente",this.ruta);
        } else {
            this.escribir.escribirTxt("Ha habido un error al ingresar el desafío "+ nombre,this.ruta);
        }
    }

    private void procesarGrafo(String linea) throws IOException{
        //* P: hab1; hab2; etiqueta
        //le debería pasar la linea sin el identificatorio
        StringTokenizer st = new StringTokenizer(linea, ";");

        //trim saca los espacio atras y adelante
        Habitacion hab1 = (Habitacion) this.habitaciones.obtenerInfo(Integer.parseInt(st.nextToken().trim()));
        Habitacion hab2 = (Habitacion) this.habitaciones.obtenerInfo(Integer.parseInt(st.nextToken().trim()));
        int etiqueta = Integer.parseInt(st.nextToken().trim());

        if (this.plano.insertarArco(hab1, hab2, etiqueta)) {
            this.escribir.escribirTxt("Arco cargado correctamente",this.ruta);
        } else {
            this.escribir.escribirTxt("Ha habido un error al ingresar el arco", this.ruta);
        }
    }

    private void procesarDesafiosResueltos(String linea) throws IOException{
        //* L;nombreEquipo;(habitacion:des1,des2)(habitacion:des1,des2)(habitacion:des1,des2)
        //le debería pasar la linea sin el identificatorio
        StringTokenizer st = new StringTokenizer(linea, ";");

        //trim saca los espacio atras y adelante
        //la clave del hash map
        String nombreEquipo = st.nextToken().trim();

        String listaDesafios = st.nextToken().trim();

        StringTokenizer stGrupos = new StringTokenizer(listaDesafios, "()");
        while (stGrupos.hasMoreTokens()){
            String grupo = stGrupos.nextToken().trim();

            StringTokenizer stGrupo = new StringTokenizer(grupo, ":");
            String habitacion = stGrupo.nextToken().trim();
            String desafiosString = stGrupo.nextToken().trim();

            StringTokenizer stDesafios = new StringTokenizer(desafiosString, ",");
            while (stDesafios.hasMoreTokens()) {
                int puntaje = Integer.parseInt(stDesafios.nextToken().trim());
            }
        }

        Habitacion hab = (Habitacion) this.habitaciones.obtenerInfo(Integer.parseInt(st.nextToken().trim()));
        Desafio des
        int etiqueta = Integer.parseInt(st.nextToken().trim());

        if (this.plano.insertarArco(hab1, hab2, etiqueta)) {
            this.escribir.escribirTxt("Arco cargado correctamente",this.ruta);
        } else {
            this.escribir.escribirTxt("Ha habido un error al ingresar el arco",this.ruta);
        }
        
    }

}
