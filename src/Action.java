public class Action {
    private String type;
    private String page;
    private String feature;
    private Credentials credentials;
    private String StartsWith;
    private String movie;
    private int count;
    private int rate;
    private Filters filters;
    private Movie addedMovie;
    private String deletedMovie;
    private String subscribedGenre;

    public Action() {
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }

    public Movie getAddedMovie() {
        return addedMovie;
    }

    public void setAddedMovie(Movie addedMovie) {
        this.addedMovie = addedMovie;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public void setDeletedMovie(String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public String getStartsWith() {
        return StartsWith;
    }

    public void setStartsWith(String startsWith) {
        StartsWith = startsWith;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Filters getFilter() {
        return filters;
    }

    public void setFilter(Filters filter) {
        this.filters = filter;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type='" + type + '\'' +
                ", page='" + page + '\'' +
                ", feature='" + feature + '\'' +
                ", credentials=" + credentials +
                ", StartsWith='" + StartsWith + '\'' +
                ", movie='" + movie + '\'' +
                ", count=" + count +
                ", rate=" + rate +
                ", filters=" + filters +
                '}' + '\n';
    }
}
