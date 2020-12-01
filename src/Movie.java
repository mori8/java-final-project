public class Movie extends Item {
    private String director;
    private String Actors;
    private String genre;
    private String grade;

    public Movie(String title, String director, String actors, String genre, String grade, String year, int stars, String plot, String opinion) {
        super(title, year, stars, plot, opinion);
        this.director = director;
        Actors = actors;
        this.genre = genre;
        this.grade = grade;
    }
}
