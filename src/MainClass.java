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

        for (String[] user1: userList) {
            System.out.println(Arrays.toString(user1));
        }

        Book b1 = new Book(bookList.get(1),2);
        ad.newBook(b1);
        Basket basket = new Basket();
        basket.addBook(b1);

        double cost = basket.getBasketTotal();
        System.out.println(cost);

        Checkout checkout1 = new Checkout(c1,basket,"purchased","PayPal");
        Checkout checkout2 = new Checkout(c1,basket,"cancelled");
        checkout1.checkoutBasket();
        checkout2.checkoutBasket();

        basket.clearBasket();
        cost = basket.getBasketTotal();
        System.out.print(cost);
    }
}
