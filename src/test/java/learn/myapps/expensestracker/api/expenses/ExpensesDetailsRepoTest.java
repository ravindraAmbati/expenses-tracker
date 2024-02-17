package learn.myapps.expensestracker.api.expenses;

import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
import learn.myapps.expensestracker.api.user.UserDetails;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpensesDetailsRepoTest {

    @Autowired
    private ExpensesDetailsRepo expensesDetailsRepo;
    private ExpensesDetails expensesDetails;


    @BeforeEach
    void setUp() {
        //Given
        BasicDetails basicDetails = BasicDetails.builder()
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        CurrencyDetails currencyDetails = CurrencyDetails.builder()
                .currency("EUR")
                .exchangeRate(new BigDecimal("90.854321"))
                .defaultCurrency("INR")
                .basicDetails(basicDetails)
                .build();
        ExpensesCategoryDetails expensesCategoryDetails = ExpensesCategoryDetails.builder()
                .expensesCategory("EDUCATION")
                .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                .basicDetails(basicDetails)
                .build();
        PaymentModeDetails paymentModeDetails = PaymentModeDetails.builder()
                .paymentMode("CARD")
                .cardDetails("4315***99")
                .cardType("C")
                .upiDetails("ravindra.ambati@upi.com")
                .accountDetails("")
                .basicDetails(basicDetails)
                .build();
        UserDetails userDetails = UserDetails.builder()
                .firstName("Ravindra")
                .lastName("Ambati")
                .emailId("ravindra.reddy.ambati@outlook.in")
                .mobileNo("+919849547160")
                .basicDetails(basicDetails)
                .build();
        //Serialization test
        expensesDetails = ExpensesDetails.builder()
                .amount(new BigDecimal("123.45"))
                .paidBy(userDetails)
                .paidTo("Ravindra")
                .dateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .paymentMode(paymentModeDetails)
                .currency(currencyDetails)
                .category(expensesCategoryDetails)
                .basicDetails(basicDetails)
                .build();
        expensesDetailsRepo.deleteAll();
    }

    @Order(91)
    @Test
    void findById() {
        //when
        expensesDetailsRepo.save(expensesDetails);
        Iterable<ExpensesDetails> all = expensesDetailsRepo.findAll();
        Optional<ExpensesDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long searchId = first.get().getId();
        Optional<ExpensesDetails> actual = expensesDetailsRepo.findById(searchId);
        //then
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expensesDetails, actual.get());
    }

    @Order(92)
    @Test
    void create() {
        //when
        ExpensesDetails actual = expensesDetailsRepo.save(expensesDetails);
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expensesDetails, actual);
    }

    @Order(93)
    @Test
    void update() {
        //when
        expensesDetails = expensesDetailsRepo.save(expensesDetails);
        BigDecimal updatedAmount = new BigDecimal("123.4567");
        expensesDetails.setAmount(updatedAmount);
        ExpensesDetails actual = expensesDetailsRepo.save(expensesDetails);
        //then
        Assertions.assertNotNull(actual.getAmount());
        Assertions.assertEquals(updatedAmount, actual.getAmount());
    }

    @Order(94)
    @Test
    void deleteById() {
        //when
        expensesDetailsRepo.save(expensesDetails);
        Iterable<ExpensesDetails> all = expensesDetailsRepo.findAll();
        Optional<ExpensesDetails> first = StreamSupport.stream(all.spliterator(), false).findFirst();
        Assertions.assertNotNull(first);
        Assertions.assertTrue(first.isPresent());
        Assertions.assertNotNull(first.get());
        Long deleteId = first.get().getId();
        Optional<ExpensesDetails> actual = expensesDetailsRepo.findById(deleteId);
        //then
        Assertions.assertTrue(actual.isPresent());
        expensesDetailsRepo.deleteById(53L);
        actual = expensesDetailsRepo.findById(53L);
        Assertions.assertFalse(actual.isPresent());
    }
}
