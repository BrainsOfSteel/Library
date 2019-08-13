package com.library.Library.controller;

import com.library.Library.com.library.Library.dto.GenericResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping(path = "/testHello", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponseDTO testHello(){
        GenericResponseDTO dto = new GenericResponseDTO();
        dto.setMessage("Working just fine");
        return dto;
    }
}
