public class Book  extends Item {
    private String author;
    private String publisher;

    public Book(String title, String author, String publisher, String year, int stars, String plot, String opinion) {
        super(title, year, stars, plot, opinion);
        this.author = author;
        this.publisher = publisher;
    }
}
