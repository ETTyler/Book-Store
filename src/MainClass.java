import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {
    public static void main(String[] args) throws IOException {

        ArrayList<String[]> userList = Tools.read("Data/UserAccounts.txt");
        ArrayList<String[]> bookList = Tools.read("Data/Stock.txt");

        admin ad = null;
        if (userList != null) {
            ad = new admin(userList.get(1));
        }

        Customer c1 = new Customer(userList.get(2));
        ad.viewBooks();

        //for (String[] user1: userList) {
            //System.out.println(Arrays.toString(user1));
        //}

        Book b1 = new Book(bookList.get(0),2);
        //ad.newBook(b1);
        Book b2 = new Book(bookList.get(1),1);
        Book b3 = new Book(bookList.get(2),3);
        Basket basket = new Basket();
        basket.addBook(b1);
        basket.addBook(b2);
        basket.addBook(b3);

        double cost = basket.getBasketTotal();
        System.out.println(cost);

        Checkout checkout1 = new Checkout(c1,basket,"purchased","PayPal");
        Checkout checkout2 = new Checkout(c1,basket,"cancelled");
        checkout1.checkoutBasket();
        //checkout2.checkoutBasket();

        basket.clearBasket();
        cost = basket.getBasketTotal();
        System.out.print(cost);
    }
}
/* TODO
    1. Design frontend
    2. Create Search feature
    3. Validate all inputs

    DONE:
    1. Admin add new book
    2. User view all books
    3. Add items to basket
    4. Checkout Items
    5. Log activities
    6. Cancel shopping basket
 */