package com.library.Library.dao;

import com.library.Library.models.BookType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<BookType, Integer>{
    BookType findById(Long id);
    BookType findByBookTitleAndBookTypeAndDeletedFalse(String bookTitle, String bookType);
}
