import java.util.ArrayList;
import java.util.Collections;

public class Output {
    private String error;
    private ArrayList<MovieRun> currentMoviesList;
    private CurrentUser currentUser;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<MovieRun> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(ArrayList<MovieRun> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public Output() {
    }

    public Output(String error, ArrayList<MovieRun> currentMoviesList, CurrentUser currentUser) {
        this.error = error;
        if (currentMoviesList == null)
            this.currentMoviesList = null;
        else {
            this.currentMoviesList = new ArrayList<MovieRun>();
            for (MovieRun movieRun : currentMoviesList) {

                this.currentMoviesList.add(new MovieRun(movieRun));
            }
        }
        if (currentUser != null) {
            this.currentUser = new CurrentUser(currentUser);
        }
        else {
            this.currentUser = null;
        }
    }

    @Override
    public String toString() {
        return "Output{" +
                "error='" + error + '\'' +
                ", currentMoviesList=" + currentMoviesList +
                ", currentUser=" + currentUser +
                '}';
    }
}
