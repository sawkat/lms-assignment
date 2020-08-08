package com.assignment.lms.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.assignment.lms.dto.LibraryDTO;
import com.assignment.lms.exception.ResourceNotFoundException;
import com.assignment.lms.model.Library;
import com.assignment.lms.repository.LibraryRepository;
import com.assignment.lms.service.LibraryService;
import com.assignment.lms.utils.ObjectMapper;

/**
 * This is the implementation class of LibraryService
 * 
 * @author 703177676
 *
 */
@Service
public class LibraryServiceImpl implements LibraryService{
	
	@Value("${lms.msg.library_not_found_with_id}")
	private String msgLibraryNotFound;
	
	@Autowired
	private LibraryRepository libraryRepository;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Override
	@Transactional
	public LibraryDTO createLibrary(LibraryDTO libraryDTO) {
		return objectMapper.map(libraryRepository.save(objectMapper.map(libraryDTO, Library.class)), LibraryDTO.class);
	}
	
	@Override
	public List<LibraryDTO> getAllLibrary() {
		List<LibraryDTO> listOfLibraryDTO = new ArrayList<LibraryDTO>();
		libraryRepository.findAll().forEach((element) -> {
			listOfLibraryDTO.add(objectMapper.map(element, LibraryDTO.class));
		});
		return listOfLibraryDTO;
	}

	@Override
	public LibraryDTO findById(Long libraryId) {
		Library library = libraryRepository.findById(libraryId).orElseThrow(() -> new ResourceNotFoundException(msgLibraryNotFound + libraryId));
		return objectMapper.map(library, LibraryDTO.class);
	}
	
}
