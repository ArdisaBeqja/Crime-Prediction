package integrationTest;

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
import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTestI {

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
        ManagerTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testDeleteLibrarian() {
        // Scenario 1: Librarian to be deleted is present in the list
        ArrayList<LibrarianModel> librarians = new ArrayList<>();
        ManagerModel.librarians = librarians;

        LibrarianModel librarianToDelete = new LibrarianModel("TestLibrarian", "password", "Test Name", 500, "(123) 456-7890", "test@example.com");
        librarians.add(librarianToDelete);
        int initialSize = librarians.size();

        ManagerFuncController.deleteLibrarian(librarianToDelete);

        assertEquals(initialSize - 1, librarians.size());
        assertFalse(librarians.contains(librarianToDelete));

        // Scenario 2: Librarian to be deleted is not present in the list
        LibrarianModel nonExistentLibrarian = new LibrarianModel("NonExistentLibrarian", "password", "Non Existent", 500, "(987) 654-3210", "nonexistent@example.com");
        int sizeAfterNonExistent = librarians.size();

        FixLibrariansController.deleteLibrarian(nonExistentLibrarian);

        assertEquals(sizeAfterNonExistent, librarians.size()); // Size should remain unchanged

        // Scenario 3: Deleting from an empty list
        ArrayList<LibrarianModel> emptyList = new ArrayList<>();
        ManagerModel.librarians = emptyList; // Setting the librarians list to an empty list

        LibrarianModel emptyListLibrarian = new LibrarianModel("EmptyListLibrarian", "password", "Empty List", 500, "(111) 222-3333", "empty@example.com");
        int sizeBeforeEmptyList = librarians.size();

        FixLibrariansController.deleteLibrarian(emptyListLibrarian);

        assertEquals(sizeBeforeEmptyList, librarians.size()); // Size should remain unchanged
    }




    @Test
    public void testGetLowStock() {
        // Assuming createTestBooks method creates books with varying stock levels
        ArrayList<BookModel> books = createTestBooks();
        saveBooksToTemporaryFile(books); // Save test books to a temporary file

        // Retrieve books from the temporary file
        ArrayList<BookModel> retrievedBooks = bookController.getStockBooks();

        // Get low stock books using the Manager's method
        ArrayList<BookModel> lowStockBooks = managerFuncController.getLowStock();

        // Verify low stock books count and their stock levels
        int expectedLowStockCount = 0;
        for (BookModel book : retrievedBooks) {
            if (book.getStock() < 5) {
                expectedLowStockCount++;
                // Verify if the low stock book is in the list obtained from the method
                assertEquals(true, lowStockBooks.contains(book), "Book " + book.getTitle() + " is low in stock");
            }
        }

        // Compare the expected low stock count with the count from the method
        assertEquals(expectedLowStockCount, lowStockBooks.size(), "Correct count of low stock books");

    }


    private ArrayList<BookModel> createTestBooks() {
        ArrayList<BookModel> testBooks = new ArrayList<>();
        // Create multiple test books with varying stock values
        testBooks.add(new BookModel("1234567890123", "Test Book 1", "Category1", "Test Publisher", 20.00, 25.00, "Test Author", 3));
        testBooks.add(new BookModel("2345678901234", "Test Book 2", "Category2", "Another Publisher", 18.00, 22.00, "Another Author", 6));

        return testBooks;
    }


    @Test
    public void testUpdateLibrarians() {
        // Create a Manager instance
        ManagerModel manager = new ManagerModel();

        // Create some initial librarians
        ArrayList<LibrarianModel> initialLibrarians = new ArrayList<>();
        initialLibrarians.add(new LibrarianModel("username1", "password1", "Librarian1", 1000, "(123) 456-7890", "librarian1@example.com"));
        initialLibrarians.add(new LibrarianModel("username2", "password2", "Librarian2", 1200, "(987) 654-3210", "librarian2@example.com"));

        // Set the initial librarians for testing
        ManagerModel.librarians = initialLibrarians;

        // Create a Librarian with updated information
        LibrarianModel updatedLibrarian = new LibrarianModel("username1", "newPassword", "NewLibrarian", 1500, "(111) 222-3333", "newlibrarian@example.com");

        // Call the method to be tested
        //manager.
        ManagerFuncController.updateLibrarians(updatedLibrarian);

        // Verify the results
        assertEquals("Librarian1", ManagerModel.librarians.get(0).getName());
        assertEquals("newlibrarian@example.com", ManagerModel.librarians.get(0).getEmail());
        assertEquals("(111) 222-3333", ManagerModel.librarians.get(0).getPhone());
        assertEquals(1500, ManagerModel.librarians.get(0).getSalary(), 0.001); // 0.001 is the delta for double comparison

        // Ensure that other librarians are not affected
        assertEquals("Librarian2", ManagerModel.librarians.get(1).getName());
        assertEquals("librarian2@example.com", ManagerModel.librarians.get(1).getEmail());
        assertEquals("(987) 654-3210", ManagerModel.librarians.get(1).getPhone());
        assertEquals(1200, ManagerModel.librarians.get(1).getSalary(), 0.001);
    }



}