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

    public Checkout(Customer c1, Basket b1, String status) {
        this.c1 = c1;
        this.b1 = b1;
        this.status = status;
    }
    public Checkout(Customer c1, Basket b1, String status, String paymentType) {
        this.c1 = c1;
        this.b1 = b1;
        this.status = status;
        this.paymentType = paymentType;
    }

    public void subtractStock() throws FileNotFoundException {
        List<Book> basketList = b1.getBookBasket();
        ArrayList<String[]> bookList = Tools.read("Data/Stock.txt");
        List<String[]> remove = new ArrayList<>();
        System.out.println(b1.getQuantity());

        for (String[] row : bookList) {
            for (Book book : basketList) {
                if (String.valueOf(book.getISBN()).equals(row[0])) {
                    int stock = Integer.parseInt(row[7].trim()) - book.getQuantity();
                    System.out.println(Integer.parseInt(row[7].trim()));
                    System.out.println(book.getQuantity());

                    if (stock == 0) {
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
        Tools.write("Data/Stock.txt", sb.toString());
    }

    public void checkoutBasket() throws FileNotFoundException {
        List<Book> bookList = b1.getBookBasket();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        if (status.equals("purchased")) {
            for (Book books : bookList) {
                String log = c1.getID()+", "+c1.getPostcode()+", "+books.getISBN()+", "+books.getPrice()+", "+books.getQuantity()+
                        ", "+status+", "+paymentType+", "+date+"\n";
                Tools.append("Data/ActivityLog.txt", log);
            }
            subtractStock();
        }
        else {
            for (Book books : bookList) {
                String log = c1.getID()+", "+c1.getPostcode()+", "+books.getISBN()+", "+books.getPrice()+", "+books.getQuantity()+
                        ", "+status+", , "+date+"\n";
                Tools.append("Data/ActivityLog.txt", log);
            }
        }
        b1.clearBasket();
    }
}
