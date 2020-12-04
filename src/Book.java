public class Book  extends Item {
    private String author;
    private String publisher;

    public Book(String title, String author, String publisher, int year, String imagePath, int stars, String plot, String opinion) {
        super(title, year, imagePath, stars, plot, opinion);
        this.author = author;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
