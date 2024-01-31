package learn.myapps.expensestracker.api.user;

import learn.myapps.expensestracker.JsonTestUtils;
import learn.myapps.expensestracker.api.user.UserDetails;
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
        UserDetails userDetails =
                UserDetails
                        .builder()
                        .basicId(12345L)
                        .id(12345L)
                        .firstName("Ravindra")
                        .lastName("Ambati")
                        .emailId("ravindra.reddy.ambati@outlook.in")
                        .mobileNo("+919849547160")
                        .isDeleted(false)
                        .lastUpdatedBy("Ravindra")
                        .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-12T20:00:00"))
                        .build();
        //Serialization test
        JsonContent<UserDetails> actual = userDetailsJacksonTester.write(userDetails);
        assertThat(actual)
                .isEqualToJson("userDetails.json")
                .hasJsonPathNumberValue("@.basicId")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.firstName")
                .hasJsonPathStringValue("@.lastName")
                .hasJsonPathStringValue("@.emailId")
                .hasJsonPathStringValue("@.mobileNo")
                .hasJsonPathBooleanValue("@.isDeleted")
                .hasJsonPathStringValue("@.lastUpdatedBy")
                .hasJsonPathStringValue("@.lastUpdatedDateAndTime");
        //Deserialization test
        ObjectContent<UserDetails> actualObject = userDetailsJacksonTester.parse(jsonTestUtils.getJson("userDetails.json"));
        UserDetails actualUserDetails = actualObject.getObject();
        assertThat(actualUserDetails).isEqualTo(userDetails);
    }
}