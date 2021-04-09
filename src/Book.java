public class Book {
    private int ISBN;
    private String bookType;
    private String title;
    private String language;
    private String genre;
    private String releaseDate;
    private double price;
    private int quantity;
    private String addInfo1;
    private String addInfo2;

    public Book(int ISBN, String bookType, String title, String language, String genre, String releaseDate, double price, int quantity, String addInfo1, String addInfo2) {
        this.ISBN = ISBN;
        this.bookType = bookType;
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.price = price;
        this.quantity = quantity;
        this.addInfo1 = addInfo1;
        this.addInfo2 = addInfo2;
    }

    public double getPrice() {
        return this.price;
    }

    public int getISBN() {
        return this.ISBN;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getAddInfo1() {
        return this.addInfo1;
    }

    public String getAddInfo2() {
        return this.addInfo2;
    }

    public String getBookType() {
        return this.bookType;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public String toString() {
        return (getISBN() + ", " + getBookType() + ", " + getTitle() + ", "
                + getLanguage()+ ", " + getGenre() + ", " + getReleaseDate() + ", "
                + getPrice() + ", " + getQuantity() + ", " + getAddInfo1() + ", " + getAddInfo2());
    }
}
