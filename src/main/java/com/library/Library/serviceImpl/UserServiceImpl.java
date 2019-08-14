package com.library.Library.serviceImpl;

import com.library.Library.com.library.Library.dto.Tuple;
import com.library.Library.dao.BookInventoryRepo;
import com.library.Library.dao.UserBookInventoryHistoryRepo;
import com.library.Library.dao.UserPaymentRepo;
import com.library.Library.dao.UserRepo;
import com.library.Library.models.BookInventory;
import com.library.Library.models.User;
import com.library.Library.models.UserBookInventoryHistory;
import com.library.Library.models.UserPayment;
import com.library.Library.service.PaymentService;
import com.library.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final BookInventoryRepo bookInventoryRepo;
    private final UserBookInventoryHistoryRepo userBookInventoryHistoryRepo;
    private final UserPaymentRepo userPaymentRepo;
    private final PaymentService paymentService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo,BookInventoryRepo bookInventoryRepo, UserBookInventoryHistoryRepo userBookInventoryHistoryRepo,
                           UserPaymentRepo userPaymentRepo, PaymentService paymentService) {
        this.userRepo = userRepo;
        this.bookInventoryRepo = bookInventoryRepo;
        this.userBookInventoryHistoryRepo = userBookInventoryHistoryRepo;
        this.userPaymentRepo = userPaymentRepo;
        this.paymentService = paymentService;
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

    private Tuple<Optional<User>, BookInventory> getUserAndInventory(Long userId, String barcode){
        BookInventory bookInventory =  bookInventoryRepo.findByBarcode(barcode);
        if(bookInventory == null){
            throw new RuntimeException("Book not present for barcode");
        }

        Optional<User> userOptional = userRepo.findById(userId);
        if(!userOptional.isPresent()){
            throw new RuntimeException("User Id is invalid");
        }

        return new Tuple<>(userOptional, bookInventory);
    }

    @Override
    @Transactional
    public void borrowBook(Long userId, String barcode){
        Tuple<Optional<User>, BookInventory> userAndInventory = getUserAndInventory(userId, barcode);
        borrowBook(userAndInventory.getV1().get(), userAndInventory.getV2());
    }

    @Override
    public void borrowBook(User user, BookInventory bookInventory) {
        if(bookInventory.getBorrowedTimeSeconds() == null){
            bookInventory.setBorrowedTimeSeconds(System.currentTimeMillis()/1000);
            bookInventory.setCurrentUserId(user.getId());
            bookInventoryRepo.save(bookInventory);
        }
        else{
            throw new RuntimeException("Book is already borrowed");
        }
    }

    @Override
    public UserBookInventoryHistory returnBook(User user, BookInventory bookInventory){
        Long userId = user.getId();
        if(bookInventory.getBorrowedTimeSeconds() != null && userId.equals(bookInventory.getCurrentUserId())){

            UserBookInventoryHistory history = new UserBookInventoryHistory(userId,(long) bookInventory.getId(), bookInventory.getBorrowedTimeSeconds(),
                    System.currentTimeMillis() / 1000);

            UserBookInventoryHistory inventoryHistory = userBookInventoryHistoryRepo.save(history);
            bookInventory.setCurrentUserId(null);
            bookInventory.setBorrowedTimeSeconds(null);
            double fineCalculation = 0;
            try {
                fineCalculation = paymentService.fineCalculation(history.getBorrowedTime(), history.getReturnedTime());
            }catch (ParseException e){
                throw new RuntimeException("Unable to calculate fine: Rolling back", e);
            }
            Long currentTimeSeconds = System.currentTimeMillis() / 1000;
            UserPayment userPayment = new UserPayment(history, fineCalculation, null, false, false, currentTimeSeconds,
                    currentTimeSeconds, userId);
            userPaymentRepo.save(userPayment);
            bookInventoryRepo.save(bookInventory);
            return inventoryHistory;
        }
        else{
            throw new RuntimeException("Book cannot be returned");
        }
    }

    @Override
    @Transactional
    public void returnBook(Long userId, String barcode){
        Tuple<Optional<User>, BookInventory> userAndInventory = getUserAndInventory(userId, barcode);
        returnBook(userAndInventory.getV1().get(), userAndInventory.getV2());
    }

    @Override
    @Transactional
    public void reissue(Long userId, String barcode){
        Tuple<Optional<User>, BookInventory> userAndInventory = getUserAndInventory(userId, barcode);
        returnBook(userAndInventory.getV1().get(), userAndInventory.getV2());
        borrowBook(userAndInventory.getV1().get(), userAndInventory.getV2());
    }
}
