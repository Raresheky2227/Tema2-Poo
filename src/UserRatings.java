import java.util.ArrayList;

public class UserRatings {
    private String user;
    private ArrayList<Ratings> ratings = new ArrayList<>();

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Ratings> ratings) {
        this.ratings = ratings;
    }

    public UserRatings(CurrentUser currentUser) {
        this.user = currentUser.getCredentials().getName();
        this.ratings = new ArrayList<>();
    }

    public UserRatings() {

    }
}
