import java.util.ArrayList;

public class UserSubs {
    private String name;
    private ArrayList<String> SubGenres;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubGenres() {
        return SubGenres;
    }

    public void setSubGenres(ArrayList<String> subGenres) {
        SubGenres = subGenres;
    }

    public UserSubs (CurrentUser currentUser) {
        this.name = currentUser.getCredentials().getName();
        this.SubGenres = new ArrayList<>(0);
    }
}
