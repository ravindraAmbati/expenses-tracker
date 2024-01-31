package learn.myapps.expensestracker.api.category;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ExpensesCategoryDetailsJsonTest {

    @Autowired
    private JacksonTester<ExpensesCategoryDetails> expensesCategoryDetailsJacksonTester;
    private final JsonTestUtils<ExpensesCategoryDetails> jsonTestUtils = new JsonTestUtils<>(ExpensesCategoryDetails.class);

    @Test
    void expensesCategoryDetailsJsonTest() throws IOException {
        //initialization
        BasicDetails basicDetails = BasicDetails.builder()
                .basicId(12345L)
                .description("test")
                .isDeleted(false)
                .lastUpdatedBy("Ravindra")
                .lastUpdatedDateAndTime(LocalDateTime.parse("2024-01-13T20:00:00"))
                .build();
        ExpensesCategoryDetails expensesCategoryDetails =
                ExpensesCategoryDetails
                        .builder()
                        .id(12345L)
                        .expensesCategory("EDUCATION")
                        .alias(List.of("school fee", "tuition fee", "online courses", "udemy", "certification"))
                        .basicDetails(basicDetails)
                        .build();
        //Serialization test
        JsonContent<ExpensesCategoryDetails> actual = expensesCategoryDetailsJacksonTester.write(expensesCategoryDetails);
        assertThat(actual)
                .isEqualToJson("expensesCategoryDetails.json")
                .hasJsonPathNumberValue("@.id")
                .hasJsonPathStringValue("@.expensesCategory")
                .hasJsonPathArrayValue("@.alias")
                .hasJsonPathValue("@.basicDetails")
                .hasJsonPathNumberValue("@.basicDetails.basicId")
                .hasJsonPathStringValue("@.basicDetails.description")
                .hasJsonPathBooleanValue("@.basicDetails.isDeleted")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedBy")
                .hasJsonPathStringValue("@.basicDetails.lastUpdatedDateAndTime");
        //Deserialization test
        ObjectContent<ExpensesCategoryDetails> actualObject = expensesCategoryDetailsJacksonTester.parse(jsonTestUtils.getJson("expensesCategoryDetails.json"));
        ExpensesCategoryDetails actualExpensesCategory = actualObject.getObject();
        assertThat(actualExpensesCategory).isEqualTo(expensesCategoryDetails);
    }
}