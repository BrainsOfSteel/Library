package com.library.Library.serviceImpl;

import com.library.Library.dao.BookInventoryRepo;
import com.library.Library.dao.UserBookInventoryHistoryRepo;
import com.library.Library.dao.UserRepo;
import com.library.Library.models.BookInventory;
import com.library.Library.models.BookType;
import com.library.Library.models.User;
import com.library.Library.models.UserBookInventoryHistory;
import com.library.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final BookInventoryRepo bookInventoryRepo;
    private final UserBookInventoryHistoryRepo userBookInventoryHistoryRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo,BookInventoryRepo bookInventoryRepo, UserBookInventoryHistoryRepo userBookInventoryHistoryRepo) {
        this.userRepo = userRepo;
        this.bookInventoryRepo = bookInventoryRepo;
        this.userBookInventoryHistoryRepo = userBookInventoryHistoryRepo;
    }

    @Override
    public void createUser(String name, String userId, String dob, String email){
        User user = userRepo.findByUserId(userId);
        if(user != null){
            throw new RuntimeException("User already exists");
        }

        user = new User();
        user.setName(name);
        user.setDateOfBirth(dob);
        user.setUserId(userId);
        user.setEmail(email);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void borrowBook(Long userId, String barcode) {
        BookInventory bookInventory =  bookInventoryRepo.findByBarcode(barcode);
        if(bookInventory == null){
            throw new RuntimeException("Book not present for barcode");
        }


        if(bookInventory.getBorrowedTimeSeconds() == null){
            Optional<User> userOptional = userRepo.findById(userId);
            if(!userOptional.isPresent()){
                throw new RuntimeException("User Id is invalid");
            }
            if(barcode.equals("barcode1") && userId.equals(10L)){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bookInventory.setBorrowedTimeSeconds(System.currentTimeMillis()/1000);
            bookInventory.setCurrentUserId(userId);
            bookInventoryRepo.save(bookInventory);
        }
        else{
            throw new RuntimeException("Book is already borrowed");
        }
    }

    @Override
    @Transactional
    public void returnBook(Long userId, String barcode){
        BookInventory bookInventory = bookInventoryRepo.findByBarcode(barcode);
        if(bookInventory == null){
            throw new RuntimeException("Book not present for barcode");
        }

        Optional<User> userOptional = userRepo.findById(userId);
        if(!userOptional.isPresent()){
            throw new RuntimeException("User with userId = "+userId+" is not present");
        }
        if(bookInventory.getBorrowedTimeSeconds() != null && userId.equals(bookInventory.getCurrentUserId())){

            UserBookInventoryHistory history = new UserBookInventoryHistory(userId,(long) bookInventory.getId(), bookInventory.getBorrowedTimeSeconds(),
                    System.currentTimeMillis() / 1000);

            userBookInventoryHistoryRepo.save(history);
            bookInventory.setCurrentUserId(null);
            bookInventory.setBorrowedTimeSeconds(null);
            bookInventoryRepo.save(bookInventory);
        }
        else{
            throw new RuntimeException("Book cannot be returned");
        }
    }
}
