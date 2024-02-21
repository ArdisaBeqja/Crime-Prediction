package unitTesting;


import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.LibrarianFuncController;
import com.example.kthimi.Controller.Mockers.FileBasedStockBookRepository;
import com.example.kthimi.Model.BookModel;
import com.example.kthimi.Model.LibrarianModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    //BookController bookController = new BookController();

    private static String TEMP_STOCK_FILE_PATH = "tempStockFile.bin";
    private static final String TEST_ISBN = "1234567890123";
    private static final String TEST_TITLE = "Test Book";

    public static void setStockFilePath(String newPath) {
        try {
            changeField(BookController.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianModel.class, "STOCK_FILE_PATH", newPath);
            changeField(LibrarianFuncController.class, "STOCK_FILE_PATH", newPath);
            changeField(FileBasedStockBookRepository.class, "STOCK_FILE_PATH", newPath);
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
        BookTest.setStockFilePath(TEMP_STOCK_FILE_PATH);
        //temp file created
        createTemporaryFile();
    }

    private void createTemporaryFile() {
        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(TEMP_STOCK_FILE_PATH))) {

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
    public void testGetPurchasedAmount() {
        BookModel book = new BookModel();

        book.addSale(new Date(), 5);
        book.addSale(new Date(), 3);

        int totalPurchased = book.getPurchasedAmount();

        int expectedTotal = 5 + 3;

        assertEquals(expectedTotal, totalPurchased);
    }


    @Test
    public void testGetQuantitiesPurchased() {
        BookModel book = new BookModel();

        book.addPurchase(new Date());
        book.addPurchase(new Date());

        int totalQuantitiesPurchased = book.getQuantitiesPurchased();

        int expectedTotal = 1 + 1;

        assertEquals(expectedTotal, totalQuantitiesPurchased);
    }



    @Test
    public void testGetISBN() {
        BookModel testBook = createTestBook();
        String isbn = "1234567890";
        testBook.setISBN(isbn);

        String retrievedISBN = testBook.getISBN();
        assertEquals(isbn, retrievedISBN);
    }

    @Test
    public void testGetCategory() {
        BookModel testBook = createTestBook();
        String category = "Fantasy";
        testBook.setCategory(category);

        String retrievedCategory = testBook.getCategory();
        assertEquals(category, retrievedCategory);
    }

    @Test
    public void testGetSupplier() {
        BookModel testBook = createTestBook();
        String supplier = "Supplier X";
        testBook.setSupplier(supplier);

        String retrievedSupplier = testBook.getSupplier();
        assertEquals(supplier, retrievedSupplier);
    }

    @Test
    public void testGetSellingPrice() {
        BookModel testBook = createTestBook();

        double expectedSellingPrice = 25.99;
        testBook.setSellingPrice(expectedSellingPrice);

        double retrievedSellingPrice = testBook.getSellingPrice();
        assertEquals(expectedSellingPrice, retrievedSellingPrice, 0.001);
    }

    @Test
    public void testGetOriginalPrice() {
        BookModel testBook = createTestBook();

        double expectedOriginalPrice = 19.99;
        testBook.setOriginalPrice(expectedOriginalPrice);

        double retrievedOriginalPrice = testBook.getOriginalPrice();
        assertEquals(expectedOriginalPrice, retrievedOriginalPrice, 0.001);
    }

    @Test
    public void testGetStock() {
        BookModel testBook = createTestBook();

        int expectedStock = 10;
        testBook.setStock(expectedStock);

        int retrievedStock = testBook.getStock();
        assertEquals(expectedStock, retrievedStock);
    }


    @Test
    public void testRemoveStock() {
        BookModel book = createTestBook();

        int initialStock = book.getStock();
        int quantityToRemove = 3;
        book.RemoveStock(quantityToRemove);

        int expectedStock = initialStock - quantityToRemove;
        int actualStock = book.getStock();

        assertEquals(expectedStock, actualStock, "Stock count should reduce by the specified quantity");
    }


    @Test
    public void testAddDate() {
        BookModel book = createTestBook();

        ArrayList<Date> initialDates = new ArrayList<>(book.getDates());
        Date newDate = new Date();
        book.addDate(newDate);

        ArrayList<Date> updatedDates = book.getDates();
        boolean dateAdded = updatedDates.contains(newDate);

        assertEquals(initialDates.size() + 1, updatedDates.size(), "A new date should be added");
        assertEquals(true, dateAdded, "The newly added date should exist in the dates list");
    }




    @Test
    public void testGetSoldDatesQuantitiesDay() {
        BookModel book = createTestBook();
        Date today = new Date();
        book.addDate(today);
        book.addQuantity(5);

        String result = book.getSoldDatesQuantitiesDay();

        String expected = "For \"" + book.getTitle() + "\" We have sold in a day:\n5 at " + today + "\n";

        assertEquals(expected, result);
    }





}
