import estructuras.grafo.Grafo;
import estructuras.lineales.Lista;
import estructuras.tdaEspecifico.tablaBusquedaAVL.DiccionarioAvl;
import estructuras.tdaEspecifico.tablaBusquedaHash.DiccionarioHash;
import persistencia.Lectura;

import java.io.IOException;
import java.util.HashMap;

public class prueba {

        public static void main(String[] args) throws IOException {
            Grafo grafo = new Grafo(/* lo que pida tu constructor */);
            DiccionarioAvl habitaciones = new DiccionarioAvl();
            DiccionarioHash equipos = new DiccionarioHash(17);
            HashMap<String, Lista> desafiosResueltos = new HashMap<>();

            Lectura lectura = new Lectura(grafo, habitaciones, equipos, desafiosResueltos);
            System.out.println("proceso terminado");
        }
}
