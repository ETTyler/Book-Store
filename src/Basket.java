import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Book> bookBasket = new ArrayList<Book>();

    public Basket(){

    }

    public Basket(List<Book> books) {
        this.bookBasket = books;
    }

    public void addBook(Book book) {
        bookBasket.add(book);
    }

    public void clearBasket(){
        bookBasket.clear();
    }

    public List<Book> getBookBasket(){
        return bookBasket;
    }

    public double getBasketTotal(){
        double total = 0.0;
        for (Book b1: bookBasket){
            total += b1.getPrice();
        }
        return total;
    }

    public int getQuantity(){
        return bookBasket.size();
    }
}
