package vadya_zakusylo.library.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vadya_zakusylo.library.daoimpl.BooksDaoBrowser;
import vadya_zakusylo.library.model.Book;
import vadya_zakusylo.library.model.exception.DataFormatException;
import vadya_zakusylo.library.model.exception.DataSizeException;
import vadya_zakusylo.library.model.exception.UploadException;

public class SelectBooksServlet extends HttpServletLibrary {

	/**
	 * Vadya Zakusylo
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BooksDaoBrowser booksDaoBrowser = new BooksDaoBrowser();
		try {
			List<Book> uploadedBookList = booksDaoBrowser.uploadBooks(request, response);
			request.getSession().setAttribute(UPLOAD_BOOKLIST, uploadedBookList);
			request.getRequestDispatcher(SELECT_BOOKS_PAGE).forward(request, response);
		} catch (DataFormatException | UploadException | DataSizeException e) {
			String errorMessage = e.getMessage();
			request.setAttribute(ERROR_MESSAGE, errorMessage);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}
}
