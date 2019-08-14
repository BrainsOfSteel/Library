package com.library.Library.service;

import com.library.Library.com.library.Library.dto.BooksDetailsDTO;
import com.library.Library.com.library.Library.dto.PendingDueInfoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookAuthorAndInventoryService {
    void validateAndCreateBookAndAuthor(String bookName, String bookType, String authorName);

    @Transactional
    void validateBookAndCreateInventory(String bookTitle, String bookType, String barcode);

    @Transactional
    List<PendingDueInfoDTO> getAllUserAndDuesInfo();

    @Transactional
    List<BooksDetailsDTO> getBookInventoryDetails();
}
