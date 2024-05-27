package com.example.MyBankService.controllers;

import com.example.MyBankService.models.Currencies;
import com.example.MyBankService.models.cards.CreditCard;
import com.example.MyBankService.models.cards.DebitCard;
import com.example.MyBankService.services.cards.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/cards")
public class CardController {
    CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/createDebitCard")
    public ResponseEntity createDebitCard   (@RequestParam("username") String username,
                                             @RequestParam("FIO") String FIO, @RequestParam("val") String val){

        DebitCard debitCard = new DebitCard();

        debitCard.setCurrency(Currencies.valueOf(val));
        debitCard.setOwnerName(FIO);
        cardService.issueCard(debitCard, username);

        return ResponseEntity.ok("status: ok");
    }

    @PostMapping("/createCreditCard")
    public ResponseEntity createCreditCard(@RequestParam("username") String username, @RequestParam("limit") String limit,
                                           @RequestParam("FIO") String FIO, @RequestParam("val") String val
            , @RequestParam("salary") String salary){

        CreditCard creditCard = new CreditCard();

        creditCard.setCurrency(Currencies.valueOf(val));
        creditCard.setOwnerName(FIO);
        creditCard.setLimit(Integer.parseInt(limit));
        cardService.issueCard(creditCard, salary, username);

        return ResponseEntity.ok("status: ok");
    }

    @DeleteMapping("/removeCard")
    public ResponseEntity removeCard(@RequestParam("number") String number){
        System.out.println(1);
        if (cardService.deleteCard(number))
            return ResponseEntity.ok("status: ok");
        return ResponseEntity.ok("Unknown");
    }
    @PostMapping("/makeTransfer")
    public ResponseEntity makeTransfer(@RequestParam("this_number") String number, @RequestParam("number") String otherNumber,
                                       @RequestParam("money") String money){
        if (cardService.makeTransfer(number, otherNumber, Double.parseDouble(money)))
            return ResponseEntity.ok("status: ok");
        return ResponseEntity.ok("wrong");
    }
}
