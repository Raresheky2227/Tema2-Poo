public class Notification {
    private String movieName;
    private String message;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification(Notification notification) {
        if (notification.getMovieName() != null)
            this.setMovieName(notification.getMovieName());
        if (notification.getMessage() != null)
            this.setMessage(notification.getMessage());
    }

    public Notification() {
    }
}
