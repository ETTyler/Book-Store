public class Customer extends User {

	/*
	 * The point of this class was because based off the spec
	 * I initially assumed that the payment details which the admin 
	 * would not have would be used at some point. However after already
	 * developing most of the program I realised that the payment details
	 * are not used beyond the GUI so realistically this class is 
	 * the exact same as the user class. 
	 * 
	 * It is still needed though because only customers
	 * and not admins or users can checkout a book. 
	 */
    public Customer(String[] user) {
        super(user);
    }
}
