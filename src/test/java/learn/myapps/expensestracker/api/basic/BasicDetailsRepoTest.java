package learn.myapps.expensestracker.api.basic;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

    @Order(61)
    @Test
    void findById() {
        //when
        basicDetailsRepo.save(basicDetails);
        Iterable<BasicDetails> all = basicDetailsRepo.findAll();
        Optional<BasicDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long searchId = first.get().getBasicId();
        Optional<BasicDetails> actual = basicDetailsRepo.findById(searchId);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(basicDetails, actual.get());
    }

    @Order(62)
    @Test
    void create() {
        //when
        BasicDetails actual = basicDetailsRepo.save(basicDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(basicDetails, actual);
    }

    @Order(63)
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

    @Order(64)
    @Test
    void deleteById() {
        //when
        basicDetailsRepo.save(basicDetails);
        Iterable<BasicDetails> all = basicDetailsRepo.findAll();
        Optional<BasicDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long deleteId = first.get().getBasicId();
        Optional<BasicDetails> actual = basicDetailsRepo.findById(deleteId);
        //then
        Assertions.assertTrue(actual.isPresent());
        basicDetailsRepo.deleteById(deleteId);
        actual = basicDetailsRepo.findById(deleteId);
        Assertions.assertFalse(actual.isPresent());
    }
}
