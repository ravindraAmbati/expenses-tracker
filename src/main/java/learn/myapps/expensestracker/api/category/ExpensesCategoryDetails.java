package learn.myapps.expensestracker.api.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder(toBuilder = true)
public class ExpensesCategoryDetails extends BasicDetails {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("expensesCategory")
    private String expensesCategory;
    @JsonProperty("alias")
    private List<String> alias;
}
