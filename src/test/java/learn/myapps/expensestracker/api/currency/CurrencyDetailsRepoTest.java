package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Order(81)
    @Test
    void findById() {
        //when
        currencyDetailsRepo.save(currencyDetails);
        Iterable<CurrencyDetails> all = currencyDetailsRepo.findAll();
        Optional<CurrencyDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long searchId = first.get().getId();
        Optional<CurrencyDetails> actual = currencyDetailsRepo.findById(searchId);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(currencyDetails, actual.get());
    }

    @Order(82)
    @Test
    void create() {
        //when
        CurrencyDetails actual = currencyDetailsRepo.save(currencyDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(currencyDetails, actual);
    }

    @Order(83)
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

    @Order(84)
    @Test
    void deleteById() {
        //when
        currencyDetailsRepo.save(currencyDetails);
        Iterable<CurrencyDetails> all = currencyDetailsRepo.findAll();
        Optional<CurrencyDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long deleteId = first.get().getId();
        Optional<CurrencyDetails> actual = currencyDetailsRepo.findById(deleteId);
        //then
        Assertions.assertTrue(actual.isPresent());
        currencyDetailsRepo.deleteById(deleteId);
        actual = currencyDetailsRepo.findById(deleteId);
        Assertions.assertFalse(actual.isPresent());
    }
}
