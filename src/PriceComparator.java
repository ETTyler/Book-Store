import java.util.Comparator;

public class PriceComparator implements Comparator<Book> {
    
	// Compares the cost of book objects
	@Override
    public int compare (Book b1, Book b2) {
        return Double.compare(b1.getCost(), b2.getCost());
    }

}
