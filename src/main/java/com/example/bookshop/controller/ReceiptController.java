package com.example.bookshop.controller;

import com.example.bookshop.entity.*;
import com.example.bookshop.security.LoginController;
import com.example.bookshop.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ClientCardService clientCardService;

    private final WorkerService workerService;
    private final BookInstanceService bookInstanceService;
    private final BookService bookService;

    public ReceiptController(ReceiptService receiptService, ClientCardService clientCardService, WorkerService workerService, BookInstanceService bookInstanceService, BookService bookService) {
        this.receiptService = receiptService;
        this.clientCardService = clientCardService;
        this.workerService = workerService;
        this.bookInstanceService = bookInstanceService;
        this.bookService = bookService;
    }

    @ModelAttribute("receipt")
    public Receipt getReceipt() {
        return new Receipt();
    }

    @GetMapping({"/receipt", "/receipt.html"})
    public String showReceiptPage(@RequestParam(value = "starttime", required = false) LocalDateTime starttime,
                                  @RequestParam(value = "endtime", required = false) LocalDateTime endtime,
                                  HttpSession session, Model model) {
        List<Receipt> receipts = new ArrayList<>();
        try {
            if (starttime != null && endtime != null) {
                session.setAttribute("starttime", starttime);
                session.setAttribute("endtime", endtime);
                receipts = receiptService.getReceiptsByDateRange(starttime, endtime);
            } else {
                starttime = (LocalDateTime) session.getAttribute("starttime");
                endtime = (LocalDateTime) session.getAttribute("endtime");
                receipts = (starttime != null && endtime != null)
                        ? receiptService.getReceiptsByDateRange(starttime, endtime)
                        : receiptService.getAllReceipts();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            model.addAttribute("starttime", starttime != null ? starttime.format(formatter) : "");
            model.addAttribute("endtime", endtime != null ? endtime.format(formatter) : "");
            model.addAttribute("receipts", receipts);
            Optional<Worker> user = workerService.findByTabEmail(LoginController.USER);
            model.addAttribute("tab", user.isPresent() ? user.get().getTabNumber() : "");
        } catch (Exception e) {
            model.addAttribute("error", "Не вдалося завантажити чеки");
            model.addAttribute("receipts", new ArrayList<>());
            model.addAttribute("starttime", "");
            model.addAttribute("endtime", "");
            model.addAttribute("tab", "");
        }
        return "receipt/index";
    }

    @GetMapping("/receipt/clear")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> clearFilters(HttpSession session) {
        session.removeAttribute("starttime");
        session.removeAttribute("endtime");
        return ResponseEntity.ok(Map.of("success", true));
    }
    @GetMapping("/receipt/{id}")
    public String viewReceiptCard(@PathVariable Long id, Model model) {
        Receipt receipt = receiptService.getById(id);
        List<BookInstance> instances = bookInstanceService.getBookInstancesByReceiptId(id);

        model.addAttribute("receipt", receipt);
        model.addAttribute("instances", instances);

        return "receipt/card";
    }


    @PostMapping("/receipts")
    public String saveReceipt(@ModelAttribute("receipt") Receipt receipt,
                              @RequestParam("instanceIds") List<Long> instanceIds, BindingResult result, Model model) {
        ClientCard clientCard;
        Book book;
        if(receipt.getClient_id() != null && !receipt.getClient_id().isEmpty()) {
            clientCard = clientCardService.getById(receipt.getClient_id());
            if(clientCard.getAge()<18){
                for (Long instanceId : instanceIds) {
                    book = bookService.getByIsbn(bookInstanceService.getById(instanceId).getISBN());
                    if(book.getAdultsOnly()){
                        result.rejectValue("client_id", "error.client_id", "В списку є книга 18+, Ви не можете продати її неповнолітньому клієнту!");
                    }
                }
            }
            if(receipt.getBonuses() != null && clientCard.getNumberOfBonuses() < receipt.getBonuses()) {
                result.rejectValue("bonuses", "error.bonuses", "Недостатньо бонусів на картці клієнта!");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("clientCards", clientCardService.getAllClientCards());
            model.addAttribute("workers", workerService.getAllWorkers());
            model.addAttribute("availableInstances", bookInstanceService.getAvailableInstances());
            model.addAttribute("selectedInstanceIds", instanceIds);
            return "receipt/add_receipt";
        }


        List<BookInstance> instances = bookInstanceService.getInstancesByIds(instanceIds);

        double totalPrice = receiptService.calculateTotalPrice(instances);
        totalPrice -= (receipt.getBonuses() != null) ? receipt.getBonuses() : 0;
        receipt.setTotalPrice(totalPrice);
        if (totalPrice > 0) {
            receiptService.saveReceipt(receipt);
            for (BookInstance instance : instances) {
                instance.setReceipt_id(receipt.getId());
            }
            bookInstanceService.updateAll(instances);
            if (receipt.getClient_id() != null) {
                clientCardService.updateBonuses(receipt);
            }
        }
        return "redirect:/receipt";
    }

    @GetMapping("/add_receipt")
    public String addReceipt(Model model) {
        Receipt receipt = new Receipt();
        model.addAttribute("receipt", receipt);
        model.addAttribute("clientCards", clientCardService.getAllClientCards());
        model.addAttribute("workers", workerService.getAllWorkers());
        model.addAttribute("availableInstances", bookInstanceService.getAvailableInstances());
        return "receipt/add_receipt";
    }

    @GetMapping("/add_my_receipt")
    public String addCashierReceipt(Model model) {
        Receipt receipt = new Receipt();
        Optional<Worker> worker = workerService.findByTabEmail(LoginController.USER);
        receipt.setWorker_id(worker.get().getTabNumber());
        model.addAttribute("receipt", receipt);
        model.addAttribute("clientCards", clientCardService.getAllClientCards());
        model.addAttribute("workers", workerService.getAllWorkers());
        model.addAttribute("availableInstances", bookInstanceService.getAvailableInstances());
        //System.out.println(worker.get().getTabNumber());
        //model.addAttribute("tabNumber", worker.get().getTabNumber());
        return "receipt/add_receipt";
    }

    @GetMapping("/edit_receipt/{id}")
    public String editReceipt(@PathVariable Long id, Model model) {
        Receipt receipt = receiptService.getById(id);
        model.addAttribute("receipt", receipt);
        model.addAttribute("clientCards", clientCardService.getAllClientCards());
        model.addAttribute("workers", workerService.getAllWorkers());
        return "receipt/edit_receipt";
    }

    @PostMapping("/receipt/update/{id}")
    public String updateReceipt(@PathVariable Long id, Receipt receipt) {
        receipt.setId(id);
        receiptService.update(receipt);
        return "redirect:/receipt";
    }
    @RequestMapping(value = "/receipt/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteReceipt(@PathVariable Long id) {
        receiptService.delete(id);
        return "redirect:/receipt";
    }



}
