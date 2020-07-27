package com.assignment.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.lms.dto.BookDTO;
import com.assignment.lms.service.BookService;


/**
 * This is a controller class for Book 
 * 
 * @author 703177676
 *
 */
@RestController
@RequestMapping("/book")
public class BookController {
	
	
	@Autowired
	private BookService bookService;
	
	/**
	 * API to create a book
	 * 
	 * @param libraryId
	 * @param book
	 * @return BookDTO with generated Id 
	 */
	@PostMapping("/create/{libraryId}")
	public BookDTO addBookToLibrary(@PathVariable("libraryId") long libraryId, @Valid @RequestBody BookDTO bookDTO) {
		return bookService.addBookToLibrary(libraryId, bookDTO);
	}
	
	
	/**
	 * API to update a book
	 * 
	 * @param bookDTO
	 * @return BookDTO
	 */
	@PostMapping("/update")
	public BookDTO updateBook(@Valid @RequestBody BookDTO bookDTO) {
		return bookService.updateBook(bookDTO);
	}
	
	
	/**
	 * API to get a book by bookId
	 * 
	 * @param bookId
	 * @return
	 */
	@GetMapping("/get/{bookId}")
	public BookDTO getBookById(@PathVariable("bookId") Long bookId) {
		return bookService.getBookById(bookId);
	}
	
	
	/**
	 * API to get all book by libraryId
	 * 
	 * @param libraryId
	 * @return
	 */
	@GetMapping("/getallbookbylibrary/{libraryId}")
	public List<BookDTO> getAllByLibrary(@PathVariable(value = "libraryId") Long libraryId) {
		return bookService.getAllByLibrary(libraryId);
	}	

}
