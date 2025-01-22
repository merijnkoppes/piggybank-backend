package com.testing.piggybank;

import com.testing.piggybank.account.AccountController;
import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.account.GetAccountsResponse;
import com.testing.piggybank.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class AccountTest {
    @Autowired
    AccountController accountController;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void getAccounts_withValidUserId_returnsListOfAccounts() {
        long userId = 1L; //maakt een big int user id verbonden met de 1ste bankaccount

        ResponseEntity<GetAccountsResponse> response = accountController.getAccounts(userId); //vraagt voor accounts (bankrekeningen) met een User id
        Assertions.assertEquals(1, response.getBody().getAccounts().size()); //Krijgt 1 account terug want we hebben maar 1 account(bankrekening) gemaakt met userID
    }

    @Test
    void getAccounts_withinValidUserId_returnEmptyListOfAccounts() {
        long userId = 99991L; //maakt een big int met een userID die niet bestaat (er bestaan er maar 4)

        ResponseEntity<GetAccountsResponse> response = accountController.getAccounts(userId); //vraagt voor accounts (bankrekeningen) met een User id
        Assertions.assertEquals(0, response.getBody().getAccounts().size()); //Krijgt 0 account terug want we hebben geen valid userID
    }

    @Test
    void getAccount_withValidAccountId_returnsAccount() {
        long accountId = 1L; //maakt een big int aan genaamd account id met de 1ste bankrekening, we noemen het account want we willen echt de bankrekening aanspreken

        ResponseEntity<AccountResponse> response = accountController.getAccount(accountId); //vraagt voor een accountID (bankrekening) met een account id
        Assertions.assertNotNull(response.getBody(), "Response cant be null"); //Resultaat kan niet null zijn want de accountid is valid


    }

    @Test
    void getAccount_withInValidAccountId_returnsAccount() {
        long accountId = 9991L; //Maakt een big int genaamd accountid met een id die niet bestaat

        ResponseEntity<AccountResponse> response = accountController.getAccount(accountId); //vraagt voor een account (bankrekening) met een account id
        Assertions.assertNull(response.getBody(), "Response is null"); //Resultaat is null want de account id bestaat niet.


    }
}
