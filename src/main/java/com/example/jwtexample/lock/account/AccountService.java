package com.example.jwtexample.lock.account;

public interface AccountService {

    void reduceStock(Long fromId, Long toId, Double amount);
}
