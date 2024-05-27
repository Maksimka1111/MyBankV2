package com.example.SprignReactOauth2.Controllers;

import com.example.SprignReactOauth2.Entities.MyUser;
import com.example.SprignReactOauth2.Excpetion.PasswordNotMatchesException;
import com.example.SprignReactOauth2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    UserService userService;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MyUser user){
//        if (userService.findUser(user) == null){
            return ResponseEntity.ok(userService.save(user).getUsername());
        //}
        //return ResponseEntity.ok("Exists");
    }
    @GetMapping("/login")
    public String loginPage() {
        return "loginM";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("userForm") @Validated MyUser userForm, BindingResult bindingResult)
            throws PasswordNotMatchesException {
        var user = userService.findUser(userForm);
        System.out.println(userForm.getPassword());
        System.out.println(user.getPassword());
        System.out.println(passwordEncoder.matches(userForm.getPassword(), user.getPassword()));
        if (bindingResult.hasErrors() || user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        else if (!passwordEncoder.matches(userForm.getPassword(), user.getPassword())){
            throw new PasswordNotMatchesException("Password is not correct");
        }

        return "ok";
    }
    @DeleteMapping("/delUser")
    public ResponseEntity deleteUser(@RequestParam("username")String username){
        userService.delUser(username);
        return ResponseEntity.ok("status: ok");
    }

    @GetMapping("/checkAccessProfile")
    public ResponseEntity checkAccessProfile(){
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/checkAccessAdmin")
    public ResponseEntity checkAccessAdmin(){
        return ResponseEntity.ok("status: ok");
    }
}
