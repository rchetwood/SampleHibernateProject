package edu.sjsu.cs157a.DAOs;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.sjsu.cs157a.models.Book;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOTest {
	private static BookDAOImpl bookDAO;
	private static Connection conn;

	@BeforeClass
	public static void init() {
		try {
			// open connection to test db
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_library", "root", "");
			executeSQLScript(conn, "library.sql");

			bookDAO = new BookDAOImpl();
			bookDAO.setSessionFactory(new Configuration().configure("devHibernate.cfg.xml").buildSessionFactory());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDown() {
		try {
			executeSQLScript(conn, "deleteLibrary.sql");
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				fail();
			}
		}
	}
	
	@Test
	public void a_addBookTest() {
		Book lotr = new Book("Lord of the Rings", "J.R.R. Tolkien", 3);
		String title = bookDAO.addBook(lotr);
		assert title.equals("Lord of the Rings");
	}
	
	@Test
	public void b_updateBookTest() {
		bookDAO.updateBook("Bambi", 10);
		bookDAO.updateBook("Bambi", "J.K. Rowling");
		Book updatedBook = bookDAO.getBook("Bambi");
		assert updatedBook.getAuthor().equals("J.K. Rowling") && updatedBook.getCopies() == 10;
		bookDAO.updateBook("Bambi", "Adam Douglas", 42);
		updatedBook = bookDAO.getBook("Bambi");
		assert updatedBook.getAuthor().equals("Adam Douglas") && updatedBook.getCopies() == 42;
	}

	@Test
	public void c_getBookTest() {
		Book book = bookDAO.getBook("Lion King");
		assert book.getTitle().equals("Lion King");
	}

	@Test
	public void d_getAllBooks() {
		ArrayList<Book> result = null;
		result = (ArrayList<Book>) bookDAO.getAllBooks();
		assert result.size() == 14;
	}
	
	@Test 
	public void e_deleteBookTest() {
		bookDAO.removeBook("The Path's Secrets");
		Book deletedBook = bookDAO.getBook("The Path's Secrets");
		assert deletedBook == null;
	}

	public static void executeSQLScript(Connection conn, String fileName) throws Exception {
		final String basePath = new File("").getAbsolutePath();
		final String projectPath = "\\src\\main\\resources\\Library\\";
		final String aSQLScriptFilePath = basePath + projectPath + fileName;
		ScriptRunner sr = null;

		try {
			// Initialize object for ScripRunner
			sr = new ScriptRunner(conn);

			// Give the input file to Reader
			Reader reader = new BufferedReader(new FileReader(aSQLScriptFilePath));

			// Execute script
			sr.runScript(reader);
		} catch (Exception e) {
			throw e;
		}
	}

}
