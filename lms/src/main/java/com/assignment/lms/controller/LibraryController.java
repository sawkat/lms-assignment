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

import com.assignment.lms.dto.LibraryDTO;
import com.assignment.lms.service.LibraryService;

/**
 * This is a controller class for Library 
 * 
 * @author 703177676
 *
 */
@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;
	
	
	/**
	 * API to create library
	 * 
	 * @param libraryDTO
	 * @return LibraryDTO with generated Id
	 */
	@PostMapping("/create")
	public LibraryDTO createLibrary(@Valid @RequestBody LibraryDTO libraryDTO) {
		return libraryService.createLibrary(libraryDTO);
	}
	
	/**
	 * API to get all library
	 * 
	 * @return List<LibraryDTO> list of LibraryDTO
	 */
	@GetMapping("/get")
	public List<LibraryDTO> getAllLibrary() {
		return libraryService.getAllLibrary();
	}
	
	/**
	 * API to get a library by libraryId
	 * 
	 * @param libraryId
	 * @return LibraryDTO
	 */
	@GetMapping("/get/{libraryId}")
	public LibraryDTO getLibraryById(@PathVariable("libraryId") long libraryId) {
		return libraryService.findById(libraryId);
	}

}
