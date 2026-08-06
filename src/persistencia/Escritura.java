package persistencia;
import estructuras.lineales.Lista;
import java.io.*;

public class Escritura {
    private BufferedWriter bw;

    public Escritura(){
        bw = null;
    }

    //String habitacionesUrl = "C:\\facultad develop\\edat\\tpo final estructuras\\src\\sistema\\informacionBase\\salida\\salida1.txt";
    public void escribirTxt(Lista cadenas, String ruta) throws IOException {
        String aux;
        Lista cadenasCopia = cadenas.clone();
        FileWriter archivo = new FileWriter(ruta);
        this.bw = new BufferedWriter(archivo);
        while (!cadenasCopia.esVacia()) {
            aux = (String) cadenasCopia.recuperar(1);
            this.bw.write(aux);
            this.bw.newLine();
            cadenasCopia.eliminar(1);
        }
        this.bw.flush();
        this.bw.close();
    }

    public void escribirTxt(String cadena, String ruta) throws IOException {
        FileWriter archivo = new FileWriter(ruta,true);
        this.bw = new BufferedWriter(archivo);
        this.bw.write(cadena);
        this.bw.newLine();
        this.bw.flush();
        this.bw.close();
    }
}
