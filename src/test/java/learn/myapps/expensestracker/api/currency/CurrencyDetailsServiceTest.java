package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CurrencyDetailsServiceTest {

    private CurrencyDetailsService currencyDetailsService;

    @Mock
    private CurrencyDetailsRepo currencyDetailsRepo;

    private CurrencyDetails currencyDetails;

    @BeforeEach
    void setUp() {
        this.currencyDetailsService = new CurrencyDetailsService(currencyDetailsRepo);
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(20L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        currencyDetails = CurrencyDetails.builder()
                .id(21L)
                .currency("EUR")
                .exchangeRate(new BigDecimal("90.854321"))
                .defaultCurrency("INR")
                .basicDetails(basicDetails)
                .build();
    }

    @Test
    void create() {
        currencyDetails.setId(null);
        CurrencyDetails createdCurrencyDetails = new CurrencyDetails();
        BeanUtils.copyProperties(currencyDetails, createdCurrencyDetails);
        createdCurrencyDetails.setId(20L);
        Mockito.when(currencyDetailsRepo.save(currencyDetails)).thenReturn(createdCurrencyDetails);
        Long id = currencyDetailsService.create(currencyDetails);
        Assertions.assertEquals(20L, id);
    }

    @Test
    void createFail() {
        currencyDetails.setId(null);
        Mockito.when(currencyDetailsRepo.save(currencyDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.create(currencyDetails), "");
        Assertions.assertEquals("Failed to create the Currency Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void createEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.create(null), "");
        Assertions.assertEquals("Requested Currency Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void createIdNotEmptyFail() {
        currencyDetails.setId(20L);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.create(currencyDetails), "");
        Assertions.assertEquals("Requested Currency Details Id should be empty", illegalArgumentException.getMessage());
    }

    @Test
    void update() throws ResourceNotFoundException {
        Mockito.when(currencyDetailsRepo.findById(21L)).thenReturn(Optional.of(currencyDetails));
        Mockito.when(currencyDetailsRepo.save(currencyDetails)).thenReturn(currencyDetails);
        CurrencyDetails createdCurrencyDetails = currencyDetailsService.update(currencyDetails);
        Assertions.assertEquals(createdCurrencyDetails, currencyDetails);
    }

    @Test
    void updateFail() {
        Mockito.when(currencyDetailsRepo.findById(21L)).thenReturn(Optional.of(currencyDetails));
        Mockito.when(currencyDetailsRepo.save(currencyDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.update(currencyDetails), "");
        Assertions.assertEquals("Failed to update the Currency Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void updateEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.update(null), "");
        Assertions.assertEquals("Requested Currency Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void updateIdNotEmptyFail() {
        currencyDetails.setId(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.update(currencyDetails), "");
        Assertions.assertEquals("Requested Currency Details Id should NOT be empty", illegalArgumentException.getMessage());
    }

    @Test
    void isDeleted() {
        Mockito.when(currencyDetailsRepo.findById(20L)).thenReturn(Optional.empty());
        Assertions.assertTrue(currencyDetailsService.isDeleted(20L));
        Mockito.verify(currencyDetailsRepo).deleteById(20L);
    }

    @Test
    void isDeletedFail() {
        Mockito.when(currencyDetailsRepo.findById(20L)).thenReturn(Optional.of(currencyDetails));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.isDeleted(20L), "");
        Mockito.verify(currencyDetailsRepo).deleteById(20L);
        Assertions.assertEquals("Failed to delete the Currency Details Entity of id: 20", illegalArgumentException.getMessage());
    }

    @Test
    void findById() throws ResourceNotFoundException {
        Mockito.when(currencyDetailsRepo.findById(20L)).thenReturn(Optional.of(currencyDetails));
        CurrencyDetails retrievedCurrencyDetails = currencyDetailsService.findById(20L);
        Assertions.assertEquals(currencyDetails, retrievedCurrencyDetails);
    }

    @Test
    void findByIdFail() {
        Mockito.when(currencyDetailsRepo.findById(20L)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> currencyDetailsService.findById(20L), "");
        Assertions.assertEquals("Searched Currency Details Entity is not found of the id: 20", resourceNotFoundException.getMessage());
    }

    @Test
    void findAll() {
        Mockito.when(currencyDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("id"))));
        Page<CurrencyDetails> all = currencyDetailsService.findAll(0, 10, Sort.by("id"));
        Assertions.assertEquals(Page.empty(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")))), all);
    }

    @Test
    void findAllPageNumberFail() {
        Mockito.when(currencyDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(1, 10, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities from page number: 0", illegalArgumentException.getMessage());
    }

    @Test
    void findAllPageSizeFail() {
        Mockito.when(currencyDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 11, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities of page size: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAllSortFail() {
        Mockito.when(currencyDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("description"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> currencyDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities by sort: id: ASC", illegalArgumentException.getMessage());
    }
}
