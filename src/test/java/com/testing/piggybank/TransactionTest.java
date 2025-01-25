package com.testing.piggybank;

import com.testing.piggybank.account.AccountController;
import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.CreateTransactionRequest;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import com.testing.piggybank.transaction.TransactionController;
import com.testing.piggybank.transaction.TransactionRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class TransactionTest {

    @Autowired private TransactionController transactionController;

    @Autowired private TransactionRepository transactionRepository;

    @Autowired private AccountController accountController;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
    }

    @Test
    // Maak geldige transactie en haal transacties op
    void TransactieTest1() {
        // Arrange
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setCurrency(Currency.EURO);
        request.setReceiverAccountId(1L);
        request.setSenderAccountId(2L);
        request.setDescription("Integratietest: Maak geldige transactie en haal transacties op");
        request.setAmount(new BigDecimal("100"));

        // Act
        transactionController.createTransaction(request);
        ResponseEntity<GetTransactionsResponse> result = transactionController.getTransactions(null, 1L);

        // Assert
        Assertions.assertEquals(1, result.getBody().getTransactions().size());
    }


    @Test
    // Maak geldige transactie en sla transactie op in database
    void TransactieTest2() {
        // Arrange
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setCurrency(Currency.EURO);
        request.setReceiverAccountId(1L);
        request.setSenderAccountId(2L);
        request.setDescription("Intergratietest: Maak geldige transactie en sla transactie op in database");
        request.setAmount(new BigDecimal("100"));

        // Act
        transactionController.createTransaction(request);

        // Assert
        List<Transaction> result = transactionRepository.findAllByReceiverAccount_Id(1L);
        Assertions.assertEquals(1, result.size());
    }

    @Test
// Valideer ongeldige transactie
    void TransactieTest3() {
        // Arrange
        long senderAccountId = 1L;
        long receiverAccountId = 2L;

        // Haal saldo van de verzender op via de AccountController
        ResponseEntity<AccountResponse> accountResponse = accountController.getAccount(senderAccountId);
        Assertions.assertNotNull(accountResponse.getBody(), "Accountresponse mag niet null zijn.");
        BigDecimal senderInitialBalance = accountResponse.getBody().getBalance();
        BigDecimal transferAmount = senderInitialBalance.multiply(new BigDecimal("2"));

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setCurrency(Currency.EURO);
        request.setReceiverAccountId(receiverAccountId);
        request.setSenderAccountId(senderAccountId);
        request.setDescription("Transactietest: Valideer ongeldige transactie");
        request.setAmount(transferAmount);

        // Act
        ResponseEntity<HttpStatus> response = transactionController.createTransaction(request);

        // Assert
        Assertions.assertNotEquals(HttpStatus.OK, response.getStatusCode(), "De ongeldige transactie mag niet slagen.");

        // Controleer dat er geen transacties zijn aangemaakt
        List<Transaction> result = transactionRepository.findAllByReceiverAccount_Id(receiverAccountId);
        Assertions.assertEquals(0, result.size(), "Er mogen geen transacties zijn aangemaakt.");
    }



}