package com.library.Library.dao;

import com.library.Library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long>{
    User findByUserId(String userId);
    List<User> findByIdInAndDeletedFalse(Collection<Long> userId);
}
