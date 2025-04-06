package edu.byui.apj.storefront.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<String> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        // TODO: fill in the details here
        return (userDetails != null) ?  ResponseEntity.ok(userDetails.getUsername()) :  ResponseEntity.noContent().build();

    }
}