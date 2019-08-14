package com.library.Library.controller;

import com.library.Library.com.library.Library.dto.GenericResponseDTO;
import com.library.Library.com.library.Library.dto.PaymentDueDTO;
import com.library.Library.request.UserCreateRequest;
import com.library.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> createUser(@RequestBody UserCreateRequest request){
        try{
            userService.createUser(request.getName(), request.getUserId(), request.getDob(), request.getEmail());
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("User created successfully"),HttpStatus.OK);
    }

    @RequestMapping(path = "/borrowBook/{userId}/{barcode}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> borrowBook(@PathVariable("userId") Long userId,
                                                         @PathVariable("barcode") String barcode){
        try{
            userService.borrowBook(userId, barcode);
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("book Successfully borrowed"),HttpStatus.OK);
    }

    @RequestMapping(path = "/returnBook/{userId}/{barcode}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> returnBook(@PathVariable("userId") Long userId,
                                                         @PathVariable("barcode") String barcode){
        try{
            userService.returnBook(userId, barcode);
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("book Successfully returned"),HttpStatus.OK);
    }

    @RequestMapping(path = "/reissue/{userId}/{barcode}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> reissue(@PathVariable("userId")Long userId,
                                                      @PathVariable("barcode") String barcode){
        try{
            userService.reissue(userId, barcode);
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("book reissued successfully"),HttpStatus.OK);
    }

    @RequestMapping(path = "/unpaidDues/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> getUnpaidDues(@PathVariable("userId")Long userId){
        try{
            GenericResponseDTO<List<PaymentDueDTO>> response = new GenericResponseDTO<>("Data returned successfully" , "200" , userService.getPaymentDuesForUser(userId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/payDues/{userId}/{amount}")
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> payDues(@PathVariable("userId")Long userId, @PathVariable("amount") double amount, @RequestParam(value= "paymentId") List<Long> paymentIds) {
        try{
            userService.payDues(userId, paymentIds, amount);
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("Dues cleared successfully"),HttpStatus.OK);
    }
}
