package com.assignment.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assignment.lms.dto.LibraryDTO;
import com.assignment.lms.model.Library;
import com.assignment.lms.repository.LibraryRepository;
import com.assignment.lms.service.Impl.LibraryServiceImpl;
import com.assignment.lms.utils.ObjectMapper;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class LibraryServiceUnitTest {
	
	@Mock
	private LibraryRepository libraryRepository;
	
	@Mock
	ObjectMapper objectMapper;
	
	@InjectMocks 
	private LibraryServiceImpl libraryService;
	
	Library library;
	
	LibraryDTO libraryDTO;
	
	@BeforeEach
	void setMockOutput() {
		library = new Library();
		library.setName("CCU Library");
		library.setAddress("Kolkata");
		libraryDTO = new LibraryDTO();
		libraryDTO.setName("CCU Library");
		libraryDTO.setAddress("Kolkata");
		when(objectMapper.map(libraryDTO,Library.class)).thenReturn(library);
		when(objectMapper.map(library,LibraryDTO.class)).thenReturn(libraryDTO);
		when(libraryRepository.save(any(Library.class))).thenReturn(library);
	}

	@Test
	@DisplayName("Test Create Library")
	public void testLibraryCreate() {
		libraryDTO.setName("CCU Library");
		libraryDTO.setAddress("Kolkata");
		LibraryDTO libraryDTOResponse = libraryService.createLibrary(libraryDTO);
		assertEquals(libraryDTO.getName(), libraryDTOResponse.getName());
		assertEquals(libraryDTO.getAddress(), libraryDTOResponse.getAddress());

	}


}
