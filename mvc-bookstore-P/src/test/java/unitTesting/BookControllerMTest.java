package unitTesting;

import com.example.kthimi.Controller.BookController;
import com.example.kthimi.Controller.Mockers.MockStockBookRepository;
import com.example.kthimi.Model.BookModel;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BookControllerMTest {


    @Test
    public void testAddBookToStock() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("testBooks", ".bin");

        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        BookModel bookToAdd = new BookModel("newISBN", "saLot", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20);

        bookController.addBookToStock(bookToAdd);

        // getting the updated books from the temp file
        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        assertEquals(1, updatedBooks.size()); // only one book should be added
        assertEquals(bookToAdd, updatedBooks.get(0)); // & the added book should match the bookToAdd


        tempFile.delete();
    }

    @Test
    void testAddBookToStockEmptyStock() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("testBooks", ".bin");
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        BookModel bookToAdd = new BookModel("newISBN", "saLot", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20);

        bookController.addBookToStock(bookToAdd);

        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        assertEquals(1, updatedBooks.size());
        assertEquals(bookToAdd, updatedBooks.get(0));

        tempFile.delete();
    }

    @Test
    void testAddBookToStockExistingStock() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("testBooks", ".bin");
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        BookModel existingBook = new BookModel("existingISBN", "Existing Book", "Existing Category", "Existing Supplier", 12.0, 8.0, "Existing Author", 15);
        ArrayList<BookModel> testData = new ArrayList<>(Arrays.asList(existingBook));
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        BookModel bookToAdd = new BookModel("newISBN", "saLot", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20);

        bookController.addBookToStock(bookToAdd);

        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        assertEquals(2, updatedBooks.size());
        assertTrue(updatedBooks.contains(existingBook));
        assertTrue(updatedBooks.contains(bookToAdd));

        tempFile.delete();
    }






    @Test
    public void testUpdateBooks() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("testBooks", ".bin");

        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        ArrayList<BookModel> existingBooks = new ArrayList<>();
        existingBooks.add(new BookModel("existingISBN", "Title", "Category", "Supplier", 20.0, 15.0, "Author", 50));

        try (ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream(tempFile))) {
            objout.writeObject(existingBooks);
        }

        ArrayList<BookModel> newBooks = new ArrayList<>();
        newBooks.add(new BookModel("existingISBN", "Title", "Category", "Supplier", 20.0, 15.0, "Author", 50));
        newBooks.add(new BookModel("newISBN", "New Title", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));

        bookController.updateBooks(newBooks);

        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        assertEquals(2, updatedBooks.size()); // 2 books should be updated


        tempFile.delete();
    }


    @Test
    void testUpdateBooksEmptyStock() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("testBooks", ".bin");

        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        ArrayList<BookModel> newBooks = new ArrayList<>();
        newBooks.add(new BookModel("newISBN", "New Title", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));

        bookController.updateBooks(newBooks);

        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        assertEquals(1, updatedBooks.size());
        assertEquals(newBooks.get(0), updatedBooks.get(0));

        tempFile.delete();
    }

    @Test
    void testUpdateBooksWithExistingStock() throws IOException, ClassNotFoundException {
        File tempFile = File.createTempFile("testBooks", ".bin");

        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> existingBooks = new ArrayList<>();
        existingBooks.add(new BookModel("existingISBN", "Title", "Category", "Supplier", 20.0, 15.0, "Author", 50));
        mockRepository.setStockBooks(existingBooks);

        BookController bookController = new BookController(mockRepository);
        BookController.STOCK_FILE_PATH = tempFile.getAbsolutePath();

        ArrayList<BookModel> newBooks = new ArrayList<>();
        newBooks.add(new BookModel("existingISBN", "Updated Title", "Updated Category", "Updated Supplier", 25.0, 12.0, "Updated Author", 30));
        newBooks.add(new BookModel("newISBN", "New Title", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));

        bookController.updateBooks(newBooks);

        ArrayList<BookModel> updatedBooks;
        try (ObjectInputStream objis = new ObjectInputStream(new FileInputStream(tempFile))) {
            Object obj = objis.readObject();
            if (obj instanceof ArrayList) {
                updatedBooks = (ArrayList<BookModel>) obj;
            } else {
                updatedBooks = new ArrayList<>();
            }
        }

        assertEquals(2, updatedBooks.size());
        assertTrue(updatedBooks.contains(newBooks.get(1)));

        tempFile.delete();
    }





    @Test
    public void testGetISBNName() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add(new BookModel("1", "mos1", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));
        testData.add(new BookModel("2", "mos2", "New Category", "New Supplier", 15.0, 10.0, "New Author", 20));
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);

        ArrayList<String> result = bookController.getISBNName();

        ArrayList<String> expected = new ArrayList<>();
        expected.add("1 - mos1");
        expected.add("2 - mos2");

        assertEquals(expected, result);
    }


    @Test
    public void testGetCategories() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();

        testData.add(new BookModel("ISBN1", "Book 1", "Category A", "Supplier 1", 20.0, 15.0, "Author 1", 50));
        testData.add(new BookModel("ISBN2", "Book 2", "Category B", "Supplier 2", 20.0, 15.0, "Author 2", 50));
        testData.add(new BookModel("ISBN3", "Book 3", "Category A", "Supplier 3", 20.0, 15.0, "Author 3", 50));
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);

        ArrayList<String> categories = bookController.getCategories();

        assertEquals(2, categories.size());

    }


    @Test
    public void testGetBookFromCategory() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();
        ArrayList<BookModel> testData = new ArrayList<>();

        testData.add(new BookModel("ISBN1", "Book 1", "Category A", "Supplier 1", 20.0, 15.0, "Author 1", 50));
        testData.add(new BookModel("ISBN2", "Book 2", "Category B", "Supplier 2", 20.0, 15.0, "Author 2", 50));
        testData.add(new BookModel("ISBN3", "Book 3", "Category A", "Supplier 3", 20.0, 15.0, "Author 3", 50));
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);

        ArrayList<BookModel> booksInCategoryA = bookController.getBookFromCategory("Category A");

        assertEquals(2, booksInCategoryA.size()); // two books in Category A

    }



    @Test
    public void testIsPartOfBooks() {
        MockStockBookRepository mockRepository = new MockStockBookRepository();

        ArrayList<BookModel> testData = new ArrayList<>();
        testData.add( new BookModel("ISBN123", "Test Book", "Test Category", "Test Supplier", 20.0, 15.0, "Test Author", 50));
        mockRepository.setStockBooks(testData);

        BookController bookController = new BookController(mockRepository);


        boolean existingISBNResult = bookController.isPartOfBooks("ISBN123");

        boolean nonExistingISBNResult = bookController.isPartOfBooks("NonExistingISBN");

        assertTrue(existingISBNResult);
        assertFalse(nonExistingISBNResult);
    }



}