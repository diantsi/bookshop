package com.example.bookshop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

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

            if ("Менеджер".equals(role)) {
                return "redirect:/manager/home";
            } else if ("Касир".equals(role)) {
                return "redirect:/cashier/home";
            }
        }

        return "redirect:/login?error=unknownrole";
    }
}
