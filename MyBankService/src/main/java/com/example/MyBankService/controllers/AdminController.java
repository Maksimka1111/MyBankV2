package com.example.MyBankService.controllers;

import com.example.MyBankService.services.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {
    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getModel")
    public ResponseEntity getModel(@RequestParam("number") String number){
        var debitCard = adminService.getDebitCard(number);
        if (debitCard != null)
            return ResponseEntity.ok(debitCard);
        var creditCard = adminService.getCreditCard(number);
        if (creditCard != null)
            return ResponseEntity.ok(creditCard);
        var credit = adminService.getCredit(number);
        if (credit != null)
            return ResponseEntity.ok(credit);
        var contribution = adminService.getContribution(number);
        if (contribution != null)
            return ResponseEntity.ok(contribution);

        return ResponseEntity.ok("Nothing");
    }
    @GetMapping("/getUser")
    public ResponseEntity getUser(@RequestParam("username") String username){
        var user = adminService.getUser(username);
        if (user != null)
            return ResponseEntity.ok(user);
        return ResponseEntity.ok("Nothing");
    }
    @DeleteMapping("/delUser")
    public ResponseEntity delUser(@RequestParam("username") String username){
        adminService.deleteUser(username);
        return ResponseEntity.ok("status:ok");
    }
    @PostMapping("/addMoney")
    public ResponseEntity addMoney(@RequestParam("number") String number, @RequestParam("money") String money){

        if (adminService.addMoney(number, Integer.parseInt(money))){
            return ResponseEntity.ok("status: ok");
        }

        return ResponseEntity.ok("Nothing");
    }
}
