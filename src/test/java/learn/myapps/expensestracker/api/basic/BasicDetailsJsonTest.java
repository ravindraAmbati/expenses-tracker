package learn.myapps.expensestracker.api.basic;

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
class BasicDetailsJsonTest {

    @Autowired
    private JacksonTester<BasicDetails> basicDetailsJacksonTester;
    private final JsonTestUtils<BasicDetails> jsonTestUtils = new JsonTestUtils<>(BasicDetails.class);

    @Test
    void basicDetailsJsonTest() throws IOException {
        //initialization
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(12345L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        //Serialization test
        JsonContent<BasicDetails> actual = basicDetailsJacksonTester.write(basicDetails);
        assertThat(actual)
                .isEqualToJson("basicDetails.json")
                .hasJsonPathNumberValue("@.basicId")
                .hasJsonPathStringValue("@.description")
                .hasJsonPathBooleanValue("@.isDeleted")
                .hasJsonPathStringValue("@.lastUpdatedBy")
                .hasJsonPathStringValue("@.lastUpdatedDateAndTime");
        //Deserialization test
        ObjectContent<BasicDetails> actualObject = basicDetailsJacksonTester.parse(jsonTestUtils.getJson("basicDetails.json"));
        BasicDetails actualBasicDetails = actualObject.getObject();
        assertThat(actualBasicDetails).isEqualTo(basicDetails);
    }
}
