package com.library.Library.service;

import com.library.Library.com.library.Library.dto.BooksDetailsDTO;
import com.library.Library.com.library.Library.dto.PaymentDueDTO;
import com.library.Library.models.BookInventory;
import com.library.Library.models.User;
import com.library.Library.models.UserBookInventoryHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void createUser(String name, String userId, String dob, String email);

    @Transactional
    void borrowBook(Long userId, String barcode);

    @Transactional
    void borrowBook(User user, BookInventory bookInventory);

    @Transactional
    UserBookInventoryHistory returnBook(User user, BookInventory bookInventory);

    @Transactional
    void returnBook(Long userId, String barcode);

    @Transactional
    void reissue(Long userId, String barcode);

    @Transactional
    List<PaymentDueDTO> getPaymentDuesForUser(Long userId);

    @Transactional
    void payDues(Long userId, List<Long> paymentIds, Double amount);
}
