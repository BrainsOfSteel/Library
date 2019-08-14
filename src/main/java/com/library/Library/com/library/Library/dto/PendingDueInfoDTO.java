package com.library.Library.com.library.Library.dto;

import java.util.ArrayList;
import java.util.List;

public class PendingDueInfoDTO {
    private String userName;
    private Double totalDue;
    private String userEmail;
    private List<PaymentDueDTO> paymentDueDTOS = new ArrayList<>();
    private String userUniqueId;
    private Long userId;

    public PendingDueInfoDTO() {
    }

    public PendingDueInfoDTO(String userName, Double totalDue, String userEmail, List<PaymentDueDTO> paymentDueDTOS, String userUniqueId, Long userId) {
        this.userName = userName;
        this.totalDue = totalDue;
        this.userEmail = userEmail;
        this.paymentDueDTOS = paymentDueDTOS;
        this.userUniqueId = userUniqueId;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<PaymentDueDTO> getPaymentDueDTOS() {
        return paymentDueDTOS;
    }

    public void setPaymentDueDTOS(List<PaymentDueDTO> paymentDueDTOS) {
        this.paymentDueDTOS = paymentDueDTOS;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
