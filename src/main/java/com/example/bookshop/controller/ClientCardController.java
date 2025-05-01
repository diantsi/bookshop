package com.example.bookshop.controller;

import com.example.bookshop.entity.ClientCard;
import com.example.bookshop.entity.Worker;
import com.example.bookshop.service.ClientCardService;
import com.example.bookshop.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientCardController {

    public final ClientCardService clientCardService;

    public ClientCardController(ClientCardService service) {
        this.clientCardService = service;
    }

    @GetMapping({"/clientcard", "/clientcard.html"})
    public String showClientCardsPage(Model model, String keyword) {
        List<ClientCard> clientcards;
        if (keyword == null || keyword.isEmpty()) {
            clientcards = clientCardService.getAllClientCards();
        } else {
            clientcards = clientCardService.getAllClientCardsByPrompt(keyword);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("clientcards", clientcards);
        return "clientcard/index";
    }

    @ModelAttribute("clientcard")
    public ClientCard getClientCard() {
        return new ClientCard();
    }

    @GetMapping("/clientcard_info/{idNumber}")
    public String infoClientCard(@PathVariable String idNumber, Model model) {
        ClientCard clientCard = clientCardService.getById(idNumber);
        model.addAttribute("clientcard", clientCard);
        return "clientcard/clientcard_info";
    }


    @GetMapping("/add_clientcard")
    public String addClientCard(Model model) {
        return "clientcard/add_clientcard";
    }

    @GetMapping("/edit_clientcard/{idNumber}")
    public String editClientCard(@PathVariable String idNumber, Model model) {
        ClientCard clientCard = clientCardService.getById(idNumber);
        model.addAttribute("clientcard", clientCard);
        return "clientcard/edit_clientcard";
    }

    @RequestMapping(value = "/clientcard/edit/{idNumber}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editClientCard(@ModelAttribute("clientcard") ClientCard clientcard, BindingResult result, Model model) {
        // Тут виконується валідація

        Optional<ClientCard> existsClientCard = clientCardService.findByPhoneNumber(clientcard.getPhoneNumber());
        if (existsClientCard.isPresent()) {
            if (!existsClientCard.get().getIdNumber().equals(clientcard.getIdNumber())) {
                result.rejectValue("phoneNumber", "error.phoneNumber", "Карта іншого клієнта з таким номером телефону вже існує!");
            }
        }

        if (clientcard.calculateAge() < 0) {
            result.rejectValue("dateOfBirth", "error.dateOfBirth", "Клієнт не може бути ненародженим.");
        }

        if (result.hasErrors()) {
            return "clientcard/edit_clientcard";
        }

        clientCardService.editClientCard(clientcard);
        return "redirect:/clientcard";

    }


    @PostMapping("/clientcards")
    public String saveClientCard(@ModelAttribute("clientcard") ClientCard clientcard, BindingResult result) {
        // Тут виконується валідація
        if (clientCardService.existsByIdNumber(clientcard.getIdNumber())) {
            result.rejectValue("idNumber", "error.idNumber", "Карта клієнта з таким ідентифікаційним номером вже існує!");
        }

        if (clientcard.calculateAge() < 0) {
            result.rejectValue("dateOfBirth", "error.dateOfBirth", "Клієнт не може бути ненародженим.");
        }

        if (clientCardService.findByPhoneNumber(clientcard.getPhoneNumber()).isPresent()) {
            result.rejectValue("phoneNumber", "error.phoneNumber", "Карта клієнта з таким номером телефону вже існує!");
        }

        if (result.hasErrors()) {
            return "clientcard/add_clientcard";
        }

        clientCardService.saveClientCard(clientcard);
        return "redirect:/clientcard";

    }


    @RequestMapping(value = "/clientcard/delete/{idNumber}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteClientCard(@PathVariable String idNumber) {
        clientCardService.delete(idNumber);
        return "redirect:/clientcard";
    }

}
