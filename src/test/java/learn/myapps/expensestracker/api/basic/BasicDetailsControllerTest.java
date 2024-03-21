package learn.myapps.expensestracker.api.basic;

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
class BasicDetailsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;
    private String getApi;
    private String deleteApi;
    private String findAll;

    private BasicDetails basicDetails;

    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/api/v1/basic", port);
        getApi = String.join("/", url, "{id}");
        deleteApi = String.join("/", url, "{id}");
        findAll = String.join("/", url, "all");
        basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
    }

    @Order(1)
    @Test
    void create() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, basicDetails, String.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(2)
    @Test
    void update() {
        ResponseEntity<CustomPageImpl<BasicDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<BasicDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getBasicId());
        long updateId = first.get().getBasicId();
        BasicDetails updateBasicDetails = new BasicDetails();
        BeanUtils.copyProperties(basicDetails, updateBasicDetails);
        updateBasicDetails.setBasicId(updateId);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<BasicDetails> requestBody = new HttpEntity<>(updateBasicDetails, httpHeaders);
        ResponseEntity<BasicDetails> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestBody, BasicDetails.class);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(updateBasicDetails, responseEntity.getBody());
    }

    @Order(3)
    @Test
    void findById() {
        ResponseEntity<CustomPageImpl<BasicDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<BasicDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getBasicId());
        long searchId = first.get().getBasicId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<BasicDetails> responseEntity = restTemplate.getForEntity(getApi, BasicDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Order(4)
    @Test
    void isDeleted() {
        ResponseEntity<CustomPageImpl<BasicDetails>> findAllresponseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<BasicDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getBasicId());
        long deleteId = first.get().getBasicId();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> requestBody = new HttpEntity<>("", httpHeaders);
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(deleteId));
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(deleteApi, HttpMethod.DELETE, requestBody, Boolean.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ResponseEntity<BasicDetails> findResponseEntity = restTemplate.getForEntity(getApi, BasicDetails.class, params);
        Assertions.assertNotNull(findResponseEntity);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, findResponseEntity.getStatusCode());
    }

    @Order(5)
    @Test
    void findAll() {
        ResponseEntity<CustomPageImpl<BasicDetails>> responseEntity = restTemplate.exchange(findAll, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getContent());
    }

    @Order(56)
    @Test
    void search() {
        final String search = url + "?search=lastUpdatedBy==Ravindra,description%test";
        ResponseEntity<CustomPageImpl<BasicDetails>> findAllresponseEntity = restTemplate.exchange(search, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertNotNull(findAllresponseEntity);
        Assertions.assertNotNull(findAllresponseEntity.getBody());
        Assertions.assertNotNull(findAllresponseEntity.getBody().getContent());
        Optional<BasicDetails> first = findAllresponseEntity.getBody().getContent().stream().findFirst();
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Assertions.assertNotNull(first.get().getBasicId());
        long searchId = first.get().getBasicId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(searchId));
        ResponseEntity<BasicDetails> responseEntity = restTemplate.getForEntity(getApi, BasicDetails.class, params);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }
}
