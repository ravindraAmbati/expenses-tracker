package learn.myapps.expensestracker.api.expenses;

import learn.myapps.expensestracker.JsonTestUtils;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
import learn.myapps.expensestracker.api.user.UserDetails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ExpensesDetailsJsonTest {

    @Autowired
    private JacksonTester<ExpensesDetails> expensesDetailsJacksonTester;
    private final JsonTestUtils<ExpensesDetails> jsonTestUtils = new JsonTestUtils<>(ExpensesDetails.class);
    @Test
    void expensesDetailsJsonSerializeTest() throws IOException {
        //initialization
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(12345L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        CurrencyDetails currencyDetails = CurrencyDetails.builder()
                        .id(12345L)
                        .currency("EUR")
                        .exchangeRate(new BigDecimal("90.854321"))
                        .defaultCurrency("INR")
                        .basicDetails(basicDetails)
                        .build();
        ExpensesCategoryDetails expensesCategoryDetails = ExpensesCategoryDetails
                        .builder().id(12345L)
                        .expensesCategory("EDUCATION")
                        .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                        .basicDetails(basicDetails)
                        .build();
        PaymentModeDetails paymentModeDetails = PaymentModeDetails.builder()
                        .id(12345L)
                        .paymentMode("CARD")
                        .cardDetails("4315***99")
                        .cardType("C")
                        .upiDetails("ravindra.ambati@upi.com")
                        .accountDetails("")
                        .basicDetails(basicDetails)
                        .build();
        UserDetails userDetails = UserDetails.builder()
                        .id(12345L)
                        .firstName("Ravindra")
                        .lastName("Ambati")
                        .emailId("ravindra.reddy.ambati@outlook.in")
                        .mobileNo("+919849547160")
                        .basicDetails(basicDetails)
                        .build();
        //Serialization test
        ExpensesDetails expensesDetails = ExpensesDetails.builder()
                        .id(1234L)
                        .amount(new BigDecimal("123.45"))
                        .paidBy(userDetails)
                        .paidTo("Ravindra")
                        .dateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                        .paymentMode(paymentModeDetails)
                        .currency(currencyDetails)
                        .category(expensesCategoryDetails)
                        .basicDetails(basicDetails)
                        .build();
        JsonContent<ExpensesDetails> actual = expensesDetailsJacksonTester.write(expensesDetails);
        Assertions.assertThat(actual).isEqualToJson("expensesDetails.json");
        //Deserialization test
        ObjectContent<ExpensesDetails> actualObject = expensesDetailsJacksonTester.parse(jsonTestUtils.getJson("expensesDetails.json"));
        ExpensesDetails actualExpensesDetails = actualObject.getObject();
        assertThat(actualExpensesDetails).isEqualTo(expensesDetails);
    }
}
