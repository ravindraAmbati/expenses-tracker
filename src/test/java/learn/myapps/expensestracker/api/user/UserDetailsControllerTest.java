package learn.myapps.expensestracker.api.user;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.util.CustomPageImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@TestClassOrder(ClassOrderer.ClassName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private String getApi;
    private String deleteApi;
    private String findAll;

    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/api/v1/user", port);
        getApi = String.join("/", url, "{id}");
        deleteApi = String.join("/", url, "{id}");
        findAll = String.join("/", url, "all");
        BasicDetails basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        userDetails = UserDetails.builder()
                .firstName("Ravindra")
                .lastName("Ambati")
                .emailId("ravindra.reddy.ambati@outlook.in")
                .mobileNo("+919849547160")
                .basicDetails(basicDetails)
                .build();
    }

    @Order(1)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, userDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals("30", responseEntity.getBody());
    }

    @Order(2)
    @Test
    void update() {
        UserDetails updateUserDetails = new UserDetails();
        BeanUtils.copyProperties(userDetails, updateUserDetails);
        updateUserDetails.setId(30L);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<UserDetails> requestBody = new HttpEntity<>(updateUserDetails, httpHeaders);
        ResponseEntity<UserDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, UserDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        BasicDetails updatedBasicDetails = new BasicDetails();
        BeanUtils.copyProperties(updateUserDetails.getBasicDetails(), updatedBasicDetails);
        updatedBasicDetails.setBasicId(32L);
        updateUserDetails.setBasicDetails(updatedBasicDetails);
        Assertions.assertEquals(updateUserDetails, responseEntity.getBody());
    }

    @Order(3)
    @Test
    void findById() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "30");
        ResponseEntity<UserDetails> responseEntity = restTemplate.getForEntity(getApi, UserDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(responseEntity.getBody(), responseEntity.getBody());
    }

    @Order(4)
    @Test
    void isDeleted() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> requestBody = new HttpEntity<>("", httpHeaders);
        Map<String, String> params = new HashMap<>();
        params.put("id", "30");
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(deleteApi, HttpMethod.DELETE, requestBody, Boolean.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Order(5)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<UserDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
    }
}
