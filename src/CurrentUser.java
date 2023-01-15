import java.util.ArrayList;

public class CurrentUser {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<MovieRun> purchasedMovies;
    private ArrayList<MovieRun> watchedMovies;
    private ArrayList<MovieRun> likedMovies;
    private ArrayList<MovieRun> ratedMovies;
    private ArrayList<Notification> notifications;

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<MovieRun> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<MovieRun> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<MovieRun> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<MovieRun> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<MovieRun> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<MovieRun> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<MovieRun> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<MovieRun> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public CurrentUser(User user) {
        this.credentials = user.getCredentials();
        this.numFreePremiumMovies = 15;
        this.tokensCount = 0;
        this.likedMovies = new ArrayList<>(0);
        this.purchasedMovies = new ArrayList<>(0);
        this.watchedMovies = new ArrayList<>(0);
        this.ratedMovies = new ArrayList<>(0);
        this.notifications = new ArrayList<>(0);
    }

    public CurrentUser() {

    }

    public CurrentUser(CurrentUser currentUser) {
        if (currentUser != null) {
            this.credentials = new Credentials();
            this.purchasedMovies = new ArrayList<MovieRun>();
            this.ratedMovies = new ArrayList<MovieRun>();
            this.likedMovies = new ArrayList<MovieRun>();
            this.watchedMovies = new ArrayList<MovieRun>();
            this.notifications = new ArrayList<Notification>();

            if (currentUser.getCredentials() != null) {
                this.getCredentials().setBalance(currentUser.getCredentials().getBalance());
                this.getCredentials().setName(currentUser.getCredentials().getName());
                this.getCredentials().setAccountType(currentUser.getCredentials().getAccountType());
                this.getCredentials().setCountry(currentUser.getCredentials().getCountry());
                this.getCredentials().setPassword(currentUser.getCredentials().getPassword());
            }
            this.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies());
            this.setTokensCount(currentUser.getTokensCount());
            for (Notification notification : currentUser.getNotifications()) {
                this.getNotifications().add(new Notification(notification));
            }
            for (MovieRun movieRun : currentUser.getPurchasedMovies()) {
                this.getPurchasedMovies().add(new MovieRun(movieRun));
            }
            for (MovieRun movieRun : currentUser.getRatedMovies()) {
                this.getRatedMovies().add(new MovieRun(movieRun));
            }
            for (MovieRun movieRun : currentUser.getWatchedMovies()) {
                this.getWatchedMovies().add(new MovieRun(movieRun));
            }
            for (MovieRun movieRun : currentUser.getLikedMovies()) {
                this.getLikedMovies().add(new MovieRun(movieRun));
            }
        }
    }


    @Override
    public String toString() {
        return "CurrentUser{" +
                "credentials=" + credentials +
                ", tokensCount=" + tokensCount +
                ", numFreePremiumMovies=" + numFreePremiumMovies +
                ", purchasedMovies=" + purchasedMovies +
                ", watchedMovies=" + watchedMovies +
                ", likedMovies=" + likedMovies +
                ", ratedMovies=" + ratedMovies +
                '}' + '\n';
    }

    public boolean equals(CurrentUser currentUser) {
        if (!this.getCredentials().getName().equals(currentUser.getCredentials().getName())) {
            return false;
        }
        return true;
    }

}
