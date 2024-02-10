package learn.myapps.expensestracker.api.payment;

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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PaymentModeDetailsServiceTest {

    private PaymentModeDetailsService paymentModeDetailsService;

    @Mock
    private PaymentModeDetailsRepo paymentModeDetailsRepo;

    private PaymentModeDetails paymentModeDetails;

    @BeforeEach
    void setUp() {
        this.paymentModeDetailsService = new PaymentModeDetailsService(paymentModeDetailsRepo);
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(30L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        paymentModeDetails = PaymentModeDetails.builder()
                .id(31L)
                .paymentMode("CARD")
                .cardDetails("4315***99")
                .cardType("C")
                .upiDetails("ravindra.ambati@upi.com")
                .accountDetails("")
                .basicDetails(basicDetails)
                .build();
    }

    @Test
    void create() {
        paymentModeDetails.setId(null);
        PaymentModeDetails createPaymentModeDetails = new PaymentModeDetails();
        BeanUtils.copyProperties(paymentModeDetails, createPaymentModeDetails);
        createPaymentModeDetails.setId(30L);
        Mockito.when(paymentModeDetailsRepo.save(paymentModeDetails)).thenReturn(createPaymentModeDetails);
        Long id = paymentModeDetailsService.create(paymentModeDetails);
        Assertions.assertEquals(30L, id);
    }

    @Test
    void createFail() {
        paymentModeDetails.setId(null);
        Mockito.when(paymentModeDetailsRepo.save(paymentModeDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.create(paymentModeDetails), "");
        Assertions.assertEquals("Failed to create the Payment Mode Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void createEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.create(null), "");
        Assertions.assertEquals("Requested Payment Mode Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void createIdNotEmptyFail() {
        paymentModeDetails.setId(30L);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.create(paymentModeDetails), "");
        Assertions.assertEquals("Requested Payment Mode Details Id should be empty", illegalArgumentException.getMessage());
    }

    @Test
    void update() {
        Mockito.when(paymentModeDetailsRepo.findById(31L)).thenReturn(Optional.of(paymentModeDetails));
        Mockito.when(paymentModeDetailsRepo.save(paymentModeDetails)).thenReturn(paymentModeDetails);
        PaymentModeDetails createdCurrencyDetails = paymentModeDetailsService.update(paymentModeDetails);
        Assertions.assertEquals(createdCurrencyDetails, paymentModeDetails);
    }

    @Test
    void updateFail() {
        Mockito.when(paymentModeDetailsRepo.findById(31L)).thenReturn(Optional.of(paymentModeDetails));
        Mockito.when(paymentModeDetailsRepo.save(paymentModeDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.update(paymentModeDetails), "");
        Assertions.assertEquals("Failed to update the Payment Mode Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void updateEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.update(null), "");
        Assertions.assertEquals("Requested Payment Mode Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void updateIdNotEmptyFail() {
        paymentModeDetails.setId(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.update(paymentModeDetails), "");
        Assertions.assertEquals("Requested Payment Mode Details Id should NOT be empty", illegalArgumentException.getMessage());
    }

    @Test
    void isDeleted() {
        Mockito.when(paymentModeDetailsRepo.findById(30L)).thenReturn(Optional.empty());
        Assertions.assertTrue(paymentModeDetailsService.isDeleted(30L));
        Mockito.verify(paymentModeDetailsRepo).deleteById(30L);
    }

    @Test
    void isDeletedFail() {
        Mockito.when(paymentModeDetailsRepo.findById(30L)).thenReturn(Optional.of(paymentModeDetails));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.isDeleted(30L), "");
        Mockito.verify(paymentModeDetailsRepo).deleteById(30L);
        Assertions.assertEquals("Failed to delete the Payment Mode Details Entity of id: 30", illegalArgumentException.getMessage());
    }

    @Test
    void findById() {
        Mockito.when(paymentModeDetailsRepo.findById(30L)).thenReturn(Optional.of(paymentModeDetails));
        PaymentModeDetails retrievedCurrencyDetails = paymentModeDetailsService.findById(30L);
        Assertions.assertEquals(paymentModeDetails, retrievedCurrencyDetails);
    }

    @Test
    void findByIdFail() {
        Mockito.when(paymentModeDetailsRepo.findById(30L)).thenReturn(Optional.empty());
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.findById(30L), "");
        Assertions.assertEquals("Searched Payment Mode Details Entity is not found of the id: 30", illegalArgumentException.getMessage());
    }

    @Test
    void findAll() {
        Mockito.when(paymentModeDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("id"))));
        Page<PaymentModeDetails> all = paymentModeDetailsService.findAll(0, 10, Sort.by("id"));
        Assertions.assertEquals(Page.empty(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")))), all);
    }

    @Test
    void findAllPageNumberFail() {
        Mockito.when(paymentModeDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(1, 10, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested Payment Mode Details Entities from page number: 0", illegalArgumentException.getMessage());
    }

    @Test
    void findAllPageSizeFail() {
        Mockito.when(paymentModeDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 11, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested Payment Mode Details Entities of page size: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAllSortFail() {
        Mockito.when(paymentModeDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("description"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> paymentModeDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested Payment Mode Details Entities by sort: id: ASC", illegalArgumentException.getMessage());
    }
}
