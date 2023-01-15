import java.util.ArrayList;
import java.util.Comparator;

public class ActionHandler {
    public static boolean Login (Platform platform, Credentials credentials) {
        for (CurrentUser currentUser : platform.getRegisteredUsers()) {
            if (currentUser.getCredentials().getName().equals(credentials.getName()) && currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
                platform.setCurrentUser(currentUser);
                platform.setState("Homepage Log");
                return true;
            }
        }
        return false;
    }

    public static boolean Register (Platform platform, Credentials credentials) {
        for (CurrentUser currentUser : platform.getRegisteredUsers()) {
            if (currentUser.getCredentials().getName().equals(credentials.getName())) {
                return false;
            }
        }
        User user = new User();
        user.setCredentials(credentials);
        CurrentUser currentUser = new CurrentUser(user);
        platform.getRegisteredUsers().add(currentUser);
        platform.setCurrentUser(currentUser);
        UserSubs subs = new UserSubs(currentUser);
        platform.getUserSubs().add(subs);
        UserRatings ratings = new UserRatings(currentUser);
        platform.getUserRatings().add(ratings);
        platform.setState("Homepage Log");
        return true;
    }

    public static void Logout (Platform platform) {
        platform.getListedMovies().clear();
        platform.setCurrentUser(null);
        platform.setState("Homepage NoLog");
    }

    public static void ListMovies (Platform platform) {
        String Ban = platform.getCurrentUser().getCredentials().getCountry();
        for (MovieRun movieRun : platform.getPlatformMovies()) {
            if (!movieRun.getCountriesBanned().contains(Ban))
                platform.getListedMovies().add(movieRun);
        }
    }

    public static void SearchMovies (Platform platform, String toSearch) {
            platform.getListedMovies().removeIf(n -> (!n.getName().startsWith(toSearch)));
    }

    static class SortDurationAsc implements Comparator<MovieRun> {
        public int compare (MovieRun A, MovieRun B) {
            return A.getDuration() - B.getDuration();
        }
    }

    static class SortDurationDesc implements Comparator<MovieRun> {
        public int compare (MovieRun A, MovieRun B) {
            return B.getDuration() - A.getDuration();
        }
    }

    static class SortRatingAsc implements Comparator<MovieRun> {
        public int compare (MovieRun A, MovieRun B) {
            if (A.getRating() > B.getRating())
                return 1;
            else if (A.getRating() < B.getRating())
                return -1;
            return 0;
        }
    }

    static class SortRatingDesc implements Comparator<MovieRun> {
        public int compare (MovieRun a, MovieRun b) {
            if (a.getRating() < b.getRating()) {
                return 1;
            } else if (a.getRating() > b.getRating()) {
                return -1;
            }
            return 0;
        }
    }

    public static void FilterMovies (Platform platform, Filters filters) {
        if (filters.getSort() != null) {
            String ratingSort = filters.getSort().getRating();
            String durationSort = filters.getSort().getDuration();
            if (ratingSort != null) {
                if (ratingSort.equals("decreasing"))
                    platform.getListedMovies().sort(new SortRatingDesc());
                if (ratingSort.equals("increasing"))
                    platform.getListedMovies().sort(new SortRatingAsc());
            }
            if (durationSort != null) {
                if (durationSort.equals("decreasing"))
                    platform.getListedMovies().sort(new SortDurationDesc());
                if (durationSort.equals("increasing"))
                    platform.getListedMovies().sort(new SortDurationAsc());
            }
        }
        ArrayList<Integer> pos = new ArrayList<Integer>();
        ArrayList<MovieRun> mov = new ArrayList<MovieRun>();
        if (filters.getContains() != null) {
            if (filters.getContains().getActors() != null){
                for (String actor : filters.getContains().getActors()) {
                    for (MovieRun movieRun : platform.getListedMovies()) {
                        for (String string : movieRun.getActors()) {
                            if (string.equals(actor)) {
                                pos.add(platform.getListedMovies().indexOf(movieRun));
                            }
                        }
                    }
                }
                for (Integer integer : pos) {
                    mov.add(platform.getListedMovies().get(integer));
                }
                platform.getListedMovies().clear();
                platform.getListedMovies().addAll(mov);
            }
            if (filters.getContains().getGenre() != null) {
                for (String genre : filters.getContains().getGenre()) {
                    platform.getListedMovies().removeIf(n -> (!n.getGenres().contains(genre)));
                }
            }
        }
    }

    public static void SeeDetails (Platform platform, String movie) {
        platform.getListedMovies().removeIf(n -> (!n.getName().contains(movie)));
    }

    public static boolean BuyTokens (Platform platform, int tokensAdded) {
        if (Integer.parseInt(platform.getCurrentUser().getCredentials().getBalance()) < tokensAdded) {
            return false;
        }
        else {
            platform.getCurrentUser().getCredentials().setBalance(Integer.toString(Integer.parseInt(platform.getCurrentUser().getCredentials().getBalance()) - tokensAdded));
            platform.getCurrentUser().setTokensCount(platform.getCurrentUser().getTokensCount() + tokensAdded);
            for (CurrentUser currentUser : platform.getRegisteredUsers()) {
                if (currentUser.getCredentials().getName().equals(platform.getCurrentUser().getCredentials().getName())) {
                    currentUser.getCredentials().setBalance(platform.getCurrentUser().getCredentials().getBalance());
                    currentUser.setTokensCount(platform.getCurrentUser().getTokensCount());
                }
            }
            return true;
        }
    }

