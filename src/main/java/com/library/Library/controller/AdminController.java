package com.library.Library.controller;

import com.library.Library.com.library.Library.dto.GenericResponseDTO;
import com.library.Library.com.library.Library.dto.PendingDueInfoDTO;
import com.library.Library.request.BookAndAuthorRequest;
import com.library.Library.request.BookInventoryRequest;
import com.library.Library.service.BookAuthorAndInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {
    private final BookAuthorAndInventoryService bookAuthorAndInventoryService;

    @Autowired
    public AdminController(BookAuthorAndInventoryService bookAuthorAndInventoryService) {
        this.bookAuthorAndInventoryService = bookAuthorAndInventoryService;
    }

    @RequestMapping(path = "/addBookAndAuthor", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> addBookAndAuthor(@RequestBody BookAndAuthorRequest request){
        try{
            bookAuthorAndInventoryService.validateAndCreateBookAndAuthor(request.getBookTitle(), request.getBookType(), request.getAuthorName());
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("Successfully created books and author and their relationships"),HttpStatus.OK);
    }

    @RequestMapping(path = "/addBookInventory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> addBookInventory(@RequestBody BookInventoryRequest request){
        try{
            bookAuthorAndInventoryService.validateBookAndCreateInventory(request.getBookTitle(),
                    request.getBookType(), request.getBarcode());
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(GenericResponseDTO.generateSuccessResponseDTO("Successfully added inventory"), HttpStatus.OK);
    }

    @RequestMapping(path = "/getPendingDueInformation", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseDTO> getPendingDueInformation(){
        try{
            GenericResponseDTO<List<PendingDueInfoDTO>> responseDTO = new GenericResponseDTO<>("Dues Information", "200",
                    bookAuthorAndInventoryService.getAllUserAndDuesInfo());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(GenericResponseDTO.generateErrorResponseDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
