import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Checkout {
    Customer c1;
    Basket b1;
    String status;
    String paymentType;

    // This is the constructor for a cancelled checkout
    public Checkout(Customer c1, Basket b1, String status) {
        this.c1 = c1;
        this.b1 = b1;
        this.status = status;
    }
    
    // Constructor for successful checkout
    public Checkout(Customer c1, Basket b1, String status, String paymentType) {
        this.c1 = c1;
        this.b1 = b1;
        this.status = status;
        this.paymentType = paymentType;
    }
    
    /*
     * Loops through the all the books in the text file and 
     * the books in the basket and subtracts the quantity in the basket
     * from the stock of the book.
     */
    public void subtractStock() throws FileNotFoundException {
        List<Book> basketList = b1.getBookBasket();
        ArrayList<String[]> bookList = Tools.read("Stock.txt");
        List<String[]> remove = new ArrayList<>();

        for (String[] row : bookList) {
            for (Book book : basketList) {
                if (String.valueOf(book.getISBN()).equals(row[0])) {
                    int stock = Integer.parseInt(row[7].trim()) - book.getQuantity();
                    if (stock <= 0) {
                        remove.add(row);
                        break;
                    }
                    else {
                        row[7] = String.valueOf(stock);
                    }
                }
            }
        }
        bookList.removeAll(remove);
        StringBuilder sb = new StringBuilder();
        for (String[] row : bookList) {
            sb.append(Arrays.toString(row).trim().replaceAll("[\\[\\]]","").replaceAll(" +", " "));
            sb.append("\n");
        }
        int last, prev = sb.length() - 1;
        while ((last = sb.lastIndexOf("\n", prev)) == prev) { 
        	prev = last - 1;
        }
        if (last >= 0) { 
        	sb.delete(last, sb.length()); 
        }
        Tools.write("Stock.txt", sb.toString());
    }

    /*
     * Formats the strings to be appended to the activity log by looping through
     * the books in the basket.
     */
    public void checkoutBasket() throws FileNotFoundException {
        List<Book> bookList = b1.getBookBasket();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        ArrayList<String[]> stockList = Tools.read("ActivityLog.txt");
        StringBuilder sb = new StringBuilder();
        for (String[] row : stockList) {
            sb.append(Arrays.toString(row).trim().replaceAll("[\\[\\]]","").replaceAll(" +", " "));
            sb.append("\n");
        }
        
        if (status.equals("purchased")) {
            for (Book books : bookList) {
                String log = c1.getID()+","+c1.getPostcode()+", "+books.getISBN()+", "+books.getPrice()+", "+books.getQuantity()+
                        ", "+status+", "+paymentType+", "+date+"\n";
                sb.insert(0, log);
                Tools.write("ActivityLog.txt", sb.toString());
            }
            subtractStock();
        }
        else {
            for (Book books : bookList) {
                String log = c1.getID()+","+c1.getPostcode()+", "+books.getISBN()+", "+books.getPrice()+", "+books.getQuantity()+
                        ", "+status+", , "+date+"\n";
                sb.insert(0, log);
                Tools.write("ActivityLog.txt", sb.toString());
            }
        }
    }
}
