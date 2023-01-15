import java.util.ArrayList;

public class MovieRun {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;

    public MovieRun (Movie movie) {
        this.name= movie.getName();
        this.year = movie.getYear();
        this.duration = movie.getDuration();
        this.genres = movie.getGenres();
        this.actors = movie.getActors();
        this.countriesBanned = movie.getCountriesBanned();
        this.numLikes = 0;
        this.rating = 0;
        this.numRatings = 0;
    }

    public MovieRun(MovieRun movieRun) {
        if (movieRun != null) {
            this.genres = new ArrayList<String>();
            this.actors = new ArrayList<String>();
            this.countriesBanned = new ArrayList<String>();
            this.setNumLikes(movieRun.getNumLikes());
            if (movieRun.getName() != null)
            this.setName(movieRun.getName());
            this.setYear(movieRun.getYear());
            this.setRating(movieRun.getRating());
            this.setNumRatings(movieRun.getNumRatings());
            this.setDuration(movieRun.getDuration());
            if (movieRun.getActors() != null) {
                for (String string : movieRun.getActors()) {
                    this.getActors().add(string);
                }
            }
            if (movieRun.getGenres() != null) {
                for (String string : movieRun.getGenres()) {
                    this.getGenres().add(string);
                }
            }
            if (movieRun.getCountriesBanned() != null) {
                for (String string : movieRun.getCountriesBanned()) {
                    this.getCountriesBanned().add(string);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    @Override
    public String toString() {
        return "MovieRun{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", genres=" + genres +
                ", actors=" + actors +
                ", countriesBanned=" + countriesBanned +
                ", numLikes=" + numLikes +
                ", rating=" + rating +
                ", numRatings=" + numRatings +
                '}' + '\n';
    }
}
