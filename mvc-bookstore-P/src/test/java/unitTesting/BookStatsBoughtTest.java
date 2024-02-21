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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookStatsBoughtTest {

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
    public void testGetBoughtDatesQuantitiesDay_NoPurchases() {
        BookModel bookWithoutPurchases = createTestBook();

        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesDay_WithPurchases() {
        BookModel bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today);

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a day:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesDay();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesMonth_NoPurchases() {
        BookModel bookWithoutPurchases = createTestBook();

        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesMonth_WithPurchases() {
        BookModel bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today);

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a month:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesMonth();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesYear_NoPurchases() {
        BookModel bookWithoutPurchases = createTestBook();
        String expected = "We have made no purchases on \"" + bookWithoutPurchases.getTitle() + "\"\n";
        String actual = bookWithoutPurchases.getBoughtDatesQuantitiesYear();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoughtDatesQuantitiesYear_WithPurchasesThisYear() {
        BookModel bookWithPurchases = createTestBook();
        Date today = new Date();
        bookWithPurchases.addPurchase(today);

        String expected = "For \"" + bookWithPurchases.getTitle() + "\" We have bought in a year:\n" +
                "1 at " + today + "\n";
        String actual = bookWithPurchases.getBoughtDatesQuantitiesYear();
        assertEquals(expected, actual);
    }



    @Test
    public void testGetTotalBooksBoughtDay() {
        // Arrange
        BookModel book = createTestBook();

        // Mock data for testing
        Date today = new Date();
        book.addPurchasedDate(today);
        book.addQuantitiesPurchased(5);

        // Add an entry for yesterday (should be ignored)
        Calendar yesterdayCalendar = Calendar.getInstance();
        yesterdayCalendar.setTime(today);
        yesterdayCalendar.add(Calendar.DATE, -1);
        Date yesterday = yesterdayCalendar.getTime();
        book.addPurchasedDate(yesterday);
        book.addQuantity(10);

        // Act
        int result = book.getTotalBooksBoughtDay();

        // Assert
        assertEquals(5, result); // Only consider purchases on the specified day
    }

    @Test
    public void testGetTotalBooksBoughtMonth() {
        // Arrange
        BookModel book = createTestBook();

        // Mock data for testing
        Date today = new Date();
        book.addPurchasedDate(today);
        book.addQuantitiesPurchased(5);

        // Add an entry for last month (should be ignored)
        Calendar lastMonthCalendar = Calendar.getInstance();
        lastMonthCalendar.setTime(today);
        lastMonthCalendar.add(Calendar.MONTH, -1);
        Date lastMonth = lastMonthCalendar.getTime();
        book.addPurchasedDate(lastMonth);
        book.addQuantity(10);

        // Act
        int result = book.getTotalBooksBoughtMonth();

        // Assert
        assertEquals(5, result); // Only consider purchases in the current month
    }

    @Test
    public void testGetTotalBooksBoughtYear() {
        // Arrange
        BookModel book = createTestBook();

        // Mock data for testing
        Date today = new Date();
        book.addPurchasedDate(today);
        book.addQuantitiesPurchased(5);

        // Add an entry for last year (should be ignored)
        Calendar lastYearCalendar = Calendar.getInstance();
        lastYearCalendar.setTime(today);
        lastYearCalendar.add(Calendar.YEAR, -1);
        Date lastYear = lastYearCalendar.getTime();
        book.addPurchasedDate(lastYear);
        book.addQuantity(10);

        // Act
        int result = book.getTotalBooksBoughtYear();

        // Assert
        assertEquals(5, result); // Only consider purchases in the current year
    }
    @Test
    public void testGetTotalBooksBoughtYearWhenPurchasedDatesEmpty() {
        // Arrange
        BookModel book = createTestBook();

        // Act
        int result = book.getTotalBooksBoughtYear();

        // Assert
        assertEquals(0, result); // The result should be 0 when purchasedDates list is empty
    }
    @Test
    public void testGetTotalBooksBoughtMonthWhenPurchasedDatesEmpty() {
        // Arrange
        BookModel book = createTestBook();

        // Act
        int result = book.getTotalBooksBoughtMonth();

        // Assert
        assertEquals(0, result); // The result should be 0 when purchasedDates list is empty
    }
    @Test
    public void testGetTotalBooksBoughtDayWhenPurchasedDatesEmpty() {
        // Arrange
        BookModel book = createTestBook();

        // Act
        int result = book.getTotalBooksBoughtDay();

        // Assert
        assertEquals(0, result); // The result should be 0 when purchasedDates list is empty
    }




}
