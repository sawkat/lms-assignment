package com.assignment.lms.service.Impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.lms.dto.BookDTO;
import com.assignment.lms.exception.ResourceNotFoundException;
import com.assignment.lms.model.Book;
import com.assignment.lms.model.Library;
import com.assignment.lms.repository.BookRepository;
import com.assignment.lms.repository.LibraryRepository;
import com.assignment.lms.service.BookService;
import com.assignment.lms.utils.ObjectMapper;



/**
 * This is the implementation class of BookService
 * 
 * @author 703177676
 *
 */
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	public BookDTO addBookToLibrary(long libraryId, BookDTO bookDTO){
		Optional<Library> optLibrary = libraryRepository.findById(libraryId);
		if (optLibrary.isPresent()){
			Book book = objectMapper.map(bookDTO, Book.class);
			book.setLibrary(optLibrary.get());
			return objectMapper.map(bookRepository.save(book), BookDTO.class);
		}else {
			throw new ResourceNotFoundException("Library not found with id " + libraryId);
		}
	}

	@Override
	public BookDTO updateBook(BookDTO bookDTO){
		Long bookId = bookDTO.getId();
		if(bookId == null)  throw new ResourceNotFoundException("Book id can not null.");
		Optional<Book> optBook = bookRepository.findById(bookId);
		if (optBook.isPresent()){
			Book updatedBook = objectMapper.map(bookDTO, Book.class);
			updatedBook.setLibrary(optBook.get().getLibrary());
			return objectMapper.map(bookRepository.save(updatedBook),BookDTO.class);
		}else {
			throw new ResourceNotFoundException("Book not found with id " + bookId);
		}
	}
	
	@Override
	public void deleteBook(long bookId) {
		Optional<Book> optBook = bookRepository.findById(bookId);
		if (optBook.isPresent()){
			bookRepository.deleteById(bookId);
		}else {
			throw new ResourceNotFoundException("Book not found with id " + bookId);
		}
	}
	
	@Override
	public BookDTO getBookById(Long bookId){
		Optional<Book> optBook = bookRepository.findById(bookId);
		if (optBook.isPresent()){
			return objectMapper.map(optBook.get(), BookDTO.class);
		}else {
			throw new ResourceNotFoundException("Book not found with id " + bookId);
		}
	}
	
	@Override
	public List<BookDTO> getAllByLibrary(Long libraryId) {
		return objectMapper.mapList(bookRepository.findAllByLibraryId(libraryId), BookDTO.class);
	}
	
}
