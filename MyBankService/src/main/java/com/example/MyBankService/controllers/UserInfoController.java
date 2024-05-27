package com.example.MyBankService.controllers;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.Credit;
import com.example.MyBankService.models.User;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;
import com.example.MyBankService.services.userInfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
    UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public User getUserInfo(@RequestParam String username){
        return userInfoService.getUser(username);
    }
    @PostMapping("/fillUserInfo")
    public ResponseEntity fillUserInfo(@RequestParam("username") String username,@RequestParam("FIO") String FIO){
        userInfoService.fillUserInfo(username, FIO);
        return ResponseEntity.ok("status: ok");
    }

    @GetMapping("/getDebitCards")
    public List<DebitCard> getDebitCards(@RequestParam("username") String username){
        return userInfoService.getUserDebitCards(username);
    }
    @GetMapping("/getCreditCards")
    public List<CreditCard> getCreditCards(@RequestParam("username") String username){
        return userInfoService.getUserCreditCards(username);
    }
    @GetMapping("/getCredits")
    public List<Credit> getCredits(@RequestParam("username") String username){
        return userInfoService.getUserCredits(username);
    }
    @GetMapping("/getContributions")
    public List<Contribution> getContributions(@RequestParam("username") String username){
        return userInfoService.getUserContributions(username);
    }
}

