package learn.myapps.expensestracker.api.expenses;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
import learn.myapps.expensestracker.api.user.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ExpensesDetailsServiceTest {

    private ExpensesDetailsService expensesDetailsService;

    @Mock
    private ExpensesDetailsRepo expensesDetailsRepo;

    private ExpensesDetails expensesDetails;

    @BeforeEach
    void setUp() {
        this.expensesDetailsService = new ExpensesDetailsService(expensesDetailsRepo);
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(50L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        CurrencyDetails currencyDetails = CurrencyDetails.builder()
                .id(51L)
                .currency("EUR")
                .exchangeRate(new BigDecimal("90.854321"))
                .defaultCurrency("INR")
                .basicDetails(basicDetails)
                .build();
        ExpensesCategoryDetails expensesCategoryDetails = ExpensesCategoryDetails.builder()
                .id(52L)
                .expensesCategory("EDUCATION")
                .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                .basicDetails(basicDetails)
                .build();
        PaymentModeDetails paymentModeDetails = PaymentModeDetails.builder()
                .id(53L)
                .paymentMode("CARD")
                .cardDetails("4315***99")
                .cardType("C")
                .upiDetails("ravindra.ambati@upi.com")
                .accountDetails("")
                .basicDetails(basicDetails)
                .build();
        UserDetails userDetails = UserDetails.builder()
                .id(54L)
                .firstName("Ravindra")
                .lastName("Ambati")
                .emailId("ravindra.reddy.ambati@outlook.in")
                .mobileNo("+919849547160")
                .basicDetails(basicDetails)
                .build();
        expensesDetails = ExpensesDetails.builder()
                .id(55L)
                .amount(new BigDecimal("123.45"))
                .paidBy(userDetails)
                .paidTo("Ravindra")
                .dateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .paymentMode(paymentModeDetails)
                .currency(currencyDetails)
                .category(expensesCategoryDetails)
                .basicDetails(basicDetails)
                .build();
    }

    @Test
    void create() {
        expensesDetails.setId(null);
        ExpensesDetails createExpensesDetails = new ExpensesDetails();
        BeanUtils.copyProperties(expensesDetails, createExpensesDetails);
        createExpensesDetails.setId(55L);
        Mockito.when(expensesDetailsRepo.save(expensesDetails)).thenReturn(createExpensesDetails);
        Long id = expensesDetailsService.create(expensesDetails);
        Assertions.assertEquals(55L, id);
    }

    @Test
    void createFail() {
        expensesDetails.setId(null);
        Mockito.when(expensesDetailsRepo.save(expensesDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.create(expensesDetails), "");
        Assertions.assertEquals("Failed to create the Expenses Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void createEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.create(null), "");
        Assertions.assertEquals("Requested Expenses Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void createIdNotEmptyFail() {
        expensesDetails.setId(55L);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.create(expensesDetails), "");
        Assertions.assertEquals("Requested Expenses Details Id should be empty", illegalArgumentException.getMessage());
    }

    @Test
    void update() throws ResourceNotFoundException {
        Mockito.when(expensesDetailsRepo.findById(55L)).thenReturn(Optional.of(expensesDetails));
        Mockito.when(expensesDetailsRepo.save(expensesDetails)).thenReturn(expensesDetails);
        ExpensesDetails createdCurrencyDetails = expensesDetailsService.update(expensesDetails);
        Assertions.assertEquals(createdCurrencyDetails, expensesDetails);
    }

    @Test
    void updateFail() {
        Mockito.when(expensesDetailsRepo.findById(55L)).thenReturn(Optional.of(expensesDetails));
        Mockito.when(expensesDetailsRepo.save(expensesDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.update(expensesDetails), "");
        Assertions.assertEquals("Failed to update the Expenses Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void updateEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.update(null), "");
        Assertions.assertEquals("Requested Expenses Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void updateIdNotEmptyFail() {
        expensesDetails.setId(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.update(expensesDetails), "");
        Assertions.assertEquals("Requested Expenses Details Id should NOT be empty", illegalArgumentException.getMessage());
    }

    @Test
    void isDeleted() {
        Mockito.when(expensesDetailsRepo.findById(55L)).thenReturn(Optional.empty());
        Assertions.assertTrue(expensesDetailsService.isDeleted(55L));
        Mockito.verify(expensesDetailsRepo).deleteById(55L);
    }

    @Test
    void isDeletedFail() {
        Mockito.when(expensesDetailsRepo.findById(55L)).thenReturn(Optional.of(expensesDetails));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.isDeleted(55L), "");
        Mockito.verify(expensesDetailsRepo).deleteById(55L);
        Assertions.assertEquals("Failed to delete the Expenses Details Entity of id: 55", illegalArgumentException.getMessage());
    }

    @Test
    void findById() throws ResourceNotFoundException {
        Mockito.when(expensesDetailsRepo.findById(55L)).thenReturn(Optional.of(expensesDetails));
        ExpensesDetails retrievedCurrencyDetails = expensesDetailsService.findById(55L);
        Assertions.assertEquals(expensesDetails, retrievedCurrencyDetails);
    }

    @Test
    void findByIdFail() {
        Mockito.when(expensesDetailsRepo.findById(55L)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> expensesDetailsService.findById(55L), "");
        Assertions.assertEquals("Searched Expenses Details Entity is not found of the id: 55", resourceNotFoundException.getMessage());
    }

    @Test
    void findAll() {
        Mockito.when(expensesDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("id"))));
        Page<ExpensesDetails> all = expensesDetailsService.findAll(0, 10, Sort.by("id"));
        Assertions.assertEquals(Page.empty(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")))), all);
    }

    @Test
    void findAllPageNumberFail() {
        Mockito.when(expensesDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(1, 10, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities from page number: 0", illegalArgumentException.getMessage());
    }

    @Test
    void findAllPageSizeFail() {
        Mockito.when(expensesDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 11, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities of page size: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAllSortFail() {
        Mockito.when(expensesDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("description"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities by sort: id: ASC", illegalArgumentException.getMessage());
    }
}
