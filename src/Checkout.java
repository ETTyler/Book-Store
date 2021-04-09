import java.text.SimpleDateFormat;
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
    public void checkoutBasket(){
        List<Book> bookList = b1.getBookBasket();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        if (status.equals("purchased")) {
            for (Book books : bookList) {
                String log = c1.getID()+", "+c1.getPostcode()+", "+books.getISBN()+", "+books.getPrice()+", "+books.getQuantity()+", "
                        +status+", "+paymentType+", "+date;
                Tools.append("Data/ActivityLog.txt", log);
            }

        }
        else {
            for (Book books : bookList) {
                String log = c1.getID()+", "+c1.getPostcode()+", "+books.getISBN()+", "+books.getPrice()+", "+books.getQuantity()+", "
                        +status+", , "+date;
                Tools.append("Data/ActivityLog.txt", log);
            }
        }
    }
}
