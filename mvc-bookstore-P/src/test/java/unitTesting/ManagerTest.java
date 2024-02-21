package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.FixLibrariansController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.ManagerFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import com.example.kthimi.Model.ManagerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";
    BookController bookController = new BookController();
    ManagerFuncController managerFuncController = new ManagerFuncController();

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
        ManagerTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
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

    public ArrayList<BookModel> saveBooksToTemporaryFile(ArrayList<BookModel> books) {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {
            objout.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }


    @Test
    public void testInstantiateLibrarians() {
        List<LibrarianModel> librarians = new ArrayList<>();
        LibrarianModel lib = new LibrarianModel("Alfie123", "SSU6umwt", "Alfie", 500, "(912) 921-2728", "aflie@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("@Leo", "TyFzN8we", "Leo", 500, "(912) 152-7493", "leo@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("Julie?!", "NDt8f6xL", "Julie", 500, "(912) 742-7832", "julie@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("MargiE", "vGtM6beC", "Margie", 500, "(912) 253-6939", "margie@librarian.com");
        librarians.add(lib);

        lib = new LibrarianModel("1", "1", "TestLibrarian", 500, "(912) 632-6353", "TestEmail@librarian.com");
        librarians.add(lib);
        ManagerModel manager = new ManagerModel("1", "2");
        manager.InstantiateLibrarians();

        assertNotNull(librarians);
        assertEquals(5, librarians.size());

        LibrarianModel firstLibrarian = librarians.get(0);
        assertEquals("Alfie123", firstLibrarian.getUsername());
    }

    @Test
    public void testLibrarianChecker() {

        FixLibrariansController.InstantiateLibrarians();

        assertTrue(FixLibrariansController.LibrarianChecker(new LibrarianModel("Alfie123", "SSU6umwt", null, 0, null, null)));
        assertTrue(FixLibrariansController.LibrarianChecker(new LibrarianModel("@Leo", "TyFzN8we", null, 0, null, null)));
        assertTrue(FixLibrariansController.LibrarianChecker(new LibrarianModel("Julie?!", "NDt8f6xL", null, 0, null, null)));
        assertTrue(FixLibrariansController.LibrarianChecker(new LibrarianModel("MargiE", "vGtM6beC", null, 0, null, null)));
        assertTrue(FixLibrariansController.LibrarianChecker(new LibrarianModel("1", "1", null, 0, null, null)));

        assertFalse(FixLibrariansController.LibrarianChecker(new LibrarianModel("InvalidUsername", "InvalidPassword", null, 0, null, null)));
    }

    @Test
    public void testGetAllCategories() {
        ArrayList<String> actualCategories = ManagerModel.getAllCategories();

        ArrayList<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("Modernist");
        expectedCategories.add("Fiction");
        expectedCategories.add("Novel");
        expectedCategories.add("Magic Realism");
        expectedCategories.add("Tragedy");
        expectedCategories.add("Adventure Fiction");
        expectedCategories.add("Historical Novel");
        expectedCategories.add("Epic");
        expectedCategories.add("War");
        expectedCategories.add("Autobiography and memoir");
        expectedCategories.add("Biography");
        expectedCategories.add("Non-fiction novel");
        expectedCategories.add("Self-help");
        expectedCategories.add("Short stories");
        expectedCategories.add("Horror");
        expectedCategories.add("Mystery");
        expectedCategories.add("Romance");
        expectedCategories.add("Thriller");

        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void testAddLibrarian() {
        FixLibrariansController.InstantiateLibrarians();
        LibrarianModel librarian = new LibrarianModel("NewLibrarian", "password", "New", 500, "(912) 987-6543", "new@email.com");

        ManagerModel.AddLibrarian(librarian);

        assertTrue(ManagerModel.getLibrarians().contains(librarian));
    }

    @Test
    public void testGetLibrarians() {
        ManagerModel.InstantiateLibrarians();

        ArrayList<LibrarianModel> result = ManagerModel.getLibrarians();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }


}
