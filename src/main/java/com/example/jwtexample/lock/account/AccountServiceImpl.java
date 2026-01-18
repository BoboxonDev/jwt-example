package com.example.jwtexample.lock.account;

import com.example.jwtexample.common.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Transactional
    @Override
    public void reduceStock(Long fromId, Long toId, Double amount) {
        AccountEntity from = repository.findByIdForUpdate(fromId);
        AccountEntity to = repository.findByIdForUpdate(toId);

        if (from == null || to == null) {
            throw new ResourceNotFoundException("Account not found");
        }

        if (from.getBalance() < amount) {
            throw new ResourceNotFoundException("Insufficient balance!");
        }

        System.out.println(Thread.currentThread().getName() + "transferring " + amount);


        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        repository.save(from);
        repository.save(to);
    }
}
