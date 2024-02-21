package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.ManagerFuncController;
import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
import com.example.kthimi.Model.BookModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagerMTest {

    @Test
    public void testGetLowStock() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        ManagerFuncController managerFuncController = new ManagerFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 4));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 8));
        testBooks.add(new BookModel("ISBN3", "Book3", "Category3", "Supplier3", 10.0, 5.0, "Author3", 2));

        mockRepository.setStockBooks(testBooks);

        ArrayList<BookModel> result = managerFuncController.getLowStock();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetLowStock_NoLowStock() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        mockRepository.setStockBooks(new ArrayList<>( Arrays.asList(
                new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 10),
                new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 15),
                new BookModel("ISBN3", "Book3", "Category3", "Supplier3", 10.0, 5.0, "Author3", 20)
        )));

        BookController mockBookController = new BookController(mockRepository);

        ManagerFuncController managerFuncController = new ManagerFuncController(mockBookController);

        ArrayList<BookModel> result = managerFuncController.getLowStock();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetLowStock_SingleLowStock() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        mockRepository.setStockBooks(new ArrayList<>( Arrays.asList(
                new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 10),
                new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 15),
                new BookModel("ISBN3", "Book3", "Category3", "Supplier3", 10.0, 5.0, "Author3", 2)
        )));

        BookController mockBookController = new BookController(mockRepository);

        ManagerFuncController managerFuncController = new ManagerFuncController(mockBookController);

        ArrayList<BookModel> result = managerFuncController.getLowStock();

        assertEquals(1, result.size());
        assertTrue(result.contains(new BookModel("ISBN3", "Book3", "Category3", "Supplier3", 10.0, 5.0, "Author3", 2)));
    }

    @Test
    public void testGetLowStock_EmptyStock() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        ManagerFuncController managerFuncController = new ManagerFuncController(mockBookController);

        ArrayList<BookModel> result = managerFuncController.getLowStock();

        assertTrue(result.isEmpty());
    }





}
