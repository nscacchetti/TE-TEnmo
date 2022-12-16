package com.techelevator;
import com.techelevator.tenmo.model.*;
import junit.runner.Version;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterUserTest {

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private TokenDTO user1Token;
    private TokenDTO user2Token;

    // run before all tests to set-up user data for all testing
//    https://www.baeldung.com/junit-5-test-order
    @Test

    public void Test1_register_users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RegisterUserDTO newUser = new RegisterUserDTO();
        System.out.println("JUnit version is: " + Version.id());
        //setup user1 for testing
        newUser.setUsername("user1");
        newUser.setPassword("password");

        HttpEntity<RegisterUserDTO> entity = new HttpEntity<>(newUser, headers);
        try {
            restTemplate.postForObject(API_BASE_URL + "/register", entity, Void.class); //https://stackoverflow.com/questions/30452318/how-to-handle-empty-response-in-spring-resttemplate
        } catch (RestClientResponseException e) {
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }

        //setup user2 for testing
        newUser.setUsername("user2");
        newUser.setPassword("password2");

        entity = new HttpEntity<>(newUser, headers);
        try {
            restTemplate.postForObject(API_BASE_URL + "/register", entity, Void.class); //https://stackoverflow.com/questions/30452318/how-to-handle-empty-response-in-spring-resttemplate
        } catch (RestClientResponseException e) {
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void Test2_login_users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginDTO newUser = new LoginDTO();

        //login user1 for testing
        newUser.setUsername("user1");
        newUser.setPassword("password");

        HttpEntity<LoginDTO> entity = new HttpEntity<>(newUser, headers);
        try {
            user1Token = restTemplate.postForObject(API_BASE_URL + "/login", entity, TokenDTO.class);
        } catch (RestClientResponseException e) {
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
        System.out.println(user1Token);

        //login user2 for testing
        newUser.setUsername("user2");
        newUser.setPassword("password2");

        entity = new HttpEntity<>(newUser, headers);
        try {
            user2Token = restTemplate.postForObject(API_BASE_URL + "/login", entity, TokenDTO.class);
        } catch (RestClientResponseException e) {
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
        System.out.println(user2Token);
        assertNotNull(user1Token);
    }
}
