package com.assignment.lms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.lms.model.Library;

@Repository
public interface LibraryRepository extends CrudRepository<Library, Long> {

}
