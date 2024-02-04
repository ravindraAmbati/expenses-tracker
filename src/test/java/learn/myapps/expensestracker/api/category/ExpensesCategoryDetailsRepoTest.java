package learn.myapps.expensestracker.api.category;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class ExpensesCategoryDetailsRepoTest {

    @Autowired
    private ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;
    private ExpensesCategoryDetails expensesCategoryDetails;


    @BeforeEach
    void setUp() {
        //Given
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
        expensesCategoryDetailsRepo.deleteAll();
    }

    @Order(1)
    @Test
    void findById() {
        //when
        expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        Optional<ExpensesCategoryDetails> actual = expensesCategoryDetailsRepo.findById(5L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expensesCategoryDetails, actual.get());
    }

    @Test
    void create() {
        //when
        ExpensesCategoryDetails actual = expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expensesCategoryDetails, actual);
    }

    @Test
    void update() {
        //when
        expensesCategoryDetails = expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        List<String> expectedAlias = List.of("updated");
        expensesCategoryDetails.setAlias(expectedAlias);
        ExpensesCategoryDetails actual = expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        //then
        Assertions.assertNotNull(actual.getAlias());
        Assertions.assertEquals(expectedAlias, actual.getAlias());
    }

    @Order(2)
    @Test
    void deleteById() {
        //when
        expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        Optional<ExpensesCategoryDetails> actual = expensesCategoryDetailsRepo.findById(7L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expensesCategoryDetails, actual.get());
    }
}
