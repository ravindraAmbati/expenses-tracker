package learn.myapps.expensestracker.api.payment;

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
import java.util.Optional;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentModeDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private String getApi;
    private String deleteApi;
    private String findAll;

    private PaymentModeDetails paymentModeDetails;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/api/v1/payment", port);
        getApi = String.join("/", url, "{id}");
        deleteApi = String.join("/", url, "{id}");
        findAll = String.join("/", url, "all");
        BasicDetails basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        paymentModeDetails = PaymentModeDetails.builder()
                .paymentMode("CARD")
                .cardDetails("4315***99")
                .cardType("C")
                .upiDetails("ravindra.ambati@upi.com")
                .accountDetails("")
                .basicDetails(basicDetails)
                .build();
    }

    @Order(31)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, paymentModeDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(32)
    @Test
    void update() {
        ResponseEntity<CustomPageImpl<PaymentModeDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<PaymentModeDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long updateId = first.get().getId();
        PaymentModeDetails updatePaymentModeDetails = new PaymentModeDetails();
        BeanUtils.copyProperties(paymentModeDetails, updatePaymentModeDetails);
        updatePaymentModeDetails.setId(updateId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<PaymentModeDetails> requestBody = new HttpEntity<>(updatePaymentModeDetails, httpHeaders);
        ResponseEntity<PaymentModeDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, PaymentModeDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(33)
    @Test
    void findById() {
        ResponseEntity<CustomPageImpl<PaymentModeDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<PaymentModeDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long searchId = first.get().getId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<PaymentModeDetails> responseEntity = restTemplate.getForEntity(getApi, PaymentModeDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(34)
    @Test
    void isDeleted() {
        ResponseEntity<CustomPageImpl<PaymentModeDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<PaymentModeDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
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
        ResponseEntity<PaymentModeDetails> findResponseEntity = restTemplate.getForEntity(getApi, PaymentModeDetails.class, params);
        Assertions.assertNotNull(findResponseEntity);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, findResponseEntity.getStatusCode());
    }

    @Order(35)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<PaymentModeDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
    }

    @Order(59)
    @Test
    void search() {
        final String search = url + "?search=paymentMode==card";
        ResponseEntity<CustomPageImpl<PaymentModeDetails>> findAllresponseEntity = restTemplate.exchange(search, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<PaymentModeDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getId());
        long searchId = first.get().getId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<PaymentModeDetails> responseEntity = restTemplate.getForEntity(getApi, PaymentModeDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }
}
