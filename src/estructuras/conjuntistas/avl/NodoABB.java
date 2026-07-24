package estructuras.conjuntistas.avl;

public class NodoABB {
        Comparable elem;
        NodoABB izquierdo;
        NodoABB derecho;

        public NodoABB (Comparable e){
            this.elem = e;
            this.izquierdo = null;
            this.derecho = null;
        }
        public Comparable getElem(){
            return this.elem;
        }
        public void setElem(Comparable e){
            this.elem = e;
        }
        public NodoABB getIzquierdo() {
            return this.izquierdo;
        }
        public void setIzquierdo(NodoABB i){
            this.izquierdo = i;
        }
        public NodoABB getDerecho() {
            return this.derecho;
        }
        public void setDerecho(NodoABB d){
            this.derecho = d;
        }


}
