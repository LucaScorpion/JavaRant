import com.scorpiac.javarant.DevRant;
import com.scorpiac.javarant.Rant;
import com.scorpiac.javarant.Sort;
import com.scorpiac.javarant.User;

public class Main {
    public static void main(String[] args) {
        Rant[] rants = DevRant.getRants(Sort.ALGO, 1, 0);
        rants[0].getComments();
        Rant random = DevRant.surprise();
        Rant some = Rant.byId(32502);
        User me = User.byId(102959);
        User dfox = User.byUsername("dfox");
        Rant[] search = DevRant.search("js");
        System.out.println("Done.");
    }
}
