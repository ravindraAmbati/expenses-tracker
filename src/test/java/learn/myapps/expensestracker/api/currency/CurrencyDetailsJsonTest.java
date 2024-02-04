package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.JsonTestUtils;
import learn.myapps.expensestracker.api.basic.BasicDetails;
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

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CurrencyDetailsJsonTest {

    @Autowired
    private JacksonTester<CurrencyDetails> currencyDetailsJacksonTester;
    private final JsonTestUtils<CurrencyDetails> jsonTestUtils = new JsonTestUtils<>(CurrencyDetails.class);

    @Test
    void currencyDetailsJsonTest() throws IOException {
        //initialization
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(12345L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        CurrencyDetails currencyDetails =
                CurrencyDetails.builder()
                        .id(12345L)
                        .currency("EUR")
                        .exchangeRate(new BigDecimal("90.854321"))
                        .defaultCurrency("INR")
                        .basicDetails(basicDetails)
                        .build();
        //Serialization test
        JsonContent<CurrencyDetails> actual = currencyDetailsJacksonTester.write(currencyDetails);
        Assertions.assertThat(actual)
                .isEqualToJson("currencyDetails.json")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.currency")
                .hasJsonPathNumberValue("@.exchangeRate")
                .hasJsonPathStringValue("@.defaultCurrency")
                .hasJsonPathValue("@.basicDetails")
                .hasJsonPathNumberValue("@.basicDetails.basicId")
                .hasJsonPathStringValue("@.basicDetails.description")
                .hasJsonPathBooleanValue("@.basicDetails.isDeleted")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedBy")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedDateAndTime");
        //Deserialization test
        ObjectContent<CurrencyDetails> actualObject = currencyDetailsJacksonTester.parse(jsonTestUtils.getJson("currencyDetails.json"));
        CurrencyDetails actualCurrencyDetails = actualObject.getObject();
        assertThat(actualCurrencyDetails).isEqualTo(currencyDetails);
    }
}