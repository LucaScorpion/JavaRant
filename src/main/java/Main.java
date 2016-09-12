import com.scorpiac.javarant.DevRant;
import com.scorpiac.javarant.Rant;
import com.scorpiac.javarant.Sort;

public class Main {
    public static void main(String[] args) {
        Rant[] rants = DevRant.getRants(Sort.ALGO, 1, 0);
        System.out.println("Done.");
    }
}
