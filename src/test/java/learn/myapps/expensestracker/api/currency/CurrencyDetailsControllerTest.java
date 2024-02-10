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

@TestClassOrder(ClassOrderer.ClassName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Order(1)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, currencyDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals("5", responseEntity.getBody());
    }

    @Order(2)
    @Test
    void update() {
        CurrencyDetails updateCurrencyDetails = new CurrencyDetails();
        BeanUtils.copyProperties(currencyDetails, updateCurrencyDetails);
        updateCurrencyDetails.setId(5L);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<CurrencyDetails> requestBody = new HttpEntity<>(updateCurrencyDetails, httpHeaders);
        ResponseEntity<CurrencyDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, CurrencyDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        BasicDetails updatedBasicDetails = new BasicDetails();
        BeanUtils.copyProperties(updateCurrencyDetails.getBasicDetails(), updatedBasicDetails);
        updatedBasicDetails.setBasicId(7L);
        updateCurrencyDetails.setBasicDetails(updatedBasicDetails);
        Assertions.assertEquals(updateCurrencyDetails, responseEntity.getBody());
    }

    @Order(3)
    @Test
    void findById() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "5");
        ResponseEntity<CurrencyDetails> responseEntity = restTemplate.getForEntity(getApi, CurrencyDetails.class, params);
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
        params.put("id", "5");
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(deleteApi, HttpMethod.DELETE, requestBody, Boolean.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Order(5)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<CurrencyDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
        Assertions.assertTrue(responseEntity.getBody().getContent().isEmpty());
    }
}
