package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()") //check to be authenticated
public class AppController {

    JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;
    private JdbcAccountDao jdbcAccountDao;
    private JdbcTransferDao jdbcTransferDao;

    public AppController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = new JdbcUserDao(this.jdbcTemplate);
        this.jdbcAccountDao = new JdbcAccountDao(this.jdbcTemplate);
        this.jdbcTransferDao = new JdbcTransferDao(this.jdbcTemplate);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public void accountCreate(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username =  securityContext.getAuthentication().getName();
        int userId = jdbcUserDao.findIdByUsername(username);

        Account account = new Account();

        account.setUserID(userId);
        account.setBalance(BigDecimal.valueOf(1000));
        jdbcAccountDao.create(account);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username =  securityContext.getAuthentication().getName();
        int userId = jdbcUserDao.findIdByUsername(username);

        Account account = jdbcAccountDao.get(userId);
        BigDecimal balance = account.getBalance();

        return balance;
    }

    @ResponseStatus (HttpStatus.OK)
    @RequestMapping(value = "/list_of_users", method = RequestMethod.GET)
    public List<User> users(){
        return jdbcUserDao.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/transfers", method = RequestMethod.GET)
    public List<Transfer> transfersList(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username =  securityContext.getAuthentication().getName();
        int userId = jdbcUserDao.findIdByUsername(username);

        List<Transfer> transfer = jdbcTransferDao.getAll();

        return transfer;
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping(value = "/transfers/new", method = RequestMethod.POST) //cannot transfer bucks from another's account
    public void newTransfer(@Valid @RequestBody Transfer transfer) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username =  securityContext.getAuthentication().getName();
        boolean checkUser = username.equals(transfer.getUserFrom());
        if(!checkUser){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Transfer from other user not permitted"
            );
        }
        // need to transfer the bucks, need to do exception handling for decrease amt in Account.java
        jdbcAccountDao.

        jdbcTransferDao.addTransfer(transfer);

    }
}



