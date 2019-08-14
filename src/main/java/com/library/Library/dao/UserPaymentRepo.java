package com.library.Library.dao;

import com.library.Library.models.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepo extends JpaRepository<UserPayment, Long>{

}
