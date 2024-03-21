package learn.myapps.expensestracker.api.user;

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

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    private UserDetailsService userDetailsService;

    @Mock
    private UserDetailsRepo userDetailsRepo;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        this.userDetailsService = new UserDetailsService(userDetailsRepo);
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(40L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        userDetails = UserDetails.builder()
                .id(41L)
                .firstName("Ravindra")
                .lastName("Ambati")
                .emailId("ravindra.reddy.ambati@outlook.in")
                .mobileNo("+919849547160")
                .basicDetails(basicDetails)
                .build();
    }

    @Test
    void create() {
        userDetails.setId(null);
        UserDetails createUserDetails = new UserDetails();
        BeanUtils.copyProperties(userDetails, createUserDetails);
        createUserDetails.setId(41L);
        Mockito.when(userDetailsRepo.save(userDetails)).thenReturn(createUserDetails);
        Long id = userDetailsService.create(userDetails);
        Assertions.assertEquals(41L, id);
    }

    @Test
    void createFail() {
        userDetails.setId(null);
        Mockito.when(userDetailsRepo.save(userDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.create(userDetails), "");
        Assertions.assertEquals("Failed to create the User Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void createEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.create(null), "");
        Assertions.assertEquals("Requested User Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void createIdNotEmptyFail() {
        userDetails.setId(40L);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.create(userDetails), "");
        Assertions.assertEquals("Requested User Details Id should be empty", illegalArgumentException.getMessage());
    }

    @Test
    void update() throws ResourceNotFoundException {
        Mockito.when(userDetailsRepo.findById(41L)).thenReturn(Optional.of(userDetails));
        Mockito.when(userDetailsRepo.save(userDetails)).thenReturn(userDetails);
        UserDetails createdCurrencyDetails = userDetailsService.update(userDetails);
        Assertions.assertEquals(createdCurrencyDetails, userDetails);
    }

    @Test
    void updateFail() {
        Mockito.when(userDetailsRepo.findById(41L)).thenReturn(Optional.of(userDetails));
        Mockito.when(userDetailsRepo.save(userDetails)).thenReturn(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.update(userDetails), "");
        Assertions.assertEquals("Failed to update the User Details Entity", illegalArgumentException.getMessage());
    }

    @Test
    void updateEmptyFail() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.update(null), "");
        Assertions.assertEquals("Requested User Details should not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void updateIdNotEmptyFail() {
        userDetails.setId(null);
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.update(userDetails), "");
        Assertions.assertEquals("Requested User Details Id should NOT be empty", illegalArgumentException.getMessage());
    }

    @Test
    void isDeleted() {
        Mockito.when(userDetailsRepo.findById(41L)).thenReturn(Optional.empty());
        Assertions.assertTrue(userDetailsService.isDeleted(41L));
        Mockito.verify(userDetailsRepo).deleteById(41L);
    }

    @Test
    void isDeletedFail() {
        Mockito.when(userDetailsRepo.findById(41L)).thenReturn(Optional.of(userDetails));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.isDeleted(41L), "");
        Mockito.verify(userDetailsRepo).deleteById(41L);
        Assertions.assertEquals("Failed to delete the User Details Entity of id: 41", illegalArgumentException.getMessage());
    }

    @Test
    void findById() throws ResourceNotFoundException {
        Mockito.when(userDetailsRepo.findById(41L)).thenReturn(Optional.of(userDetails));
        UserDetails retrievedCurrencyDetails = userDetailsService.findById(41L);
        Assertions.assertEquals(userDetails, retrievedCurrencyDetails);
    }

    @Test
    void findByIdFail() {
        Mockito.when(userDetailsRepo.findById(41L)).thenReturn(Optional.empty());
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> userDetailsService.findById(41L), "");
        Assertions.assertEquals("Searched User Details Entity is not found of the id: 41", resourceNotFoundException.getMessage());
    }

    @Test
    void findAll() {
        Mockito.when(userDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("id"))));
        Page<UserDetails> all = userDetailsService.findAll(0, 10, Sort.by("id"));
        Assertions.assertEquals(Page.empty(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id")))), all);
    }

    @Test
    void findAllPageNumberFail() {
        Mockito.when(userDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(1, 10, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities from page number: 0", illegalArgumentException.getMessage());
    }

    @Test
    void findAllPageSizeFail() {
        Mockito.when(userDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 11, Sort.by("id"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities of page size: 10", illegalArgumentException.getMessage());
    }

    @Test
    void findAllSortFail() {
        Mockito.when(userDetailsRepo.findAll(PageRequest.of(0, 10, Sort.by("id")))).thenReturn(Page.empty(PageRequest.of(0, 10, Sort.by("description"))));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> userDetailsService.findAll(0, 10, Sort.by("id")), "");
        Assertions.assertEquals("Failed to get requested entities by sort: id: ASC", illegalArgumentException.getMessage());
    }
}
