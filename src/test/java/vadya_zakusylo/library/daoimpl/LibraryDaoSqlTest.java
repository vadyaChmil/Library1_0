package vadya_zakusylo.library.daoimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import vadya_zakusylo.library.daoimpl.LibraryDaoSql;
import vadya_zakusylo.library.model.Book;

public class LibraryDaoSqlTest {
	static LibraryDaoSql libraryDaoMySql;
	static Connection connection;

	@BeforeClass
	public static void oneTimeSetUp() {
		libraryDaoMySql = new LibraryDaoSql(connection);
	}

	@AfterClass
	public static void oneTimeTearDown() {
		libraryDaoMySql = null;
	}

	@Test
	public void testCreateCommandSelectBooks() {
		assertEquals("select id, autor, title, year_edition, pages from books;",
				libraryDaoMySql.createCommandSelectBooks());
	}

	@Test
	public void testCreateCommandInsertBook() {
		assertEquals("insert into books (id, autor, title, year_edition, pages) values (?,?,?,?,?);",
				libraryDaoMySql.createCommandInsertBook());
	}

	@Test
	public void shouldSetBookList_whenSomeBooks() throws SQLException {
		// given
		LibraryDaoSql libraryDaoMySql = mock(LibraryDaoSql.class);
		final List<Book> books = new ArrayList<Book>();
		// when
		books.add(new Book(0, null, null, 0, 0));
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				countCallMethodInsertBook(books.size());
				return null;
			}
		}).when(libraryDaoMySql).addBookList(books);
		// then
		libraryDaoMySql.addBookList(books);
		assertEquals(countCallMethodInsertBook, 1);

		// when
		books.add(new Book(0, null, null, 0, 0));
		books.add(new Book(0, null, null, 0, 0));
		// then
		libraryDaoMySql.addBookList(books);
		assertEquals(countCallMethodInsertBook, 3);
	}

	int countCallMethodInsertBook;

	protected void countCallMethodInsertBook(int size) {
		countCallMethodInsertBook = size;
	}
}
