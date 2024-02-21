package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsMTest {

    @Test
    public void testGetBooksSoldDay() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        String result = statisticsFuncController.getBooksSoldDay();

        assertNotNull(result);
        assertTrue(result.contains("For Books Sold Today We Have:")); // Check if the output contains the expected string
    }



    @Test
    public void testGetBooksSoldMonth() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        String result = statisticsFuncController.getBooksSoldMonth();

        assertNotNull(result);
        assertTrue(result.contains("For Books Sold In A Month We Have")); // Check if the output contains the expected string
    }


    @Test
    public void testGetBooksSoldYear() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        String result = statisticsFuncController.getBooksSoldYear();

        assertNotNull(result);
        assertTrue(result.contains("For Books Sold In A Year We Have"));
    }


    @Test
    public void testGetBooksBoughtDay() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        String result = statisticsFuncController.getBooksBoughtDay();

        assertNotNull(result);
        assertTrue(result.contains("For Books Bought Today We Have"));
    }



    @Test
    public void testGetBooksBoughtMonth() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        String result = statisticsFuncController.getBooksBoughtMonth();

        assertNotNull(result);
        assertTrue(result.contains("For Books Bought In A Month We Have"));
    }

    @Test
    public void testGetBooksBoughtYear() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        String result = statisticsFuncController.getBooksBoughtYear();

        assertNotNull(result);
        assertTrue(result.contains("For Books Bought In A Year We Have"));
    }


    @Test
    public void testGetIntBooksSoldDay() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        int result = statisticsFuncController.getIntBooksSoldDay();

        assertEquals(0, result);
    }

    @Test
    public void testGetIntBooksSoldMonth() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        int result = statisticsFuncController.getIntBooksSoldMonth();

        assertEquals(0, result);
    }

    @Test
    public void testGetIntBooksSoldYear() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        int result = statisticsFuncController.getIntBooksSoldYear();

        assertEquals(0, result);
    }

    @Test
    public void testGetIncomeDay() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        double result = statisticsFuncController.getIncomeDay();

        double expectedIncome = 0;
        for (BookModel book : testBooks) {
            expectedIncome += book.getTotalBooksSoldDay() * book.getSellingPrice();
        }

        assertEquals(expectedIncome, result, 0.01);
    }

    @Test
    public void testGetIncomeMonth() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        double result = statisticsFuncController.getIncomeMonth();

        double expectedIncome = 0;
        for (BookModel book : testBooks) {
            expectedIncome += book.getTotalBooksSoldMonth() * book.getSellingPrice();
        }

        assertEquals(expectedIncome, result, 0.01);
    }


    @Test
    public void testGetIncomeYear() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        double result = statisticsFuncController.getIncomeYear();

        double expectedIncome = 0;
        for (BookModel book : testBooks) {
            expectedIncome += book.getTotalBooksSoldYear() * book.getSellingPrice();
        }

        assertEquals(expectedIncome, result, 0.01);
    }


    @Test
    public void testGetTotalBoughtBooksDay() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        int result = statisticsFuncController.getTotalBoughtBooksDay();

        int expectedTotal = 0;
        for (BookModel book : testBooks) {
            expectedTotal += book.getTotalBooksBoughtDay();
        }

        assertEquals(expectedTotal, result);
    }

    @Test
    public void testGetTotalBoughtBooksMonth() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        int result = statisticsFuncController.getTotalBoughtBooksMonth();

        int expectedTotal = 0;
        for (BookModel book : testBooks) {
            expectedTotal += book.getTotalBooksBoughtMonth();
        }

        assertEquals(expectedTotal, result);
    }

    @Test
    public void testGetTotalBoughtBooksYear() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        int result = statisticsFuncController.getTotalBoughtBooksYear();

        int expectedTotal = 0;
        for (BookModel book : testBooks) {
            expectedTotal += book.getTotalBooksBoughtYear();
        }

        assertEquals(expectedTotal, result);
    }


    @Test
    public void testGetCostDay() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        double result = statisticsFuncController.getCostDay();

        double expectedCost = 0;
        for (BookModel book : testBooks) {
            expectedCost += book.getTotalBooksBoughtDay() * book.getOriginalPrice();
        }

        assertEquals(expectedCost, result, 0.01);
    }


    @Test
    public void testGetCostMonth() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        double result = statisticsFuncController.getCostMonth();

        double expectedCost = 0;
        for (BookModel book : testBooks) {
            expectedCost += book.getTotalBooksBoughtMonth() * book.getOriginalPrice();
        }

        assertEquals(expectedCost, result, 0.01);
    }

    @Test
    public void testGetCostYear() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        BookController mockBookController = new BookController(mockRepository);

        StatisticsFuncController statisticsFuncController = new StatisticsFuncController(mockBookController);

        ArrayList<BookModel> testBooks = new ArrayList<>();
        testBooks.add(new BookModel("ISBN1", "Book1", "Category1", "Supplier1", 20.0, 15.0, "Author1", 50));
        testBooks.add(new BookModel("ISBN2", "Book2", "Category2", "Supplier2", 30.0, 25.0, "Author2", 30));

        mockRepository.setStockBooks(testBooks);

        double result = statisticsFuncController.getCostYear();

        double expectedCost = 0;
        for (BookModel book : testBooks) {
            expectedCost += book.getTotalBooksBoughtYear() * book.getOriginalPrice();
        }

        assertEquals(expectedCost, result, 0.01);
    }


}
