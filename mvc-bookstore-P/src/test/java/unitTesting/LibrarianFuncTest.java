package unitTesting;

import com.example.kthimi.Controller.FixLibrariansController;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianFuncTest {


    @Test
    void testGetBackLibrarian() {
        // Assuming you have instantiated some librarians
        LibrarianModel testLibrarian = new LibrarianModel("Alfie123", "SSU6umwt", "Alfie", 500, "(912) 921-2728", "aflie@librarian.com");
        FixLibrariansController.InstantiateLibrarians();

        // Get the librarian by username
        LibrarianModel resultLibrarian = FixLibrariansController.getBackLibrarian(testLibrarian);

        // Check if the result is not null and has the expected username
        assertNotNull(resultLibrarian);
        assertEquals(testLibrarian.getUsername(), resultLibrarian.getUsername());

    }

    @Test
    void testGetBackLibrarianNonExistent() {
        // Assuming you have instantiated some librarians
        FixLibrariansController.InstantiateLibrarians();

        // Create a Librarian that is not in the librarians list
        LibrarianModel nonExistentLibrarian = new LibrarianModel("NonExistent", "Password", "John", 500, "(123) 456-7890", "john@example.com");

        // Get the librarian by the non-existent librarian
        LibrarianModel resultLibrarian = FixLibrariansController.getBackLibrarian(nonExistentLibrarian);

        // Check if the result is null
        assertNull(resultLibrarian);
    }
}
