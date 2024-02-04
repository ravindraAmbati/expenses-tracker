package learn.myapps.expensestracker.api.basic;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class BasicDetailsRepoTest {


    @Autowired
    private BasicDetailsRepo basicDetailsRepo;
    private BasicDetails basicDetails;

    @BeforeEach
    void setUp() {
        //Given
        basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        basicDetailsRepo.deleteAll();
    }

    @Order(1)
    @Test
    void findById() {
        //when
        basicDetailsRepo.save(basicDetails);
        Optional<BasicDetails> actual = basicDetailsRepo.findById(1L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(basicDetails, actual.get());
    }

    @Test
    void create() {
        //when
        BasicDetails actual = basicDetailsRepo.save(basicDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(basicDetails, actual);
    }

    @Test
    void update() {
        //when
        basicDetails = basicDetailsRepo.save(basicDetails);
        basicDetails.setDescription("updated");
        BasicDetails actual = basicDetailsRepo.save(basicDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals("updated", actual.getDescription());
    }

    @Order(2)
    @Test
    void deleteById() {
        //when
        basicDetailsRepo.save(basicDetails);
        Optional<BasicDetails> actual = basicDetailsRepo.findById(2L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(basicDetails, actual.get());
    }
}
