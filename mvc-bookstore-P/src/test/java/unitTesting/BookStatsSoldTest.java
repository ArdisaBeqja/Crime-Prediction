package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookStatsSoldTest {

    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";

    public static void setStockFilePath(String newPath) {
        try {
            // change the STOCK_FILE_PATH field in BookController class
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
        BookStatsBoughtTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testGetSoldDatesQuantitiesYear_NoPurchases() {
        BookModel testBook = createTestBook(); // Create a test book without any sales
        String expected = testBook.getTitle() + " has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSoldDatesQuantitiesYear_WithPurchases() {
        BookModel testBook = createTestBook();

        // Adding a sale to the book with today's date
        Date today = new Date();
        testBook.addSale(today, 5);

        String expected = "For \"" + testBook.getTitle() + "\" We have sold in a year:\n";
        expected += "5 at " + today + "\n";

        String actual = testBook.getSoldDatesQuantitiesYear();
        assertEquals(expected, actual);
    }



    @Test
    public void testGetTotalBooksSoldDay_NoSales() {
        BookModel bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldDay();
        assertEquals(expected, actual);
    }


    @Test
    public void testGetTotalBooksSoldDay_WithSalesToday() {
        BookModel bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5);

        int expected = 5;
        int actual = bookWithSalesToday.getTotalBooksSoldDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldDay_EmptyDate() {
        BookModel testBook = createTestBook();

        assertTrue(testBook.getDates().isEmpty());

        int totalBooksSold = testBook.getTotalBooksSoldDay();
        assertTrue(totalBooksSold == 0);
    }

    //created an empty constructor
    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListDay() {
        BookModel book = new BookModel();
        book.setDates(null);
        int result = book.getTotalBooksSoldDay();
        assertEquals(0, result);
    }





    @Test
    public void testGetTotalBooksSoldMonth_NoSales() {
        BookModel bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldMonth_WithSalesToday() {
        BookModel bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5); // Adding 5 sales for today

        int expected = 5; // Assuming 5 sales were added for today
        int actual = bookWithSalesToday.getTotalBooksSoldMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldMonth_EmptyDate() {
        // Create a test book without any sales (empty dates)
        BookModel testBook = createTestBook(); // Assuming you have a method to create a test book

        // Ensure the book's dates list is empty
        assertTrue(testBook.getDates().isEmpty());

        // Get the total books sold for the day and assert it's zero
        int totalBooksSold = testBook.getTotalBooksSoldMonth();
        assertTrue(totalBooksSold == 0);
    }


    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListMonth() {
        BookModel book = new BookModel();
        book.setDates(null);
        int result = book.getTotalBooksSoldMonth();
        assertEquals(0, result);
    }







    @Test
    public void testGetTotalBooksSoldYear_NoSales() {
        BookModel bookWithoutSales = createTestBook();
        int expected = 0;
        int actual = bookWithoutSales.getTotalBooksSoldYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldYear_WithSalesToday() {
        BookModel bookWithSalesToday = createTestBook();
        Date today = new Date();
        bookWithSalesToday.addSale(today, 5); // Adding 5 sales for today

        int expected = 5; // Assuming 5 sales were added for today
        int actual = bookWithSalesToday.getTotalBooksSoldYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetTotalBooksSoldYear_EmptyDate() {
        // Create a test book without any sales (empty dates)
        BookModel testBook = createTestBook(); // Assuming you have a method to create a test book

        // Ensure the book's dates list is empty
        assertTrue(testBook.getDates().isEmpty());

        // Get the total books sold for the day and assert it's zero
        int totalBooksSold = testBook.getTotalBooksSoldYear();
        assertTrue(totalBooksSold == 0);
    }


    //changed the method in Book.java -> date == null
    @Test
    public void testEmptyDatesListYear() {
        BookModel book = new BookModel();
        book.setDates(null);
        int result = book.getTotalBooksSoldYear();
        assertEquals(0, result);
    }


    @Test
    public void testGetSoldDatesQuantitiesDay_WithPurchases() {
        // Create a Book object
        BookModel book = new BookModel();
        book.setTitle("Test Book");

        // Add a sale for today with a quantity of 3
        Date saleDate = new Date();
        book.addSale(saleDate, 3);

        // Get the sold dates and quantities for today
        String soldDatesQuantitiesToday = book.getSoldDatesQuantitiesDay();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Calculate the expected result based on the added sale for today
        StringBuilder expected = new StringBuilder("For \"Test Book\" We have sold in a day:\n");
        expected.append("3 at ").append(saleDate).append("\n");

        // Assert that the calculated result matches the expected result
        assertEquals(expected.toString(), soldDatesQuantitiesToday);
    }

    @Test
    public void testGetSoldDatesQuantitiesDay_NoPurchases() {
        BookModel testBook = createTestBook();

        // Set dates to null to simulate the condition
        testBook.setDates(null);
        testBook.setPurchasedAmount(null);

        String expected = "Test Book has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesDay();

        assertEquals(expected, actual);
    }



    @Test
    public void testGetSoldDatesQuantitiesMonth_WithPurchases() {
        // Create a Book object
        BookModel book = new BookModel();
        book.setTitle("Test Book");

        // Add a sale for today with a quantity of 3
        Date saleDate = new Date();
        book.addSale(saleDate, 3);

        // Get the sold dates and quantities for today
        String soldDatesQuantitiesToday = book.getSoldDatesQuantitiesMonth();

        // Get today's date
        LocalDate today = LocalDate.now();

        // Calculate the expected result based on the added sale for today
        StringBuilder expected = new StringBuilder("For \"Test Book\" We have sold in a month:\n");
        expected.append("3 at ").append(saleDate).append("\n");

        // Assert that the calculated result matches the expected result
        assertEquals(expected.toString(), soldDatesQuantitiesToday);
    }

    @Test
    public void testGetSoldDatesQuantitiesMonth_NoPurchases() {
        BookModel testBook = createTestBook();

        // Set dates to null to simulate the condition
        testBook.setDates(null);
        testBook.setPurchasedAmount(null);

        String expected = "Test Book has had no purchases\n";
        String actual = testBook.getSoldDatesQuantitiesMonth();

        assertEquals(expected, actual);
    }

}
