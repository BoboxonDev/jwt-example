package com.example.jwtexample.lock.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping("/transfer")
    ResponseEntity<Void> buyProduct(@PathVariable Long from,
                                    @PathVariable Long to,
                                    Double amount) {
        service.reduceStock(from, to, amount);
        return ResponseEntity.ok().build();
    }
}