    public static boolean BuyPremiumAccount (Platform platform) {
        if (platform.getCurrentUser().getTokensCount() < 10)
            return false;
        platform.getCurrentUser().setTokensCount(platform.getCurrentUser().getTokensCount() - 10);
        platform.getCurrentUser().getCredentials().setAccountType("premium");
        for (CurrentUser currentUser : platform.getRegisteredUsers()) {
            if (currentUser.getCredentials().getName().equals(platform.getCurrentUser().getCredentials().getName())) {
                currentUser.setTokensCount(platform.getCurrentUser().getTokensCount());
                currentUser.getCredentials().setAccountType(platform.getCurrentUser().getCredentials().getAccountType());
            }
        }
        return true;
    }


    public static boolean PurchaseMovie (Platform platform, String movie) {
        if (platform.getListedMovies().isEmpty()) return false;
        for (MovieRun movieRun : platform.getCurrentUser().getPurchasedMovies()) {
            if (movieRun.equals(platform.getListedMovies().get(0))) {
                return false;
            }
        }

        Movie Movie = new Movie();
        MovieRun movieR = platform.getListedMovies().get(0);
        int x = 1;

        if (platform.getCurrentUser().getCredentials().getAccountType().equals("premium"))
            if (platform.getCurrentUser().getNumFreePremiumMovies() > 0) {
                platform.getCurrentUser().setNumFreePremiumMovies(platform.getCurrentUser().getNumFreePremiumMovies() - 1);
                platform.getCurrentUser().getPurchasedMovies().add(movieR);
                x--;
            }
        if ((platform.getCurrentUser().getCredentials().getAccountType().equals("standard") || (platform.getCurrentUser().getCredentials().getAccountType().equals("premium") && platform.getCurrentUser().getNumFreePremiumMovies() == 0)) && (x != 0)) {
            platform.getCurrentUser().setTokensCount(platform.getCurrentUser().getTokensCount() - 2);
            platform.getCurrentUser().getPurchasedMovies().add(movieR);
            }
        for (CurrentUser currentUser : platform.getRegisteredUsers()) {
            if (currentUser.getCredentials().getName().equals(platform.getCurrentUser().getCredentials().getName())) {
                currentUser.setNumFreePremiumMovies(platform.getCurrentUser().getNumFreePremiumMovies());
                currentUser.setTokensCount(platform.getCurrentUser().getTokensCount());
            }
        }
        return true;
    }

    public static boolean WatchMovie (Platform platform, String movie) {
        if (platform.getListedMovies().isEmpty()) return false;
        for (MovieRun movieRun : platform.getCurrentUser().getWatchedMovies()) {
            if (movieRun.equals(platform.getListedMovies().get(0))) {
                return true;
            }
        }

        for (MovieRun movieRun : platform.getCurrentUser().getPurchasedMovies()) {
            if (movieRun.equals(platform.getListedMovies().get(0))) {
                platform.getCurrentUser().getWatchedMovies().add(movieRun);
                return true;
            }
        }
        return false;
    }

    public static boolean LikeMovie (Platform platform, String movie) {
        if (platform.getListedMovies().isEmpty()) return false;
        for (MovieRun movieRun : platform.getCurrentUser().getLikedMovies()) {
            if (movieRun.getName().equals(platform.getListedMovies().get(0).getName())) {
                return false;
            }
        }

        for (MovieRun movieRun : platform.getCurrentUser().getWatchedMovies()) {
            if (movieRun.equals(platform.getListedMovies().get(0))) {
                movieRun.setNumLikes(movieRun.getNumLikes() + 1);
                platform.getCurrentUser().getLikedMovies().add(movieRun);
                return true;
            }
        }
        return false;
    }


    public static boolean RateMovie (Platform platform, int rate) {
        if (rate > 5) return false;
        if (platform.getListedMovies().isEmpty()) return false;

        MovieRun RatedMovie = platform.getListedMovies().get(0);
        UserRatings userRate = new UserRatings();
        for (UserRatings userRatings : platform.getUserRatings()) {
            if (userRatings.getUser().equals(platform.getCurrentUser().getCredentials().getName())) {
                userRate= userRatings;
                break;
            }
        }

        int found = 0;
        for (Ratings ratings : userRate.getRatings()) {
            if (ratings.getMovieName().equals(RatedMovie.getName())) {
                found = 1;
                ratings.setRate(rate);
                break;
            }
        }
        if (found == 0) {
            Ratings ratings = new Ratings();
            ratings.setRate(rate);
            ratings.setMovieName(RatedMovie.getName());
            userRate.getRatings().add(ratings);
            platform.getCurrentUser().getRatedMovies().add(RatedMovie);
        }

        int sum = 0;
        int num_Ratings = 0;
        for (UserRatings userRatings : platform.getUserRatings()) {
            for (Ratings ratings : userRatings.getRatings()) {
                if (ratings.getMovieName().equals(RatedMovie.getName())) {
                    sum += ratings.getRate();
                    num_Ratings++;
                }
            }
        }
        RatedMovie.setNumRatings(num_Ratings);
        RatedMovie.setRating((double)sum/num_Ratings);
        return true;
    }

