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
import java.util.Optional;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
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

    @Order(11)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, expensesCategoryDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(12)
    @Test
    void update() {
        ResponseEntity<CustomPageImpl<ExpensesCategoryDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<ExpensesCategoryDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long updateId = first.get().getId();
        ExpensesCategoryDetails updatedExpensesCategory = new ExpensesCategoryDetails();
        BeanUtils.copyProperties(expensesCategoryDetails, updatedExpensesCategory);
        updatedExpensesCategory.setId(updateId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<ExpensesCategoryDetails> requestBody = new HttpEntity<>(updatedExpensesCategory, httpHeaders);
        ResponseEntity<ExpensesCategoryDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, ExpensesCategoryDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(13)
    @Test
    void findById() {
        ResponseEntity<CustomPageImpl<ExpensesCategoryDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<ExpensesCategoryDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long searchId = first.get().getId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<ExpensesCategoryDetails> responseEntity = restTemplate.getForEntity(getApi, ExpensesCategoryDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(14)
    @Test
    void isDeleted() {
        ResponseEntity<CustomPageImpl<ExpensesCategoryDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<ExpensesCategoryDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long deleteId = first.get().getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> requestBody = new HttpEntity<>("", httpHeaders);
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(deleteId));
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(deleteApi, HttpMethod.DELETE, requestBody, Boolean.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<ExpensesCategoryDetails> findResponseEntity = restTemplate.getForEntity(getApi, ExpensesCategoryDetails.class, params);
        Assertions.assertNotNull(findResponseEntity);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, findResponseEntity.getStatusCode());
    }

    @Order(15)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<ExpensesCategoryDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
    }
}
