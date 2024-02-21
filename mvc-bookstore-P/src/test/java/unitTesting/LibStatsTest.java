package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibStatsTest {

    LibrarianFuncController librarianFuncController = new LibrarianFuncController();
    BookController bookController = new BookController();
    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";


    public static void setStockFilePath(String newPath) {
        try {
            // Change the STOCK_FILE_PATH field in BookController class
            changeField(BookController.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianModel.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianFuncController.class, "STOCK_FILE_PATH", newPath);
            changeField(FileBasedStockBookRepository.class, "STOCK_FILE_PATH", newPath);
            // Add more classes and fields to change here as necessary
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void changeField(Class<?> targetClass, String fieldName, String newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, newValue);
    }

    private BookModel createTestBook() {
        return new BookModel(TEST_ISBN, TEST_TITLE, "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
    }

    @BeforeEach
    public void setUp() {
        LibStatsTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
        // Create a temporary file for testing
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            // Write initial data to the temporary file if needed for setup
            // For instance:
            BookModel book = createTestBook();
            objout.writeObject(book);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<BookModel> saveBooksToTemporaryFile(ArrayList<BookModel> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Test
    public void testMoneymadeinDay1(){
        var stat=new StatisticsFuncController();
        stat.datesSold = null;
        double result =stat.moneyMadeInDay();
        assertEquals(0, result);
    }

    @Test
    public void testMoneymadeinDay2(){
        Date today = new Date();

        System.out.println(today);

        var stat=new StatisticsFuncController();
        ArrayList<Date> getDates=new ArrayList<>();
        getDates.add(today);

        // Create an instance of your class

        // Set up datesSold and moneyMadeDates with specific values
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();

        // Add dates and corresponding money made values for today (replace with actual logic)
        datesSold.add(today);
        moneyMadeDates.add(50.0);  // Replace with the actual money made for today

        // Set datesSold and moneyMadeDates in your class
        stat.datesSold = datesSold;
        stat.moneyMadeDates = moneyMadeDates;

        // Test the method
        double result = stat.moneyMadeInDay();  // Replace with the actual method you're testing

        // Assert the result
        // If today's date is present in datesSold, the result should be the corresponding money made value
        assertEquals(50.0, result);


    }



    @Test
    public void testMoneymadeinMonthy1(){
        var stat=new StatisticsFuncController();
        stat.datesSold = null;
        double result =stat.moneyMadeInMonth(); // Replace with the actual method you're testing
        assertEquals(0, result);



    }
    @Test
    public void testMoneymadeinMonth2(){
        Date today = new Date();

        System.out.println(today);

        var stat=new StatisticsFuncController();
        ArrayList<Date> getDates=new ArrayList<>();
        getDates.add(today);

        // Create an instance of your class

        // Set up datesSold and moneyMadeDates with specific values
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();

        // Add dates and corresponding money made values for today (replace with actual logic)
        datesSold.add(today);
        moneyMadeDates.add(50.0);  // Replace with the actual money made for today

        // Set datesSold and moneyMadeDates in your class
        stat.datesSold = datesSold;
        stat.moneyMadeDates = moneyMadeDates;

        // Test the method
        double result = stat.moneyMadeInMonth();  // Replace with the actual method you're testing

        // Assert the result
        // If today's date is present in datesSold, the result should be the corresponding money made value
        assertEquals(50.0, result);


    }

    @Test
    public void testMoneymadeinYear1(){
        var stat=new StatisticsFuncController();
        stat.datesSold = null;
        double result =stat.moneyMadeInYear(); // Replace with the actual method you're testing
        assertEquals(0, result);



    }
    @Test
    public void testMoneymadeinYear2(){
        Date today = new Date();

        System.out.println(today);

        var stat=new StatisticsFuncController();
        ArrayList<Date> getDates=new ArrayList<>();
        getDates.add(today);


        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();

        datesSold.add(today);
        moneyMadeDates.add(50.0);

        stat.datesSold = datesSold;
        stat.moneyMadeDates = moneyMadeDates;

        double result = stat.moneyMadeInYear();

        assertEquals(50.0, result);


    }

    @Test
    public void testMoneyMadeInMonthNoSales() {
        LibrarianModel librarian = new LibrarianModel("1", "1"); // Instantiate your Librarian class
        librarianFuncController.datesSold = null; // Set datesSold to null explicitly
        double moneyMade = statisticsFuncController.moneyMadeInMonth();
        assertEquals(0, moneyMade, 0); // Assuming a negligible error    }
    }

    @Test
    public void testMoneyMadeInMonthWithSales() throws IOException {
        LibrarianModel librarian = new LibrarianModel("1","1"); // Instantiate your Librarian class

        // Add some sales to datesSold and moneyMadeDates
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();
        datesSold.add(new Date()); // Add today's date
        moneyMadeDates.add(1.0); // Add some money made for today

        // Set the datesSold and moneyMadeDates in librarian
        BookModel b1=createTestBook();
        BookModel b2=createTestBook();

        ArrayList<BookModel>arrayList=new ArrayList<>();
        arrayList.add(b1);
        arrayList.add(b2);

        int x=b1.getPurchasedAmount();
        int y=b2.getPurchasedAmount();
        System.out.println("x");
        System.out.println(x);
        ArrayList<Integer>quantities=new ArrayList<>();
        quantities.add(x);
        quantities.add(y);
        librarianFuncController.checkOutBooks(arrayList,quantities);


        double moneyMade = statisticsFuncController.moneyMadeInMonth();
        assertEquals(1.0, moneyMade, 1.0); // Adjust the expected value based on your test data
    }

    @Test
    public void testMoneyMadeInYearNoSales() {
        LibrarianModel librarian = new LibrarianModel("1", "1"); // Instantiate your Librarian class
        librarianFuncController.datesSold = null; // Set datesSold to null explicitly
        double moneyMade = statisticsFuncController.moneyMadeInYear();
        assertEquals(0, moneyMade, 0); // Assuming a negligible error    }
    }

    @Test
    public void testMoneyMadeInYearWithSales() throws IOException {
        LibrarianModel librarian = new LibrarianModel("1","1"); // Instantiate your Librarian class

        // Add some sales to datesSold and moneyMadeDates
        ArrayList<Date> datesSold = new ArrayList<>();
        ArrayList<Double> moneyMadeDates = new ArrayList<>();
        datesSold.add(new Date()); // Add today's date
        moneyMadeDates.add(1.0); // Add some money made for today

        // Set the datesSold and moneyMadeDates in librarian
        BookModel  b1=createTestBook();
        BookModel b2=createTestBook();

        ArrayList<BookModel>arrayList=new ArrayList<>();
        arrayList.add(b1);
        arrayList.add(b2);

        int x=b1.getPurchasedAmount();
        int y=b2.getPurchasedAmount();
        System.out.println("x");
        System.out.println(x);
        ArrayList<Integer>quantities=new ArrayList<>();
        quantities.add(x);
        quantities.add(y);
        librarianFuncController.checkOutBooks(arrayList,quantities);


        double moneyMade = statisticsFuncController.moneyMadeInYear();
        assertEquals(1.0, moneyMade, 1.0); // Adjust the expected value based on your test data
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_NoPurchases() {
        BookModel testBook = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        String result = testBook.getBoughtDatesQuantitiesDay();

        String expected = "We have made no purchases on \"Test Book\"\n";

        assertEquals(expected, result);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_WithPurchases() {
        BookModel testBook = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        testBook.addPurchase(new Date());

        String result = testBook.getBoughtDatesQuantitiesDay();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String currentDateFormatted = dateFormat.format(new Date());

        String expected = "For \"Test Book\" We have bought in a day:\n1 at " + currentDateFormatted + "\n";

        assertEquals(expected, result);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_MultiplePurchases() {
        BookModel testBook = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);

        testBook.addPurchase(getYesterday());
        testBook.addPurchase(new Date());

        String result = testBook.getBoughtDatesQuantitiesDay();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        String currentDateFormatted = dateFormat.format(new Date());

        String expected = "For \"Test Book\" We have bought in a day:\n1 at " + currentDateFormatted + "\n";
        assertEquals(expected, result);
    }

    // helper method to get yesterday's date
    private Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }



}
