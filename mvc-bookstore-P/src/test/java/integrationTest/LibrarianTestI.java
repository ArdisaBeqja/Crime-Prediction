package integrationTest;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LibrarianTestI {

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
        LibrarianTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testCheckoutBooks() throws IOException, ClassNotFoundException {

        //LibrarianModel librarian = new LibrarianModel("username", "password", "name", 1000.00, "1234567890", "test@example.com");

        // Create a list of stock books
        ArrayList<BookModel> stockBooks = new ArrayList<>();
        stockBooks.add(createTestBook()); // Adding a test book to the stock

        // Set up the initial stock data
        saveBooksToTemporaryFile(stockBooks);

        // Choose a book to check out
        ArrayList<BookModel> chosenBooks = new ArrayList<>();
        chosenBooks.add(createTestBook()); // Choosing the test book to buy

        // Specify the quantity of the chosen book
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(2); // Buying two copies of the test book

        // Execute the checkout process
        librarianFuncController.checkOutBooks(chosenBooks, quantities);
        System.out.println("te testi");
        //System.out.println(stockBooks.toString());

        // Directly query the Librarian instance for the current stockBooks
        ArrayList<BookModel> updatedStock = bookController.getStockBooks();

        // Retrieve the updated book after checkout
        BookModel updatedBook = null;
            for (BookModel book : updatedStock) {
                System.out.println(createTestBook().getTitle() + "hhahhahhahahaha" + book.getTitle());
                if (book.getISBN().equals(createTestBook().getISBN())) {
                updatedBook = book;
                break;
            }
        }

        // Print the book with the reduced quantity
        if (updatedBook != null) {
            System.out.println("Updated Book: " + updatedBook.toString());
        } else {
            System.out.println("Book not found.");
        }

        // Assert the stock has reduced after checkout
        assertNotNull(updatedBook);
        assertEquals(8, updatedBook.getStock(), "Stock should decrease after checkout");

    }


}