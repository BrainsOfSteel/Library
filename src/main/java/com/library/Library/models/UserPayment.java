package com.library.Library.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_payment")
public class UserPayment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_inventory_history_id")
    private UserBookInventoryHistory userBookInventoryHistory;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "due_to_pay")
    private Double dueToPay;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "cleared")
    private boolean cleared;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    public UserPayment() {
    }

    public UserPayment(UserBookInventoryHistory userBookInventoryHistory, Double dueToPay, Double paidAmount, boolean cleared, boolean deleted, Long createdAt, Long updatedAt, Long userId) {
        this.userBookInventoryHistory = userBookInventoryHistory;
        this.dueToPay = dueToPay;
        this.paidAmount = paidAmount;
        this.cleared = cleared;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBookInventoryHistory getUserBookInventoryHistory() {
        return userBookInventoryHistory;
    }

    public void setUserBookInventoryHistory(UserBookInventoryHistory userBookInventoryHistory) {
        this.userBookInventoryHistory = userBookInventoryHistory;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getDueToPay() {
        return dueToPay;
    }

    public void setDueToPay(Double dueToPay) {
        this.dueToPay = dueToPay;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
