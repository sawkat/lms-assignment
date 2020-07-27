package com.assignment.lms.service;

import java.util.List;

import com.assignment.lms.dto.BookDTO;



/**
 * This is the service class of Book
 * 
 * @author 703177676
 *
 */
public interface BookService {

	/**
	 * Method to create a book
	 * 
	 * @param libraryId
	 * @param book
	 * @return BookDTO with generated Id 
	 */
	BookDTO addBookToLibrary(long libraryId, BookDTO bookDTO);
	
	/**
	 * Method to update a book
	 * 
	 * @param bookDTO
	 * @return BookDTO
	 */
	BookDTO updateBook(BookDTO bookDTO);
	
	/**
	 * Method to get a book by bookId
	 * 
	 * @param bookId
	 * @return
	 */
	BookDTO getBookById(Long bookId);
	
	/**
	 * Method to get all book by libraryId
	 * 
	 * @param libraryId
	 * @return
	 */
	List<BookDTO> getAllByLibrary(Long libraryId);
}
