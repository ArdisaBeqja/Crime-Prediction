package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BillFuncTest {

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
    public void testRemoveDuplicatesSoldBooks() {

        BookModel book1 = new BookModel("ISBN1", "Title1","Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10);
        BookModel book2 = new BookModel("ISBN2", "Title2", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Add details for book2
        BookModel book3 = new BookModel("ISBN1", "Title3", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Another book with the same ISBN as book1
        BookModel book4 = new BookModel("ISBN4", "Title4", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 10); // Unique book

        ArrayList<BookModel> books = new ArrayList<>(Arrays.asList(book1, book2, book3, book4));
        ArrayList<Integer> quantities = new ArrayList<>(Arrays.asList(1, 2, 3, 4));

        LibrarianFuncController.removeDuplicatesSoldBooks(books, quantities);

        for(BookModel book : books){
            System.out.println(book.toString());
        }
        assertEquals(3, books.size());


        System.out.println(quantities.get(0));
        assertEquals(4, quantities.get(0)); // book 1 and 3 quantities are merged ( 1 + 3 = 4)

        assertEquals(2, quantities.get(1));
    }




}
