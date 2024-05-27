package com.example.MyBankService.controllers;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.models.Credit;
import com.example.MyBankService.services.BankUtils;
import com.example.MyBankService.services.conrtibutions.ContributionService;
import com.example.MyBankService.services.credits.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/credits")
public class CreditController {
    CreditService creditService;

    @Autowired
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("/createCredit")
    public ResponseEntity createCredit(@RequestParam("sum") String sum, @RequestParam("duration") String duration,
                                             @RequestParam("FIO") String FIO, @RequestParam("username") String username,
                                             @RequestParam("salary") String salary){
        Credit credit = new Credit();

        credit.setNumber(BankUtils.generateNumber());
        credit.setSum(Integer.parseInt(sum));
        credit.setTerm(Integer.parseInt(duration));
        credit.setOwnerName(FIO);

        creditService.createCredit(credit, username, Integer.parseInt(salary));
        return ResponseEntity.ok("status: ok");
    }

    @PostMapping("/calculate")
    public ResponseEntity calculateCredit(){
        return ResponseEntity.ok("");
    }
}
