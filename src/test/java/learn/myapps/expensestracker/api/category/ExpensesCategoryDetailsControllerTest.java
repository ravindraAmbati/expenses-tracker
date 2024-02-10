package learn.myapps.expensestracker.api.category;

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
import java.util.List;
import java.util.Map;

@TestClassOrder(ClassOrderer.ClassName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExpensesCategoryDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private String getApi;
    private String deleteApi;
    private String findAll;

    private ExpensesCategoryDetails expensesCategoryDetails;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/api/v1/category", port);
        getApi = String.join("/", url, "{id}");
        deleteApi = String.join("/", url, "{id}");
        findAll = String.join("/", url, "all");
        BasicDetails basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        expensesCategoryDetails = ExpensesCategoryDetails.builder()
                .expensesCategory("EDUCATION")
                .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                .basicDetails(basicDetails)
                .build();
    }

    @Order(1)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, expensesCategoryDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals("2", responseEntity.getBody());
    }

    @Order(2)
    @Test
    void update() {
        ExpensesCategoryDetails updatedExpensesCategory = new ExpensesCategoryDetails();
        BeanUtils.copyProperties(expensesCategoryDetails, updatedExpensesCategory);
        updatedExpensesCategory.setId(2L);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<ExpensesCategoryDetails> requestBody = new HttpEntity<>(updatedExpensesCategory, httpHeaders);
        ResponseEntity<ExpensesCategoryDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, ExpensesCategoryDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        BasicDetails updatedBasicDetails = new BasicDetails();
        BeanUtils.copyProperties(updatedExpensesCategory.getBasicDetails(), updatedBasicDetails);
        updatedBasicDetails.setBasicId(4L);
        updatedExpensesCategory.setBasicDetails(updatedBasicDetails);
        Assertions.assertEquals(updatedExpensesCategory, responseEntity.getBody());
    }

    @Order(3)
    @Test
    void findById() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "2");
        ResponseEntity<ExpensesCategoryDetails> responseEntity = restTemplate.getForEntity(getApi, ExpensesCategoryDetails.class, params);
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
        params.put("id", "2");
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(deleteApi, HttpMethod.DELETE, requestBody, Boolean.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Order(5)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<ExpensesCategoryDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
        Assertions.assertTrue(responseEntity.getBody().getContent().isEmpty());
    }
}
