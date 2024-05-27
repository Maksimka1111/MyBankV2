package com.example.MyBankService.controllers;

import com.example.MyBankService.models.Contribution;
import com.example.MyBankService.services.conrtibutions.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/contributions")
public class ContributionController {
    ContributionService contributionService;

    @Autowired
    public ContributionController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping("/createContribution")
    public ResponseEntity createContribution(@RequestParam("sum") String sum, @RequestParam("duration") String duration,
                                             @RequestParam("FIO") String FIO, @RequestParam("username") String username,
                                             @RequestParam("replenished") String replenished){
        Contribution contribution = new Contribution();
        contribution.setTerm(Integer.parseInt(duration));
        contribution.setSum(Integer.parseInt(sum));
        contribution.setReplenished(Boolean.parseBoolean(replenished));
        contribution.setOwnerName(FIO);

        contributionService.createContribution(contribution, username);
        return ResponseEntity.ok("status: ok");
    }

    @PostMapping("/calculate")
    public ResponseEntity calculateContribution(){
        return ResponseEntity.ok("");
    }
}
