
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.model.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import com.techelevator.tenmo.model.User;
import org.junit.runners.MethodSorters;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppControllerTests {
//@TODO ask andy about forks
    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private TokenDTO user1Token;
    private TokenDTO user2Token;
    private TokenDTO user3Token;


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


@Before
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
        assertNotNull(user2Token);

        //login user3 for testing
        newUser.setUsername("user3");
        newUser.setPassword("password2");

        entity = new HttpEntity<>(newUser, headers);
        try {
            user3Token = restTemplate.postForObject(API_BASE_URL + "/login", entity, TokenDTO.class);
        } catch (RestClientResponseException e) {
            e.printStackTrace();
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
        System.out.println(user3Token);

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

        headers = new HttpHeaders();
        System.out.println("user3Token equals " + user3Token.getToken());
        headers.setBearerAuth(user3Token.getToken());
        entity = new HttpEntity<>(headers);
        restTemplate.exchange(API_BASE_URL + "/account/create", HttpMethod.POST, entity, Void.class);


        BigDecimal newAccountBalance = restTemplate.exchange(API_BASE_URL + "/account/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();
        boolean is1000= newAccountBalance.compareTo(BigDecimal.valueOf(1000))==0;
        assertTrue(is1000);
    }

    @Test
//@TODO bug where running all tests causes transfer to not deduct
    public void Test4_a_Test_4_b_test_create_and_get_by_transfer() {
        Transfer transfer = new Transfer(BigDecimal.valueOf(200.00), 1002, 1001);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(transfer, headers);
        BigDecimal orgAccountBalance = restTemplate.exchange(API_BASE_URL + "/account/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();

//        int newTransferId= restTemplate.exchange(API_BASE_URL + "/transfers/new", HttpMethod.POST, entity, Integer.class).getBody().intValue();
        int newTransferId= restTemplate.exchange(API_BASE_URL + "/transfers/new", HttpMethod.POST, entity, Integer.class).getBody();

        // we look up the new transfer id in the db using the api for /transfers/{id}
        Transfer transferInDB = restTemplate.exchange(API_BASE_URL + "/transfers/" + newTransferId, HttpMethod.GET, entity, Transfer.class).getBody();
        //The app controller should find no transfer object with that id and it will return null. check if the new object is null.
        assertEquals(transfer.getTransferAmount().compareTo(transferInDB.getTransferAmount()),0);
        assertEquals(transfer.getUserFrom(),transferInDB.getUserFrom());
        assertEquals(transfer.getUserTo(),transferInDB.getUserTo());
//        @TODO check balances
        BigDecimal newAccountBalance = restTemplate.exchange(API_BASE_URL + "/account/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();
        BigDecimal expected = orgAccountBalance.subtract(BigDecimal.valueOf(200));
        BigDecimal actual = newAccountBalance;
        assertEquals(0,actual.compareTo(expected));
    }
    @Test
    public void Test4_c_list_of_my_transfers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(headers);

        List<Transfer> transferList= restTemplate.exchange(API_BASE_URL + "/transfers", HttpMethod.GET, entity, List.class).getBody();

        assertEquals(transferList.size(),1);

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

    @Test
    public void Test6a_list_of_users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
//        Use resttemplate to send the http request, send me a response containing class List, give me the list in the body (.getbody)
       String response = restTemplate.exchange(API_BASE_URL + "/list_of_users", HttpMethod.GET, entity, String.class).getBody();
        System.out.println("TEST: ");
        System.out.println(response);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode json = objectMapper.readTree(response);
            for (int i=0; i < json.size(); i++) {
                String currentUserId = json.path(i).path("id").asText();
                System.out.println(currentUserId);
            }

            String idOfFirst = json.path(0).path("id").asText();
            assertEquals("1001", idOfFirst);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


@Test
    public void Test6_list_of_users() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(user1Token.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
//        Use resttemplate to send the http request, send me a response containing class List, give me the list in the body (.getbody)
        List<User> listOfUsers= restTemplate.exchange(API_BASE_URL + "/list_of_users", HttpMethod.GET, entity, ArrayList.class).getBody();
    System.out.println(listOfUsers.toString());
//        check that the list contains only/exactly user1 and user2
        assertEquals(3,listOfUsers.size());

//        check user1
    System.out.println(listOfUsers.get(0));
    ObjectMapper mapper= new ObjectMapper();

    User firstUsers = listOfUsers.get(0);

//    User firstUser = mapper.convertValue(listOfUsers.get(0), new TypeReference<User>(){});
//User firstUse
//    @TODO convert response object to USER class currently just pulling int from string
    String user1MapString = String.valueOf(listOfUsers.get(0));
    System.out.println("userMap= "+user1MapString);
        int expected = 1001;
        int actual = Integer.parseInt(user1MapString.substring(4,8));

        assertEquals(expected, actual);

//        check user2
    String user2MapString = String.valueOf(listOfUsers.get(1));
    System.out.println("userMap= "+user2MapString);
    expected = 1002;
    actual = Integer.parseInt(user2MapString.substring(4,8));

    assertEquals(expected, actual);
//      equivilent statement  assertEquals(null,transferIdInDB);
    }
}





