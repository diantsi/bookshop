package com.example.bookshop;

import com.example.bookshop.entity.Worker;
import com.example.bookshop.security.LoginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ApplicationController {

    @Controller
    @RequestMapping("/manager")
    public class ManagerController {

        @GetMapping({"/index", "/index.html", "/home"})
        public String home(Model model) {
            model.addAttribute("instances", LoginController.SURNAME);
            model.addAttribute("instances", LoginController.FIRST_NAME);
            return "index";
        }

        @GetMapping({"/index2", "/index2.html"})
        public String home2() {
            return "index2";
        }

        @GetMapping({"/_layout", "/_layout.html"})
        public String layout(Model model) {
            return "_layout";
        }

//    @GetMapping({"/book", "/book.html"})
//    public String book(){
//        return "/book/index";
//    }

//    @GetMapping({"/bookcopy", "/bookcopy.html"})
//    public String bookcopy(){
//        return "/bookcopy/index";
//    }

        @GetMapping({"/clientcard", "/clientcard.html"})
        public String clientcard() {
            return "/clientcard/index";
        }

//    @GetMapping({"/genre", "/genre.html"})
//    public String genre(){
//        return "/genre/index";
//    }

//    @GetMapping({"/receipt", "/receipt.html"})
//    public String receipt(){
//        return "/receipt/index";
//    }

        @GetMapping({"/review", "/review.html"})
        public String review() {
            return "/review/index";
        }

//    @GetMapping({"/worker", "/worker.html"})
//    public String worker(){
//        return "/worker/index";
//    }
    }
}

