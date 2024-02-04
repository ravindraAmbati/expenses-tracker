package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class CurrencyDetailsRepoTest {

    @Autowired
    private CurrencyDetailsRepo currencyDetailsRepo;
    private CurrencyDetails currencyDetails;


    @BeforeEach
    void setUp() {
        //Given
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
        currencyDetailsRepo.deleteAll();
    }

    @Order(1)
    @Test
    void findById() {
        //when
        currencyDetailsRepo.save(currencyDetails);
        Optional<CurrencyDetails> actual = currencyDetailsRepo.findById(13L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(currencyDetails, actual.get());
    }

    @Test
    void create() {
        //when
        CurrencyDetails actual = currencyDetailsRepo.save(currencyDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(currencyDetails, actual);
    }

    @Test
    void update() {
        //when
        currencyDetails = currencyDetailsRepo.save(currencyDetails);
        BigDecimal updatedExchangeRate = new BigDecimal("3.756432");
        currencyDetails.setExchangeRate(updatedExchangeRate);
        CurrencyDetails actual = currencyDetailsRepo.save(currencyDetails);
        //then
        Assertions.assertEquals(updatedExchangeRate, actual.getExchangeRate());
    }

    @Order(2)
    @Test
    void deleteById() {
        //when
        currencyDetailsRepo.save(currencyDetails);
        Optional<CurrencyDetails> actual = currencyDetailsRepo.findById(15L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(currencyDetails, actual.get());
    }
}
