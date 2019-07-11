package edu.sjsu.cs157a.DAOs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.sjsu.cs157a.models.Book;

public class BookDAOImpl implements BookDAO {

	private static final Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public String addBook(Book newBook) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		String title = null;

		try {
			tx = session.beginTransaction();
			title = (String) session.save(newBook);
			logger.info(newBook + " has been ADDED to the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}

		return title;
	}

	public Book getBook(String aTitle) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Book book = null;

		try {
			tx = session.beginTransaction();
			book = (Book) session.get(Book.class, aTitle);
			logger.info(book + " has been RETRIEVED from the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}

		return book;
	}

	public List<Book> getAllBooks() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Book> books = new ArrayList<Book>();

		try {
			tx = session.beginTransaction();
			List result = session.createQuery("FROM Book").list();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				Book aBook = (Book) iterator.next();
				books.add(aBook);
			}
			logger.info(books + " has been RETRIEVED from the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}

		return books;
	}

	public void updateBook(String aTitle, String updatedAuthor) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Book book = (Book) session.get(Book.class, aTitle);	
			book.setAuthor(updatedAuthor);
			session.update(book);
			logger.info(book + " has been UPDATED from the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public void updateBook(String aTitle, int updatedCopies) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Book book = (Book) session.get(Book.class, aTitle);	
			book.setCopies(updatedCopies);
			session.update(book);
			logger.info(book + " has been UPDATED from the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
	}
	
	public void updateBook(String aTitle, String updatedAuthor, int updatedCopies) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Book book = (Book) session.get(Book.class, aTitle);	
			book.setAuthor(updatedAuthor);
			book.setCopies(updatedCopies);
			session.update(book);
			logger.info(book + " has been UPDATED from the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
	}

	public void removeBook(String aTitle) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Book book = (Book) session.get(Book.class, aTitle);	
			session.delete(book);
			logger.info(aTitle + " has been DELETED from the database.");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error(e.getMessage());
		} finally {
			session.close();
		}
	}

}
