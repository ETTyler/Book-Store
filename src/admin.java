import java.io.IOException;

public class admin extends User {

    public admin(String[] user) {
        super(user);
    }
    
    /*
     * Adds a new book to the stock file by converting a
     * book object to a formatted String and appending it
     * to the file.
     */
    public void newBook(Book b1) throws IOException {
        String bookDetails = b1.toString();
        Tools.append("Stock.txt", bookDetails);
    }

   
}
