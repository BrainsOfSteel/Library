package com.library.Library.dao;

import com.library.Library.models.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPaymentRepo extends JpaRepository<UserPayment, Long>{
    List<UserPayment> findByUserIdAndClearedFalse(Long userId);
    List<UserPayment> findByIdIn(List<Long> ids);
}
