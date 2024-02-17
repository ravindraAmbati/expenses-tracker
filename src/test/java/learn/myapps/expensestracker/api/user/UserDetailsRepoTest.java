package learn.myapps.expensestracker.api.user;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
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
class UserDetailsRepoTest {

    @Autowired
    private UserDetailsRepo userDetailsRepo;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        //Given
        BasicDetails basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        userDetails = UserDetails.builder()
                .firstName("Ravindra")
                .lastName("Ambati")
                .emailId("ravindra.reddy.ambati@outlook.in")
                .mobileNo("+919849547160")
                .basicDetails(basicDetails)
                .build();
        userDetailsRepo.deleteAll();
    }

    @Order(111)
    @Test
    void findById() {
        //when
        userDetailsRepo.save(userDetails);
        Iterable<UserDetails> all = userDetailsRepo.findAll();
        Optional<UserDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long searchId = first.get().getId();
        Optional<UserDetails> actual = userDetailsRepo.findById(searchId);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(userDetails, actual.get());
    }

    @Order(112)
    @Test
    void create() {
        //when
        UserDetails actual = userDetailsRepo.save(userDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(userDetails, actual);
    }

    @Order(113)
    @Test
    void update() {
        //when
        userDetails = userDetailsRepo.save(userDetails);
        String updatedEmail = "test@mail.com";
        userDetails.setEmailId(updatedEmail);
        UserDetails actual = userDetailsRepo.save(userDetails);
        //then
        Assertions.assertNotNull(actual.getEmailId());
        Assertions.assertEquals(updatedEmail, actual.getEmailId());
    }

    @Order(114)
    @Test
    void deleteById() {
        //when
        userDetailsRepo.save(userDetails);
        Iterable<UserDetails> all = userDetailsRepo.findAll();
        Optional<UserDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long deleteId = first.get().getId();
        Optional<UserDetails> actual = userDetailsRepo.findById(deleteId);
        //then
        Assertions.assertTrue(actual.isPresent());
        userDetailsRepo.deleteById(deleteId);
        actual = userDetailsRepo.findById(deleteId);
        Assertions.assertFalse(actual.isPresent());
    }
}
