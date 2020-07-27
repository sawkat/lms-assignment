package com.assignment.lms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.lms.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findAllByLibraryId(Long libraryId);
}
