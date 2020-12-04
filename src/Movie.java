public class Movie extends Item {
    private String director;
    private String Actors;
    private int genre;
    private int grade;

    public Movie(String title, String director, String actors, int genre, int grade, int year, String imagePath, int stars, String plot, String opinion) {
        super(title, year, imagePath, stars, plot, opinion);
        this.director = director;
        Actors = actors;
        this.genre = genre;
        this.grade = grade;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
