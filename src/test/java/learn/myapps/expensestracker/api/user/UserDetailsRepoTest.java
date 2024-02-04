package learn.myapps.expensestracker.api.user;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
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

    @Order(1)
    @Test
    void findById() {
        //when
        userDetailsRepo.save(userDetails);
        Optional<UserDetails> actual = userDetailsRepo.findById(53L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(userDetails, actual.get());
    }

    @Test
    void create() {
        //when
        UserDetails actual = userDetailsRepo.save(userDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(userDetails, actual);
    }

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

    @Order(2)
    @Test
    void deleteById() {
        //when
        userDetailsRepo.save(userDetails);
        Optional<UserDetails> actual = userDetailsRepo.findById(55L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(userDetails, actual.get());
    }
}
