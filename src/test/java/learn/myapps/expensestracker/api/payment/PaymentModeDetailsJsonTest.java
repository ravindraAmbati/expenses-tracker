package learn.myapps.expensestracker.api.payment;

import learn.myapps.expensestracker.JsonTestUtils;
import learn.myapps.expensestracker.api.basic.BasicDetails;
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
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(12345L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        PaymentModeDetails paymentModeDetails =
                PaymentModeDetails.builder()
                        .id(12345L)
                        .paymentMode("CARD")
                        .cardDetails("4315***99")
                        .cardType("C")
                        .upiDetails("ravindra.ambati@upi.com")
                        .accountDetails("")
                        .basicDetails(basicDetails)
                        .build();
        //Serialization test
        JsonContent<PaymentModeDetails> actual = paymentModeDetailsJacksonTester.write(paymentModeDetails);
        assertThat(actual)
                .isEqualToJson("paymentModeDetails.json")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.paymentMode")
                .hasJsonPathStringValue("@.cardDetails")
                .hasJsonPathStringValue("@.cardType")
                .hasJsonPathStringValue("@.upiDetails")
                .hasJsonPathStringValue("@.accountDetails")
                .hasJsonPathValue("@.basicDetails")
                .hasJsonPathNumberValue("@.basicDetails.basicId")
                .hasJsonPathStringValue("@.basicDetails.description")
                .hasJsonPathBooleanValue("@.basicDetails.isDeleted")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedBy")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedDateAndTime");
        //Deserialization test
        ObjectContent<PaymentModeDetails> actualObject = paymentModeDetailsJacksonTester.parse(jsonTestUtils.getJson("paymentModeDetails.json"));
        PaymentModeDetails actualPaymentModeDetails = actualObject.getObject();
        assertThat(actualPaymentModeDetails).isEqualTo(paymentModeDetails);
    }
}
