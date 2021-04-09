import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class admin extends User {

    public admin(String[] user) {
        super(user);
    }

    public void newBook(String[] bookDetails) throws IOException {
        String book = bookDetails[0].trim() + ", " + bookDetails[1].trim() + ", "
                + bookDetails[2].trim() + ", " + bookDetails[3].trim() + ", " + bookDetails[4].trim() + ", "
                + bookDetails[5].trim() + ", " + bookDetails[6].trim() + ", " + bookDetails[7].trim() + ", "
                + bookDetails[8].trim() + ", " + bookDetails[9].trim();
        // use array to create entry into Stock.txt

        BufferedWriter bw = null;
        Tools.append("Data/Stock.txt",book);
    }

    // view book method needed to be used from user
}
