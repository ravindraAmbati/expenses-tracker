package learn.myapps.expensestracker.api.payment;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.expenses.ExpensesDetails;
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

    @Order(101)
    @Test
    void findById() {
        //when
        paymentModeDetailsRepo.save(paymentModeDetails);
        Iterable<PaymentModeDetails> all = paymentModeDetailsRepo.findAll();
        Optional<PaymentModeDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long searchId = first.get().getId();
        Optional<PaymentModeDetails> actual = paymentModeDetailsRepo.findById(searchId);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(paymentModeDetails, actual.get());
    }

    @Order(102)
    @Test
    void create() {
        //when
        PaymentModeDetails actual = paymentModeDetailsRepo.save(paymentModeDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(paymentModeDetails, actual);
    }

    @Order(103)
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

    @Order(104)
    @Test
    void deleteById() {
        //when
        paymentModeDetailsRepo.save(paymentModeDetails);
        Iterable<PaymentModeDetails> all = paymentModeDetailsRepo.findAll();
        Optional<PaymentModeDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long deleteId = first.get().getId();
        Optional<PaymentModeDetails> actual = paymentModeDetailsRepo.findById(deleteId);
        //then
        Assertions.assertTrue(actual.isPresent());
        paymentModeDetailsRepo.deleteById(deleteId);
        actual = paymentModeDetailsRepo.findById(deleteId);
        Assertions.assertFalse(actual.isPresent());
    }
}
