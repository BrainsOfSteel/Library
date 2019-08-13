package com.library.Library.dao;

import com.library.Library.models.BookAuthorMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookAuthorMappingRepo extends JpaRepository<BookAuthorMapping, Integer>{
    List<BookAuthorMapping> findByBookId(Long bookId);
    List<BookAuthorMapping> findByAuthorId(Long authorId);
    BookAuthorMapping findByAuthorIdAndBookId(Long authorId, Long bookId);
}
