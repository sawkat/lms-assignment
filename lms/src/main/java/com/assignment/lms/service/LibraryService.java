package com.assignment.lms.service;


import java.util.List;
import com.assignment.lms.dto.LibraryDTO;



/**
 * This is the service class of Library
 * 
 * @author 703177676
 *
 */
public interface LibraryService {
	
	
	/**
	 * Method to create library
	 * 
	 * @param libraryDTO
	 * @return LibraryDTO with generated Id
	 */
	public LibraryDTO createLibrary(LibraryDTO libraryDTO);
	
	/**
	 * Method to get all library
	 * 
	 * @return List<LibraryDTO> list of LibraryDTO
	 */
	public List<LibraryDTO> getAllLibrary();

	/**
	 * Method to get a library by libraryId
	 * 
	 * @param libraryId 
	 * @return LibraryDTO
	 */
	public LibraryDTO findById(Long libraryId);

}
