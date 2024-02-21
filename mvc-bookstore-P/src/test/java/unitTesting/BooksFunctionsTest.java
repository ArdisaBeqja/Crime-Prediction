package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Controller.StatisticsFuncController;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import integrationTest.StatsOtherTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BooksFunctionsTest {

    StatisticsFuncController statisticsFuncController = new StatisticsFuncController();
    BookController bookController = new BookController();
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
        BooksFunctionsTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
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
    public void testShowStock() {
        BookModel book = new BookModel("1234567890", "Test Book", "Test Category", "Test Publisher", 10.0, 15.0, "Test Author", 20);
        ArrayList<BookModel> books = new ArrayList<>();
        books.add(book);
        saveBooksToTemporaryFile(books);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bookController.showStock();
        System.setOut(System.out);
        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains(book.toString()));
    }




    @Test
    void testRemoveDuplicates() {
        ArrayList<String> originalList = new ArrayList<>();
        originalList.add("Science");
        originalList.add("Fiction");
        originalList.add("Science");
        originalList.add("Comedy");
        originalList.add("Fiction");
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("Science");
        expectedList.add("Fiction");
        expectedList.add("Comedy");
        ArrayList<String> result = bookController.removeDuplicates(originalList);
        assertEquals(expectedList, result);
    }

    @Test
    void testRemoveDuplicatesEmptyList() {
        ArrayList<String> originalList = new ArrayList<>();
        ArrayList<String> expectedList = new ArrayList<>();
        ArrayList<String> result = bookController.removeDuplicates(originalList);
        assertEquals(expectedList, result, "Removing duplicates from an empty list should result in an empty list");
    }

    @Test
    void testRemoveDuplicatesNoDuplicates() {
        ArrayList<String> originalList = new ArrayList<>();
        originalList.add("Science");
        originalList.add("Fiction");
        originalList.add("Comedy");
        ArrayList<String> expectedList = new ArrayList<>(originalList);
        ArrayList<String> result = bookController.removeDuplicates(originalList);
        assertEquals(expectedList, result, "List with no duplicates should remain unchanged");
    }


    @Test
    void testRemoveDuplicatesWithNulls() {
        ArrayList<String> originalList = new ArrayList<>();
        originalList.add("Science");
        originalList.add(null);
        originalList.add("Fiction");
        originalList.add(null);

        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("Science");
        expectedList.add(null);
        expectedList.add("Fiction");

        ArrayList<String> result = bookController.removeDuplicates(originalList);
        assertEquals(expectedList, result, "Duplicates including nulls should be removed");
    }


    @Test
    public void testAddSaleWhenDatesAreNull() {
        BookModel book = new BookModel();
        assertNull(book.getDates());
        Date saleDate = new Date();
        book.addSale(saleDate, 5);
        assertNotNull(book.getDates());
        assertEquals(1, book.getDates().size());
        assertEquals(saleDate, book.getDates().get(0));
    }
    @Test
    void testAddSaleWhenDatesAreNullSingleSale() {
        BookModel book = new BookModel();
        assertNull(book.getDates());

        Date saleDate = new Date();
        book.addSale(saleDate, 5);

        assertNotNull(book.getDates());
        assertEquals(1, book.getDates().size());
        assertEquals(saleDate, book.getDates().get(0));
    }

    @Test
    void testAddSaleWhenDatesAreNullMultipleSales() {
        BookModel book = new BookModel();
        assertNull(book.getDates());

        Date saleDate1 = new Date();
        Date saleDate2 = new Date();
        book.addSale(saleDate1, 3);
        book.addSale(saleDate2, 7);

        assertNotNull(book.getDates());
        assertEquals(2, book.getDates().size());
        assertEquals(saleDate1, book.getDates().get(0));
        assertEquals(saleDate2, book.getDates().get(1));
    }

    @Test
    void testAddSaleWhenDatesExist() {
        BookModel book = new BookModel();
        Date existingDate1 = new Date();
        Date existingDate2 = new Date();
        book.setDates(new ArrayList<>(Arrays.asList(existingDate1, existingDate2)));

        assertNotNull(book.getDates());
        assertEquals(2, book.getDates().size());

        Date saleDate = new Date();
        book.addSale(saleDate, 2);

        assertEquals(3, book.getDates().size());
        assertEquals(saleDate, book.getDates().get(2));
    }


    @Test
    public void testAddPurchase() {
        BookModel book = createTestBook();
        Date purchaseDate = new Date();

        book.addPurchase(purchaseDate);

        assertNotNull(book.getPurchasedDates());
        assertEquals(1, book.getPurchasedDates().size());
        assertEquals(purchaseDate, book.getPurchasedDates().get(0));

        book.getQuantitiesPurchased();
        assertEquals(1, book.getQuantitiesPurchased());
        assertEquals(Integer.valueOf(1), book.getQuantitiesPurchased());

        ArrayList<Date> a=book.getPurchasedDates();
        if(a==null){
            book.addPurchasedDate(new Date());
        }
    }

    @Test
    void testAddPurchaseSinglePurchase() {
        BookModel book = createTestBook();
        Date purchaseDate = new Date();

        book.addPurchase(purchaseDate);

        assertNotNull(book.getPurchasedDates());
        assertEquals(1, book.getPurchasedDates().size());
        assertEquals(purchaseDate, book.getPurchasedDates().get(0));

        book.getQuantitiesPurchased();
        assertEquals(1, book.getQuantitiesPurchased());
        assertEquals(Integer.valueOf(1), book.getQuantitiesPurchased());
    }

    @Test
    void testAddPurchaseMultiplePurchases() {
        BookModel book = createTestBook();
        Date purchaseDate1 = new Date();
        Date purchaseDate2 = new Date();

        book.addPurchase(purchaseDate1);
        book.addPurchase(purchaseDate2);

        assertNotNull(book.getPurchasedDates());
        assertEquals(2, book.getPurchasedDates().size());
        assertEquals(purchaseDate1, book.getPurchasedDates().get(0));
        assertEquals(purchaseDate2, book.getPurchasedDates().get(1));

        book.getQuantitiesPurchased();
        assertEquals(2, book.getQuantitiesPurchased());
        assertEquals(Integer.valueOf(2), book.getQuantitiesPurchased());
    }

    @Test
    void testAddPurchaseWithExistingPurchases() {
        BookModel book = createTestBook();
        Date existingPurchase1 = new Date();
        Date existingPurchase2 = new Date();
        book.setPurchasedDates(new ArrayList<>(Arrays.asList(existingPurchase1, existingPurchase2)));

        assertNotNull(book.getPurchasedDates());
        assertEquals(2, book.getPurchasedDates().size());

        Date newPurchaseDate = new Date();
        book.addPurchase(newPurchaseDate);

        assertEquals(3, book.getPurchasedDates().size());
        assertEquals(newPurchaseDate, book.getPurchasedDates().get(2));

        book.getQuantitiesPurchased();
        assertEquals(1, book.getQuantitiesPurchased());
        assertEquals(Integer.valueOf(1), book.getQuantitiesPurchased());
    }

    @Test
    void testAddPurchaseWithNullDates() {
        BookModel book = createTestBook();
        book.setPurchasedDates(null);

        assertNull(book.getPurchasedDates());

        Date purchaseDate = new Date();
        book.addPurchase(purchaseDate);

        assertNotNull(book.getPurchasedDates());
        assertEquals(1, book.getPurchasedDates().size());
        assertEquals(purchaseDate, book.getPurchasedDates().get(0));

        book.getQuantitiesPurchased();
        assertEquals(1, book.getQuantitiesPurchased());
        assertEquals(Integer.valueOf(1), book.getQuantitiesPurchased());
    }


    /////////////////////////////////////////


    @Test
    public void testInitializeListsIfNull() {
        BookModel book = createTestBook();

        if (book.getPurchasedDates() == null) {
            book.addPurchasedDate(new Date());
        }
        if (book.getQuantitiesPurchased() == 0) {
            book.addQuantity(0);
        }

        if(book.getPurchasedDates()==null){
            ArrayList<Date>dates=new ArrayList<>();

        }

        assertNotNull(book.getPurchasedDates());
        assertEquals(0, book.getPurchasedDates().size());

        assertNotNull(book.getQuantitiesPurchased());
        assertEquals(0, book.getQuantitiesPurchased());

    }


    @Test
    public void testEquals() {
        BookModel instance1 = createTestBook();
        BookModel instance2 = createTestBook();
        BookModel instance3=new BookModel(null);

        assertTrue(instance1.equals(instance1));

        assertTrue(instance1.equals(instance2));
        assertTrue(instance2.equals(instance1));

        assertFalse(instance3.equals(null));


    }




}
