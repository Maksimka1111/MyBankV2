package com.example.MyBankService;

import com.example.MyBankService.services.credits.CreditServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreditServiceTest {
    @Mock
    CreditServiceImpl creditService;
    @Test
    void checkCreditCalculations(){
        var credit = creditService.calculateCredit(10000, 6);
        Assertions.assertEquals(credit, 18.9);
    }
}