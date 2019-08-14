package com.library.Library.dao;

import com.library.Library.models.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface UserPaymentRepo extends JpaRepository<UserPayment, Long>{
    List<UserPayment> findByUserIdAndClearedFalse(Long userId);

    List<UserPayment> findAllByClearedFalseAndDeletedFalse();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<UserPayment> findByIdInAndDeletedFalse(List<Long> ids);
}
