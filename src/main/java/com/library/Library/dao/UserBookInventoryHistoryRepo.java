package com.library.Library.dao;

import com.library.Library.models.UserBookInventoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookInventoryHistoryRepo extends JpaRepository<UserBookInventoryHistory, Long> {

}
