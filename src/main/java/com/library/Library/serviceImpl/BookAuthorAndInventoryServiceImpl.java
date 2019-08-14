package com.library.Library.serviceImpl;

import com.library.Library.com.library.Library.dto.BooksDetailsDTO;
import com.library.Library.com.library.Library.dto.PaymentDueDTO;
import com.library.Library.com.library.Library.dto.PendingDueInfoDTO;
import com.library.Library.dao.AuthorRepo;
import com.library.Library.dao.BookAuthorMappingRepo;
import com.library.Library.dao.BookInventoryRepo;
import com.library.Library.dao.BookRepo;
import com.library.Library.dao.UserPaymentRepo;
import com.library.Library.dao.UserRepo;
import com.library.Library.models.Author;
import com.library.Library.models.BookAuthorMapping;
import com.library.Library.models.BookInventory;
import com.library.Library.models.BookType;
import com.library.Library.models.User;
import com.library.Library.models.UserPayment;
import com.library.Library.service.BookAuthorAndInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class BookAuthorAndInventoryServiceImpl implements BookAuthorAndInventoryService {

    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final BookAuthorMappingRepo bookAuthorMappingRepo;
    private final BookInventoryRepo bookInventoryRepo;
    private final UserPaymentRepo userPaymentRepo;
    private final UserRepo userRepo;

    @Autowired
    public BookAuthorAndInventoryServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, BookAuthorMappingRepo bookAuthorMappingRepo,
                                             BookInventoryRepo bookInventoryRepo, UserPaymentRepo userPaymentRepo,
                                             UserRepo userRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.bookAuthorMappingRepo = bookAuthorMappingRepo;
        this.bookInventoryRepo = bookInventoryRepo;
        this.userPaymentRepo = userPaymentRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void validateAndCreateBookAndAuthor(String bookTitle, String bookType, String authorName) {
        BookType book = bookRepo.findByBookTitleAndBookTypeAndDeletedFalse(bookTitle, bookType);
        if(book == null){
            book = new BookType();
            book.setBookTitle(bookTitle);
            book.setBookType(bookType);
            book = bookRepo.save(book);
        }

        Author author = authorRepo.findByNameAndDeletedFalse(authorName);
        if(author == null){
            author = new Author();
            author.setName(authorName);
            author = authorRepo.save(author);
        }

        BookAuthorMapping bookAuthorMapping = bookAuthorMappingRepo.findByAuthorIdAndBookId((long) author.getId(), (long)book.getId());
        if(bookAuthorMapping != null){
            throw new RuntimeException("Book and author already created");
        }
        bookAuthorMapping = new BookAuthorMapping();
        bookAuthorMapping.setAuthorId((long)author.getId());
        bookAuthorMapping.setBookId((long) book.getId());
        bookAuthorMappingRepo.save(bookAuthorMapping);
    }

    @Override
    @Transactional
    public void validateBookAndCreateInventory(String bookTitle, String bookType, String barcode){
        BookType book = bookRepo.findByBookTitleAndBookTypeAndDeletedFalse(bookTitle, bookType);
        if(book == null){
            throw new RuntimeException("Book is not present");
        }

        BookInventory inventory = bookInventoryRepo.findByBarcode(barcode);
        if(inventory != null){
            throw new RuntimeException("Book with given barcode is already present");
        }

        inventory = new BookInventory();
        inventory.setBookId(book.getId());
        inventory.setBarcode(barcode);
        bookInventoryRepo.save(inventory);
    }

    @Override
    @Transactional
    public List<PendingDueInfoDTO> getAllUserAndDuesInfo(){
        List<UserPayment> userPayments = userPaymentRepo.findAllByClearedFalseAndDeletedFalse();
        Map<Long, List<UserPayment>> idVsUserPayment = new HashMap<>();
        if(CollectionUtils.isEmpty(userPayments)){
            return new ArrayList<>();
        }

        Set<Long> uniqueUserIds = new HashSet<>();
        for(UserPayment userPayment : userPayments){
            idVsUserPayment.computeIfAbsent(userPayment.getUserId(), k->new ArrayList<>());
            idVsUserPayment.get(userPayment.getUserId()).add(userPayment);
            uniqueUserIds.add(userPayment.getUserId());
        }

        List<User> users = userRepo.findByIdInAndDeletedFalse(uniqueUserIds);
        Map<Long, User> idVsUser = new HashMap<>();
        if(!CollectionUtils.isEmpty(users)){
            for(User user : users){
                idVsUser.put(user.getId(), user);
            }
        }

        List<PendingDueInfoDTO> pendingDueInfoDTOS = new ArrayList<>();
        for(Map.Entry<Long, List<UserPayment>> entrySet : idVsUserPayment.entrySet()){
            PendingDueInfoDTO pendingDueInfoDTO = new PendingDueInfoDTO();
            double dueToPay = 0.0;
            for(UserPayment userPayment : entrySet.getValue()) {
                dueToPay += userPayment.getDueToPay();
                PaymentDueDTO paymentDueDTO = new PaymentDueDTO(userPayment.getId(), userPayment.getUserId(), userPayment.getDueToPay());
                pendingDueInfoDTO.getPaymentDueDTOS().add(paymentDueDTO);
            }
            User user = idVsUser.get(entrySet.getKey());
            pendingDueInfoDTO.setTotalDue(dueToPay);
            pendingDueInfoDTO.setUserEmail(user.getEmail());
            pendingDueInfoDTO.setUserName(user.getName());
            pendingDueInfoDTO.setUserUniqueId(user.getUserId());
            pendingDueInfoDTO.setUserId(user.getId());
            pendingDueInfoDTOS.add(pendingDueInfoDTO);
        }
        return pendingDueInfoDTOS;
    }

    @Override
    @Transactional
    public List<BooksDetailsDTO> getBookInventoryDetails(){
        List<BookType> bookTypes = bookRepo.findAll();
        List<BooksDetailsDTO> booksDetailsDTOS = new ArrayList<>();
        if(CollectionUtils.isEmpty(bookTypes)){
            return new ArrayList<>();
        }

        for(BookType bookType : bookTypes){
            int availableBooks = bookInventoryRepo.countBookInventoryByBookIdAndCurrentUserIdAndDeletedFalse(bookType.getId(), null);
            int totalBooks = bookInventoryRepo.countBookInventoryByBookIdAndDeletedFalse(bookType.getId());
            BooksDetailsDTO dto = new BooksDetailsDTO(totalBooks, availableBooks, bookType.getBookType(), bookType.getBookTitle());
            booksDetailsDTOS.add(dto);
        }

        return booksDetailsDTOS;
    }
}
