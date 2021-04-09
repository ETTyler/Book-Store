import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        String[] book = {"1","2","3","4","5","6","7","8","9","10"};
        String[] user = {"101", "user1", "Smith", "12", "LE11 3TU", "Loughborough", "admin"};

        admin ad = new admin(user);
        Customer c1 = new Customer(user);
        ad.newBook(book);
        //ad.viewBooks();

        Book b1 = new Book(1, "2", "3", "4", "5", "6", 7, 8, "9", "10");
        Basket basket = new Basket();
        basket.addBook(b1);
        basket.addBook(b1);
        basket.addBook(b1);
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
