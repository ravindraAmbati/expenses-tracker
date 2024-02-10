package learn.myapps.expensestracker.api.user;

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
class UserDetailsJsonTest {

    @Autowired
    private JacksonTester<UserDetails> userDetailsJacksonTester;
    private final JsonTestUtils<UserDetails> jsonTestUtils = new JsonTestUtils<>(UserDetails.class);

    @Test
    void userDetailsJsonSerializeTest() throws IOException {
        //initialization
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(12345L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
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
        JsonContent<UserDetails> actual = userDetailsJacksonTester.write(userDetails);
        assertThat(actual)
                .isEqualToJson("userDetails.json")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.firstName")
                .hasJsonPathStringValue("@.lastName")
                .hasJsonPathStringValue("@.emailId")
                .hasJsonPathStringValue("@.mobileNo")
                .hasJsonPathValue("@.basicDetails")
                .hasJsonPathNumberValue("@.basicDetails.basicId")
                .hasJsonPathStringValue("@.basicDetails.description")
                .hasJsonPathBooleanValue("@.basicDetails.isDeleted")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedBy")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedDateAndTime");

        //Deserialization test
        ObjectContent<UserDetails> actualObject = userDetailsJacksonTester.parse(jsonTestUtils.getJson("userDetails.json"));
        UserDetails actualUserDetails = actualObject.getObject();
        assertThat(actualUserDetails).isEqualTo(userDetails);
    }
}