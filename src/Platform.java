import java.util.ArrayList;

public class Platform {
    private ArrayList<CurrentUser> registeredUsers = new ArrayList<CurrentUser>();
    private ArrayList<MovieRun> platformMovies = new ArrayList<MovieRun>();
    private CurrentUser currentUser;
    private ArrayList<MovieRun> listedMovies = new ArrayList<MovieRun>();
    private String state;
    private ArrayList<UserSubs> userSubs = new ArrayList<>();

    private ArrayList<UserRatings> userRatings = new ArrayList<>();

    public ArrayList<UserRatings> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(ArrayList<UserRatings> userRatings) {
        this.userRatings = userRatings;
    }

    public ArrayList<UserSubs> getUserSubs() {
        return userSubs;
    }

    public void setUserSubs(ArrayList<UserSubs> userSubs) {
        this.userSubs = userSubs;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void SetPlatformMovies (ArrayList<Movie> movies) {
        for (Movie movie : movies) {
            MovieRun movieRun = new MovieRun(movie);
            this.platformMovies.add(movieRun);
        }
    }

    public void SetRegisteredUsers (ArrayList<User> users) {
        for (User user : users) {
            CurrentUser currentUser = new CurrentUser(user);
            this.registeredUsers.add(currentUser);
        }
    }

    public ArrayList<MovieRun> getPlatformMovies() {
        return platformMovies;
    }

    public ArrayList<CurrentUser> getRegisteredUsers() {
        return registeredUsers;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<MovieRun> getListedMovies() {
        return listedMovies;
    }

    public void setListedMovies(ArrayList<MovieRun> listedMovies) {
        this.listedMovies = listedMovies;
    }

    public Platform() {
    }
}
