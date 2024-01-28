package learn.myapps.expensestracker.model;

import learn.myapps.expensestracker.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class PaymentModeDetailsJsonTest {

    @Autowired
    private JacksonTester<PaymentModeDetails> paymentModeDetailsJacksonTester;

    private final JsonTestUtils<PaymentModeDetails> jsonTestUtils = new JsonTestUtils<>(PaymentModeDetails.class);

    @Test
    void paymentModeDetailsJsonTest() throws IOException {
        //initialization
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
        JsonContent<PaymentModeDetails> actual = paymentModeDetailsJacksonTester.write(paymentModeDetails);
        assertThat(actual)
                .isEqualToJson("paymentModeDetails.json")
                .hasJsonPathNumberValue("@.basicId")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.paymentMode")
                .hasJsonPathStringValue("@.cardDetails")
                .hasJsonPathStringValue("@.cardType")
                .hasJsonPathStringValue("@.upiDetails")
                .hasJsonPathStringValue("@.accountDetails")
                .hasJsonPathStringValue("@.description")
                .hasJsonPathBooleanValue("@.isDeleted")
                .hasJsonPathStringValue("@.lastUpdatedBy")
                .hasJsonPathStringValue("@.lastUpdatedDateAndTime");
        //Deserialization test
        ObjectContent<PaymentModeDetails> actualObject = paymentModeDetailsJacksonTester.parse(jsonTestUtils.getJson("paymentModeDetails.json"));
        PaymentModeDetails actualPaymentModeDetails = actualObject.getObject();
        assertThat(actualPaymentModeDetails).isEqualTo(paymentModeDetails);
    }
}
