
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
public class AppControllerTests {

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private TokenDTO user1Token;
    private TokenDTO user2Token;

    // run before all tests to set-up user data for all testing
//    https://www.baeldung.com/junit-5-test-order
/*    @Test

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

    }*/



    public void Test2_1_login_users() {
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
@Before
    public void Test6_6_login_users() {
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
    @Test

    public void Test3_and_Test3_1_test_create_account_and_check_balance() {
        HttpHeaders headers = new HttpHeaders();
        System.out.println("user1Token equals " + user1Token.getToken());
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        restTemplate.exchange(API_BASE_URL + "/account/create", HttpMethod.POST, entity, Void.class);

        headers = new HttpHeaders();
        System.out.println("user1Token equals " + user2Token.getToken());
        headers.setBearerAuth(user2Token.getToken());
        entity = new HttpEntity<>(headers);
        restTemplate.exchange(API_BASE_URL + "/account/create", HttpMethod.POST, entity, Void.class);



        BigDecimal newAccountBalance = restTemplate.exchange(API_BASE_URL + "/account/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();
        boolean is1000= newAccountBalance.compareTo(BigDecimal.valueOf(1000))==0;
        assertTrue(is1000);
    }

    @Test

    public void Test4_test_create_transfer() {
        Transfer transfer = new Transfer(BigDecimal.valueOf(200.00), 1002, 1001);
        HttpHeaders headers = new HttpHeaders();
        System.out.println("user1Token equals " + user1Token.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(transfer, headers);
        restTemplate.exchange(API_BASE_URL + "/transfers/new", HttpMethod.POST, entity, Void.class);


    }
    @Test

    public void Test5_test_self_no_transfer() {
        Transfer transfer = new Transfer(BigDecimal.valueOf(200.00), 1001, 1001);
        HttpHeaders headers = new HttpHeaders();
        System.out.println("user1Token equals " + user1Token.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(transfer, headers);
        // sending a transfer to the appcontroller to post, then it is returning the id of the new transfer
        int newTransferId= restTemplate.exchange(API_BASE_URL + "/transfers/new", HttpMethod.POST, entity, Integer.class).getBody().intValue();
        // we look up the new transfer id in the db using the api for /transfers/{id}
        Transfer transferIdInDB = restTemplate.exchange(API_BASE_URL + "/transfers/" + newTransferId, HttpMethod.GET, entity, Transfer.class).getBody();
        //The app controller should find no transfer object with that id and it will return null. check if the new object is null.
        assertNull(transferIdInDB);
//      equivilent statement  assertEquals(null,transferIdInDB);
    }


}





