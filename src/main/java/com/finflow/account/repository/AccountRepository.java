package com.finflow.account.repository;

import com.finflow.account.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Account> findByAccountNumber(String accountNumber);

    @Modifying
    @Query(value = """
            UPDATE ff_account 
            SET balance = balance - :amount 
            WHERE account_number=:accountNumber 
            AND balance >= :amount """,
            nativeQuery = true)
    int amountTransfer(@Param("accountNumber") String accountNumber,@Param("amount") BigDecimal amount);

    boolean existsByCustomer_Id(Long customerId);

    List<Account> findByCustomer_CustomerNumber(String customerNumber);

}
