package edu.sjsu.cs157a.DAOs;

import java.util.List;

import edu.sjsu.cs157a.models.Book;

public interface BookDAO {
	/**
	 * Create new book in the database
	 * 
	 * @param newBook
	 * @return the title of the newly inserted book
	 */
	public String addBook(Book newBook);
	
	/**
	 * Retrieve a book with a given title 
	 * 
	 * @param aTitle is a unique title in the database
	 * @return the book with the given title
	 */
	public Book getBook(String aTitle);
	
	/**
	 * Retrieve all books from the database
	 * 
	 * @return all books from database
	 */
	public List<Book> getAllBooks();
	
	/**
	 * Updates a book with a given title
	 * 
	 * @param updatedBook book with updated details
	 */
	public void updateBook(String aTitle, String updatedAuthor);
	
	/**
	 * Updates a book with a given title
	 * 
	 * @param updatedBook book with updated details
	 */
	public void updateBook(String aTitle, int updatedCopies);
	
	/**
	 * Updates a book with a given title
	 * 
	 * @param updatedBook book with updated details
	 */
	public void updateBook(String aTitle, String updatedAuthor, int updatedCopies);
	
	/**
	 * Deletes book with a give title from the database
	 * 
	 * @param title of the book to delete
	 */
	public void removeBook(String title);
}
 