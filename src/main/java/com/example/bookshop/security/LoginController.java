package com.example.bookshop.security;

import com.example.bookshop.entity.Worker;
import com.example.bookshop.service.WorkerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    public static String TAB_NUMBER;
    public static String FIRST_NAME;
    public static String SURNAME;
    public static String USER;


    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html
    }

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login?error"; // якщо неавторизовано
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();

            USER = authentication.getName();
            System.out.println(USER);
            /*TAB_NUMBER = userDetails.getTabNumber();
            FIRST_NAME= userDetails.getFirstName();
            SURNAME = userDetails.getSurname();*/

            if ("Менеджер".equals(role)) {
                return "redirect:/manager/home";
            } else if ("Касир".equals(role)) {
                return "redirect:/manager/home";
            }
        }

        return "redirect:/login?error=unknownrole";
    }
}
