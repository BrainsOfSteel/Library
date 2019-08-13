package com.library.Library.serviceImpl;

import com.library.Library.dao.AuthorRepo;
import com.library.Library.dao.BookAuthorMappingRepo;
import com.library.Library.dao.BookInventoryRepo;
import com.library.Library.dao.BookRepo;
import com.library.Library.models.Author;
import com.library.Library.models.BookAuthorMapping;
import com.library.Library.models.BookInventory;
import com.library.Library.models.BookType;
import com.library.Library.service.BookAuthorAndInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookAuthorAndInventoryServiceImpl implements BookAuthorAndInventoryService {

    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final BookAuthorMappingRepo bookAuthorMappingRepo;
    private final BookInventoryRepo bookInventoryRepo;

    @Autowired
    public BookAuthorAndInventoryServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, BookAuthorMappingRepo bookAuthorMappingRepo,
                                             BookInventoryRepo bookInventoryRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.bookAuthorMappingRepo = bookAuthorMappingRepo;
        this.bookInventoryRepo = bookInventoryRepo;
    }

    @Override
    @Transactional
    public void validateAndCreateBookAndAuthor(String bookTitle, String bookType, String authorName) {
        BookType book = bookRepo.findByBookTitleAndBookTypeAndDeletedFalse(bookTitle, bookType);
        if(book == null){
            book = new BookType();
            book.setBookTitle(bookTitle);
            book.setBookType(bookType);
            book = bookRepo.save(book);
        }

        Author author = authorRepo.findByNameAndDeletedFalse(authorName);
        if(author == null){
            author = new Author();
            author.setName(authorName);
            author = authorRepo.save(author);
        }

        BookAuthorMapping bookAuthorMapping = bookAuthorMappingRepo.findByAuthorIdAndBookId((long) author.getId(), (long)book.getId());
        if(bookAuthorMapping != null){
            throw new RuntimeException("Book and author already created");
        }
        bookAuthorMapping = new BookAuthorMapping();
        bookAuthorMapping.setAuthorId((long)author.getId());
        bookAuthorMapping.setBookId((long) book.getId());
        bookAuthorMappingRepo.save(bookAuthorMapping);
    }

    @Override
    @Transactional
    public void validateBookAndCreateInventory(String bookTitle, String bookType, String barcode){
        BookType book = bookRepo.findByBookTitleAndBookTypeAndDeletedFalse(bookTitle, bookType);
        if(book == null){
            throw new RuntimeException("Book is not present");
        }

        BookInventory inventory = bookInventoryRepo.findByBarcode(barcode);
        if(inventory != null){
            throw new RuntimeException("Book with given barcode is already present");
        }

        inventory = new BookInventory();
        inventory.setBookId(book.getId());
        inventory.setBarcode(barcode);
        bookInventoryRepo.save(inventory);
    }
}
