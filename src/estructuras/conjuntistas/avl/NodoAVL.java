package estructuras.conjuntistas.avl;

//herencia de ABB
public class NodoAVL extends NodoABB {
    int altura;


    public NodoAVL (Comparable e){
        super(e);
        this.altura = 0;
    }
    public int getAltura(){
        return this.altura;
    }
    public void recalcularAltura() {
        int altIzq = -1;
        int altDer = -1;
        if (this.izquierdo != null) {
            altIzq = ((NodoAVL) this.izquierdo).getAltura();
        }
        if (this.derecho != null){
            altDer = ((NodoAVL) this.derecho).getAltura();
        }
       this.altura = 1 + Math.max(altIzq, altDer);
    }
    @Override
    public NodoAVL getIzquierdo() {
        return (NodoAVL) super.getIzquierdo();
    }

    @Override
    public NodoAVL getDerecho() {
        return (NodoAVL) super.getDerecho();
    }
}
