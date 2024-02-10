package learn.myapps.expensestracker.api.category;

import learn.myapps.expensestracker.api.basic.BasicDetails;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ExpensesCategoryDetailsServiceTest {

    private ExpensesCategoryDetailsService expensesCategoryDetailsService;

    @Mock
    private ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;

    private ExpensesCategoryDetails expensesCategoryDetails;

    @BeforeEach
    void setUp() {
        this.expensesCategoryDetailsService = new ExpensesCategoryDetailsService(expensesCategoryDetailsRepo);
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(10L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        expensesCategoryDetails =
                ExpensesCategoryDetails.builder()
                        .id(11L)
                        .expensesCategory("EDUCATION")
                        .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                        .basicDetails(basicDetails)
                        .build();
    }

    @Test
    void create() {
        expensesCategoryDetails.setId(null);
        ExpensesCategoryDetails createdExpensesCategoryDetails = new ExpensesCategoryDetails();
        BeanUtils.copyProperties(expensesCategoryDetails, createdExpensesCategoryDetails);
        createdExpensesCategoryDetails.setId(10L);
        Mockito.when(expensesCategoryDetailsRepo.save(expensesCategoryDetails)).thenReturn(createdExpensesCategoryDetails);
        Long id = expensesCategoryDetailsService.create(expensesCategoryDetails);
        Assertions.assertEquals(10L, id);
    }

    @Test
    void createFail() {
        expensesCategoryDetails.setId(null);
        Mockito.when(expensesCategoryDetailsRepo.save(expensesCategoryDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.create(expensesCategoryDetails), "");
        Assertions.assertEquals("Failed to create the Expenses Category Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void createEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.create(null), "");
        Assertions.assertEquals("Requested Expenses Category Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void createIdNotEmptyFail() {
        expensesCategoryDetails.setId(10L);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.create(expensesCategoryDetails), "");
        Assertions.assertEquals("Requested Expenses Category Details Id should be empty", illegalArgumentException.getMessage());
    }

    @Test
    void update() {
        Mockito.when(expensesCategoryDetailsRepo.findById(11L)).thenReturn(Optional.of(expensesCategoryDetails));
        Mockito.when(expensesCategoryDetailsRepo.save(expensesCategoryDetails)).thenReturn(expensesCategoryDetails);
        ExpensesCategoryDetails createdExpensesCategoryDetails = expensesCategoryDetailsService.update(expensesCategoryDetails);
        Assertions.assertEquals(createdExpensesCategoryDetails, expensesCategoryDetails);
    }

    @Test
    void updateFail() {
        Mockito.when(expensesCategoryDetailsRepo.findById(11L)).thenReturn(Optional.of(expensesCategoryDetails));
        Mockito.when(expensesCategoryDetailsRepo.save(expensesCategoryDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.update(expensesCategoryDetails), "");
        Assertions.assertEquals("Failed to update the Expenses Category Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void updateEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.update(null), "");
        Assertions.assertEquals("Requested Expenses Category Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void updateIdNotEmptyFail() {
        expensesCategoryDetails.setId(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.update(expensesCategoryDetails), "");
        Assertions.assertEquals("Requested Expenses Category Details Id should NOT be empty", illegalArgumentException.getMessage());
    }

    @Test
    void isDeleted() {
        Mockito.when(expensesCategoryDetailsRepo.findById(10L)).thenReturn(Optional.empty());
        Assertions.assertTrue(expensesCategoryDetailsService.isDeleted(10L));
        Mockito.verify(expensesCategoryDetailsRepo).deleteById(10L);
    }

    @Test
    void isDeletedFail() {
        Mockito.when(expensesCategoryDetailsRepo.findById(10L)).thenReturn(Optional.of(expensesCategoryDetails));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.isDeleted(10L), "");
        Mockito.verify(expensesCategoryDetailsRepo).deleteById(10L);
        Assertions.assertEquals("Failed to delete the Expenses Category Details Entity of id: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findById() {
        Mockito.when(expensesCategoryDetailsRepo.findById(10L)).thenReturn(Optional.of(expensesCategoryDetails));
        ExpensesCategoryDetails retrievedExpensesCategoryDetails = expensesCategoryDetailsService.findById(10L);
        Assertions.assertEquals(expensesCategoryDetails, retrievedExpensesCategoryDetails);
    }

    @Test
    void findByIdFail() {
        Mockito.when(expensesCategoryDetailsRepo.findById(10L)).thenReturn(Optional.empty());
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.findById(10L), "");
        Assertions.assertEquals("Searched Expenses Category Details Entity is not found of the id: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAll() {
        Mockito.when(expensesCategoryDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("id"))));
        Page<ExpensesCategoryDetails> all = expensesCategoryDetailsService.findAll(0, 10, Sort.by("id"));
        Assertions.assertEquals(Page.empty(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")))), all);
    }

    @Test
    void findAllPageNumberFail() {
        Mockito.when(expensesCategoryDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(1, 10, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested Expenses Category Details Entities from page number: 0", illegalArgumentException.getMessage());
    }

    @Test
    void findAllPageSizeFail() {
        Mockito.when(expensesCategoryDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 11, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested Expenses Category Details Entities of page size: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAllSortFail() {
        Mockito.when(expensesCategoryDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("description"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> expensesCategoryDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested Expenses Category Details Entities by sort: id: ASC", illegalArgumentException.getMessage());
    }
}
