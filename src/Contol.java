import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import javax.swing.plaf.ActionMapUIResource;

public class Contol implements KeyListener{
   
   Pieza actual;
   public enum Accion {LEFT,RIGHT,SPACE,DOWN,NOTHING};
   Accion accion;
   int pinix=5;
   int piniy=0;
   int pfinalx;
   int pfinaly;
   int limitetabd;
   int limitetabi;

   Comparator comparator = new ComparadorPersonal();
   ArrayList<Coordenadas> lpiezas = new ArrayList<Coordenadas>();
   //constructor
   public Contol(){
      actual = new Pieza();//CAMBIA EL VALOR DE ACTUAL A NULL PERO OBTENIENDO INFORMACION DE PIEZAS PARA EL RANDOM
      crearPieza();//ACTIVA LA FUNCION/CLASE DE ABAJO v
      accion = Accion.NOTHING;
   } 

   public void crearPieza(){
      Random r = new Random();//FUNCION DE RANDOM
      int npieza = r.nextInt(actual.lnombrepieza.length);//PARAMETROS DE NUMERO A ELEGIR
      actual = new Pieza(npieza);//PIEZA CREADA EN BASE ^
      this.moverPiezaaInicio();//ACOMODA LA PIEZA EN EL CENTRO (v ABAJO FUNCION)
   }
   //CLASE-FUNCION DE ARRIBA 'ULTIMA LINEA'
   public void moverPiezaaInicio(){
      for(Coordenadas c : actual.getBody()){
            int px = c.getX();
            int py = c.getY();
            int cx = px+pinix;
            int cy = py+piniy;
            c.setX(cx);
            c.setY(cy);
      }
   }
   //SUMA VALOR Y
   public void bajarPieza(){
      for(Coordenadas c : actual.getBody()){
            int py = c.getY();
            int cy = py+1;
            c.setY(cy);
      }
   }
   //NAME ROTAR PIEZA
   public void rotarPieza(){
      actual.rotarPieza();
   }
   //NAME MOVER DERECHA
   public void moverDerecha(){
      for(Coordenadas c : actual.getBody()){
            int x = c.getX();
            int y = c.getY();
            x=x+1;
            c.setX(x);
      }
   }
   //NAME MOVER IZQUIERDA
   public void moverIzquierda(){
      for(Coordenadas c : actual.getBody()){
            int x = c.getX();
            int y = c.getY();
            x=x-1;
            c.setX(x);
      }
   }
   //DETECTA SI ES EL FINAL
   public boolean hayFinalTablero(){
      boolean condicion = false;
       for(Coordenadas c : actual.getBody()){
          if(c.getY()+1 == this.pfinaly ){
            return true;
          }
      }
      return condicion;
   }
   //SI EXITE COLICION CON PIEZA
   public boolean hayColisioncontraPieza(){
      boolean condicion = false;
            for(Coordenadas ct : this.lpiezas){//EN BASE A LA LISTA
               for(Coordenadas cp : actual.getBody()){
                  if ((cp.getY()+1 == ct.getY()) && (cp.getX() == ct.getX())) {
                     condicion = true;
                  }
               }
            }
         
      return condicion;
   }

   public void ordenarCoordenadas(){
      this.lpiezas.sort(comparator);
   }
   
    public void imprimir(){
      Iterator<Coordenadas> itera = this.lpiezas.iterator();
      while (itera.hasNext()) {
         Coordenadas c=itera.next();
      }
    }

   public boolean hayMover(){
      boolean condicion = true;
      for(Coordenadas c : actual.getBody()){
          if(accion == Accion.RIGHT){
            if(c.getX()+1 == this.limitetabd){
               condicion = false;
            }
          }
          if(accion == Accion.LEFT){
            if(c.getX() == this.limitetabi){
               condicion = false;
            }
          }       
      }
      return condicion;
   }

   public void keyPressed(KeyEvent e){
      char tecla = e.getKeyChar();
      switch(tecla){
         case 'a' : {accion = Accion.LEFT;} break;
         case 'd' : {accion = Accion.RIGHT;} break;
         //case 's' : {accion = Accion.DOWN;} break;TEST ACELERAR
         case ' ' : {accion = Accion.SPACE;} break;
      }
   }

   public void keyReleased(KeyEvent e){}

   public void keyTyped(KeyEvent e){}
   
   public Pieza getActual() {
      return actual;
   }

   public void setActual(Pieza actual) {
      this.actual = actual;
   }

   
   public int getPfinalx() {
      return pfinalx;
   }

   public void setPfinalx(int pfinalx) {
      this.pfinalx = pfinalx;
   }

   public int getPfinaly() {
      return pfinaly;
   }

   public void setPfinaly(int pfinaly) {
      this.pfinaly = pfinaly;
   }
   //LISTA PIEZAS (CALL)
   public ArrayList<Coordenadas> getLpiezas() {
      return lpiezas;
   }

   public void setLpiezas(ArrayList<Coordenadas> lpiezas) {
      this.lpiezas = lpiezas;
   }
   //ACCIONES (CALL)
   public Accion getAccion() {
      return accion;
   }

   public void setAccion(Accion accion) {
      this.accion = accion;
   }
   //LIMITE (CALL)
   public int getLimitetabd() {
      return limitetabd;
   }

   public void setLimitetabd(int limitetabd) {
      this.limitetabd = limitetabd;
   }

   public int getLimitetabi() {
      return limitetabi;
   }

   public void setLimitetabi(int limitetabi) {
      this.limitetabi = limitetabi;
   }

   //EJECUTA ESE BUCLE DE ESTAR MOVIENDO LA PIEZA DE APP
   public void ejecutarFrame() {
   
      if(this.hayMover()){
         if(accion == Accion.RIGHT){
            this.moverDerecha();
         }
         if(accion == Accion.LEFT){
            this.moverIzquierda();
         }
         if(accion == Accion.SPACE){
            this.rotarPieza();
         }
         if(accion == Accion.DOWN){
            this.bajarPieza();
         }
         accion=Accion.NOTHING;
      }else{
         accion=Accion.NOTHING;
      }

      if((!this.hayFinalTablero()) && (!this.hayColisioncontraPieza())){
         this.bajarPieza();
      }else{
         this.getLpiezas().addAll(actual.getBody());
         this.crearPieza();
         this.ordenarCoordenadas();
         this.imprimir();
      }
      
   }

   
   
}