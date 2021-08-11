import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Book> bookBasket = new ArrayList<Book>();

    public Basket(){

    }

    public Basket(List<Book> books) {
        this.bookBasket = books;
    }
    
    // Adds a book object to the basket list
    public void addBook(Book b1) {
    	int index = findBook(b1);
    	if (index >= 0) {
    		Book currentBook = bookBasket.get(index);
    		currentBook.setQuantity(currentBook.getQuantity()+1);
    		bookBasket.set(index, currentBook);
    	}
    	else {
    		bookBasket.add(b1);
    	}
    }

    /* Checks whether there is available stock when a customer
     * tries to add a book to their basket by looping through
     * the book list, if the quantity in basket exceeds the stock
     * then the quantity in the basket is updated.
     */
    public String availableStock() {
    	List<Book> bookList = getBookBasket();
    	for (Book b1 : bookList) {
    		if (b1.getQuantity() > b1.getStock()) {
    			int quantity = b1.getQuantity();
    			bookBasket.remove(b1);
    			b1.setQuantity(quantity-1);
    			bookBasket.add(b1);
    			return "Cannot Add to Basket \nQuantity selected for "+b1.getTitle()+" exceeds available stock";
    			
    		}
    	}
    	return "available";
    }
    
    /* Checks to see if the book has already been added to the basket
     * and if it has then the index is returned so that the quantity
     * can be increased rather that adding a new book object to the basket.
     */
	private int findBook(Book book) {
		for (Book b1 : bookBasket) {
			if (b1.getISBN() == book.getISBN()) {
				return bookBasket.indexOf(b1);
			}
		}
		return -1;
	}

    public void clearBasket(){
        bookBasket.clear();
    }

    public List<Book> getBookBasket(){
        return bookBasket;
    }

    // Gets the total by looping through books
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
