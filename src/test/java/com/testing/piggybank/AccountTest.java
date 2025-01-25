package com.testing.piggybank;

import com.testing.piggybank.account.AccountController;
import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.account.GetAccountsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class AccountTest {

    @Autowired
    private AccountController accountController;

    @Test
        // Haal accounts op met ongeldige User ID
    void AccountTest2() {
        // Arrange
        long userId = 99991L;

        // Act
        ResponseEntity<GetAccountsResponse> response = accountController.getAccounts(userId);

        // Assert
        Assertions.assertEquals(0, response.getBody().getAccounts().size(), "Er mogen geen accounts terugkomen.");
    }

    @Test
        // Haal account op met ongeldige Account ID
    void AccountTest4() {
        // Arrange
        long accountId = 9991L;

        // Act
        ResponseEntity<AccountResponse> response = accountController.getAccount(accountId);

        // Assert
        Assertions.assertNull(response.getBody(), "Account moet null zijn.");
    }

    @Test
        // Haal account op met geldige Account ID
    void AccountTest3() {
        // Arrange
        long accountId = 1L;

        // Act
        ResponseEntity<AccountResponse> response = accountController.getAccount(accountId);

        // Assert
        Assertions.assertNotNull(response.getBody(), "Account mag niet null zijn.");
    }

    @Test
        // Haal accounts op met geldige User ID
    void AccountTest1() {
        // Arrange
        long userId = 1L;

        // Act
        ResponseEntity<GetAccountsResponse> response = accountController.getAccounts(userId);

        // Assert
        Assertions.assertEquals(1, response.getBody().getAccounts().size(), "Er moet precies 1 account terugkomen.");
    }
}
