package learn.myapps.expensestracker.model;

import learn.myapps.expensestracker.JsonTestUtils;
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
        CurrencyDetails currencyDetails =
                CurrencyDetails
                        .builder()
                        .basicId(12345L)
                        .id(12345L)
                        .currency("EUR")
                        .exchangeRate(new BigDecimal("90.854321"))
                        .defaultCurrency("INR")
                        .description("test")
                        .isDeleted(false)
                        .lastUpdatedBy("Ravindra")
                        .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                        .build();
        ExpensesCategoryDetails expensesCategoryDetails =
                ExpensesCategoryDetails
                        .builder()
                        .basicId(12345L)
                        .id(12345L)
                        .expensesCategory("EDUCATION")
                        .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                        .description("test")
                        .isDeleted(false)
                        .lastUpdatedBy("Ravindra")
                        .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                        .build();
        PaymentModeDetails paymentModeDetails =
                PaymentModeDetails
                        .builder()
                        .basicId(12345L)
                        .id(12345L)
                        .paymentMode("CARD")
                        .cardDetails("4315***99")
                        .cardType("C")
                        .upiDetails("ravindra.ambati@upi.com")
                        .accountDetails("")
                        .description("test")
                        .isDeleted(false)
                        .lastUpdatedBy("Ravindra")
                        .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                        .build();
        //Serialization test
        ExpensesDetails expensesDetails =
                ExpensesDetails
                        .builder()
                        .basicId(12345L)
                        .id(1234L)
                        .amount(new BigDecimal("123.45"))
                        .paidBy("Ravindra")
                        .paidTo("Ravindra")
                        .dateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                        .description("test")
                        .paymentMode(paymentModeDetails)
                        .currency(currencyDetails)
                        .category(expensesCategoryDetails)
                        .isDeleted(false)
                        .lastUpdatedBy("Ravindra")
                        .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                        .build();
        JsonContent<ExpensesDetails> actual = expensesDetailsJacksonTester.write(expensesDetails);
        Assertions.assertThat(actual).isEqualToJson("expensesDetails.json");
        //Deserialization test
        ObjectContent<ExpensesDetails> actualObject = expensesDetailsJacksonTester.parse(jsonTestUtils.getJson("expensesDetails.json"));
        ExpensesDetails actualExpensesDetails = actualObject.getObject();
        assertThat(actualExpensesDetails).isEqualTo(expensesDetails);
    }
}
