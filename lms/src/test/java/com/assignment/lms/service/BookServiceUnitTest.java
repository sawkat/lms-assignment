package com.assignment.lms.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.assignment.lms.dto.BookDTO;
import com.assignment.lms.dto.LibraryDTO;
import com.assignment.lms.exception.ResourceNotFoundException;
import com.assignment.lms.model.Book;
import com.assignment.lms.model.Library;
import com.assignment.lms.repository.BookRepository;
import com.assignment.lms.repository.LibraryRepository;
import com.assignment.lms.service.Impl.BookServiceImpl;
import com.assignment.lms.utils.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class BookServiceUnitTest {

	@Mock
	private BookRepository bookRepository;

	@Mock
	private LibraryRepository libraryRepository;
	
	@Mock
	ObjectMapper objectMapper;
	
	@InjectMocks
	private BookServiceImpl bookService;

	private static BookDTO bookDTO;

	private static LibraryDTO libraryDTO;
	
	private static Library library;
	
	private static Book book;
	
	private static List<Book> listOfBook;
	
	private static List<BookDTO> listOfBookDTO;
	
	@BeforeAll
	public static void init() {
		createLibraryDTO();
		createBookDTO();
		createLibrary();
		createBook();
		listOfBook = new ArrayList<Book>();
		listOfBook.add(book);
		listOfBookDTO = new ArrayList<BookDTO>();
		listOfBookDTO.add(bookDTO);
	}
	
	@Test
	@Order(1)
	@DisplayName("Test Add Book - Success Case")
	public void testBookCreate() {
		Optional<Library> optionalLibrary = Optional.of(library);
		when(libraryRepository.findById(any(Long.class))).thenReturn(optionalLibrary);
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(objectMapper.map(bookDTO,Book.class)).thenReturn(book);
		when(objectMapper.map(book,BookDTO.class)).thenReturn(bookDTO);
		BookDTO bookDTOResponse = bookService.addBookToLibrary(1l,bookDTO);
		assertEquals(bookDTO.getTitle(), bookDTOResponse.getTitle());

	}
	
	@Test
	@Order(2)
	@DisplayName("Test Add Book - Failed Case - Library Id not found")
	public void testBookCreateFailed() {
		Optional<Library> optionalLibrary = Optional.ofNullable(null);
		when(libraryRepository.findById(any(Long.class))).thenReturn(optionalLibrary);
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.addBookToLibrary(10l,bookDTO);
		});
	}
	
	@Test
	@Order(3)
	@DisplayName("Test Update Book - Success Case")
	public void testBookUpdate() {
		Optional<Book> optionalBook = Optional.of(book);
		when(bookRepository.findById(any(Long.class))).thenReturn(optionalBook);
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		when(objectMapper.map(bookDTO,Book.class)).thenReturn(book);
		when(objectMapper.map(book,BookDTO.class)).thenReturn(bookDTO);
		String oldAuthorName = bookDTO.getAuthorName();
		bookDTO.setAuthorName("Author Name Updated");
		BookDTO bookDTOResponse = bookService.updateBook(bookDTO);
		assertEquals(bookDTO.getTitle(), bookDTOResponse.getTitle());
		assertNotEquals(oldAuthorName, bookDTOResponse.getAuthorName());

	}
	
	@Test
	@Order(4)
	@DisplayName("Test Update Book - Failed Case - Book Id not found")
	public void testBookUpdateFailedIdNotFound() {
		Optional<Book> optionalBook = Optional.ofNullable(null);
		when(bookRepository.findById(any(Long.class))).thenReturn(optionalBook);
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.updateBook(bookDTO);
		});
	}
	
	@Test
	@Order(5)
	@DisplayName("Test Update Book - Failed Case - Book Id NULL")
	public void testBookUpdateFailedIdNull() {
		bookDTO.setId(null);
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.updateBook(bookDTO);
		});
	}
	
	@Test
	@Order(6)
	@DisplayName("Test Get Book By Id")
	public void testGetBookById() {
		Optional<Book> optionalBook = Optional.of(book);
		when(bookRepository.findById(any(Long.class))).thenReturn(optionalBook);
		when(objectMapper.map(book,BookDTO.class)).thenReturn(bookDTO);
		BookDTO bookDTOResponse = bookService.getBookById(2l);
		assertEquals(bookDTO.getTitle(), bookDTOResponse.getTitle());
	}
	
	@Test
	@Order(7)
	@DisplayName("Test Get Book By Id  - Failed Case - Book Id not found")
	public void testGetBookByIdNotFound() {
		Optional<Book> optionalBook = Optional.ofNullable(null);
		when(bookRepository.findById(any(Long.class))).thenReturn(optionalBook);
		assertThrows(ResourceNotFoundException.class, () -> {
			bookService.getBookById(20l);
		});
	}
	
	@Test
	@Order(8)
	@DisplayName("Test Get All Book By Library")
	public void testGetAllBookByLibraryId() {	
		when(bookRepository.findAllByLibraryId(any(Long.class))).thenReturn(listOfBook);
		when(objectMapper.mapList(listOfBook,BookDTO.class)).thenReturn(listOfBookDTO);
		assertTrue(bookService.getAllByLibrary(1l).size() == 1);
	}
	
	private static void createLibraryDTO() {
		libraryDTO = new LibraryDTO();
		libraryDTO.setId(1l);
		libraryDTO.setName("CCU Library");
		libraryDTO.setAddress("Kolkata");
	}
	
	private static void createLibrary() {
		library = new Library();
		library.setId(1l);
		library.setName("CCU Library");
		library.setAddress("Kolkata");
	}
	
	private static void createBookDTO() {
		bookDTO = new BookDTO();
		bookDTO.setId(2l);
		bookDTO.setTitle("Java 8");
		bookDTO.setNoOfPages(400);
		bookDTO.setAuthorName("Author Name");
	}
	
	private static void createBook() {
		book = new Book();
		book.setId(2l);
		book.setTitle("Java 8");
		book.setNoOfPages(400);
		book.setAuthorName("Author Name");
	}
	
}
