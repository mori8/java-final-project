public class Item {
    private String title;
    private int year;
    private String imagePath;
    private int stars;
    private String plot;
    private String opinion;

    public Item(String title, int year, String imagePath, int stars, String plot, String opinion) {
        this.title = title;
        this.year = year;
        this.imagePath = imagePath;
        this.stars = stars;
        this.plot = plot;
        this.opinion = opinion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