    public static boolean Subscribe (Platform platform, String genre) {
        String name = platform.getCurrentUser().getCredentials().getName();
        for (UserSubs userSubs : platform.getUserSubs()) {
            if (userSubs.getName().equals(name)) {
                for (String string : userSubs.getSubGenres()) {
                    if (genre.equals(string)) return false;
                }
                userSubs.getSubGenres().add(genre);
                return true;
            }
        }
        return false;
    }

    public static void Recommend (Platform platform) {
        ActionHandler.ListMovies(platform);

        class Top {
            String name;
            int num_likes;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNum_likes() {
                return num_likes;
            }

            public void setNum_likes(int num_likes) {
                this.num_likes = num_likes;
            }

            public Top (String string, int no) {
                this.name = string;
                this.num_likes = no;
            }

            @Override
            public String toString() {
                return this.name +  " " + this.num_likes;
            }
        }
        int found;
        ArrayList<Top> top = new ArrayList<>();
        for (MovieRun movieRun : platform.getListedMovies()) {
            if (movieRun.getNumLikes() > 0) {
                for (String genre : movieRun.getGenres()) {
                    found = 0;
                    for (Top top1 : top) {
                        if (top1.getName().equals(genre)) {
                            top1.setNum_likes(top1.getNum_likes() + movieRun.getNumLikes());
                            found = 1;
                            break;
                        }
                    }
                    if (found == 0) {
                        top.add(new Top(genre, movieRun.getNumLikes()));
                    }
                }
            }
        }
        String movieName = "No recommendation";
        if (!top.isEmpty()) {
            int max_likes = 0;
            ArrayList<String> Genres = new ArrayList<>();
            for (Top top1 : top) {
                if (top1.getNum_likes() > max_likes) {
                    max_likes = top1.getNum_likes();
                }
            }
            final int MAX = max_likes;
            top.removeIf(n -> (n.getNum_likes() < MAX));
            String topGenre = " ";
            for (Top top1 : top) {
                if (topGenre.compareTo(top1.getName()) < 0) {
                    topGenre = top1.getName();
                }
            }

            ArrayList<Integer> to_delete = new ArrayList<>();
            for (MovieRun movieRun : platform.getListedMovies()) {
                for (MovieRun watched : platform.getCurrentUser().getWatchedMovies()) {
                    if (movieRun.getName().equals(watched.getName())) {
                        to_delete.add(platform.getListedMovies().indexOf(movieRun));
                        break;
                    }
                }
            }
            for (Integer in : to_delete) {
                platform.getListedMovies().remove((int) in);
            }
            max_likes = 0;
            ArrayList<MovieRun> Recom = new ArrayList<>();
            ArrayList<Integer> to_delete1 = new ArrayList<>();
            for (MovieRun movieRun : platform.getListedMovies()) {
                found = 0;
                for (String genre : movieRun.getGenres()) {
                    if (genre.equals(topGenre)) {
                        found = 1;
                        break;
                    }
                }
                if (found == 1) {
                    to_delete1.add(platform.getListedMovies().indexOf(movieRun));
                    if (movieRun.getNumLikes() > max_likes) {
                        max_likes = movieRun.getNumLikes();
                    }
                    Recom.add(movieRun);
                }
            }

            final int MAX1 = max_likes;
            Recom.removeIf(n -> (n.getNumLikes() < MAX1));
            if (movieName.equals("No recommendation") && !Recom.isEmpty()) {
                movieName = Recom.get(0).getName();
            }

        }
        Notification notification = new Notification();
        notification.setMessage("Recommendation");
        notification.setMovieName(movieName);
        if (platform.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            platform.getCurrentUser().getNotifications().add(notification);
        }
    }

    public static boolean Add (Platform platform, Movie movie) {
        MovieRun movieRun = new MovieRun(movie);

        for (MovieRun movieRun1 : platform.getPlatformMovies()) {
            if (movieRun1.getName().equals(movieRun.getName()))
                return false;
        }
        platform.getPlatformMovies().add(movieRun);
        ActionHandler.UpdateADD(platform, movieRun);
        return true;
    }

    public static void UpdateADD (Platform platform, MovieRun movieRun) {
        String name = movieRun.getName();
        for (UserSubs userSubs : platform.getUserSubs()) {
            for (String genre : userSubs.getSubGenres()) {
                if (movieRun.getGenres().contains(genre)) {
                    Notification notification = new Notification();
                    notification.setMessage("ADD");
                    notification.setMovieName(name);
                    for (CurrentUser currentUser : platform.getRegisteredUsers())
                        if (currentUser.getCredentials().getName().equals(userSubs.getName())) {
                            currentUser.getNotifications().add(notification);
                            break;
                        }
                    break;
                }
            }
        }
    }
}
