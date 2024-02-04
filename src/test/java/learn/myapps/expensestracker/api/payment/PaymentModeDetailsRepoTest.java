package learn.myapps.expensestracker.api.payment;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class PaymentModeDetailsRepoTest {

    @Autowired
    private PaymentModeDetailsRepo paymentModeDetailsRepo;
    private PaymentModeDetails paymentModeDetails;


    @BeforeEach
    void setUp() {
        //Given
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
        paymentModeDetailsRepo.deleteAll();
    }

    @Order(1)
    @Test
    void findById() {
        //when
        paymentModeDetailsRepo.save(paymentModeDetails);
        Optional<PaymentModeDetails> actual = paymentModeDetailsRepo.findById(45L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(paymentModeDetails, actual.get());
    }

    @Test
    void create() {
        //when
        PaymentModeDetails actual = paymentModeDetailsRepo.save(paymentModeDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(paymentModeDetails, actual);
    }

    @Test
    void update() {
        //when
        paymentModeDetails = paymentModeDetailsRepo.save(paymentModeDetails);
        String paymentMode = "M";
        paymentModeDetails.setPaymentMode(paymentMode);
        PaymentModeDetails actual = paymentModeDetailsRepo.save(paymentModeDetails);
        //then
        Assertions.assertNotNull(actual.getPaymentMode());
        Assertions.assertEquals(paymentMode, actual.getPaymentMode());
    }

    @Order(2)
    @Test
    void deleteById() {
        //when
        paymentModeDetailsRepo.save(paymentModeDetails);
        Optional<PaymentModeDetails> actual = paymentModeDetailsRepo.findById(47L);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(paymentModeDetails, actual.get());
    }
}
