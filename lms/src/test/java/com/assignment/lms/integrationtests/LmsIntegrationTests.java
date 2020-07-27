package com.assignment.lms.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.assignment.lms.LmsApplication;
import com.assignment.lms.dto.BookDTO;
import com.assignment.lms.dto.LibraryDTO;


@SpringBootTest(classes = LmsApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT) 
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Lms Integration Tests")
public class LmsIntegrationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static Long libraryId;
	private static String libraryName = "CCU library";
	private static String libraryAddress = "Kolkata";
	
	private static Long bookId;
	private static String bookTitle = "Java 8 Book";
	private static String bookAuthorName = "Author Name";
	
	
	private static String bookAuthorNameUpdated = "Author Nameupdated";

	@Test
	@Order(1)
	@DisplayName("Test Create Library")
	public void testCreateLibrary() {
		LibraryDTO libraryDTO = new LibraryDTO();
		libraryDTO.setName(libraryName);
		libraryDTO.setAddress(libraryAddress);
		ResponseEntity<LibraryDTO> responseEntity = this.restTemplate
			.postForEntity(createLibraryEndpoint("create"), libraryDTO, LibraryDTO.class);
		libraryId = (responseEntity.getBody()).getId();
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(libraryName, responseEntity.getBody().getName());
		assertEquals(libraryAddress, responseEntity.getBody().getAddress());
	}
	
	@Test
	@Order(2)
	@DisplayName("Test Get Library By Id")
	public void testGetLibraryById() {
		ResponseEntity<LibraryDTO> responseEntity = this.restTemplate
				.getForEntity(createLibraryEndpoint("get/"+libraryId), LibraryDTO.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(libraryName, responseEntity.getBody().getName());
		assertEquals(libraryAddress, responseEntity.getBody().getAddress());
	}
	
	@Test
	@Order(3)
	@DisplayName("Test Get All Library")
	public void testGetAllLibrary() {
		ResponseEntity<List<LibraryDTO>> responseEntity = this.restTemplate
				.exchange(createLibraryEndpoint("get"), HttpMethod.GET, null,
						new ParameterizedTypeReference<List<LibraryDTO>>() {
	            });
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.getBody().size() == 1);
	}
	
	@Test
	@Order(4)
	@DisplayName("Test Add Book")
	public void testAddBookToLibrary() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle(bookTitle);
		bookDTO.setAuthorName(bookAuthorName);
		ResponseEntity<BookDTO> responseEntity = this.restTemplate
			.postForEntity(createBookEndpoint("create") +"/"+ libraryId, bookDTO, BookDTO.class);
		bookId = (responseEntity.getBody()).getId();
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(bookTitle, responseEntity.getBody().getTitle());
		assertEquals(bookAuthorName, responseEntity.getBody().getAuthorName());
	}
	
	@Test
	@Order(5)
	@DisplayName("Test Add Book - Failed Case - Library Id not found")
	public void testAddBookToLibraryWrongLibraryId() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle(bookTitle);
		bookDTO.setAuthorName(bookAuthorName);
		ResponseEntity<BookDTO> responseEntity = this.restTemplate
			.postForEntity(createBookEndpoint("create") +"/"+ (libraryId + 1), bookDTO, BookDTO.class);
		assertEquals(404, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(6)
	@DisplayName("Test Update Book")
	public void testUpdateBook() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(bookId);
		bookDTO.setTitle(bookTitle);
		bookDTO.setAuthorName(bookAuthorNameUpdated);
		ResponseEntity<BookDTO> responseEntity = this.restTemplate
				.postForEntity(createBookEndpoint("update"), bookDTO, BookDTO.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(bookTitle, responseEntity.getBody().getTitle());
		assertEquals(bookAuthorNameUpdated, responseEntity.getBody().getAuthorName());
	}
	
	@Test
	@Order(7)
	@DisplayName("Test Update Book - Failed Case - Book Id not found")
	public void testBookUpdateFailedIdNotFound() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(bookId + 1);
		bookDTO.setTitle(bookTitle);
		bookDTO.setAuthorName(bookAuthorNameUpdated);
		ResponseEntity<BookDTO> responseEntity = this.restTemplate
				.postForEntity(createBookEndpoint("update"), bookDTO, BookDTO.class);
		assertEquals(404, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(8)
	@DisplayName("Test Update Book - Failed Case - Book Id NULL")
	public void testBookUpdateFailedIdNull() {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(null);
		bookDTO.setTitle(bookTitle);
		bookDTO.setAuthorName(bookAuthorNameUpdated);
		ResponseEntity<BookDTO> responseEntity = this.restTemplate
				.postForEntity(createBookEndpoint("update"), bookDTO, BookDTO.class);
		assertEquals(404, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(9)
	@DisplayName("Test Get Book By Id")
	public void testGetBookById() {
		ResponseEntity<BookDTO> responseEntity = this.restTemplate
				.getForEntity(createBookEndpoint("get") +"/"+ bookId, BookDTO.class);
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(bookTitle, responseEntity.getBody().getTitle());
		assertEquals(bookAuthorNameUpdated, responseEntity.getBody().getAuthorName());
	}
	
	@Test
	@Order(10)
	@DisplayName("Test Get All Book By Library")
	public void testGetAllBookByLibraryId() {	
		ResponseEntity<List<BookDTO>> responseEntity = this.restTemplate
				.exchange(createBookEndpoint("getallbookbylibrary") +"/"+ libraryId, HttpMethod.GET, null,
						new ParameterizedTypeReference<List<BookDTO>>() {
	            });
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertTrue(responseEntity.getBody().size() == 1);
	}
	
	private String createLibraryEndpoint(String uri) {
		return "http://localhost:" + port +"/library/"+ uri;
	}
	
	private String createBookEndpoint(String uri) {
		return "http://localhost:" + port +"/book/"+ uri;
	}

}
