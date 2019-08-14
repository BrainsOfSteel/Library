package com.library.Library.dao;

import com.library.Library.models.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface BookInventoryRepo extends JpaRepository<BookInventory, Integer> {
    List<BookInventory> findByBookId(Long bookId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    BookInventory findByBarcode(String barcode);

    int countBookInventoryByBookIdAndDeletedFalse(Integer bookId);
    int countBookInventoryByBookIdAndCurrentUserIdAndDeletedFalse(Integer bookId, Long currentUserId);
}
