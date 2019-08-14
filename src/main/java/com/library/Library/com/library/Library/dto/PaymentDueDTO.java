package com.library.Library.com.library.Library.dto;

public class PaymentDueDTO {
    private Long id;
    private Long userId;
    private Double dueToPay;

    public PaymentDueDTO(Long id, Long userId, Double dueToPay) {
        this.id = id;
        this.userId = userId;
        this.dueToPay = dueToPay;
    }

    public PaymentDueDTO() {
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

    public Double getDueToPay() {
        return dueToPay;
    }

    public void setDueToPay(Double dueToPay) {
        this.dueToPay = dueToPay;
    }


}
