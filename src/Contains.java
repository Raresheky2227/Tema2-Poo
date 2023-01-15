import java.util.ArrayList;

public class Contains {
    private ArrayList<String> genre;
    private ArrayList<String> actors;

    public Contains() {
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Contains{" +
                "genres=" + genre +
                ", actors=" + actors +
                '}';
    }
}
