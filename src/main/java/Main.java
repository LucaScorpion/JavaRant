import com.scorpiac.javarant.*;

public class Main {
    public static void main(String[] args) {
        Rant[] rants = DevRant.getRants(Sort.ALGO, 1, 0);
        rants[0].getComments();
        Rant random = DevRant.surprise();
        Rant some = Rant.byId(32502);
        User me = User.byId(102959);
        User dfox = User.byUsername("dfox");
        System.out.println("Done.");
    }
}
