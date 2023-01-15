import java.util.ArrayList;

public class UserSubs {
    private String name;
    private ArrayList<String> SubGenres;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getSubGenres() {
        return SubGenres;
    }

    public void setSubGenres(final ArrayList<String> subGenres) {
        SubGenres = subGenres;
    }

    public UserSubs(final CurrentUser currentUser) {
        this.name = currentUser.getCredentials().getName();
        this.SubGenres = new ArrayList<>(0);
    }

    public UserSubs() {

    }
}
