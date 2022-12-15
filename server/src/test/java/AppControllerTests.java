import ch.qos.logback.core.subst.Token;
import com.techelevator.tenmo.controller.AuthenticationController;
import com.techelevator.tenmo.model.LoginDTO;
import com.techelevator.tenmo.model.RegisterUserDTO;
import com.techelevator.tenmo.model.TokenDTO;
import com.techelevator.tenmo.model.Transfer;
import org.apiguardian.api.API;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AppControllerTests {

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private TokenDTO user1Token;
    private TokenDTO user2Token;

    // run before all tests to set-up user data for all testing
    @Test
    public void setup_users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RegisterUserDTO newUser = new RegisterUserDTO();

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

    @Before
    public void login_users() {
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

    }

    @Test
    public void test_create_account() {
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

    }

    @Test
    public void test_create_transfer() {
        Transfer transfer = new Transfer(BigDecimal.valueOf(200.00), 1002, 1001);
        HttpHeaders headers = new HttpHeaders();
        System.out.println("user1Token equals " + user1Token.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(transfer, headers);
        restTemplate.exchange(API_BASE_URL + "/transfers/new", HttpMethod.POST, entity, Void.class);


    }

//    @Test
//    public void fail_test_create_transfer(){
//        Transfer transfer = new Transfer(BigDecimal.valueOf(200.00), 1002, 1002);
//        HttpHeaders headers = new HttpHeaders();
//        System.out.println("user1Token equals " + user1Token.getToken());
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(user1Token.getToken());
//        HttpEntity entity = new HttpEntity<>(transfer, headers);
//        restTemplate.exchange(API_BASE_URL + "/transfers/new", HttpMethod.POST, entity, Void.class);
//    }

}





