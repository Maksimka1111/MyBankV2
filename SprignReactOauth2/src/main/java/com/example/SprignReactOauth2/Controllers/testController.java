package com.example.SprignReactOauth2.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class testController {
    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok("ok");
    }
}
