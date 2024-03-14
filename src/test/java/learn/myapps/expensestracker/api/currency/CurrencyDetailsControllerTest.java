package learn.myapps.expensestracker.api.currency;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private String getApi;
    private String deleteApi;
    private String findAll;

    private CurrencyDetails currencyDetails;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/api/v1/currency", port);
        getApi = String.join("/", url, "{id}");
        deleteApi = String.join("/", url, "{id}");
        findAll = String.join("/", url, "all");
        BasicDetails basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        currencyDetails = CurrencyDetails.builder()
                .currency("EUR")
                .exchangeRate(new BigDecimal("90.854321"))
                .defaultCurrency("INR")
                .basicDetails(basicDetails)
                .build();
    }

    @Order(21)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, currencyDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(22)
    @Test
    void update() {
        ResponseEntity<CustomPageImpl<CurrencyDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<CurrencyDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long updateId = first.get().getId();
        CurrencyDetails updateCurrencyDetails = new CurrencyDetails();
        BeanUtils.copyProperties(currencyDetails, updateCurrencyDetails);
        updateCurrencyDetails.setId(updateId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CurrencyDetails> requestBody = new HttpEntity<>(updateCurrencyDetails, httpHeaders);
        ResponseEntity<CurrencyDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, CurrencyDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(23)
    @Test
    void findById() {
        ResponseEntity<CustomPageImpl<CurrencyDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<CurrencyDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long searchId = first.get().getId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<CurrencyDetails> responseEntity = restTemplate.getForEntity(getApi, CurrencyDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(24)
    @Test
    void isDeleted() {
        ResponseEntity<CustomPageImpl<CurrencyDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<CurrencyDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
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
        ResponseEntity<CurrencyDetails> findResponseEntity = restTemplate.getForEntity(getApi, CurrencyDetails.class, params);
        Assertions.assertNotNull(findResponseEntity);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, findResponseEntity.getStatusCode());
    }

    @Order(25)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<CurrencyDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
    }
}
