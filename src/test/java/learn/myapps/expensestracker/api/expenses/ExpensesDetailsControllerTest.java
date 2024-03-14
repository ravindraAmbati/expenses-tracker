package learn.myapps.expensestracker.api.expenses;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
import learn.myapps.expensestracker.api.user.UserDetails;
import learn.myapps.expensestracker.util.CustomPageImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExpensesDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private String getApi;
    private String deleteApi;
    private String findAll;

    private ExpensesDetails expensesDetails;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/api/v1/expenses", port);
        getApi = String.join("/", url, "{id}");
        deleteApi = String.join("/", url, "{id}");
        findAll = String.join("/", url, "all");
        BasicDetails basicDetails = BasicDetails.builder().description("test").isDeleted(false).lastUpdatedBy("Ravindra").lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00")).build();
        CurrencyDetails currencyDetails = CurrencyDetails.builder().currency("EUR").exchangeRate(new BigDecimal("90.854321")).defaultCurrency("INR").basicDetails(basicDetails).build();
        ExpensesCategoryDetails expensesCategoryDetails = ExpensesCategoryDetails.builder().expensesCategory("EDUCATION").alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification")).basicDetails(basicDetails).build();
        PaymentModeDetails paymentModeDetails = PaymentModeDetails.builder().paymentMode("CARD").cardDetails("4315***99").cardType("C").upiDetails("ravindra.ambati@upi.com").accountDetails("").basicDetails(basicDetails).build();
        UserDetails userDetails = UserDetails.builder().firstName("Ravindra").lastName("Ambati").emailId("ravindra.reddy.ambati@outlook.in").mobileNo("+919849547160").basicDetails(basicDetails).build();
        expensesDetails = ExpensesDetails.builder().amount(new BigDecimal("123.45")).paidBy(userDetails).paidTo("Ravindra").dateAndTime(LocalDateTime.parse("2024-01-13T20:00:00")).paymentMode(paymentModeDetails).currency(currencyDetails).category(expensesCategoryDetails).basicDetails(basicDetails).build();
    }

    @Order(51)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, expensesDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(52)
    @Test
    void update() {
        ResponseEntity<CustomPageImpl<ExpensesDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<ExpensesDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long updateId = first.get().getId();
        ExpensesDetails updateExpensesDetails = new ExpensesDetails();
        BeanUtils.copyProperties(expensesDetails, updateExpensesDetails);
        updateExpensesDetails.setId(updateId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<ExpensesDetails> requestBody = new HttpEntity<>(updateExpensesDetails, httpHeaders);
        ResponseEntity<ExpensesDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, ExpensesDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(53)
    @Test
    void findById() {
        ResponseEntity<CustomPageImpl<ExpensesDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<ExpensesDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long searchId = first.get().getId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<ExpensesDetails> responseEntity = restTemplate.getForEntity(getApi, ExpensesDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(54)
    @Test
    void isDeleted() {
        ResponseEntity<CustomPageImpl<ExpensesDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<ExpensesDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
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
        ResponseEntity<ExpensesDetails> findResponseEntity = restTemplate.getForEntity(getApi, ExpensesDetails.class, params);
        Assertions.assertNotNull(findResponseEntity);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, findResponseEntity.getStatusCode());
    }

    @Order(55)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<ExpensesDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
    }
}
