import java.util.Comparator;

public class ComparadorPersonal implements Comparator<Coordenadas> {

   public int compare(Coordenadas o1, Coordenadas o2) {
      return o1.y-o2.y;
   }
   
}