package com.assignment.lms.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${lms.msg.library_not_found_with_id}")
	private String msgLibraryNotFound;
	
	@Value("${lms.msg.book_not_found_with_id}")
	private String msgBookNotFound;
	
	@Value("${lms.msg.book_id_can_not_null}")
	private String msgBookCanNotNull;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	@Transactional
	public BookDTO addBookToLibrary(long libraryId, BookDTO bookDTO){
		Library library = libraryRepository.findById(libraryId).orElseThrow(() -> new ResourceNotFoundException(msgLibraryNotFound + libraryId));
		Book book = objectMapper.map(bookDTO, Book.class);
		book.setLibrary(library);
		return objectMapper.map(bookRepository.save(book), BookDTO.class);	
	}

	@Override
	@Transactional
	public BookDTO updateBook(BookDTO bookDTO){
		Long bookId = bookDTO.getId();
		if(bookId == null)  throw new ResourceNotFoundException(msgBookCanNotNull);
		Book bookInDB = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(msgBookNotFound + bookId));
		Book updatedBook = objectMapper.map(bookDTO, Book.class);
		updatedBook.setLibrary(bookInDB.getLibrary());
		return objectMapper.map(bookRepository.save(updatedBook),BookDTO.class);	
	}
	
	@Override
	@Transactional
	public void deleteBook(long bookId) {
		bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(msgBookNotFound + bookId));
		bookRepository.deleteById(bookId);
	}
	
	@Override
	public BookDTO getBookById(Long bookId){
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException(msgBookNotFound + bookId));
		return objectMapper.map(book, BookDTO.class);
	}
	
	@Override
	public List<BookDTO> getAllByLibrary(Long libraryId) {
		return objectMapper.mapList(bookRepository.findAllByLibraryId(libraryId), BookDTO.class);
	}
	
}
