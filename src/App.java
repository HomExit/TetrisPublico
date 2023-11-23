import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends JFrame implements Runnable{

    int WIDTH = 1000;//SIZE X
    int HEIGHT = 800;//SIZE Y
    int nfilas = 25;//CANTIDAD DE FILAS
    int ncolumnas = 11;//CANTIDAD DE COLUMNAS X
    int tcelda = 25;//CELDAS Y
    int desplazamiento = 35;//(ESTABA EN 100) ES UN AJUSTADOR PARA MOVER EL TABLERO

    Contol control;
    BufferedImage bi = new BufferedImage(this.WIDTH,this.HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);//IMAGEN ESTANDAR
    Graphics gbi = bi.getGraphics();//GENERAR IMAGEN


    //PINTA LAS PIEZAS YA COLOCADAS
    public void pintarPiezasTablero(Graphics g){
            for(Coordenadas c : control.getLpiezas()){//LPIEZAS REPRESENTA EL ARRAY DE LAS PIEZAS YA COLOCADAS O ALMACENADAS
                g.setColor(c.getC());
                g.fillRect((desplazamiento)+(c.getX()*tcelda), (desplazamiento)+(c.getY()*tcelda), tcelda, tcelda);
            }
    }

    //PINTA LA PIEZA, ESTA EN CONSTANTE PINTADA (SOLO PINTA LA PIEZA COLOCADA O ACTUAL QUE SE MUEVE)
    public void pintarPieza(Graphics g){
        Pieza p = this.control.getActual();//OBTIENE LA PIEZA ACTUAL
        for(Coordenadas c : p.getBody()){//MEDIANTE AL CUERPO COLOREA LA PIEZA
            g.setColor(p.getColorpieza());
            g.fillRect((desplazamiento)+(c.getX()*tcelda), (desplazamiento)+(c.getY()*tcelda), tcelda, tcelda);
        }
    }

    //PINTA EL FONDO 
    public void pintarFondo(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(10, 10, this.WIDTH, this.HEIGHT);
    }

    //tablero
    public void pintarTablero(Graphics g){
        for(int i=0;i<nfilas;i++){
            for(int j=0;j<ncolumnas;j++){
                g.setColor(Color.WHITE);
                g.drawRect(desplazamiento+(j*tcelda),desplazamiento+(i*tcelda),tcelda,tcelda);
            }
        }
    }

    public void paint(Graphics g){
        this.pintarFondo(gbi);//PINTA EL FONDO ^
        this.pintarTablero(gbi);//PINTA EL TABLERO ^
        this.pintarPieza(gbi);//PINTA EL PIEZA ^
        this.pintarPiezasTablero(gbi);//PINTA TODAS LAS PIEZAS ALMACENADAS ^
        g.drawImage(bi,10,10, this.WIDTH,this.HEIGHT,this);//LA IMAGEN COMPLETA ES GRAFICADA
    }

    public App(){
        //this.pintarFondo(gbi);//QUE PASA SI NO ESTA EN CONSTANTE COLOREADO DEL FONDO
        this.setVisible(true);//VENTANA
        this.setTitle("Clon Tetris");//NOMBRE VENTANA
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        control = new Contol(); ////faltava esta onda
        control.setPfinalx(ncolumnas);
        control.setPfinaly(nfilas);
        control.limitetabd=ncolumnas;//LIMITE DE MOVIMIENTO X POS
        control.limitetabi=0;
        this.addKeyListener(control);
        this.setSize(this.WIDTH,this.HEIGHT);//VENTANA SIZE

        //para que el juego no pare de ejecutarse, cuyando yo ponga el metodo star() se ira al metodo run()
        Thread hilo = new Thread(this);
        hilo.start();
    }
    public static void main(String[] args) throws Exception {
        App juego = new App();//INICIO
    }

    public void run(){//BUCLE DEL JUEGO PARA QUE ESTE CORRIENDO
         while(true){
            try {
                Thread.sleep(150);//DELAY
            } catch (InterruptedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            control.ejecutarFrame();
            repaint();
        }
    }

    public Contol getControl() {
        return control;
    }

    public void setControl(Contol control) {
        this.control = control;
    }
}