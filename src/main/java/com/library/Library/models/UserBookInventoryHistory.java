package com.library.Library.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_book_inventory_history")
public class UserBookInventoryHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    @Column(name = "borrowed_time",nullable = false)
    private Long borrowedTime;

    @Column(name = "returned_time", nullable = false)
    private Long returnedTime;

    public UserBookInventoryHistory(Long userId, Long inventoryId, Long borrowedTime, Long returnedTime) {
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.borrowedTime = borrowedTime;
        this.returnedTime = returnedTime;
    }

    public UserBookInventoryHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(Long borrowedTime) {
        this.borrowedTime = borrowedTime;
    }

    public Long getReturnedTime() {
        return returnedTime;
    }

    public void setReturnedTime(Long returnedTime) {
        this.returnedTime = returnedTime;
    }
}
