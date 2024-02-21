package integrationTest;

import com.example.kthimi.Controller.AdminFuncController;
import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.FixLibrariansController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Model.AdministratorModel;
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

import static org.junit.jupiter.api.Assertions.*;

class AdminTestI {

    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";

    ArrayList<ManagerModel> magList = new ArrayList<>();


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
        AdminTestI.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testSalary(){

        LibrarianModel librarian1 = new LibrarianModel("librarian1", "password1", "John Doe", 50000.0, "123456789", "john@example.com");
        LibrarianModel librarian2 = new LibrarianModel("librarian2", "password2", "Jane Doe", 50000.0, "987654321", "jane@example.com");
        ManagerModel.getLibrarians().add(librarian1);
        ManagerModel.getLibrarians().add(librarian2);

        double salary=0;
        int size=ManagerModel.getLibrarians().size();
        System.out.println(size);

        for (int i=0;i<ManagerModel.getLibrarians().size();i++) {
            salary+=ManagerModel.getLibrarians().get(i).getSalary();
            System.out.println(salary);
        }


        ManagerModel mag =new ManagerModel("Alfie123","SSU6umwt","Alfie",500,"(912) 921-2728","aflie@librarian.com");
        AdministratorModel.getManagers().add(mag);
        for (int i=0;i<AdministratorModel.getManagers().size();i++) {
            salary += AdministratorModel.getManagers().get(i).getSalary();
        }
        System.out.println("total"+salary);


        AdministratorModel adm =new AdministratorModel("Alfie123","SSU6umwt","Alfie",500,"(912) 921-2728","aflie@librarian.com");
        AdministratorModel.getAdmins().add(adm);
        for (int i=0;i<AdministratorModel.getAdmins().size();i++) {
            salary += AdministratorModel.getAdmins().get(i).getSalary();
        }

        assertEquals(salary,AdminFuncController.getSalaries());
    }




}