import java.io.IOException;

public class admin extends User {

    public admin(String[] user) {
        super(user);
    }

    public void newBook(Book b1) throws IOException {
        String bookDetails = b1.toString();
        Tools.append("Data/Stock.txt",bookDetails);
    }

    // view book method needed to be used from user
}
