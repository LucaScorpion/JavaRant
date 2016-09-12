import com.scorpiac.javarant.*;

public class Main {
    public static void main(String[] args) {
        Rant[] rants = DevRant.getRants(Sort.ALGO, 1, 0);
        rants[0].getComments();
        User me = User.byId(102959);
        System.out.println("Done.");
    }
}
