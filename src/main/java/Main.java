import com.scorpiac.javarant.Comment;
import com.scorpiac.javarant.DevRant;
import com.scorpiac.javarant.Rant;
import com.scorpiac.javarant.Sort;

public class Main {
    public static void main(String[] args) {
        Rant[] rants = DevRant.getRants(Sort.ALGO, 1, 0);
        Comment[] comments = rants[0].getComments();
        System.out.println("Done.");
    }
}
