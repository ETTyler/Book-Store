import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Book {
    private int ISBN;
    private String bookType;
    private String title;
    private String language;
    private String genre;
    private String releaseDate;
    private double price;
    private int stock;
    private String addInfo1;
    private String addInfo2;
    private int quantity;


    // This is the constructor for adding a new book to the database
public Book(int ISBN, String bookType, String title, String language, String genre, String releaseDate, double price, int stock, String addInfo1, String addInfo2) {
        this.ISBN = ISBN;
        this.bookType = bookType;
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.price = price;
        this.stock = stock;
        this.addInfo1 = addInfo1;
        this.addInfo2 = addInfo2;
    }

    // This is the constructor for adding a book to basket
    public Book(String[] book, int quantity) {
        this.ISBN = Integer.parseInt(book[0].trim());
        this.bookType = book[1].trim();
        this.title = book[2].trim();
        this.language = book[3].trim();
        this.genre = book[4].trim();
        this.releaseDate = book[5].trim();
        this.price = Double.parseDouble(book[6].trim());
        this.stock = Integer.parseInt(book[7].trim());
        this.addInfo1 = book[8].trim();
        this.addInfo2 = book[9].trim();
        this.setQuantity(quantity);
    }

    // This is the constructor for reading data from the file
    public Book(String[] book) {
        this.ISBN = Integer.parseInt(book[0].trim());
        this.bookType = book[1].trim();
        this.title = book[2].trim();
        this.language = book[3].trim();
        this.genre = book[4].trim();
        this.releaseDate = book[5].trim();
        this.price = Double.parseDouble(book[6].trim());
        this.stock = Integer.parseInt(book[7].trim());
        this.addInfo1 = book[8].trim();
        this.addInfo2 = book[9].trim();
    }

    public double getPrice() {
        return (this.price*this.quantity);
    }
    
    public double getCost() {
    	return (this.price);
    }

    public int getISBN() {
        return this.ISBN;
    }

    public int getQuantity() {
        return this.quantity;
    }
    
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

    public int getStock() {return this.stock; }
    
    // Checks to see if the ISBN of a book already exists in the text file
    public Boolean alreadyExists() {
    	ArrayList<String[]> bookList = null;
		try {
			bookList = Tools.read("Stock.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        for (String[] row : bookList) {
        	if (Integer.parseInt(row[0]) == this.ISBN) {
        		return true;
        	}
		}
        return false;
    }

    @Override
    public String toString() {
        return (getISBN() + ", " + getBookType() + ", " + getTitle() + ", "
                + getLanguage()+ ", " + getGenre() + ", " + getReleaseDate() + ", "
                + getCost() + ", " + getStock() + ", " + getAddInfo1() + ", " + getAddInfo2());
    }

}
