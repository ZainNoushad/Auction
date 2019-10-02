package com.auction.app.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
    public String handleError() {
        //do something like logging
        return "CustomError";
    }
 
    @Override
    public String getErrorPath() {
        return "/error";
    }

}
