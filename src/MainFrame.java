import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import java.awt.Color;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	
	private JTable tblBooks;
	private JTable tblBasket;
	private DefaultTableModel dtmBooks;
	private DefaultTableModel dtmBasket;
	private JTextField ISBNtxt;
	private JTextField titletxt;
	private JTextField stocktxt;
	private JTextField addInfo1txt;
	private JTextField pricetxt;
	private JLabel lblTotal;
	private JButton btnBasket;
	private Basket bookBasket = new Basket();
	private JTextField txtCardNo;
	private JTextField txtCVV;
	private JTextField txtEmail;
	
	
	/*
	 * Gets the ISBN of the row of the user has clicked on and
	 * then loops through the list of books from the text file to
	 * find a match. The details of the book and then used to create
	 * a Book object. 
	 */
	private Book getSelectedBook() throws FileNotFoundException, IndexOutOfBoundsException {
		int selectedRow = tblBooks.getSelectedRow();
		String ISBN = dtmBooks.getValueAt(selectedRow, 0).toString();
		
		ArrayList<String[]> bookList = Tools.read("Stock.txt");

        for (String[] row : bookList) {
        	if (row[0].equals(ISBN)) {
        		return new Book(row, 1);
        	}
		}
		return null;
	}
	
	/*
	 * Adds the selected book to the basket and checks the 
	 * availability of the book to ensure the selected quantity
	 * can be added. 
	 */
	private void addToBasket() {
		try {
			// add item to basket
			bookBasket.addBook(getSelectedBook());
			String availability = bookBasket.availableStock();
			if (!availability.equals("available")) {
				JOptionPane.showMessageDialog(null, availability);
			}
			// Show basket size on Basket button
			btnBasket.setText("Basket ("+bookBasket.getQuantity()+")");

		} catch (FileNotFoundException | IndexOutOfBoundsException e) {
			e.printStackTrace();

		}
	}
	
	// Displays the table of all the books
	public void viewTable() {
		dtmBooks.setRowCount(0);
		User u1 = new User();
		try {
			ArrayList<Book> stockList = u1.viewBooks();
			for (Book b1 : stockList) {
				Object[] rowdata = new Object[] {b1.getISBN(), b1.getBookType(), b1.getTitle(), b1.getLanguage(), b1.getGenre(), b1.getReleaseDate(),"£"+b1.getCost(), b1.getStock(), b1.getAddInfo1(), b1.getAddInfo2()};
				dtmBooks.addRow(rowdata);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}	
	}
	
	// Displays the table of books in the basket
	private void BasketTable() {
		// Display total price of basket
		lblTotal.setText("£"+(bookBasket.getBasketTotal()));
		dtmBasket.setRowCount(0);

		// Populate table
		List<Book> bookList = bookBasket.getBookBasket();
		for (Book b1 : bookList) {

			// get all properties of item
			Object[] rowdata = {b1.getISBN(), b1.getBookType(), b1.getTitle(), b1.getLanguage(), b1.getGenre(), b1.getQuantity(), b1.getPrice()} ;
			dtmBasket.addRow(rowdata);
		}
		tblBasket.setModel(dtmBasket);
	}
	
	// Validates whether a string is an email
	private boolean isEmailValid(String email) {
		   String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		   return email.matches(regex);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainFrame() throws FileNotFoundException {
		
		// Gets the User Accounts in a formatted way for the combobox
		ArrayList<String[]> userList = Tools.read("UserAccounts.txt");
		String[] array = new String[userList.size()];
		int i = 0;
		for (String[] user1: userList) {
			String[] temp = Arrays.toString(user1).replaceAll("[\\[\\]]","").split(",");
			String combo = "ID - "+temp[0].trim()+", Surname - "+temp[2].trim()+", Role - "+temp[6].trim();
			array[i] = combo;
			i++;
		}
		
		// The default selected user is set here
		admin a1 = new admin(userList.get(0));
		Customer c1 = new Customer(userList.get(1));
		
		// Initialises arraylists used to determine the current user
		ArrayList<admin> adminUser = new ArrayList<admin>();
		adminUser.add(a1);
		ArrayList<Customer> custUser = new ArrayList<Customer>();
		
		
		
		// Java GUI component declarations
		// I apologise but this section is an unordered mess of auto generated code
		// Line 543 is where the action listeners begin
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1293, 840);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 40, 1243, 750);
		contentPane.add(tabbedPane);
		
		dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"ISBN","Type","Title","Language","Genre","Quantity","Price"});
		
		dtmBooks = new DefaultTableModel();
		dtmBooks.setColumnIdentifiers(new Object[] {"ISBN","Type","Title","Language","Genre","Release Date","Price","Stock","Pages/Length","Condition/Format"});
		viewTable();
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Home", null, panel, null);
		panel.setLayout(null);
		
		JButton viewBooksBtn = new JButton("View All");
		viewBooksBtn.setFont(new Font("Arial", Font.PLAIN, 11));
		
		viewBooksBtn.setBounds(20, 11, 107, 23);
		panel.add(viewBooksBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 45, 1055, 262);
		panel.add(scrollPane);
		
		tblBooks = new JTable();
		tblBooks.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(tblBooks);
		tblBooks.setModel(dtmBooks);
				
		JButton btnATC = new JButton("Add to Basket");
		btnATC.setEnabled(false);

		btnATC.setFont(new Font("Arial", Font.PLAIN, 11));
		btnATC.setBounds(137, 11, 107, 23);
		panel.add(btnATC);
				
		btnBasket = new JButton("Basket");
		btnBasket.setEnabled(false);

		btnBasket.setFont(new Font("Arial", Font.PLAIN, 11));
		btnBasket.setBounds(937, 11, 138, 23);
		panel.add(btnBasket);
		
		JButton btnNewButton_4 = new JButton("> 5 Hours");

		btnNewButton_4.setFont(new Font("Arial", Font.PLAIN, 11));
		btnNewButton_4.setBounds(1108, 119, 107, 23);
		panel.add(btnNewButton_4);
				
		JLabel lblNewLabel_1 = new JLabel("Filters");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(1108, 63, 46, 14);
		panel.add(lblNewLabel_1);
				
		JComboBox cbGenre = new JComboBox();
		cbGenre.setFont(new Font("Arial", Font.PLAIN, 11));
		cbGenre.setBounds(1108, 178, 107, 22);
		panel.add(cbGenre);
				
		JLabel lblNewLabel_10 = new JLabel("Listening Length");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_10.setBounds(1108, 94, 107, 14);
		panel.add(lblNewLabel_10);
				
		JLabel lblNewLabel_11 = new JLabel("Genre");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_11.setBounds(1108, 153, 46, 14);
		panel.add(lblNewLabel_11);
				
		JLabel lblNewLabel_12 = new JLabel("Book Type");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_12.setBounds(1108, 221, 69, 14);
		panel.add(lblNewLabel_12);
				
		JComboBox cbTypeFilter = new JComboBox();
		cbTypeFilter.setFont(new Font("Arial", Font.PLAIN, 11));
		cbTypeFilter.setModel(new DefaultComboBoxModel(new String[] {"", "paperback", "ebook", "audiobook"}));
		cbTypeFilter.setBounds(1108, 246, 107, 22);
		panel.add(cbTypeFilter);
		
		JPanel AddBookpnl = new JPanel();
		AddBookpnl.setBounds(20, 341, 560, 348);
		panel.add(AddBookpnl);
		AddBookpnl.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add New Book");
		lblNewLabel.setBounds(10, 11, 86, 16);
		AddBookpnl.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		
		ISBNtxt = new JTextField();
		ISBNtxt.setBounds(35, 55, 107, 20);
		AddBookpnl.add(ISBNtxt);
		ISBNtxt.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ISBN");
		lblNewLabel_2.setBounds(35, 38, 46, 14);
		AddBookpnl.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblNewLabel_4 = new JLabel("Title");
		lblNewLabel_4.setBounds(183, 38, 46, 14);
		AddBookpnl.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 11));
		
		titletxt = new JTextField();
		titletxt.setBounds(183, 55, 107, 20);
		AddBookpnl.add(titletxt);
		titletxt.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Language");
		lblNewLabel_3.setBounds(338, 38, 68, 14);
		AddBookpnl.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblNewLabel_5 = new JLabel("Genre");
		lblNewLabel_5.setBounds(35, 109, 46, 14);
		AddBookpnl.add(lblNewLabel_5);
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 11));
		
		stocktxt = new JTextField();
		stocktxt.setBounds(338, 194, 107, 20);
		AddBookpnl.add(stocktxt);
		stocktxt.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Release Date");
		lblNewLabel_6.setBounds(183, 109, 86, 14);
		AddBookpnl.add(lblNewLabel_6);
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblNewLabel_7 = new JLabel("Stock");
		lblNewLabel_7.setBounds(338, 172, 46, 14);
		AddBookpnl.add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel lblNewLabel_8 = new JLabel("Book Type");
		lblNewLabel_8.setBounds(35, 172, 96, 22);
		AddBookpnl.add(lblNewLabel_8);
		lblNewLabel_8.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JComboBox cbBookType = new JComboBox();
		cbBookType.setBounds(35, 193, 107, 22);
		AddBookpnl.add(cbBookType);
		cbBookType.setFont(new Font("Arial", Font.PLAIN, 11));
		cbBookType.setModel(new DefaultComboBoxModel(new String[] {"paperback", "audiobook", "ebook"}));
		
		JLabel lblNewLabel_9 = new JLabel("Price");
		lblNewLabel_9.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_9.setBounds(183, 176, 46, 14);
		AddBookpnl.add(lblNewLabel_9);
		
		pricetxt = new JTextField();
		pricetxt.setBounds(183, 194, 107, 20);
		AddBookpnl.add(pricetxt);
		pricetxt.setColumns(10);
		
		JLabel addInfo1lbl = new JLabel("Number of Pages");
		addInfo1lbl.setBounds(35, 238, 141, 14);
		AddBookpnl.add(addInfo1lbl);
		addInfo1lbl.setFont(new Font("Arial", Font.PLAIN, 11));
		
		addInfo1txt = new JTextField();
		addInfo1txt.setBounds(35, 263, 107, 20);
		AddBookpnl.add(addInfo1txt);
		addInfo1txt.setColumns(10);
		
		JLabel addInfo2lbl = new JLabel("Condition");
		addInfo2lbl.setBounds(183, 238, 107, 14);
		AddBookpnl.add(addInfo2lbl);
		addInfo2lbl.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JButton btnNewButton = new JButton("Add Book");
		btnNewButton.setBounds(35, 306, 89, 23);
		AddBookpnl.add(btnNewButton);
		
		JComboBox cbLanguage = new JComboBox();
		cbLanguage.setFont(new Font("Arial", Font.PLAIN, 11));
		cbLanguage.setModel(new DefaultComboBoxModel(new String[] {"English", "French"}));
		cbLanguage.setBounds(338, 54, 107, 22);
		AddBookpnl.add(cbLanguage);
		
		JComboBox cbInputGenre = new JComboBox();
		cbInputGenre.setFont(new Font("Arial", Font.PLAIN, 11));
		cbInputGenre.setModel(new DefaultComboBoxModel(new String[] {"Politics", "Medicine", "Business", "Computer Science", "Biology"}));
		cbInputGenre.setBounds(35, 128, 107, 22);
		AddBookpnl.add(cbInputGenre);
		
		JComboBox cbAddInfo2 = new JComboBox();
		cbAddInfo2.setFont(new Font("Arial", Font.PLAIN, 11));
		cbAddInfo2.setModel(new DefaultComboBoxModel(new String[] {"new", "used"}));
		cbAddInfo2.setBounds(183, 262, 107, 22);
		AddBookpnl.add(cbAddInfo2);
		
		JComboBox cbDay = new JComboBox();
		cbDay.setFont(new Font("Arial", Font.PLAIN, 11));
		cbDay.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		cbDay.setBounds(212, 128, 46, 22);
		AddBookpnl.add(cbDay);
		
		JComboBox cbMonth = new JComboBox();
		cbMonth.setFont(new Font("Arial", Font.PLAIN, 11));
		cbMonth.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		cbMonth.setBounds(299, 128, 46, 22);
		AddBookpnl.add(cbMonth);
		
		JComboBox cbYear = new JComboBox();
		cbYear.setFont(new Font("Arial", Font.PLAIN, 11));
		cbYear.setModel(new DefaultComboBoxModel(new String[] {"2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939", "1938", "1937", "1936", "1935", "1934", "1933", "1932", "1931", "1930", "1929", "1928", "1927", "1926", "1925", "1924", "1923", "1922", "1921", "1920", "1919", "1918", "1917", "1916", "1915", "1914", "1913", "1912", "1911", "1910", "1909", "1908", "1907", "1906", "1905", "1904", "1903", "1902", "1901", "1900"}));
		cbYear.setBounds(382, 128, 63, 22);
		AddBookpnl.add(cbYear);
		
		JLabel lblNewLabel_19 = new JLabel("Day");
		lblNewLabel_19.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_19.setBounds(183, 132, 19, 14);
		AddBookpnl.add(lblNewLabel_19);
		
		JLabel lblNewLabel_20 = new JLabel("Month");
		lblNewLabel_20.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_20.setBounds(266, 132, 29, 14);
		AddBookpnl.add(lblNewLabel_20);
		
		JLabel lblNewLabel_21 = new JLabel("Year");
		lblNewLabel_21.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_21.setBounds(355, 132, 29, 14);
		AddBookpnl.add(lblNewLabel_21);
		
		JPanel pnlCheckout = new JPanel();
		
		JComboBox cbPaymentType = new JComboBox();
		
		cbPaymentType.setFont(new Font("Arial", Font.PLAIN, 11));
		cbPaymentType.setBounds(31, 91, 153, 22);
		pnlCheckout.add(cbPaymentType);
		cbPaymentType.setModel(new DefaultComboBoxModel(new String[] {"PayPal", "Credit Card"}));
		
		JLabel lblNewLabel_15 = new JLabel("Payment Type");
		lblNewLabel_15.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_15.setBounds(31, 66, 76, 14);
		pnlCheckout.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("Card Number");
		lblNewLabel_16.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_16.setBounds(31, 145, 76, 14);
		pnlCheckout.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("CVV");
		lblNewLabel_17.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_17.setBounds(31, 184, 46, 14);
		pnlCheckout.add(lblNewLabel_17);
		
		txtCardNo = new JTextField();
		txtCardNo.setFont(new Font("Arial", Font.PLAIN, 11));
		txtCardNo.setEnabled(false);
		txtCardNo.setBounds(131, 142, 117, 20);
		pnlCheckout.add(txtCardNo);
		txtCardNo.setColumns(10);
		
		txtCVV = new JTextField();
		txtCVV.setFont(new Font("Arial", Font.PLAIN, 11));
		txtCVV.setEnabled(false);
		txtCVV.setBounds(131, 181, 60, 20);
		pnlCheckout.add(txtCVV);
		txtCVV.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("Email");
		lblNewLabel_18.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNewLabel_18.setBounds(31, 235, 46, 14);
		pnlCheckout.add(lblNewLabel_18);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 11));
		txtEmail.setBounds(131, 232, 166, 20);
		pnlCheckout.add(txtEmail);
		txtEmail.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		separator.setBounds(29, 129, 276, 2);
		pnlCheckout.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(31, 219, 276, 2);
		pnlCheckout.add(separator_1);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JPanel pnlBasket = new JPanel();
		tabbedPane.addTab("Basket", null, pnlBasket, null);
		pnlBasket.setLayout(null);
		
		JButton btnClear = new JButton("Clear Basket");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 11));
		
		btnClear.setBounds(27, 21, 122, 23);
		pnlBasket.add(btnClear);
		
		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.setFont(new Font("Arial", Font.PLAIN, 11));
		
		cbGenre.setModel(new DefaultComboBoxModel(new String[] {"", "Politics", "Medicine", "Business", "Computer Science", "Biography"}));
		
		JButton btnCancelCO = new JButton("Cancel");
		btnCancelCO.setFont(new Font("Arial", Font.PLAIN, 11));
		
		JLabel userlbl = new JLabel("User:");
		userlbl.setBounds(945, 25, 56, 14);
		contentPane.add(userlbl);
		userlbl.setFont(new Font("Arial", userlbl.getFont().getStyle(), userlbl.getFont().getSize()));
		
		JComboBox userCB = new JComboBox(array);
		userCB.setBounds(1011, 21, 238, 22);
		contentPane.add(userCB);
		userCB.setFont(new Font("Arial", Font.PLAIN, 11));
		
		btnCheckout.setBounds(1120, 21, 89, 23);
		pnlBasket.add(btnCheckout);
		
		JLabel lblNewLabel_13 = new JLabel("Total:");
		lblNewLabel_13.setFont(new Font("Arial", Font.BOLD, 13));
		lblNewLabel_13.setBounds(986, 24, 41, 16);
		pnlBasket.add(lblNewLabel_13);
		
		lblTotal = new JLabel();
		lblTotal.setFont(new Font("Arial", Font.BOLD, 13));
		lblTotal.setBounds(1037, 21, 52, 23);
		pnlBasket.add(lblTotal);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27, 55, 1062, 402);
		pnlBasket.add(scrollPane_1);
		
		tblBasket = new JTable();
		scrollPane_1.setViewportView(tblBasket);
		tblBasket.setModel(dtmBasket);
		

		tabbedPane.addTab("Checkout", null, pnlCheckout, null);
		pnlCheckout.setLayout(null);
		btnCancelCO.setBounds(171, 273, 89, 23);
		pnlCheckout.add(btnCancelCO);
		btnSubmit.setBounds(51, 273, 89, 23);
		pnlCheckout.add(btnSubmit);
		
		JLabel lblNewLabel_14 = new JLabel("Checkout");
		lblNewLabel_14.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_14.setBounds(31, 11, 265, 44);
		pnlCheckout.add(lblNewLabel_14);
		
		
		
		// Action Listeners Section
		
		
		// Loads the basket table and changes the tab to the basket tab
		btnBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketTable();
				tabbedPane.setSelectedIndex(1);
			}
		});
		
		// On button click adds a new book to the text file
		btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (custUser.isEmpty()) {
				try {
					admin a1 = adminUser.get(0);
					
					// Variables to create a new book object are initialised
					int ISBN = Integer.parseInt(ISBNtxt.getText());
				    String bookType = cbBookType.getSelectedItem().toString();
				    String title = titletxt.getText();
				    String language = cbLanguage.getSelectedItem().toString();
				    String genre = cbInputGenre.getSelectedItem().toString();
				    String releaseDate = cbDay.getSelectedItem().toString()+"-"+cbMonth.getSelectedItem().toString()+"-"+cbYear.getSelectedItem().toString();
				    double price = Double.parseDouble(pricetxt.getText());
				    int stock = Integer.parseInt(stocktxt.getText());
				    String addInfo1 = addInfo1txt.getText();
				    String addInfo2 = cbAddInfo2.getSelectedItem().toString();
				    Double.parseDouble(addInfo1txt.getText());
				    
				    Book b1 = new Book(ISBN, bookType, title, language, genre, releaseDate, price, stock, addInfo1, addInfo2);
				    
				    // If a book with the same ISBN already exists then a message is shown and the book is not added
				    if (b1.alreadyExists()) {
				    	JOptionPane.showMessageDialog(null,"Book With The Same ISBN Already Exists!");
				    	return;
				    }
				    
				    // Ensures that the ISBN is 8 digits
				    if (!(String.valueOf(ISBN).length() == 8)) {
				    	JOptionPane.showMessageDialog(null,"ISBN Has To Be 8 Digits!");
				    	return;
				    }
				    
				    else {
				    // Adds the book to the text file and updates the table
				    	a1.newBook(b1);
						viewTable();
				    }
					
					// Text boxes are reset and message shown
					ISBNtxt.setText("");
					titletxt.setText("");
					pricetxt.setText("");
					stocktxt.setText("");
					addInfo1txt.setText("");
					JOptionPane.showMessageDialog(null,"Book Successfully Added!");
				
				// If any errors occur while trying to add the book the user is informed
				/* 
				 * It would probably be better for the user if I added individual validation
				 * to each text box but I believed this was the more efficient way as the only
				 * validation needed for those inputs would be whether the correct data type
				 * was entered.
				 * The only input with individual validation on it is the ISBN text box as that
				 * requires validation which would not cause any errors. 
				*/
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,"Invalid Details Please Try Again!");
				}
				
			}
		}
		});
		
		// Sets the values for the labels and Combo box depending on the type of book
		cbBookType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookType = cbBookType.getSelectedItem().toString();
				switch(bookType) {
					case "paperback":
						addInfo1lbl.setText("Number of Pages");
						addInfo2lbl.setText("Condition");
						cbAddInfo2.setModel(new DefaultComboBoxModel(new String[] {"new", "used"}));
						break;
					case "audiobook":
						addInfo1lbl.setText("Length in Hours");
						addInfo2lbl.setText("Format");
						cbAddInfo2.setModel(new DefaultComboBoxModel(new String[] {"MP3", "WMA", "AAC"}));
						break;
					case "ebook":
						addInfo1lbl.setText("Number of Pages");
						addInfo2lbl.setText("Format");
						cbAddInfo2.setModel(new DefaultComboBoxModel(new String[] {"EPUB", "MOBI", "AZW3", "PDF"}));
						break;
				}
			}
		});
		
		// Clears the basket when the button is pressed
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookBasket.clearBasket();
				BasketTable();
				btnBasket.setText("Basket");
			}
		});

		
		// When the checkout button is clicked redirects the user to the checkout panel
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});

		
		/*
		 * When the submit button is clicked the checkout object is created
		 * and the method to carry out the checkout is used.
		 * Once the checkout has been logged the message is shown on screen
		 * and the basket is reset.
		 */
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookBasket.getQuantity() == 0) {
					JOptionPane.showMessageDialog(null, "Basket is Empty");
					tabbedPane.setSelectedIndex(0);
					return;
				}
				String paymentType = cbPaymentType.getSelectedItem().toString();
				
				if (paymentType.equals("PayPal") && !isEmailValid(txtEmail.getText())) {
					JOptionPane.showMessageDialog(null, "Invalid Email Address");
					return;
				}
				
				if (paymentType.equals("Credit Card")) {
					try {
						String cardNo = txtCardNo.getText().toString();
						String CVV = txtCVV.getText().toString();
						Integer.parseInt(cardNo);
						Integer.parseInt(CVV);
						
					    if (!(cardNo.length() == 6)) {
					    	JOptionPane.showMessageDialog(null,"Card Number Has To Be 6 Digits!");
					    	return;
					    }
					    if (!(CVV.length() == 3)) {
					    	JOptionPane.showMessageDialog(null,"CVV Has To Be 3 Digits!");
					    	return;
					    }   
					}
					catch (Exception e3) {
						JOptionPane.showMessageDialog(null,"Card Number and CVV Have to be Numbers");
						return;
					}

				}
				
				Checkout payment = new Checkout(c1, bookBasket, "purchased", paymentType);
				try {
					payment.checkoutBasket();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "£"+(bookBasket.getBasketTotal())+" paid using "+ paymentType);
				bookBasket.clearBasket();
				BasketTable();
				btnBasket.setText("Basket");
				tabbedPane.setSelectedIndex(0);
			}
		});

		
		/*
		 * If the checkout is cancelled then the checkout object
		 * is created and the checkout method is carried out to 
		 * update the text file. Then returns the user to the home 
		 * page and clears the basket.
		 */
		btnCancelCO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Checkout payment = new Checkout(c1, bookBasket, "cancelled");
				try {
					payment.checkoutBasket();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				bookBasket.clearBasket();
				BasketTable();
				btnBasket.setText("Basket");
				tabbedPane.setSelectedIndex(0);
			}
		});

		
		// Carries out the add to basket method when the button is clicked
		btnATC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToBasket();
			}
		});
		

		// When button is clicked all books in the database are shown sorted by price
		viewBooksBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewTable();
			}
		});
		
		
		/*
		 *  Sets the user selected in the combo box by creating
		 *  either and admin or customer object. Which in turn sets
		 *  whether they can add to basket or add a new book. 
		 */
		userCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int user = userCB.getSelectedIndex();
				
				/* The admin can still click on the tabs to go to the basket and checkout
				 * but this is because I found that there is no way to disable a tab/panel
				 * and if i wanted to do this I would have remove the tabs and re-add them 
				 * each time.
				 */
	
				if (userCB.getSelectedItem().toString().contains("admin")) {
					admin a1 = new admin(userList.get(user));
					adminUser.clear();
					custUser.clear();
					adminUser.add(a1);
					AddBookpnl.setVisible(true);
					btnATC.setEnabled(false);
					btnBasket.setEnabled(false);
				} 
				else {
					Customer c1 = new Customer(userList.get(user));
					custUser.clear();
					adminUser.clear();
					custUser.add(c1);
					AddBookpnl.setVisible(false);
					btnATC.setEnabled(true);
					btnBasket.setEnabled(true);
				}
				bookBasket.clearBasket();
				BasketTable();
				btnBasket.setText("Basket");
				tabbedPane.setSelectedIndex(0);
			}
		});
		
		// Sets what text fields are enabled based on the payment type
		cbPaymentType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbPaymentType.getSelectedItem().toString().contains("PayPal")) {
					txtEmail.setEnabled(true);
					txtCardNo.setEnabled(false);
					txtCVV.setEnabled(false);
				} 
				else {
					txtCardNo.setEnabled(true);
					txtCVV.setEnabled(true);
					txtEmail.setEnabled(false);
				}
			}
		});
		
		/*
		 * Based off the genre selected in the combo box the
		 * table is filtered to only show books of that genre.
		 * The table is reset then the viewBooks method is used
		 * to provide an arraylist of all the books in the text file.
		 * This arraylist is then looped through and any Book objects
		 * with the matching genre are displayed on the table.
		 */
		cbGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String genre = cbGenre.getSelectedItem().toString();
				dtmBooks.setRowCount(0);
				try {
					ArrayList<Book> stockList = a1.viewBooks();
					for (Book b1 : stockList) {
						if(b1.getGenre().equals(genre)) {
							Object[] rowdata = new Object[] {b1.getISBN(), b1.getBookType(), b1.getTitle(), b1.getLanguage(), b1.getGenre(), b1.getReleaseDate(),"£"+b1.getCost(), b1.getStock(), b1.getAddInfo1(), b1.getAddInfo2()};
							dtmBooks.addRow(rowdata);
						}
						else if (genre.equals("")) {
							viewTable();
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}	
				
			}
		});
		
		/*
		 * Works the exact same way as the genre filter but uses
		 * the book type. This could've maybe have been made into
		 * its own method where any filter could be set but since 
		 * the spec only required the genre filter I didn't think
		 * it was worthwhile.
		 */
		cbTypeFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = cbTypeFilter.getSelectedItem().toString();
				dtmBooks.setRowCount(0);
				try {
					ArrayList<Book> stockList = a1.viewBooks();
					for (Book b1 : stockList) {
						if(b1.getBookType().equals(type)) {
							Object[] rowdata = new Object[] {b1.getISBN(), b1.getBookType(), b1.getTitle(), b1.getLanguage(), b1.getGenre(), b1.getReleaseDate(),"£"+b1.getCost(), b1.getStock(), b1.getAddInfo1(), b1.getAddInfo2()};
							dtmBooks.addRow(rowdata);
						}
						else if (type.equals("")) {
							viewTable();
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}	
				
			}
		});
		
		
		/* Follows the same logic as the other 2 filters
		 * but also checks the length of the audiobook.
		 */
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtmBooks.setRowCount(0);
				try {
					ArrayList<Book> stockList = a1.viewBooks();
					for (Book b1 : stockList) {
						if (b1.getBookType().equals("audiobook") && Double.parseDouble(b1.getAddInfo1()) > 5.0) {
							Object[] rowdata = new Object[] {b1.getISBN(), b1.getBookType(), b1.getTitle(), b1.getLanguage(), b1.getGenre(), b1.getReleaseDate(),"£"+b1.getCost(), b1.getStock(), b1.getAddInfo1(), b1.getAddInfo2()};
							dtmBooks.addRow(rowdata);
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		// If the tab is changed to the basket then the basket table is updated
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					BasketTable();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

	}
}
