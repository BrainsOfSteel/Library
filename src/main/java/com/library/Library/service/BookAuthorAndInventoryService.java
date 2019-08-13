package com.library.Library.service;

import org.springframework.transaction.annotation.Transactional;

public interface BookAuthorAndInventoryService {
    void validateAndCreateBookAndAuthor(String bookName, String bookType, String authorName);

    @Transactional
    void validateBookAndCreateInventory(String bookTitle, String bookType, String barcode);
}
