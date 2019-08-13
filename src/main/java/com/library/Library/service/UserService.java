package com.library.Library.service;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    void createUser(String name, String userId, String dob, String email);

    @Transactional
    void borrowBook(Long userId, String barcode);

    @Transactional
    void returnBook(Long userId, String barcode);
}
