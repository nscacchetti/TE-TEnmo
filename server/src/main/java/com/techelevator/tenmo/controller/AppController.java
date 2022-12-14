package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()") //check to be authenticated
public class AppController {

    JdbcTemplate jdbcTemplate;
    private JdbcUserDao jdbcUserDao;
    private JdbcAccountDao jdbcAccountDao;

    public AppController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserDao = new JdbcUserDao(this.jdbcTemplate);
        this.jdbcAccountDao = new JdbcAccountDao(this.jdbcTemplate);
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

}
