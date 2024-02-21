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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LibrarianTest {

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
        LibrarianTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testToStringLib() {
        // Create a Librarian object with some sample data
        LibrarianModel librarian = new LibrarianModel("sampleUsername", "samplePassword", "John Doe", 50000.0, "123456789", "john.doe@example.com");

        // Define the expected result based on the sample data
        String expectedToString = "Librarian [username=sampleUsername, password=samplePassword, name=John Doe, salary=50000.0, phone=123456789, email=john.doe@example.com]";

        // Call the toString method and compare the result with the expected string
        assertEquals(expectedToString, librarian.toString());
    }



    @Test
    public void testGetNumberOfBills() {
        LibrarianModel librarian = new LibrarianModel();

        librarianFuncController.setNumberOfBills(3);
        assertEquals(3,librarianFuncController.getNumberOfBills() , "Number of bills should match");
    }

    @Test
    public void testGetBooksSold(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setBooksSold(3);
        assertEquals(3,librarian.getBooksSold());


    }

    @Test
    public void testGetUsername(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setUsername("bob");
        assertEquals("bob",librarian.getUsername());


    }

    @Test
    public void testGetPassword(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setPassword("lol");
        assertEquals("lol",librarian.getPassword());
    }


    @Test
    public void testGetName(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setName("dikush");
        assertEquals("dikush",librarian.getName());
    }

    @Test
    public void testGetSalary(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setSalary(1000);
        assertEquals(1000,librarian.getSalary());
    }

    @Test
    public void testGetPhone(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setPhone("0799");
        assertEquals("0799",librarian.getPhone());
    }

    @Test
    public void testGetEmail(){
        LibrarianModel librarian = new LibrarianModel();

        librarian.setEmail("e@m");
        assertEquals("e@m",librarian.getEmail());
    }




    @Test
    public void testEquals() {
        BookModel obj1 = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        BookModel obj2 = new BookModel("1234567890123", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        BookModel obj3 = new BookModel("9876543210987", "Different Book", "Category2", "Another Publisher", 30.00, 35.00, "Another Author", 2);
        // Arrange
        bookController.equals(obj1);

        // Act & Assert
        // Test equality with itself
        assertTrue(obj1.equals(obj1));

        // Test equality with an identical instance
        assertTrue(obj1.equals(obj2));
        assertTrue(obj2.equals(obj1));

        // Test inequality with a different instance
        assertFalse(obj1.equals(obj3));
        assertFalse(obj3.equals(obj1));

        // Test inequality with null
        assertFalse(obj1.equals(null));

        // Test inequality with an object of a different class
        assertFalse(obj1.equals("Not an instance of BookModel"));

        // Test inequality when ISBN is different
        BookModel objWithDifferentIsbn = new BookModel("9999999999999", "Test Book", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        assertFalse(obj1.equals(objWithDifferentIsbn));

        // Test inequality when Title is different
        BookModel objWithDifferentTitle = new BookModel("1234567890123", "Different Title", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 1);
        assertFalse(obj1.equals(objWithDifferentTitle));

        // Add similar tests for other properties (e.g., Category, Publisher, Price, Author, Quantity)
    }
}
