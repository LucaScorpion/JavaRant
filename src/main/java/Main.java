import com.scorpiac.javarant.*;

public class Main {
    public static void main(String[] args) {
        Rant[] rants = DevRant.getRants(Sort.ALGO, 1, 0);
        rants[0].getComments();
        User author = rants[0].getUser();
        author.fetchData();
        User me = User.byId(102959);
        Rant random = DevRant.surprise();
        System.out.println("Done.");
    }
}
