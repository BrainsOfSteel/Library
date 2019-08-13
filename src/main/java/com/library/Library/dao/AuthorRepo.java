package com.library.Library.dao;

import com.library.Library.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Integer>{
    Author findById(Long id);
    Author findByNameAndDeletedFalse(String name);
}
