package integrationTest;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unitTesting.BillFuncTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BillTest {
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
        BillFuncTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testAddBookToStock() {
        BookModel testBook = createTestBook();

        bookController.addBookToStock(testBook);

        ArrayList<BookModel> stockBooks = bookController.getStockBooks();

        assertTrue(stockBooks.contains(testBook));

        File testFile = new File(TEMP_STOCK_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddBookToStockMultipleBooks() {
        ArrayList<BookModel> initialStock = bookController.getStockBooks();
        BookModel book1 = createTestBook();
        BookModel book2 = createTestBook();

        bookController.addBookToStock(book1);
        bookController.addBookToStock(book2);

        ArrayList<BookModel> updatedStock = bookController.getStockBooks();
        assertTrue(updatedStock.contains(book1));
        assertTrue(updatedStock.contains(book2));
        assertEquals(initialStock.size() + 2, updatedStock.size());
    }

    @Test
    void testAddBookToStockNoBooksAdded() {
        ArrayList<BookModel> initialStock = bookController.getStockBooks();
        int initialStockSize = initialStock.size();

        ArrayList<BookModel> updatedStock = bookController.getStockBooks();
        assertEquals(initialStockSize, updatedStock.size());
        assertEquals(initialStock, updatedStock);
    }

}
