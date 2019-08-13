package com.library.Library.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_inventory")
public class BookInventory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "borrowed_time")
    private Long borrowedTimeSeconds;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "current_user_id")
    private Long currentUserId;

    @Column(name = "deleted")
    private boolean deleted;

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBorrowedTimeSeconds() {
        return borrowedTimeSeconds;
    }

    public void setBorrowedTimeSeconds(Long borrowedTimeSeconds) {
        this.borrowedTimeSeconds = borrowedTimeSeconds;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
