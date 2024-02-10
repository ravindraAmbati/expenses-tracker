package learn.myapps.expensestracker.api.basic;

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
class BasicDetailsServiceTest {

    private BasicDetailsService basicDetailsService;

    @Mock
    private BasicDetailsRepo basicDetailsRepo;

    private BasicDetails basicDetails;

    @BeforeEach
    void setUp() {
        this.basicDetailsService = new BasicDetailsService(basicDetailsRepo);
        basicDetails = BasicDetails.builder()
                .basicId(1L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
    }

    @Test
    void create() {
        basicDetails.setBasicId(null);
        BasicDetails createdBasicDetails = new BasicDetails();
        BeanUtils.copyProperties(basicDetails, createdBasicDetails);
        createdBasicDetails.setBasicId(1L);
        Mockito.when(basicDetailsRepo.save(basicDetails)).thenReturn(createdBasicDetails);
        Long id = basicDetailsService.create(basicDetails);
        Assertions.assertEquals(1L, id);
    }

    @Test
    void createFail() {
        basicDetails.setBasicId(null);
        Mockito.when(basicDetailsRepo.save(basicDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.create(basicDetails), "");
        Assertions.assertEquals("Failed to create the basic details entity", illegalArgumentException.getMessage());
    }

    @Test
    void createEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.create(null), "");
        Assertions.assertEquals("Requested Basic Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void createIdNotEmptyFail() {
        basicDetails.setBasicId(1L);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.create(basicDetails), "");
        Assertions.assertEquals("Requested Basic Details BasicId should be empty", illegalArgumentException.getMessage());
    }

    @Test
    void update() {
        Mockito.when(basicDetailsRepo.findById(1L)).thenReturn(Optional.of(basicDetails));
        Mockito.when(basicDetailsRepo.save(basicDetails)).thenReturn(basicDetails);
        BasicDetails createdBasicDetails = basicDetailsService.update(basicDetails);
        Assertions.assertEquals(createdBasicDetails, basicDetails);
    }

    @Test
    void updateFail() {
        Mockito.when(basicDetailsRepo.findById(1L)).thenReturn(Optional.of(basicDetails));
        Mockito.when(basicDetailsRepo.save(basicDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.update(basicDetails), "");
        Assertions.assertEquals("Failed to update the basic details entity", illegalArgumentException.getMessage());
    }

    @Test
    void updateEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.update(null), "");
        Assertions.assertEquals("Requested Basic Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void updateIdNotEmptyFail() {
        basicDetails.setBasicId(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.update(basicDetails), "");
        Assertions.assertEquals("Requested Basic Details BasicId should NOT be empty", illegalArgumentException.getMessage());
    }

    @Test
    void isDeleted() {
        Mockito.when(basicDetailsRepo.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertTrue(basicDetailsService.isDeleted(1L));
        Mockito.verify(basicDetailsRepo).deleteById(1L);
    }

    @Test
    void isDeletedFail() {
        Mockito.when(basicDetailsRepo.findById(1L)).thenReturn(Optional.of(basicDetails));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.isDeleted(1L), "");
        Mockito.verify(basicDetailsRepo).deleteById(1L);
        Assertions.assertEquals("Failed to delete the basic details entity of id: 1", illegalArgumentException.getMessage());
    }

    @Test
    void findById() {
        Mockito.when(basicDetailsRepo.findById(1L)).thenReturn(Optional.of(basicDetails));
        BasicDetails retrievedBasicDetails = basicDetailsService.findById(1L);
        Assertions.assertEquals(basicDetails, retrievedBasicDetails);
    }

    @Test
    void findByIdFail() {
        Mockito.when(basicDetailsRepo.findById(1L)).thenReturn(Optional.empty());
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.findById(1L), "");
        Assertions.assertEquals("Searched basic details entity is not found of the id: 1", illegalArgumentException.getMessage());
    }

    @Test
    void findAll() {
        Mockito.when(basicDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("id"))));
        Page<BasicDetails> all = basicDetailsService.findAll(0, 10, Sort.by("id"));
        Assertions.assertEquals(Page.empty(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")))), all);
    }

    @Test
    void findAllPageNumberFail() {
        Mockito.when(basicDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(1, 10, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested basic details entities from page number: 0", illegalArgumentException.getMessage());
    }

    @Test
    void findAllPageSizeFail() {
        Mockito.when(basicDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 11, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested basic details entities of page size: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAllSortFail() {
        Mockito.when(basicDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("description"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> basicDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested basic details entities by sort: id: ASC", illegalArgumentException.getMessage());
    }
}
