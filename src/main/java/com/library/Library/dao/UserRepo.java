package com.library.Library.dao;

import com.library.Library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>{
    User findByUserId(String userId);
}
